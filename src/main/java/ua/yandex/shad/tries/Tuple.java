package ua.yandex.shad.tries;

public class Tuple {
    private final String term;
    private final int weight;

    public Tuple(String term, int weight) {
        this.term = term;
        this.weight = weight;
    }  

    /**
     * @return the term
     */
    public String getTerm() {
        return term;
    }

    /**
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

}
