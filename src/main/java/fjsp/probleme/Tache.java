package fjsp.probleme;

import java.util.ArrayList;

public class Tache {

    public int id;
    public boolean vide;
    public ArrayList<Ressource> ressources;
    public Job parent;

    public Tache(int id, Job parent)
    {
        ressources = new ArrayList<Ressource>();
        this.vide = false;
        this.id = id;
        this.parent = parent;
    }

    public Tache(int id)
    {
        ressources = new ArrayList<Ressource>();
        this.vide = false;
        this.id = id;
    }

    public Tache()
    {
        this.id = 0;
        this.vide = true;
    }

    public void ajouterRessource(Machine m, int temps)
    {
        Ressource nouvelle_ressource = new Ressource(m, temps);
        ressources.add(nouvelle_ressource);
    }
}
