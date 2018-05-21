package fjsp.probleme;

import fjsp.graphe.ErreurGrapheCyclique;
import fjsp.algorithme.Glouton;
import fjsp.algorithme.genetique.Evolution;

import java.io.File;

public class Main {
    public static void main(String[] args) throws ErreurGrapheCyclique, ErreurSolutionNonAdmissible {

        System.out.println("Chargement de " + args[0]);
        Instance pb = new Instance(args[0]);

        //Solveur resolutionneur = new Solveur(pb);

        //Solution solution_initiale = resolutionneur.solutionInitiale(Configuration.SOLVER_SHUFFLING);
        //solution_initiale.generationGraphe();

        //System.out.println("Test solution initiale");
        //System.out.println("Coût max: " + solution_initiale.graphe.coutMax());
        //solution_initiale.graphe.afficherDot();
        //solution_initiale.exportGantt();

        System.out.println("\nTest algorithme glouton");
        Glouton solveur_glouton = new Glouton(pb, Configuration.ALGO_LIMIT);

        Solution solution_gloutonne = solveur_glouton.resoudre();
        solveur_glouton.afficherStatistiques();
        System.out.println("Coût max: " + solution_gloutonne.graphe.coutMax());
        //solution_gloutonne.graphe.afficherDot();
        //solution_gloutonne.exportGantt();

        System.out.println("\nTest algorithme évolution");
        Evolution solveur_evolution = new Evolution(pb, 20, Configuration.ALGO_LIMIT);
        Solution solution_evolution = solveur_evolution.resoudre();
        solveur_evolution.afficherStatistiques();

    }
}
