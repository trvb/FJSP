package fjsp.probleme;

import fjsp.graphe.Noeud;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bonjour.");

        // Définition de notre problème de planification
        Machine m1 = new Machine(1);
        Machine m2 = new Machine(2);
        Machine m3 = new Machine(3);

        Tache t11 = new Tache(11);
        t11.ajouterRessource(m1, 3);

        Tache t21 = new Tache(21);
        t21.ajouterRessource(m2, 2);

        Tache t31 = new Tache(31);
        t31.ajouterRessource(m3, 5);

        Tache t12 = new Tache(12);
        t12.ajouterRessource(m2, 4);

        Tache t22 = new Tache(22);
        t22.ajouterRessource(m1, 2);

        Tache t32 = new Tache(32);
        t32.ajouterRessource(m3, 2);

        Tache t13 = new Tache(13);
        t13.ajouterRessource(m3, 2);

        Tache t23 = new Tache(23);
        t23.ajouterRessource(m2, 3);

        Job j1 = new Job();
        j1.ajouterTache(t11);
        j1.ajouterTache(t21);
        j1.ajouterTache(t31);

        Job j2 = new Job();
        j2.ajouterTache(t12);
        j2.ajouterTache(t22);
        j2.ajouterTache(t32);

        Job j3 = new Job();
        j3.ajouterTache(t13);
        j3.ajouterTache(t23);

        Job[] jobs = {j1,j2,j3};
        Machine[] machines = {m1, m2, m3};

        Solveur resolutionneur = new Solveur(jobs, machines);

        Noeud graphe_solution = resolutionneur.generationGraphe(resolutionneur.solutionInitiale());

        System.out.println(graphe_solution.coutMax());
    }

}
