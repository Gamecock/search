package edu.ecu.cs.csci6030.search;

import org.tartarus.snowball.SnowballStemmer;

import java.io.File;

public class Indexer {

    private DocumentList documentList;
    private FileIndexer fileIndexer;

    public Indexer(DocumentList documentList, FileIndexer fileIndexer) {
        this.documentList = documentList;
        this.fileIndexer = fileIndexer;
    }

    public int indexDirectory(String path) {
        int scanned = documentList.scanDir(new File(path));
        return scanFiles(scanned);
    }

    private int scanFiles(int scanned) {
        for (int i = 0; i < scanned; i++) {
            fileIndexer.indexFile(documentList.getFilePath(i), i);
        }
        return scanned;
    }

    public int indexDirectory(String path, int maxFiles) {
        int scanned = documentList.scanDir(new File(path), maxFiles);
        return scanFiles(scanned);
    }

    public DocumentList getDocumentList() {
        return this.documentList;
    }

}
