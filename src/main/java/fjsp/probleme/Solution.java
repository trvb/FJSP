package fjsp.probleme;

import fjsp.graphe.Arc;
import fjsp.graphe.ErreurGrapheCyclique;
import fjsp.graphe.Noeud;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Solution {

    public Instance probleme;

    public HashMap<Job, HashMap<Tache, Ressource>> machineAssignment;
    public ArrayList<Tache> operationSequence;

    public Noeud graphe;
    public HashMap<Tache, Noeud> liste_noeuds; // liste des noeuds par tâche, évite d'avoir à traverser le graphe

    public Boolean graphe_initialise;

    public Solution(Instance pb)
    {
        this.machineAssignment = new HashMap<>();
        this.operationSequence = new ArrayList<>();
        this.probleme = pb;
        this.graphe_initialise = false;
    }

    public Solution(Instance pb, HashMap<Job, HashMap<Tache, Ressource>>ma, ArrayList<Tache> os)
    {
        this.machineAssignment = ma;
        this.operationSequence = os;
        this.probleme = pb;
        this.graphe_initialise = false;
    }

    public Solution voisinAleatoire()
    {
        HashMap<Job, HashMap<Tache, Ressource>> new_MA = this.machineAssignment;
        ArrayList<Tache> new_OS = this.operationSequence;

        int direction = ThreadLocalRandom.current().nextInt(0, 2);

        // On modifie aléatoirement l'affectation tache/machine, si au moins une tache est flexible
        if(direction == 0 && this.probleme.jobsFlexibles() > 0)
        {
            Tache t = this.probleme.tacheFlexibleAleatoire();

            int ressource = ThreadLocalRandom.current().nextInt(0, t.ressources.size());

            // Copie du machineAssignment original au lieu de référencement
            new_MA = new HashMap<>(this.machineAssignment);
            for(Job j: new_MA.keySet())
            {
                // Copie des affectations par tache au lieu de référencement
                new_MA.put(j, new HashMap<Tache, Ressource>(new_MA.get(j)));

                // Modification de l'affectation choisie aléatoirement
                if(j == t.parent)
                    new_MA.get(j).put(t, t.ressources.get(ressource));
            }
        }
        // Sinon on modifie la séquence de taches
        else
        {
            // On sélectionne deux tâches à échanger de place
            int t1 = ThreadLocalRandom.current().nextInt(0, this.operationSequence.size()), t2;
            do {
                t2= ThreadLocalRandom.current().nextInt(0, this.operationSequence.size());
            } while(t1 == t2);

            // Copie de l'operationSequence original au lieu de référencement
            new_OS = new ArrayList<>(this.operationSequence);

            Collections.swap(new_OS, t1, t2);
        }

        return new Solution(this.probleme, new_MA, new_OS);
    }

    public boolean estAdmissible()
    {
        if(!this.graphe_initialise)
            return false;

        try {
            this.graphe.coutMax();
        } catch (ErreurGrapheCyclique err) {
            return false;
        }

        return true;
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

    public void generationGraphe()
    {
        // Scheduling is done: now we generate the solution graph

        // We iterate over the first tasks for each machine
        // Task ordering constraints + Graph generation
        HashMap<Job, Noeud> noeuds_initiaux = new HashMap<Job, Noeud>();
        HashMap<Job, Noeud> noeuds_terminaux = new HashMap<Job, Noeud>();

        // Raccourcis entre une tâche et son noeud dans le graphe
        HashMap<Tache, Noeud> raccourcis_graphe = new HashMap<Tache, Noeud>();

        // Ordre des tâches pour les jobs
        // Ces contraintes ne changent jamais
        for(Job j: this.probleme.jobs)
        {
            for(Tache t: j.taches)
            {
                Noeud n = new Noeud(t);
                raccourcis_graphe.put(t, n);

                if (!noeuds_initiaux.containsKey(j)) {
                    // C'est la première tâche du job, donc une tâche initiale
                    noeuds_initiaux.put(j, n);
                }
                else
                {
                    // Sinon, on la contraint avec la tâche terminale précédente
                    n.contraindre(noeuds_terminaux.get(j), this.coutAffectation(noeuds_terminaux.get(j).tache));
                }
                // Enfin, la tâche devient la nouvelle terminale
                noeuds_terminaux.put(j, n);
            }
        }

        // Ordre des tâches dans les machines
        for(Machine m: this.probleme.machines)
        {
            // On sélectionne les tâches pour la machine m, par ordre d'apparition dans la séquence d'ordre
            ArrayList<Tache> ord_seq = this.machineSequence(m);

            // On inverse cette séquence
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

    public void exportGantt() throws ErreurSolutionNonAdmissible {
        int cout_max_graphe;
        try {
            cout_max_graphe = this.graphe.coutMax();
        } catch (ErreurGrapheCyclique err) {
            throw new ErreurSolutionNonAdmissible("Impossible d'exporter une solution non admissible.");
        }

        for(Machine m: this.probleme.machines)
        {
            System.out.println("\\\\");
            System.out.println("\\ganttgroup{M" + m.id + "}{0}{" + cout_max_graphe + "}");
            for(Tache t: machineSequence(m))
            {
                if(!t.vide)
                {
                    Noeud n = liste_noeuds.get(t);
                    int cmax;

                    try {
                        cmax = n.coutMax();
                    } catch (ErreurGrapheCyclique err) { // Ce cas ne devrait jamais se produire
                        throw new ErreurSolutionNonAdmissible("Impossible d'exporter une solution non admissible.");
                    }

                    int date_debut = cmax;
                    int date_fin = cmax + this.coutAffectation(t) - 1;
                    System.out.println("\\ganttbar{" + t.id + "}{" + date_debut + "}{" + date_fin + "}");
                }

            }
        }
    }
}
