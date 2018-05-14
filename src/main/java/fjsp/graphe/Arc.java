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
    public void printArc()
    {
        if (this.pred.tache != null) {
            System.out.print(this.pred.tache.id);
            System.out.print(" -> ");
        }
        if (this.suiv.tache != null)
            System.out.print(this.suiv.tache.id);
        else
            System.out.print("Fin");
        if (this.cout != 0)
          System.out.print("[label=\""+this.cout+"\",weight=\""+this.cout+"\"]");
        System.out.print(";");
        System.out.println();
    }
}
