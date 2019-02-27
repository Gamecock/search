package edu.ecu.cs.csci6030.search;

import java.io.File;
import java.util.ArrayList;

public class DocumentList {

    private ArrayList<File> files;

    public DocumentList() {
        files = new ArrayList<>();
    }

    public int size() {
        return files.size();
    }

    public int scanDir(File directory, int maxFiles) {
        for (final File fileEntry : directory.listFiles()){
            if (fileEntry.isDirectory()) {
                scanDir(fileEntry);
            } else {
                files.add(fileEntry);
            }
            if (maxFiles == files.size()) break;
        }
        return files.size();
    }
    public int scanDir(final File directory) {
        return scanDir(directory, Integer.MAX_VALUE);
    }

    public String getFileName(int documentNr) {
        return  files.get(documentNr).getName();
    }

    public String getFilePath(int documentNr) {
        return files.get(documentNr).getPath();
    }

}
