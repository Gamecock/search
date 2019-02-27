package edu.ecu.cs.csci6030.search;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class DocumentListTest {

    private DocumentList docs;

    @Before
    public void setup() {
        docs = new DocumentList();
    }

    @Test
    public void newDocListTest() {
        assertEquals(0, docs.size());
    }

    @Test
    public void scanOneFileDirectoryTest() {
        assertEquals(1, docs.scanDir(new File("src/test/resources/oneFileDirectory")));
        assertEquals(1,docs.size());
        assertEquals("king.txt", docs.getFileName(0));
        assertEquals( "src/test/resources/oneFileDirectory/king.txt", docs.getFilePath(0) );
    }

    @Test
    public void scanTwoFileDirectoryTest() {
        assertEquals(2, docs.scanDir(new File("src/test/resources/twoFileDirectory")));
        assertEquals(2,docs.size());
        assertEquals("fox.txt", docs.getFileName(0));
        assertEquals("ipsum.txt", docs.getFileName(1));
    }

    @Test
    public void recursiveScanTest() {
        assertEquals(6, docs.scanDir(new File("src/test/resources")));
        assertEquals(6, docs.size());
    }

    @Test
    public void limitScanTest() {
        assertEquals(4, docs.scanDir(new File("src/test/resources"), 4));
    }



}