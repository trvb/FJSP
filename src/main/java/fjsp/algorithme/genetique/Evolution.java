package fjsp.algorithme.genetique;

import fjsp.algorithme.Algorithme;
import fjsp.graphe.ErreurGrapheCyclique;
import fjsp.probleme.Configuration;
import fjsp.probleme.Instance;
import fjsp.probleme.Solution;
import fjsp.probleme.Solveur;

import java.util.ArrayList;

public class Evolution extends Algorithme {

    private ArrayList<Solution> population;
    private int taille;

    public Evolution(Instance pb, int taille_population, int limite) {
        super(pb, limite);
        this.taille = taille_population;
        this.population = new ArrayList<>(taille);

        this.derniere = 0;
        this.nb_maj = 0;
        this.generees = 0;
        this.explorees = 0;

        Solveur resolutionneur = new Solveur(this.probleme);

        System.out.println("Initialisation de la population ...");

        for(int i=1; i<=this.taille; i++)
        {
            Solution individu = resolutionneur.solutionInitiale(Configuration.SOLVER_SHUFFLING / this.taille, false);

            if(!individu.graphe_initialise)
                individu.generationGraphe();

            this.population.add(individu);
        }
    }

    @Override
    public Solution resoudre() {

        Solution meilleureSolution = null;
        int meilleurCoutMax = Integer.MAX_VALUE, coutCourant;

        int generations = 0;
        do {
            ArrayList<Solution> nouvelle_population = new ArrayList<>(this.taille);
            Tournoi selection = new Tournoi();

            // Etape 1: reproduction
            for(int i=0; i<this.taille; i += 2)
            {
                try {
                    Famille f = new Famille(this.population.get(i), this.population.get(i+1));

                    this.generees += 2;

                    // Etape 2: mutations
                    Mutation m1 = new Mutation(f.enfant1);
                    Mutation m2 = new Mutation(f.enfant2);

                    selection.enregistrerParticipant(f.parent1);
                    selection.enregistrerParticipant(f.parent2);
                    selection.enregistrerParticipant(m1.mutee);
                    selection.enregistrerParticipant(m2.mutee);

                } catch (ErreurCroisementImpossible erreurCroisementImpossible) {
                    erreurCroisementImpossible.printStackTrace();
                }
            }

            // Etape 3: selection
            for(int i=0; i<this.taille; i++)
            {
                Solution gagnant = selection.tirerGagnant();
                nouvelle_population.add(gagnant);

                try {
                    coutCourant = gagnant.graphe.coutMax();

                    if(coutCourant < meilleurCoutMax)
                    {
                        meilleurCoutMax = coutCourant;
                        meilleureSolution = gagnant;

                        this.nb_maj++;
                        this.derniere = this.explorees;
                    }
                } catch (ErreurGrapheCyclique erreurGrapheCyclique) {
                    erreurGrapheCyclique.printStackTrace();
                }
            }

            this.population = nouvelle_population;

            this.explorees++;
            afficherProgression();
        } while(this.explorees < this.limite);

        System.out.print("\r\nPopulation finale: ");
        for(Solution s: this.population)
        {
            try {
                System.out.print(", "+ s.graphe.coutMax());
            } catch (ErreurGrapheCyclique erreurGrapheCyclique) {
                erreurGrapheCyclique.printStackTrace();
            }
        }

        System.out.print("\r\nMeilleur: " + meilleurCoutMax);

        return meilleureSolution;
    }
}
