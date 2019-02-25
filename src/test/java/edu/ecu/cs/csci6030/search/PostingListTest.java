package edu.ecu.cs.csci6030.search;

import javafx.geometry.Pos;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PostingListTest {

    private PostingList list;

    @Before
    public void setup(){
        list = new PostingList();
    }

    @Test
    public void newPostingTest() {
        assertEquals(0, list.size());
    }

    @Test
    public void addPostingTest() {
        list.add(1);
        assertEquals(1, list.size());
        int[] newlist = list.getList();
        int[] expected = new int[] {1};
        assertArrayEquals(expected, newlist);
    }

    @Test
    public void addALotTest() {
        for (int i = 0; i < 25 ; i++) {
            list.add(i);
        }
        assertEquals(25, list.size());
    }

    @Test(expected = UnexpectedPositionAddException.class)
    public void dontAddTwiceTest(){
        list.add(4);
        list.add(4);
    }

    @Test(expected = UnexpectedPositionAddException.class)
    public void cannotAddBeforeEndTest() {
        PostingList list = new PostingList();
        for (int i = 0; i < 15 ; i+=2) {
            list.add(i);
        }
        list.add(13);
    }

    @Test
    public void allowChainAdds() {
        list.add(5).add(7);
        assertEquals(2,list.size());
    }

    @Test
    public void cloneIsIndependant() {
        list.add(42);
        PostingList clone = list.clone();
        clone.add(125);
        assertEquals(1, list.size());
        assertEquals(2, clone.size());
    }

}