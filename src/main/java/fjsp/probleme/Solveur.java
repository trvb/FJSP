package fjsp.probleme;

import fjsp.graphe.*;
import java.util.HashMap;
import java.util.ArrayList;

public class Solveur {

    public Job jobs[];
    public Machine machines[];

    public Solveur(Job jobs[], Machine machines[])
    {
        this.jobs = jobs;
        this.machines = machines;
    }

    Noeud resoudre()
    {
        HashMap<Machine, ArrayList<Tache>> programme = new HashMap<Machine, ArrayList<Tache>>();

        for(Machine m: machines)
            programme.put(m, new ArrayList<Tache>());

        // We will generate an initial "naive" solution for the FJSP from which to work with
        // Basically: we schedule tasks for machines, and then we generate a solution graph
        for(Job j : jobs)
        {
            for(Tache t: j.taches)
            {
                Ressource r = t.ressources.get(0); // We select the first possible machine for this task

                programme.get(r.m).add(t); // And we add it to this machine's ordered task list
            }
        }

        // Scheduling is done: now we generate the solution graph

        // We iterate over the first tasks for each machine
        HashMap<Noeud, Noeud> noeuds_terminaux = new HashMap<Noeud, Noeud>();

        for(Machine m: programme.keySet())
        {
            Noeud initial = null;

            Noeud precedent = null;
            for(Tache t: programme.get(m))
            {
                Noeud n = new Noeud(t);

                if(precedent != null)
                {
                    n.contraindre(precedent, precedent.tache.ressources.get(0).temps);
                }

                if(initial == null)
                    initial = n;

                precedent = n;
            }

            noeuds_terminaux.put(initial, precedent);
        }

        Noeud noeud_init = new Noeud(null);
        Noeud noeud_fin = new Noeud(null);

        for(Noeud n: noeuds_terminaux.keySet())
        {
            n.contraindre(noeud_init, 0);
            noeud_fin.contraindre(noeuds_terminaux.get(n), 0);
        }

        return noeud_init;
    }
}
