package fjsp.algorithme;

import fjsp.probleme.Instance;
import fjsp.probleme.Solution;

public abstract class Algorithme {

    public int explorees;
    public int derniere;

    protected Instance probleme;

    public Algorithme(Instance pb) {
        this.probleme = pb;
    }

    public abstract Solution resoudre(int limite);
}
