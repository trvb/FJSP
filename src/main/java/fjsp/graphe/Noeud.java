package fjsp.graphe;

import java.util.ArrayList;
import fjsp.probleme.*;

public class Noeud {

    ArrayList<Arc> contraintes;

    public Tache tache;

    public Noeud(Tache t)
    {
        this.tache = t;
    }

    public void contraindre(Noeud precedent, int cout)
    {
        Arc a = new Arc(precedent, this, cout);
        contraintes.add(a);
    }

    public int coutMax()
    {
        int cout_max = Integer.MAX_VALUE;

        for(Arc a: contraintes)
        {
            int coucou = a.cout + a.pred.coutMax();
            if(cout_max < coucou)
                cout_max = coucou;
        }

        return cout_max;
    }
}
