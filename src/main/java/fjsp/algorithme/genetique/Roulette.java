package fjsp.algorithme.genetique;

import fjsp.graphe.ErreurGrapheCyclique;
import fjsp.probleme.Solution;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Roulette {

    private ArrayList<Solution> participants;
    private HashMap<Solution, Double> poids;

    private double total_poids = 0;

    public Roulette() {
        this.participants = new ArrayList<Solution>();
        this.poids = new HashMap<Solution, Double>();
    }

    public Solution tirerGagnant()
    {
        double choix = ThreadLocalRandom.current().nextDouble() * this.total_poids;

        for(Solution p: this.participants)
        {
            choix -= this.poids.get(p);
            if(choix < 0)
            {
                participants.remove(p);
                this.total_poids -= this.poids.get(p);

                return p;
            }
        }

        // Ne devrait jamais arriver
        return null;
    }

    public void enregistrerParticipant(Solution s)
    {
        try {
            if(!s.graphe_initialise)
                s.generationGraphe();

            double coutMax = (double) s.graphe.coutMax();
            double rang = (1.0 / coutMax) * 100000; // score inversement proportionnel au cout

            participants.add(s);
            this.total_poids += rang;
            this.poids.put(s, rang);

        } catch(ErreurGrapheCyclique err) {
            System.out.println("Participant " + s + " non enregistrable.");
        }
    }
}
