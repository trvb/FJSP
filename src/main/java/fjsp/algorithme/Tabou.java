package fjsp.algorithme;

import fjsp.graphe.ErreurGrapheCyclique;
import fjsp.probleme.Configuration;
import fjsp.probleme.Instance;
import fjsp.probleme.Solution;
import fjsp.probleme.Solveur;

import java.util.ArrayList;

public class Tabou extends Algorithme {

    ArrayList<Solution> tabuList = new ArrayList<Solution>();
    public Tabou(Instance pb, int limite) {
        super(pb, limite);
    }

    @Override
    public Solution resoudre()
    {
        Solveur resolutionneur = new Solveur(this.probleme);
        Solution courante = resolutionneur.solutionInitiale(Configuration.SOLVER_SHUFFLING, true), evaluee;
        courante.generationGraphe();
        int meilleurCoutMax = Integer.MAX_VALUE, coutCourant;

        this.derniere = 0;
        this.nb_maj = 0;
        this.generees = 0;
        this.explorees = 0;
        //But recherche taboo
        int indexMax=0;
        int t=0;
        while (this.explorees<this.limite) // Permet d'obtenir un solution
        {
            coutCourant = Integer.MAX_VALUE;

            //Obtenir tous les voisins de la solution courante

            ArrayList<Solution> neighbors = new ArrayList<Solution>();
            int possibilite=0; //Obtient le nombre de voisin possible
            for (int i=1 ; i<courante.operationSequence.size() ; i++)
            {
                if (!courante.operationSequence.get(i).parent.equals(courante.operationSequence.get(i-1).parent))
                {
                    possibilite++;
                }
            }
            //Recherche tout les voisins possible qui ne sont pas dans la tabuList
            for (int i=0 ; i<(possibilite/10)+1 ; i++)
            {
                Solution voisin;
                do
                {
                    voisin = courante.voisinAleatoire();
                    voisin.generationGraphe();
                }
                while (contient(tabuList,(voisin)) || (!voisin.estAdmissible())); // On s'assure que la solution trouvée n'est pas dans la liste tabu

                this.generees++;
                neighbors.add(voisin);
            }
            //Calculer cout meilleur voisin
            for (int i=0 ; i<(possibilite/10)+1 ; i++)
            {
                Solution voisin = neighbors.get(i);

                try {
                    coutCourant = voisin.graphe.coutMax();
                } catch (ErreurGrapheCyclique erreurGrapheCyclique) {
                    erreurGrapheCyclique.printStackTrace();
                }
                if (coutCourant < meilleurCoutMax) {
                    indexMax = i;
                    meilleurCoutMax = coutCourant;
                    this.derniere = this.explorees;
                    this.nb_maj++;
                }

            }
            //Si le meilleur voisin est un mouvement antérieur, on le refuse et on recommence

            //Changer la solution pour celle avec le meilleur voisin
            courante = neighbors.get(indexMax);

            //On ajoute la solution courante a la liste tabou, pour ne pas retomber dessus
            tabuList.add(courante);
            this.explorees++;
        }
        return courante;
    }

    
    public boolean contient(ArrayList<Solution> tabu, Solution voisin)
    {
        if (tabu.size() == 0)
            return false;
        for (Solution S: tabu)
        {
            if (S.operationSequence.equals(voisin.operationSequence))
                return true;
        }
        return false;
        
    }
}
