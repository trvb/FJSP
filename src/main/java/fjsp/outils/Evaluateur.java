package fjsp.outils;

import fjsp.algorithme.Algorithme;
import fjsp.graphe.ErreurGrapheCyclique;
import fjsp.probleme.Solution;

public class Evaluateur {

    Algorithme algo;

    public Evaluateur(Algorithme algo)
    {
        this.algo = algo;
    }

    public void demarrer()
    {
        long startTime = System.currentTimeMillis();
        Solution s = this.algo.resoudre();
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        this.algo.afficherStatistiques();
        try {
            System.out.println("Coût max: " + s.graphe.coutMax());
        } catch (ErreurGrapheCyclique erreurGrapheCyclique) {
            erreurGrapheCyclique.printStackTrace();
        }

        System.out.println("Résolution exécutée en " + elapsedTime + " ms. (" + elapsedTime/1000 + " s.)");
    }
}
