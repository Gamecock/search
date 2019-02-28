package edu.ecu.cs.csci6030.search;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueryParserTest {

    private QueryParser parser;

    @Before
    public void setup () {
        parser = new QueryParser();
    }

    @Test
    public void parseProximityTest () {
        parser.parse("United /5 States");
        assertEquals("United", parser.getQuery().getTerm1());
        assertEquals("States", parser.getQuery().getTerm2());
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
        assertEquals("United", parser.getQuery().getTerm1());
        assertEquals("States", parser.getQuery().getTerm2());
        assertNull(parser.getQuery().getSeparation());
    }

}