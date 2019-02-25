package edu.ecu.cs.csci6030.search;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionalIndexTest {

    PositionalIndex index;

    @Before
    public void setUp() {
        index = new PositionalIndex();
    }

    @Test
    public void newDictTest(){
        assertEquals(0, index.size());
    }

    @Test
    public void addOneTest() {
        index.add("word", 1,8);
        assertEquals(1, index.size());
        PositionalPosting wordPosting = index.getPosting("word");
        assertTrue(wordPosting.contains(1));
        assertArrayEquals(new int[]{8}, wordPosting.get(1).getList());
    }

    @Test
    public void addTwoTest() {
        index.add("word", 1,8);
        index.add("anotherWord", 1,9);
        index.add("anotherWord", 2,8);
        assertEquals(2, index.size());
        assertEquals(1, index.getPosting("word").size());
        assertEquals(2, index.getPosting("anotherWord").size());
    }

}