package fjsp.probleme;

import fjsp.graphe.ErreurGrapheCyclique;
import fjsp.algorithme.Glouton;

import java.io.File;

public class Main {
    public static void main(String[] args) throws ErreurGrapheCyclique, ErreurSolutionNonAdmissible {
        Instance pb = new Instance(args[0]);

        Solveur resolutionneur = new Solveur(pb);

        Solution solution_initiale = resolutionneur.solutionInitiale();
        solution_initiale.generationGraphe();

        System.out.println("Test solution initiale");
        System.out.println("Coût max: " + solution_initiale.graphe.coutMax());
        //solution_initiale.graphe.afficherDot();
        //solution_initiale.exportGantt();

        System.out.println("Test algorithme glouton");
        Glouton solveur_glouton = new Glouton(pb);

        Solution solution_gloutonne = solveur_glouton.resoudre(100000);
        System.out.println("Coût max: " + solution_gloutonne.graphe.coutMax());
        //solution_gloutonne.graphe.afficherDot();
        //solution_gloutonne.exportGantt();
    }
}
