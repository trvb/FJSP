package fjsp.algorithme;

import fjsp.probleme.Instance;
import fjsp.probleme.Solution;

import java.io.IOException;

public abstract class Algorithme {

    protected int explorees;
    protected int nb_maj;
    protected int generees;
    protected int derniere;
    protected int limite;

    protected Instance probleme;

    public Algorithme(Instance pb, int limite) {
        this.probleme = pb;
        this.limite = limite;
    }

    public abstract Solution resoudre();

    public void afficherProgression()
    {
        float progres = (float)(this.explorees+1) / (float)this.limite * 100f;
        String data = "\r" + "Calcul " + progres + "%";
        try {
            System.out.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afficherStatistiques()
    {
        float rejets = (float)(generees - this.explorees) / (float)(generees) * 100f;

        System.out.println("\nStatistiques solutions");
        System.out.println("\texplorées: " + this.explorees);
        System.out.println("\tgénérées: " + generees + " - " + rejets + "% rejets");
        System.out.println("\tnombre de mises à jour: " + this.nb_maj);
        System.out.println("\tdernière itération de maj: " + this.derniere);
    }
}
