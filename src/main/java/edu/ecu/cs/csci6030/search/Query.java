package edu.ecu.cs.csci6030.search;

/**
 * @author Finch
 *
 *This is a single class that does single term, two term AND boolean, and two term proximity queries.
 * Todo: refactor into multiple classes and an interface.
 */
public class Query {
    private String term1;
    private String term2;
    private Integer separation;

    public Query(String term1, String term2, Integer separation) {
        this.term1 = term1;
        this.term2 = term2;
        this.separation = separation;
    }

    public String getTerm1() {
        return term1;
    }

    public String getTerm2() {
        return term2;
    }

    public Integer getSeparation() {
        return separation;
    }
}
