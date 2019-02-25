package edu.ecu.cs.csci6030.search;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionalPostingTest {


    PositionalPosting posting;

    @Before
    public void setup() {
        posting = new PositionalPosting();
    }

    @Test
    public void newPosting() {
        assertEquals(0, posting.size());
    }

    @Test
    public void addADocTest() {
        posting.add(1,1);
        assertEquals(1, posting.size());
        PostingList doc1 = posting.get(1);
        assertEquals(1, doc1.size());
        int[] expected = new int[]{1};
        assertArrayEquals(expected, doc1.getList());
    }

    @Test
    public void addSecondDocTest() {
        posting.add(1,0);
        posting.add(4,3);
        posting.add(4, 10);
        assertEquals(2,posting.size());
        int[] expected = new int[]{3,10};
        PostingList doc4 = posting.get(4);
        assertEquals(2, doc4.size());
        assertArrayEquals(expected, doc4.getList());
        expected = new int[]{1,4};
        assertArrayEquals(expected, posting.getDocumentList());
    }

    @Test
    public void containsTest() {
        posting.add(7, 109);
        posting.add(0, 14);
        assertTrue(posting.contains(7));
        assertFalse(posting.contains(1));
    }

}