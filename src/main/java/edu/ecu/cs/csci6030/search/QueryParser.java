package edu.ecu.cs.csci6030.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {

    private Query query;

    private Pattern numberPattern;

    public QueryParser(){
        numberPattern = Pattern.compile("[0-9]+");
    }

    public void parse(String input) {
        Integer separation = null;
        String term1=null;
        String term2=null;

        String[] terms = input.split(" ");
        if ( terms.length > 3) throw new ParseFailureException("Too many terms in query");
        switch (terms.length){
            case 3:
                separation = parseSeparation(terms[1]);
                term1 = terms[0];
                term2 = terms[2];
                break;
            case 2:
                term1 = terms[0];
                term2 = terms[1];
                break;
        }

        query = new Query(term1, term2, separation);
    }

    private Integer parseSeparation(String term) {
        Matcher matcher = numberPattern.matcher(term);
        String sepString = null;
        if (matcher.find()) {
            sepString = matcher.group();
        } else {
            throw new ParseFailureException("No separation term.");
        }
        return Integer.parseInt(sepString);
    }


    public Query getQuery() {
        return query;
    }
}
