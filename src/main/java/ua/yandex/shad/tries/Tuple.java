package ua.yandex.shad.tries;

public class Tuple {
    private String term;
    private int weight;

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
     * @param term the term to set
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }
}
