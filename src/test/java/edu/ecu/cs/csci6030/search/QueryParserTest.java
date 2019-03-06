package edu.ecu.cs.csci6030.search;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueryParserTest {

    private QueryParser parser;

    private Stemmer stemmer;

    @Before
    public void setup () {
        parser = new QueryParser(stemmer);
    }

    @Test
    public void parseProximityTest () {
        parser.parse("United /5 States");
        assertEquals("united", parser.getQuery().getTerm1());
        assertEquals("states", parser.getQuery().getTerm2());
        assertEquals((Integer)5, (Integer)parser.getQuery().getSeparation());
    }

    @Test(expected = ParseFailureException.class)
    public void tooManyTermExceptionTest() {
        parser.parse("United /5 States /6 America");
    }

    @Test(expected = ParseFailureException.class)
    public void noNumberExceptionTest() {
        parser.parse("United States America");
    }

    @Test
    public void booleanQueryTest() {
        parser.parse("United States");
        assertEquals("united", parser.getQuery().getTerm1());
        assertEquals("states", parser.getQuery().getTerm2());
        assertNull(parser.getQuery().getSeparation());
    }

    @Test
    public void singleQueryTest() {
        parser.parse("United");
        assertEquals("united", parser.getQuery().getTerm1());
        assertNull(parser.getQuery().getSeparation());
    }

    @Test
    public void stemmerQueryTest() {
        try {
            stemmer = new EnglishSnowballStemmer();
        } catch (Exception e){
                //do nothing test will fail
        }
            parser = new QueryParser(stemmer);
            parser.parse("walks /2 building");
        assertEquals("walk", parser.getQuery().getTerm1());
        assertEquals("build", parser.getQuery().getTerm2());
        assertEquals((Integer)2, (Integer)parser.getQuery().getSeparation());
    }
}