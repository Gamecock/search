package edu.ecu.cs.csci6030.search;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.management.Query;
import java.util.HashSet;

import static org.junit.Assert.*;

public class IntegrationTest {

    QueryParser qp;
    int[] results;
    int[] noResults = new int[0];

    @BeforeClass
    public static void setUpIndex(){
        Search.scanDirectory("src/test/resources", null);
    }

    @Before
    public void setUp() {
        assertNotNull(Search.documentList);
        assertNotNull(Search.index);
        qp = new QueryParser(Search.stemmer);
    }

    @Test
    public void simpleQuertyTest() {
        results = Search.searchForString(qp,"a");
        assertEquals(2, results.length);
        assertTrue(resultsContainDoc( results,"one.txt"));
        assertTrue(resultsContainDoc( results, "test.txt"));
    }

    @Test
    public void stemmedQueryTest() {
        results = Search.searchForString(qp,"walks");
        assertEquals(1, results.length);
        assertTrue(resultsContainDoc( results,"test.txt"));
    }

    @Test
    public void booleanTest() {
        results = Search.searchForString(qp, "a walked");
        assertEquals(1, results.length);
        assertTrue(resultsContainDoc(results, "test.txt"));
    }

    @Test
    public void proximityTest() {
        results = Search.searchForString(qp,"Three /1 Night");
        assertTrue(resultsContainDoc(results, "mixedCase.txt"));
        assertEquals(1, results.length);
    }

    //Negative Test Cases

    @Test
    public void wordNotInCorpus(){
        assertArrayEquals(noResults, Search.searchForString(qp, "NotaWord"));
        assertArrayEquals(noResults, Search.searchForString(qp, "word notaword"));
        assertArrayEquals(noResults, Search.searchForString(qp, "notaword word"));
    }

    @Test
    public void nonIntersectingLists(){
        assertArrayEquals(noResults, Search.searchForString(qp, "Word Dog"));
        assertArrayEquals(noResults, Search.searchForString(qp, "Word /2 Dog"));
    }

    private boolean resultsContainDoc(int[] results, String s) {
        for (int i =0; i < results.length; i++) {
            if (s.equalsIgnoreCase(Search.documentList.getFileName(results[i]))) return true;
        }
        return false;
    }
}