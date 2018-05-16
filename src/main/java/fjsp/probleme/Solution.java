package fjsp.probleme;

import fjsp.graphe.Arc;
import fjsp.graphe.Noeud;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Solution {

    public Instance probleme;

    public HashMap<Job, HashMap<Tache, Ressource>> machineAssignment;
    public ArrayList<Tache> operationSequence;

    public Noeud graphe;
    public HashMap<Tache, Noeud> liste_noeuds; // liste des noeuds par tâche, évite d'avoir à traverser le graphe

    public Boolean graphe_initialise;

    public Solution(Instance pb)
    {
        this.machineAssignment = new HashMap<Job, HashMap<Tache, Ressource>>();
        this.operationSequence = new ArrayList<Tache>();
        this.probleme = pb;
        this.graphe_initialise = false;
    }

    public Machine affectationTache(Tache t)
    {
        return this.machineAssignment.get(t.parent).get(t).m;
    }

    public ArrayList<Tache> machineSequence(Machine m)
    {
        ArrayList<Tache> seq = new ArrayList<Tache>();

        for(Tache t: this.operationSequence)
        {
            if(affectationTache(t).equals(m))
                seq.add(t);
        }

        return seq;
    }

    public int coutAffectation(Tache t)
    {
        return this.machineAssignment.get(t.parent).get(t).temps;
    }

    void generationGraphe()
    {
        // Scheduling is done: now we generate the solution graph

        // We iterate over the first tasks for each machine
        // Task ordering constraints + Graph generation
        HashMap<Job, Noeud> noeuds_initiaux = new HashMap<Job, Noeud>();
        HashMap<Job, Noeud> noeuds_terminaux = new HashMap<Job, Noeud>();

        // Raccourcis entre une tâche et son noeud dans le graphe
        HashMap<Tache, Noeud> raccourcis_graphe = new HashMap<Tache, Noeud>();

        // Task order for each job
        // Those constraints NEVER change
        for(Tache t: this.operationSequence)
        {
            Noeud n = new Noeud(t);
            raccourcis_graphe.put(t, n);

            if(!noeuds_initiaux.containsKey(t.parent))
            {
                // This is the first task of this job, hence a starting node
                noeuds_initiaux.put(t.parent, n);
            }
            else
            {
                // This wasn't the first task of this job, so we constrain it with previous terminal node
                n.contraindre(noeuds_terminaux.get(t.parent), this.coutAffectation(noeuds_terminaux.get(t.parent).tache));
            }
            // Current node replaces previous terminal node
            noeuds_terminaux.put(t.parent, n);
        }

        // Machine ordering
        for(Machine m: this.probleme.machines)
        {
            ArrayList<Tache> ord_seq = this.machineSequence(m);
            Collections.reverse(ord_seq);

            Noeud suiv = null;
            for(Tache t: ord_seq)
            {
                Noeud n = raccourcis_graphe.get(t);

                if(suiv != null)
                {
                    suiv.contraindre(n, this.coutAffectation(t));
                }

                suiv = n;
            }
        }

        // Création des noeuds de début et de fin
        Noeud noeud_initial = new Noeud(new Tache());
        Noeud noeud_terminal = new Noeud(new Tache());

        for(Job j: noeuds_initiaux.keySet())
            noeuds_initiaux.get(j).contraindre(noeud_initial, 0);

        for(Job j: noeuds_terminaux.keySet())
            noeud_terminal.contraindre(noeuds_terminaux.get(j), this.coutAffectation(noeuds_terminaux.get(j).tache));

        this.liste_noeuds = raccourcis_graphe;
        this.graphe = noeud_terminal;

        graphe_initialise = true;
    }

    public void exportGantt()
    {
        for(Machine m: this.probleme.machines)
        {
            System.out.println("\\\\");
            System.out.println("\\ganttgroup{M" + m.id + "}{0}{" + this.graphe.coutMax() + "}");
            for(Tache t: machineSequence(m))
            {
                if(!t.vide)
                {
                    Noeud n = liste_noeuds.get(t);

                    int date_debut = n.coutMax();
                    int date_fin = n.coutMax() + this.coutAffectation(t) - 1;
                    System.out.println("\\ganttbar{" + t.id + "}{" + date_debut + "}{" + date_fin + "}");
                }

            }
        }
    }
}
