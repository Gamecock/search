package edu.ecu.cs.csci6030.search;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnglishSnowballStemmerTest {

    Stemmer stemmer = null;

    @Before
    public void setup() {
        try {
            stemmer = new EnglishSnowballStemmer();
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n No stemming.");
        }

    }

    @Test
    public void stem() {
        assertEquals("a" , stemmer.stem("a"));
        assertEquals("walk" , stemmer.stem("walked"));
        assertEquals("walk" , stemmer.stem("walks"));
    }
}