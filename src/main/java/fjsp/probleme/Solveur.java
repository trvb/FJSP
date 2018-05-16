package fjsp.probleme;

import fjsp.graphe.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.ListIterator;

public class Solveur {

    public ArrayList<Machine> machines;
    public ArrayList<Job> jobs;

    public Solveur(Instance pb)
    {
        this.jobs = pb.jobs;
        this.machines = pb.machines;
    }

    // Generation of an initial "naive" solution for the FJSP from which to work with
    Solution solutionInitiale() {
        Solution s = new Solution(machines, jobs);

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
}
