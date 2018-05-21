package fjsp.graphe;

import java.util.ArrayList;
import fjsp.probleme.*;

public class Noeud {

    public ArrayList<Arc> contraintes;
    public Tache tache;
    private int coutMax = -1;

    public Noeud(Tache t)
    {
        this.tache = t;
        this.contraintes = new ArrayList<Arc>();
    }

    public void contraindre(Noeud precedent, int cout)
    {
        Arc a = new Arc(precedent, this, cout);
        contraintes.add(a);
    }

    public int coutMax() throws ErreurGrapheCyclique {
        // mémoization du coutMax pour accélerer le traitement
        if(this.coutMax >= 0)
            return this.coutMax;

        // contrôle de boucle
        this.coutMax = -2;

        int cout_max = 0;

        for(Arc a: contraintes)
        {
            if(a.pred.coutMax == -2)
                throw new ErreurGrapheCyclique("Circuit détecté lors du calcul du cout max");

            int coucou = a.cout + a.pred.coutMax();
            if(cout_max < coucou)
                cout_max = coucou;
        }

        this.coutMax = cout_max;

        return cout_max;
    }

    public void afficherDot()
    {
        this.afficherDot(1, null);
    }

    private void afficherDot(int choix, ArrayList<Noeud> visites)
    {
        if(visites == null)
            visites = new ArrayList<Noeud>();
        else if(visites.contains(this))
            return;

        visites.add(this);

        if (choix == 1)
            System.out.println("digraph S {");

        for (Arc a: contraintes)
        {
            a.afficherArcDot();
            a.pred.afficherDot(0, visites);
        }

        if (choix == 1)
            System.out.println("}");
    }
}
