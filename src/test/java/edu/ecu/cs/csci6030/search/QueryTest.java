package edu.ecu.cs.csci6030.search;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(EasyMockRunner.class)
public class QueryTest {

    @Mock
    PositionalIndex index;
    @Mock
    PositionalPosting posting;
    @Mock
    PostingList list;

    @Test
    public void  nullQueryTest() {
        Query query = new Query(null,null,null);
        EasyMock.replay(index, posting);
        assertArrayEquals(null,  query.search(index));
        EasyMock.verify(index, posting);
    }

    @Test
    public void simpleTermQueryTest() {
        Query query = new Query ("a", null, null);
        int[] expected = new int[] {3,5,7};
        EasyMock.expect(posting.getDocumentList()).andReturn(expected);
        EasyMock.expect(index.getPosting("a")).andReturn(posting);
        EasyMock.replay(index, posting);
        assertArrayEquals(expected,  query.search(index));
        EasyMock.verify(index, posting);
    }

    @Test
    public void backwardsSimpleTest() {
        Query query = new Query (null, "c", null);
        int[] expected = new int[] {3,5,7};
        EasyMock.expect(posting.getDocumentList()).andReturn(expected);
        EasyMock.expect(index.getPosting("c")).andReturn(posting);
        EasyMock.replay(index, posting);
        assertArrayEquals(expected,  query.search(index));
        EasyMock.verify(index, posting);
    }

    @Test
    public void simpleBooleanQueryTest() {
        Query query = new Query ("a", "b", null);
        int[] expected = new int[] {3,5,7};
        int[] posting1 = new int[] {1,3,5,7,9};
        int[] posting2 = new int[] {3,4,5,6,7};
        EasyMock.expect(posting.getDocumentList()).andReturn(posting1);
        EasyMock.expect(posting.getDocumentList()).andReturn(posting2);
        EasyMock.expect(index.getPosting("a")).andReturn(posting);
        EasyMock.expect(index.getPosting("b")).andReturn(posting);
        EasyMock.replay(index, posting);
        assertArrayEquals(expected,  query.search(index));
        EasyMock.verify(index, posting);
    }

    @Test
    public void adjacentTest() {
        Query query = new Query ("one", "two", 1);
        int[] expected = new int[] {5};
        int[] posting1 = new int[] {3,5,7};
        int[] posting2 = new int[] {3,4,5};
        EasyMock.expect(posting.getDocumentList()).andReturn(posting1);
        EasyMock.expect(posting.getDocumentList()).andReturn(posting2);
        EasyMock.expect(index.getPosting("one")).andReturn(posting);
        EasyMock.expect(index.getPosting("two")).andReturn(posting);
        EasyMock.expect(posting.get(3)).andReturn(list).times(2);
        EasyMock.expect(posting.get(5)).andReturn(list).times(2);
        EasyMock.expect(list.getList()).andReturn( new int[] {7, 9});
        EasyMock.expect(list.getList()).andReturn( new int[] {12, 14});
        EasyMock.expect(list.getList()).andReturn( new int[] {6, 8, 18});
        EasyMock.expect(list.getList()).andReturn( new int[] {2, 4, 15});
        EasyMock.replay(index, posting, list);
        assertArrayEquals(expected,  query.search(index));
        EasyMock.verify(index, posting, list);
    }

}