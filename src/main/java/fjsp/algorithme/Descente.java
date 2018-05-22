package fjsp.algorithme;

import fjsp.graphe.ErreurGrapheCyclique;
import fjsp.probleme.Instance;
import fjsp.probleme.Solution;
import fjsp.probleme.Solveur;
import fjsp.probleme.Configuration;

public class Descente extends Algorithme {

    public Descente(Instance pb, int limite, boolean sortieConsole) {
	    super(pb, limite, sortieConsole);
	}

    @Override
	public Solution resoudre()
    {
        Solveur resolutionneur = new Solveur(this.probleme);
        Solution courante = resolutionneur.solutionInitiale(Configuration.SOLVER_SHUFFLING, true), evaluee;
        courante.generationGraphe();

        return this.resoudre(courante);
    }

    public Solution resoudre(Solution depart) {
        Solution courante = depart, evaluee;
        int meilleurCoutMax = Integer.MAX_VALUE, coutCourant;

        this.derniere = 0;
        this.nb_maj = 0;
        this.generees = 0;
        this.explorees = 0;

        boolean stop = false;

        try {
            meilleurCoutMax = courante.graphe.coutMax();
        } catch (ErreurGrapheCyclique err) {
            // Ce problème est naze
            err.printStackTrace();
        }

        while(this.explorees < this.limite && !stop)
        {
            coutCourant = Integer.MAX_VALUE;
            do {
                this.generees++;
                evaluee = courante.meilleurVoisin(Configuration.ALGO_NEIGHBOURS_COUNT);
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
            stop = this.explorees - this.derniere > Configuration.ALGO_EXTREMUM_THRESHOLD;
        }

        return courante;
    }
}
