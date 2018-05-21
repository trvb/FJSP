package fjsp.probleme;

import fjsp.graphe.*;

import java.io.IOException;
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
    public Solution solutionInitiale(int shuffler, boolean sortieConsole) {
        Solution s = new Solution(this.probleme);

        // Operation sequence: tasks as they come
        // TODO: randomize job order
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

        try {
            s.generationGraphe();
            int previous_cost = s.graphe.coutMax();

            int shfl_count = 0;
            do {
                float progres = (float)(shfl_count+1) / (float)shuffler * 100f;

                if(sortieConsole) {
                    String data = "\r" + "Shuffling " + progres + "%";
                    try {
                        System.out.write(data.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Solution z = s.voisinAleatoire();
                z.generationGraphe();
                if(z.estAdmissible())
                {
                    shfl_count++;
                    s = z;
                }
            } while(shfl_count < shuffler);


            if(sortieConsole)
            {
                System.out.print("\r\n");
                System.out.println("\tCoût max: " + previous_cost + " -> " + s.graphe.coutMax());
            }

        } catch(ErreurGrapheCyclique err) {
            err.printStackTrace();
        }

        return s;
    }
}
