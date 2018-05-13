package fjsp.graphe;

public class Arc {

    Noeud pred;
    Noeud suiv;

    int cout;

    public Arc(Noeud p, Noeud s, int c)
    {
        this.pred = p;
        this.suiv = s;
        this.cout = c;
    }
}
