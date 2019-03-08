package edu.ecu.cs.csci6030.search;

import java.util.Arrays;

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
        if (term1 == null) {
            this.term1 = term2;
            this.term2 = null;
        } else {
            this.term1 = term1;
            this.term2 = term2;
        }
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

    public int[] search(PositionalIndex index) {
        int[] results = new int[0];
        int[] list1 = null;
        PositionalPosting posting1 = null;
        PositionalPosting posting2 = null;
        if (term1 == null) {
            return results;
        } else {
            posting1 = index.getPosting(term1);
            if (null != posting1) {
                list1 = posting1.getDocumentList();
            } else {
                //null AND anything will be null, so quit looking
                return results;
            }
        }
        if (term2 != null) {
            posting2 = index.getPosting(term2);
            int[] list2 =null;
            if (null != posting2) {
                list2 = posting2.getDocumentList();
            } else {
                return results;
                //Intersection of results with null will be null
            }
            int[] finalList2 = list2;
            results = Arrays.stream(list1).filter(x -> Arrays.stream(finalList2).anyMatch(y -> y == x)).toArray();
        } else {
                results = Arrays.copyOf(list1, list1.length);
        }
        if (separation != null) results = intersectPosition(results, posting1, posting2, separation);
        return results;
    }

    private int[] intersectPosition(int[] results, PositionalPosting posting1, PositionalPosting posting2, Integer separation) {
        for (int document = 0 ; document < results.length; document++) {
            boolean match = false;
            int ptr1 = 0;
            int ptr2 = 0;
            int[] list1 = posting1.get(results[document]).getList();
            int[] list2 = posting2.get(results[document]).getList();
            while (ptr1 < list1.length & ptr2< list2.length){
                if (Math.abs(list1[ptr1] -list2[ptr2]) <= separation+1) {
                    match = true;
                    break;//no need to check rest of the list
                }
                if (list1[ptr1] < list2[ptr2] ) {
                    ptr1++;
                } else {
                    ptr2 ++;
                }
            }
            if (!match) results[document] = -1;
        }
        return Arrays.stream(results).filter(x -> x>=0).toArray();
    }

    @Override
    public String toString() {
        return "Query: term1 ["+term1+"], term2 ["+term2+"], with separation: " + separation;
    }

}
