package edu.ecu.cs.csci6030.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {

    private Stemmer stemmer = null;

    private Pattern numberPattern;

    public QueryParser(Stemmer stemmer){
        numberPattern = Pattern.compile("[0-9]+");
        this.stemmer = stemmer;
    }

    public Query parse(String input) throws ParseFailureException {
        Integer separation = null;
        String term2=null;


        String[] terms = input.split(" ");
        if ( terms.length > 3) throw new ParseFailureException("Too many terms in query");
        for (int i = 0; i < terms.length; i++ ) {
            if (null == stemmer) {
                terms[i] = terms[i].toLowerCase();
            } else {
                terms[i] = stemmer.stem(terms[i]);
            }
        }
        switch (terms.length){
            case 3:
                separation = parseSeparation(terms[1]);
                term2 = terms[2];
                break;
            case 2:
                term2 = terms[1];
                break;
        }
        String term1 = terms[0];
        return new Query(term1, term2, separation);
    }

    private Integer parseSeparation(String term) {
        Matcher matcher = numberPattern.matcher(term);
        String sepString;
        if (matcher.find()) {
            sepString = matcher.group();
        } else {
            throw new ParseFailureException("No separation term.");
        }
        return Integer.parseInt(sepString);
    }
}
