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

    protected boolean sortieConsole;

    protected Instance probleme;

    public Algorithme(Instance pb, int limite, boolean sortieConsole) {
        this.probleme = pb;
        this.limite = limite;
        this.sortieConsole = sortieConsole;
    }

    public abstract Solution resoudre();

    public void afficherProgression()
    {
        if(this.sortieConsole) {
            float progres = (float) (this.explorees + 1) / (float) this.limite * 100f;
            String data = "\r" + "Calcul " + progres + "%";
            try {
                System.out.write(data.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void afficherStatistiques()
    {
        System.out.println("\nStatistiques solutions");
        System.out.println("\titérations explorées: " + this.explorees);
        System.out.println("\tnoeuds générés: " + generees);
        System.out.println("\tnombre de mises à jour: " + this.nb_maj);
        System.out.println("\tdernière itération de mise à jour: " + this.derniere);
    }
}
