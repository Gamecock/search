package edu.ecu.cs.csci6030.search;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueryParserTest {

    private QueryParser parser;

    private Stemmer stemmer;

    Query query;

    @Before
    public void setup () {
        parser = new QueryParser(stemmer);
    }

    @Test
    public void parseProximityTest () {
        query = parser.parse("United /5 States");
        assertEquals("united", query.getTerm1());
        assertEquals("states", query.getTerm2());
        assertEquals((Integer)5, (Integer)query.getSeparation());
    }

    @Test(expected = ParseFailureException.class)
    public void tooManyTermExceptionTest() {
        parser.parse("United /5 States /6 America");
    }

    @Test(expected = ParseFailureException.class)
    public void noNumberExceptionTest() {
        parser.parse("United States America");
    }

    @Test(expected = ParseFailureException.class)
    public void numberWrongPlaceTest() { parser.parse("United States /0"); }

    @Test(expected = ParseFailureException.class)
    public void numberWrongPlaceTest2() { parser.parse("/7 United States"); }

    @Test
    public void booleanQueryTest() {
        query = parser.parse("United States");
        assertEquals("united", query.getTerm1());
        assertEquals("states", query.getTerm2());
        assertNull(query.getSeparation());
    }

    @Test
    public void singleQueryTest() {
        query = parser.parse("United");
        assertEquals("united", query.getTerm1());
        assertNull(query.getSeparation());
    }

    @Test
    public void stemmerQueryTest() {
        try {
            stemmer = new EnglishSnowballStemmer();
        } catch (Exception e){
                //do nothing test will fail
        }
            parser = new QueryParser(stemmer);
            query = parser.parse("walks /2 building");
        assertEquals("walk", query.getTerm1());
        assertEquals("build", query.getTerm2());
        assertEquals((Integer)2, (Integer)query.getSeparation());
    }
}