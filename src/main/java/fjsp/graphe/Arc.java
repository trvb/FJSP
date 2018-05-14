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

    public void afficherArc()
    {
        if (!this.pred.tache.vide)
            System.out.print(this.pred.tache.id);
        else
            System.out.print("Debut");

        System.out.print(" -> ");

        if (!this.suiv.tache.vide)
            System.out.print(this.suiv.tache.id);
        else
            System.out.print("Fin");

        if (this.cout != 0)
          System.out.print("[label=\""+this.cout+"\",weight=\""+this.cout+"\"]");

        System.out.print(";");
        System.out.println();
    }
}
