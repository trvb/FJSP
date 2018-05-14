package fjsp.probleme;

import fjsp.graphe.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.ListIterator;

public class Solveur {

    public Job jobs[];
    public Machine machines[];

    public Solveur(Job jobs[], Machine machines[])
    {
        this.jobs = jobs;
        this.machines = machines;
    }

    // Generation of an initial "naive" solution for the FJSP from which to work with
    Solution solutionInitiale() {
        Solution s = new Solution();

        // Operation sequence: tasks as they come
        for (Job j : jobs)
            s.operationSequence.addAll(j.taches);

        // Machine assignment: first machine available
        // Initialization
        for (Job j: jobs)
            s.machineAssignment.put(j, new HashMap<Tache, Ressource>());

        for (Job j : jobs) {
            for (Tache t : j.taches) {
                Ressource r = t.ressources.get(0); // We select the first possible machine for this task
                // TODO: maybe switch to the most efficient machine first (min. cost) ?

                s.machineAssignment.get(j).put(t, r); // And we add it to this machine's task list
                // Node: the order of elements in the HashMap defines the way each Machine is gonna handle the internal
                // constraint order conflict
            }
        }

        return s;
    }

    Noeud generationGraphe(Solution sol)
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
        for(Tache t: sol.operationSequence)
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
                n.contraindre(noeuds_terminaux.get(t.parent), sol.machineAssignment.get(t.parent).get(t).temps);
            }
            // Current node replaces previous terminal node
            noeuds_terminaux.put(t.parent, n);
        }

        // Machine ordering
        for(Machine m: machines)
        {
            ArrayList<Tache> ord_seq = sol.machineSequence(m);
            Collections.reverse(ord_seq);

            Noeud suiv = null;
            for(Tache t: ord_seq)
            {
                Noeud n = raccourcis_graphe.get(t);

                if(suiv != null)
                {
                    suiv.contraindre(n, sol.coutAffectation(t));
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
            noeud_terminal.contraindre(noeuds_terminaux.get(j), 0);

        return noeud_terminal;
    }
}
