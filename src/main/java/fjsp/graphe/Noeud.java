package fjsp.graphe;

import java.util.ArrayList;
import fjsp.probleme.*;

public class Noeud {

    ArrayList<Arc> contraintes;

    public Tache tache;

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

    public int coutMax()
    {
        int cout_max = 0;

        for(Arc a: contraintes)
        {
            int coucou = a.cout + a.pred.coutMax();
            if(cout_max < coucou)
                cout_max = coucou;
        }

        return cout_max;
    }
}