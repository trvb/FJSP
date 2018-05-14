package fjsp.probleme;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Solution {

    public HashMap<Job, HashMap<Tache, Ressource>> machineAssignment;
    public ArrayList<Tache> operationSequence;

    public Solution()
    {
        this.machineAssignment = new HashMap<Job, HashMap<Tache, Ressource>>();
        this.operationSequence = new ArrayList<Tache>();
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
}
