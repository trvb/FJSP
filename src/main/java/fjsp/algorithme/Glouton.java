package fjsp.algorithme;

import fjsp.graphe.ErreurGrapheCyclique;
import fjsp.probleme.Instance;
import fjsp.probleme.Solution;
import fjsp.probleme.Solveur;

import java.io.IOException;

public class Glouton extends Algorithme {

	public Glouton(Instance pb) {
	    super(pb);
	    this.explorees = 0;
	    this.derniere = 0;
	}

	@Override
    public Solution resoudre(int limite) {
        Solveur resolutionneur = new Solveur(this.probleme);
        Solution courante = resolutionneur.solutionInitiale(), evaluee;
        courante.generationGraphe();
        int meilleurCoutMax = Integer.MAX_VALUE, coutCourant;
        int generees = 0, derniere_iteration = 0;

        try {
            meilleurCoutMax = courante.graphe.coutMax();
        } catch (ErreurGrapheCyclique err) {
            // Ce problème est naze
            err.printStackTrace();
        }

        while(this.explorees < limite)
        {
            coutCourant = Integer.MAX_VALUE;
            do {
                generees++;
                evaluee = courante.voisinAleatoire();
                evaluee.generationGraphe();
            } while(!evaluee.estAdmissible());

            int tmp = this.explorees+1;
            String data = "\r" + "Calcul " + tmp + "/" + limite;
            try {
                System.out.write(data.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

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
                derniere_iteration = this.explorees;
                this.derniere++;
            }

            this.explorees++;
        }

        float rejets = (float)(generees - this.explorees) / (float)(generees) * 100f;

        System.out.println("\nStatistiques solutions");
        System.out.println("\texplorées: " + this.explorees);
        System.out.println("\tgénérées: " + generees + " - " + rejets + "% rejets");
        System.out.println("\tnombre de mises à jour: " + this.derniere);
        System.out.println("\tdernière itération de maj: " + derniere_iteration);

        return courante;
    }
}
