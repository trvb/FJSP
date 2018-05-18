package fjsp.algorithme;

import fjsp.graphe.ErreurGrapheCyclique;
import fjsp.probleme.Instance;
import fjsp.probleme.Solution;
import fjsp.probleme.Solveur;

import java.io.IOException;

public class Glouton extends Algorithme {

	public Glouton(Instance pb, int limite) {
	    super(pb, limite);
	}

	@Override
    public Solution resoudre() {
        Solveur resolutionneur = new Solveur(this.probleme);
        Solution courante = resolutionneur.solutionInitiale(), evaluee;
        courante.generationGraphe();
        int meilleurCoutMax = Integer.MAX_VALUE, coutCourant;

        this.derniere = 0;
        this.nb_maj = 0;
        this.generees = 0;
        this.explorees = 0;

        try {
            meilleurCoutMax = courante.graphe.coutMax();
        } catch (ErreurGrapheCyclique err) {
            // Ce problème est naze
            err.printStackTrace();
        }

        while(this.explorees < this.limite)
        {
            coutCourant = Integer.MAX_VALUE;
            do {
                this.generees++;
                evaluee = courante.voisinAleatoire();
                evaluee.generationGraphe();
            } while(!evaluee.estAdmissible());

            this.afficherProgression();

            try {
                coutCourant = evaluee.graphe.coutMax();
            } catch (ErreurGrapheCyclique err) {
                // Cela ne devrait jamais arriver
                err.printStackTrace();
            }

            if(coutCourant < meilleurCoutMax)
            {
                meilleurCoutMax = coutCourant;
                courante = evaluee;
                this.derniere = this.explorees;
                this.nb_maj++;
            }

            this.explorees++;
        }

        return courante;
    }
}
