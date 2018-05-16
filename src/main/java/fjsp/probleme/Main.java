package fjsp.probleme;

import fjsp.graphe.ErreurGrapheCyclique;

import java.io.File;

public class Main {
    public static void main(String[] args) throws ErreurGrapheCyclique, ErreurSolutionNonAdmissible {
        Instance pb = new Instance(args[0]);

        Solveur resolutionneur = new Solveur(pb);

        Solution solution_initiale = resolutionneur.solutionInitiale();
        solution_initiale.generationGraphe();

        System.out.println("Coût max: " + solution_initiale.graphe.coutMax());
        solution_initiale.graphe.afficherDot();

        solution_initiale.exportGantt();
    }
}
