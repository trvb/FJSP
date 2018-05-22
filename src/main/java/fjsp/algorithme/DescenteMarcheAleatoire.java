package fjsp.algorithme;

import fjsp.graphe.ErreurGrapheCyclique;
import fjsp.probleme.Configuration;
import fjsp.probleme.Instance;
import fjsp.probleme.Solution;
import fjsp.probleme.Solveur;

public class DescenteMarcheAleatoire extends Algorithme {

    public DescenteMarcheAleatoire(Instance pb, int limite, boolean sortieConsole) {
        super(pb, limite, sortieConsole);
    }

    @Override
    public Solution resoudre() {
        Solveur resolutionneur = new Solveur(this.probleme);
        Solution courante = resolutionneur.solutionInitiale(Configuration.SOLVER_SHUFFLING, true), meilleur = courante;
        courante.generationGraphe();
        int coutCourant, meilleurCoutMax = Integer.MAX_VALUE;
        Solveur solveur = new Solveur(this.probleme);

        do {
            Descente solveur_descente = new Descente(this.probleme, this.limite / 1000, false);
            courante = solveur_descente.resoudre(courante);

            try {
                coutCourant = courante.graphe.coutMax();

                if(coutCourant < meilleurCoutMax)
                {
                    meilleurCoutMax = coutCourant;
                    meilleur = courante;
                    this.derniere = this.explorees;
                    this.nb_maj++;
                }
            } catch (ErreurGrapheCyclique err) {
                // Cela ne devrait jamais arriver
                err.printStackTrace();
            }
            courante = solveur.marcheAléatoire(courante, Configuration.SOLVER_SHUFFLING / 100, false);
            afficherProgression();

            this.explorees += solveur_descente.explorees;
            this.generees += solveur_descente.generees;
        } while(this.explorees < this.limite);

        return meilleur;
    }
}
