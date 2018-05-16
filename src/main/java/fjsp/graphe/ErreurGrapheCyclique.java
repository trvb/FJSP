package fjsp.graphe;

public class ErreurGrapheCyclique extends Exception {
    public ErreurGrapheCyclique(String message) {
        super(message);
    }
}
