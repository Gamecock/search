package edu.ecu.cs.csci6030.search;


import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

public class EnglishSnowballStemmer implements Stemmer {

    SnowballStemmer stemmer;

    public EnglishSnowballStemmer() throws InstantiationException, IllegalAccessException {
        try {
            stemmer = (SnowballStemmer) Class.forName("org.tartarus.snowball.ext.englishStemmer").newInstance();
        }
        catch (ClassNotFoundException cnfe){
            System.out.println ("Stemmer failed to initialize.");
        }
    }

    @Override
    public String stem(String word) {
        stemmer.setCurrent(word.toLowerCase());
        stemmer.stem();
        return stemmer.getCurrent();
    }
}
