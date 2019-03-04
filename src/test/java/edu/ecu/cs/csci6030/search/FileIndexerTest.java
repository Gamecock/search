package edu.ecu.cs.csci6030.search;

import org.easymock.EasyMock;
import org.junit.Test;

import java.io.FileNotFoundException;

public class FileIndexerTest {

    Stemmer stemmer = null;


    @Test
    public void testOneWord(){
        PositionalIndex index = EasyMock.createMock(PositionalIndex.class);
        index.add("a",1,0);
        EasyMock.expectLastCall();
        EasyMock.replay(index);
        FileIndexer fileIndexer = new FileIndexer(index, stemmer);
        fileIndexer.indexFile("src/test/resources/one.txt", 1);
        EasyMock.verify(index);
    }

    @Test
    public void testsimpleFile(){
        PositionalIndex index = EasyMock.niceMock(PositionalIndex.class);
        index.add("one",1,0);
        index.add("word", 1,1);
        index.add("two", 1,2);
        EasyMock.expectLastCall();
        EasyMock.replay(index);
        FileIndexer fileIndexer = new FileIndexer(index, stemmer);
        fileIndexer.indexFile("src/test/resources/simple.txt", 1);
        EasyMock.verify(index);
    }

    @Test
    public void testMixedCaseFile(){
        PositionalIndex index = EasyMock.strictMock(PositionalIndex.class);
        index.add("three",3,0);
        index.add("dog", 3,1);
        index.add("night", 3,2);
        EasyMock.expectLastCall();
        EasyMock.replay(index);
        FileIndexer fileIndexer = new FileIndexer(index, stemmer);
        fileIndexer.indexFile("src/test/resources/mixedCase.txt", 3);
        EasyMock.verify(index);
    }

    @Test
    public void testMissingFileCaught() {
        PositionalIndex index = EasyMock.strictMock(PositionalIndex.class);
        EasyMock.replay(index);
        FileIndexer fileIndexer = new FileIndexer(index, stemmer);
        fileIndexer.indexFile("noFile.txt", 5);
        EasyMock.verify(index);
    }

    @Test
    public void testUsingAStemmer () {
        PositionalIndex index = EasyMock.strictMock(PositionalIndex.class);
        index.add("man",7,0);
        index.add("walk", 7,1);
        index.add("into", 7,2);
        index.add("a", 7, 3);
        index.add("build", 7, 4);
        EasyMock.expectLastCall();
        EasyMock.replay(index);
        try {
            stemmer = new EnglishSnowballStemmer();
        } catch (Exception e) {
            //ignore, test wil fail
        }
        FileIndexer fileIndexer = new FileIndexer(index, stemmer);
        fileIndexer.indexFile("src/test/resources/stemTest/test.txt", 7);
        EasyMock.verify(index);
    }

}