package fjsp.probleme;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Job {

    public ArrayList<Tache> taches;

    public Job()
    {
        this.taches = new ArrayList<Tache>();
    }

    public void ajouterTache(Tache t)
    {
        this.taches.add(t);
        t.parent = this;
    }

    // mémoization
    private int nbTachesFlexibles = -1;
    public int tachesFlexibles()
    {
        if(nbTachesFlexibles >= 0)
            return this.nbTachesFlexibles;

        int cpt = 0;
        for(Tache t: this.taches)
        {
            if(t.estFlexible())
                cpt++;
        }

        this.nbTachesFlexibles = cpt;

        return cpt;
    }

    public Tache tacheFlexibleAleatoire()
    {
        int tf = this.tachesFlexibles();
        if(tf > 0)
        {
            int tf_sel = ThreadLocalRandom.current().nextInt(0, tf);
            return this.taches.get(tf_sel);
        }

        // TODO: gestion d'erreur avec exception
        return null;
    }
}
