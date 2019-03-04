package edu.ecu.cs.csci6030.search;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileIndexer {

    private PositionalIndex index;
    private Stemmer stemmer = null;

    public FileIndexer(PositionalIndex index, Stemmer stemmer) {
        this.index = index;
        this.stemmer = stemmer;

    }

    public void indexFile(String path, int documentId) {
        BufferedReader br = null;
        String line;
        String word;
        int position =0;
        // wordPattern specifies pattern for words using a regular expression
        Pattern wordPattern = Pattern.compile("[a-zA-Z]+");

        // wordMatcher finds words by spotting word word patterns with input
        Matcher wordMatcher;
        try {
            // get a BufferedReader object, which encapsulates
            // access to a (disk) file
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null){
                wordMatcher = wordPattern.matcher(line);
                while (wordMatcher.find()) {
                    word = wordMatcher.group();
                    if (null != stemmer) {
                        word = stemmer.stem(word);
                    } else {
                        word = word.toLowerCase();
                    }
                    index.add(word, documentId, position++);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
