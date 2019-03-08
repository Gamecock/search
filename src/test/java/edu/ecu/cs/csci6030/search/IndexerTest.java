package edu.ecu.cs.csci6030.search;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.tartarus.snowball.SnowballStemmer;

import java.io.File;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

@RunWith(EasyMockRunner.class)
public class IndexerTest {

    @Mock
    DocumentList mockDocs;

    @Mock
    FileIndexer mockIndexer;

    EnglishSnowballStemmer stemmer = null;

    @TestSubject
    Indexer indexer = new Indexer(mockDocs, mockIndexer);

    @Test
    public void emptyDirectoryTest(){
        expect(mockDocs.scanDir(new File("src/test"))).andReturn(0);
        replay(mockDocs);
        replay(mockIndexer);
        assertEquals(0, indexer.indexDirectory("src/test"));
        verify(mockDocs);
        verify(mockIndexer);
    }

    @Test
    public void scanDirectoryWithDocumentsTest() {
        expect(mockDocs.scanDir(new File("src/test"))).andReturn(3);
        expect(mockDocs.getFilePath(0)).andReturn("path1");
        mockIndexer.indexFile("path1", 0);
        expect(mockDocs.getFilePath(1)).andReturn("path2");
        mockIndexer.indexFile("path2", 1);
        expect(mockDocs.getFilePath(2)).andReturn("path3");
        mockIndexer.indexFile("path3", 2);
        replay(mockDocs);
        replay(mockIndexer);
        assertEquals(3, indexer.indexDirectory("src/test"));
        verify(mockDocs, mockIndexer);
    }


    @Test
    public void scanlimitWorksTest() {
        expect(mockDocs.scanDir(new File("src/test"), 1)).andReturn(1);
        expect(mockDocs.getFilePath(0)).andReturn("path1");
        mockIndexer.indexFile("path1", 0);
        replay(mockDocs, mockIndexer);
        assertEquals(1, indexer.indexDirectory("src/test", 1));
        verify(mockDocs, mockIndexer);
    }

    @Test
    public void  returnDocumentListTest() {
        DocumentList docs = new DocumentList();
        PositionalIndex index = new PositionalIndex();
        FileIndexer fileIndexer = new FileIndexer(index, stemmer);
        Indexer indexer = new Indexer(docs, fileIndexer);
        assertEquals(1, indexer.indexDirectory("src/test/resources/oneFileDirectory"));
        assertEquals(docs, indexer.getDocumentList());
        assertEquals("king.txt", docs.getFileName(0));
        assertEquals(4, index.size());
        assertArrayEquals(new int[]{0}, index.getPosting("the").getDocumentList());
        assertArrayEquals(new int[]{1}, index.getPosting("king").get(0).getList());
    }

    @Test(expected = InvalidDirectoryException.class)
    public void invalidDirectoryTest() {
        indexer.indexDirectory("notAValidTest");
    }

    @Test(expected = InvalidDirectoryException.class)
    public void scanFileTest() {
        indexer.indexDirectory("src/test/resources/oneFileDirectory/king.txt", 4);
    }
}