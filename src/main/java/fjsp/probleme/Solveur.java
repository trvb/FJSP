package fjsp.probleme;

import fjsp.graphe.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.ThreadLocalRandom;

public class Solveur {

    public Instance probleme;

    public Solveur(Instance pb)
    {
        this.probleme = pb;
    }

    // Generation of an initial "naive" solution for the FJSP from which to work with
    public Solution solutionInitiale() {
        Solution s = new Solution(this.probleme);

        // Operation sequence: tasks as they come
        for (Job j : this.probleme.jobs)
            s.operationSequence.addAll(j.taches);

        // Machine assignment: random available machine
        // Initialization
        for (Job j: this.probleme.jobs)
            s.machineAssignment.put(j, new HashMap<Tache, Ressource>());

        for (Job j : this.probleme.jobs) {
            for (Tache t : j.taches) {
                int machine_sel = ThreadLocalRandom.current().nextInt(0, t.ressources.size());
                Ressource r = t.ressources.get(machine_sel);
                s.machineAssignment.get(j).put(t, r); // And we add it to this machine's task list
            }
        }

        return s;
    }
}
