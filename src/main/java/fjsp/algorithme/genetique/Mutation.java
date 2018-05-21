package fjsp.algorithme.genetique;

import fjsp.probleme.Configuration;
import fjsp.probleme.Solution;

import java.util.concurrent.ThreadLocalRandom;

public class Mutation {

    public Solution mutee;

    public Mutation(Solution s) {
        this.mutee = s;

        muter();
    }

    private void muter() {
        int le_miracle_de_la_vie = ThreadLocalRandom.current().nextInt(0, 100);

        if(le_miracle_de_la_vie > Configuration.MUTATION_RATE)
            this.mutee = mutee.voisinAleatoire();

        if(!this.mutee.graphe_initialise)
            this.mutee.generationGraphe();
    }
}
