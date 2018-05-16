package fjsp.probleme;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Instance pb = new Instance("./Monaldo/Fjsp/Job_Data/Barnes/mt10c1.fjs");

        Solveur resolutionneur = new Solveur(pb);

        Solution solution_initiale = resolutionneur.solutionInitiale();
        solution_initiale.generationGraphe();

        System.out.println("Coût max: " + solution_initiale.graphe.coutMax());
        solution_initiale.graphe.afficherDot();

        //solution_initiale.afficherGantt(null);
        solution_initiale.exportGantt();
    }
}
