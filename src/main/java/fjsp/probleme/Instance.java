package fjsp.probleme;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Instance {
    ArrayList<Machine> machines;
    ArrayList<Job> jobs;

    private int nbTaches = -1;
    public int nbTaches()
    {
        if(this.nbTaches >= 0)
            return this.nbTaches;

        int cpt = 0;
        for(Job j: this.jobs)
            cpt += j.taches.size();

        this.nbTaches = cpt;
        return cpt;
    }

    // TODO: gérer plus de 100 taches/job
    public Instance(String path) {

        this.jobs = new ArrayList<Job>();
        this.machines = new ArrayList<Machine>();

        //Partie lecture de fichier
        File filename = new File(path);
        String current = null;
        try {
            current = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = null;
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            line = line.trim().replaceAll(" +", " ");
            line = line.trim().replaceAll("\t+", " ");

            String[] ligneJob = line.split(" ");

            // A la première ligne on récupère les données liées au pb (nombre de job/ nombre de machine (le 3eme chiffre n'est pas utile)
            //Initialisation des Jobs
            int nb_job = Integer.parseInt(ligneJob[0]);
            Job[] jobs = new Job[nb_job];
            //Initialisation des machines
            int nb_machine = Integer.parseInt(ligneJob[1]);
            creationMachine(nb_machine);

            // Lecture du fichier
            int i = 0;
            for (i = 0; i < nb_job; i++) {
                //A chaque ligne il y a création d'un Job
                try {
                    line = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Creation du i eme Job
                line = line.trim().replaceAll(" +", " ");

                creationJob(line, i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // mémoization
    private int nbJobsFlexibles = -1;
    public int jobsFlexibles()
    {
        if(this.nbJobsFlexibles >= 0)
            return this.nbJobsFlexibles;

        int cpt = 0;
        for(Job j: this.jobs)
        {
            if(j.tachesFlexibles() > 0)
                cpt++;
        }

        this.nbJobsFlexibles = cpt;
        return cpt;
    }

    public Tache tacheFlexibleAleatoire()
    {
        int jf = this.jobsFlexibles();
        if(jf > 0)
        {
            int jobf_sel = ThreadLocalRandom.current().nextInt(0, jf);
            return this.jobs.get(jobf_sel).tacheFlexibleAleatoire();
        }

        // TODO: gestion d'erreur avec exception
        return null;
    }

    private void creationMachine(int nbMachine)
    {
        if (nbMachine >= 0)
        {
            for (int i = 0; i < nbMachine; i++)
                machines.add(new Machine(i));
        }
    }

    private void creationJob(String ligne, int nbjob)
    {
        Job newJob = new Job();

        String[] currentJob = ligne.split(" ");
        //Lecture ligne par ligne
        int i=0;
        for (int j = 1; j < currentJob.length; j=j+3) // Ne Fonctionne que si l'on a un ordinateur pour une ressource à chaque fois
        {
            //Initialisation des variables lié à UNE tâche
            int nbRessourceDispo = Integer.parseInt(currentJob[j]);

            //Creation Tâche
            Tache t = new Tache((100*(nbjob+1) + i));

            int index = j;
            for (; j<index + 2 * nbRessourceDispo ; j = j + 2) // Fonctionne même dans le cas où il y a plusieurs machine dispo
            {
                int numMachinePossible = Integer.parseInt(currentJob[j+1]);
                int nbTempsTache = Integer.parseInt(currentJob[j+2]);

                //Ajout de la ressource disponible pour la tâche
                t.ajouterRessource(machines.get(numMachinePossible-1), nbTempsTache);
            }
            j=j-2; //On va trop loin avec la boucle for, du coup faut rajuster ça, y'a sûrement des trucs plus joli, mais bon, ça marche :)
            newJob.ajouterTache(t);
            i++;
        }

        this.jobs.add(newJob);
    }
}
