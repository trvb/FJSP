package fjsp.probleme;

import fjsp.algorithme.DescenteMarcheAleatoire;
import fjsp.graphe.ErreurGrapheCyclique;
import fjsp.algorithme.Descente;
import fjsp.algorithme.genetique.*;
import fjsp.outils.Evaluateur;

public class Main {
    public static void main(String[] args) throws ErreurGrapheCyclique, ErreurSolutionNonAdmissible
    {
        System.out.println("Chargement de " + args[0]);
        Instance pb = new Instance(args[0]);

        //System.out.println("Test solution initiale");
        //System.out.println("Coût max: " + solution_initiale.graphe.coutMax());
        //solution_initiale.graphe.afficherDot();
        //solution_initiale.exportGantt();

        System.out.println("\nTest algorithme descente");
        Descente solveur_descente = new Descente(pb, Configuration.ALGO_LIMIT, true);
        Evaluateur evalD = new Evaluateur(solveur_descente);
        evalD.demarrer();

        System.out.println("\nTest algorithme descente avec marche aléatoire");
        DescenteMarcheAleatoire solveur_descenteAlea = new DescenteMarcheAleatoire(pb, Configuration.ALGO_LIMIT * 10, true);
        Evaluateur evalMA = new Evaluateur(solveur_descenteAlea);
        evalMA.demarrer();

        System.out.println("\nTest algorithme évolution");
        Evolution solveur_evolution = new Evolution(pb, 10, Configuration.ALGO_LIMIT);
        Evaluateur evalEvo = new Evaluateur(solveur_evolution);
        evalEvo.demarrer();
    }
}
