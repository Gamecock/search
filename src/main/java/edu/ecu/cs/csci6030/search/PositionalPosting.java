package edu.ecu.cs.csci6030.search;

import java.util.*;

/**
 *The posting list for a term.  It is a Map of documentIds to PostingLists
 *
 * @author Mike Finch
 *
 */
public class PositionalPosting {
    private Map<Integer, PostingList> posting;

    public PositionalPosting(){
        posting = new HashMap<>();

    }
    public void add(Integer document, Integer position){
        PostingList postingList = posting.get(document);
        if (null == postingList) postingList = new PostingList();
        postingList.add(position);
        posting.put(document, postingList);
    }

    public int size() {
        return posting.size();
    }

    public PostingList get(int documentId) {
        return posting.get(documentId).clone();
    }

    public boolean contains (int documentId) {
        return posting.containsKey(documentId);
    }

    public int[] getDocumentList() {
        int[] list = new int[posting.size()];
        int i = 0;
        for (int key : posting.keySet()) {
            list[i++] = key;
        }
        return list;
    }
}
