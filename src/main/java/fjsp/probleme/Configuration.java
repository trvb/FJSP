package fjsp.probleme;

public class Configuration {

    // Niveau de randomization du solveur initial: indique à quelle distance la solution initiale retenue doit se trouver par rapport à la solution initiale naive (nb. de voisins)
    // On pourrait voir ça comme un Random Walk mais ça n'est pas le cas: on cherche simplement à se placer dans l'espace des solutions sans introduire de biais en donnant une solution naïve trop orientée
    // Commentaires:
    //      - ça améliore de beaucoup les performances de l'algo de recherche locale! (2x sur certaines instances complexes)
    //      - c'est indispensable en utilisant un algorithme de génération de voisins conservateur, qui génère 0 solutions non admissibles (mais diversifie moins)
    public static final int SOLVER_SHUFFLING = 10000;

    public static boolean CONSERVATIVE_OS_EXPLORATION = true;

    // Nombre d'itérations à explorer par l'algorithme, au maximum
    public static final int ALGO_LIMIT = 20000;

    // Algorithme génétique
    public static final int MUTATION_RATE = 25; // 25% de mutation

}
