package edu.ecu.cs.csci6030.search;

import java.util.HashMap;
import java.util.Map;

public class PositionalIndex {

    private Map<String,PositionalPosting> index;

    public PositionalIndex(){
        index = new HashMap<>();
    }

    public int size() {
        return index.size();
    }

    public void add(String word, int documentId, int position) {
        PositionalPosting posting = index.get(word);
        if(null == posting) posting = new PositionalPosting();
        posting.add(documentId,position);
        index.put(word, posting);
    }

    public PositionalPosting getPosting(String word) {
        return index.get(word);
    }
}
