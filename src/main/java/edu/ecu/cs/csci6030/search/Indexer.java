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
        File directory = getDirectoryFromPath(path);
        int scanned = documentList.scanDir(directory);
        return scanFiles(scanned);
    }

    public int indexDirectory(String path, int maxFiles) {
        File directory = getDirectoryFromPath(path);
        int scanned = documentList.scanDir(directory, maxFiles);
        return scanFiles(scanned);
    }

    private int scanFiles(int scanned) {
        for (int i = 0; i < scanned; i++) {
            fileIndexer.indexFile(documentList.getFilePath(i), i);
        }
        return scanned;
    }

    private File getDirectoryFromPath(String path) {
        File directory = new File(path);
        if ( directory == null || !directory.isDirectory()) throw new InvalidDirectoryException("Path is not valid");
        return directory;
    }

    public DocumentList getDocumentList() {
        return this.documentList;
    }

}
