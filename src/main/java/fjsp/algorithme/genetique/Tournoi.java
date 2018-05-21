package fjsp.algorithme.genetique;

import fjsp.graphe.ErreurGrapheCyclique;
import fjsp.probleme.Solution;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Tournoi {

    private ArrayList<Solution> participants;
    private HashMap<Solution, Integer> couts;

    public Tournoi() {
        this.participants = new ArrayList<Solution>();
        this.couts = new HashMap<Solution, Integer>();
    }

    public Solution tirerGagnant()
    {
        int s1 = ThreadLocalRandom.current().nextInt(0, this.participants.size()), s2;

        do {
            s2 = ThreadLocalRandom.current().nextInt(0, this.participants.size());
        } while(s2 == s1);

        Solution s;
        if(this.couts.get(this.participants.get(s1)) < this.couts.get(this.participants.get(s2)))
        {
            s = this.participants.get(s1);
            this.participants.remove(s1);
        }
        else
        {
            s = this.participants.get(s2);
            this.participants.remove(s2);
        }

        return s;
    }

    public void enregistrerParticipant(Solution s)
    {
        try {
            if(!s.graphe_initialise)
                s.generationGraphe();

            int cout = s.graphe.coutMax();
            this.participants.add(s);
            this.couts.put(s, cout);

        } catch(ErreurGrapheCyclique err) {
            System.out.println("Participant " + s + " non enregistrable.");
        }
    }
}
