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

    public void afficherDot(int choix)
    {
        if (choix == 1)
            System.out.println("graph S {");
        for (Arc a: contraintes)
        {
            a.printArc();
            a.pred.afficherDot(0);
        }
        if (choix == 1)
            System.out.println("}");
    }
}
