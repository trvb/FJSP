package fjsp.probleme;

import java.util.ArrayList;

public class Job {

    public ArrayList<Tache> taches;

    public Job()
    {
        this.taches = new ArrayList<Tache>();
    }

    public void ajouterTache(Tache t)
    {
        this.taches.add(t);
    }
}
