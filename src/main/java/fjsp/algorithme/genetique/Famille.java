package fjsp.algorithme.genetique;

import fjsp.probleme.Solution;

public class Famille {

    public Solution parent1;
    public Solution parent2;
    public Solution enfant1;
    public Solution enfant2;

    public Famille(Solution p1, Solution p2) throws ErreurCroisementImpossible {
        this.parent1 = p1;
        this.parent2 = p2;

        this.enfant1 = new Solution(p1.probleme);
        this.enfant2 = new Solution(p1.probleme);

        this.croisement();
    }

    private void croisement() throws ErreurCroisementImpossible {
        if(!this.parent1.probleme.equals(this.parent2.probleme))
        {
            throw new ErreurCroisementImpossible("Les deux parents ne partagent pas la même instance de problème.");
        }
    }
}
