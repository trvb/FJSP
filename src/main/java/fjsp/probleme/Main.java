package fjsp.probleme;

import fjsp.graphe.Noeud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bonjour.");

        //Partie lecture de fichier

        File file = new File("C:\\Users\\Tangi\\Desktop\\Monaldo\\test.jfs");
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line = null;
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] ligneJob = line.split(" ");
            // A la première ligne on récupère les données liées au pb (nombre de job/ nombre de machine  nombre de tâche
            //Initialisation des Jobs
            int nb_job = Integer.parseInt(ligneJob[0]);
            Job[] jobs = new Job[nb_job];
            //Initialisation des machines
            int nb_machine = Integer.parseInt(ligneJob[1]);
            Machine[] machines = creaMachine(nb_machine);


            // Lecture du fichier
            int i = 0;
            for (i=0 ; i<nb_job ; i++) {
                try {
                    line = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Creation du i eme Job
                jobs[i]= creaJob(line, i, machines);


            }

        Solveur resolutionneur = new Solveur(jobs, machines);

        Solution solution_initiale = resolutionneur.solutionInitiale();
        solution_initiale.generationGraphe();

        System.out.println(solution_initiale.graphe.coutMax());
        solution_initiale.graphe.afficherDot();

        //solution_initiale.afficherGantt(null);
        solution_initiale.exportGantt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Machine[] creaMachine(int nbMachine) {
        if (nbMachine >= 0)
        {
            Machine machines[] = new Machine[nbMachine];
            for (int i = 0; i < nbMachine; i++)
                machines[i] = new Machine(i);
            return machines;
        }
        else
            return null;
    }

    public static Job creaJob(String ligne, int nbjob, Machine[] mach)
    {
        Job newJob = new Job();
        System.out.println(ligne);

        String[] currentJob = ligne.split(" ");
        //Lecture ligne par ligne

        int i=0;
        for (int j = 1; j < currentJob.length; j=j+3) // Ne Fonctionne que si l'on a un ordinateur pour une ressource à chaque fois
        {

            //Initialisation des variables lié à UNE tâche
            int numMachinePossible = Integer.parseInt(currentJob[j+1]);
            int nbTempsTache = Integer.parseInt(currentJob[j+2]);

            Tache t = new Tache((100*(nbjob+1) + i));
            t.ajouterRessource(mach[numMachinePossible-1], nbTempsTache);

            newJob.ajouterTache(t);
            i++;
        }


        return newJob;
    }
}
