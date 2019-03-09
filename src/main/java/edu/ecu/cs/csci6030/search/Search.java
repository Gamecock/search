package edu.ecu.cs.csci6030.search;

import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class Search {

    static DocumentList documentList = null;
    static PositionalIndex index = null;
    static Stemmer stemmer = null;

     static void usage() {
        System.out.println("Usage: java " + Search.class.getName()
                + " [pathToDirectory [maxFiles]]");
     }

    public static void main(String[] args) throws IOException {
            String prompt = "search> ";
            String rightPrompt = null;
            String directory;
            String maxFilesString;
            Integer maxFiles = null;

            if ((args == null) || (args.length == 0)) {
                usage();

                return;
            }
            directory = args[0];
            if (args.length == 2) {
                maxFilesString = args[1];
                try {
                    maxFiles = Integer.parseInt(maxFilesString);
                } catch (NumberFormatException nfe) {
                    System.out.println(nfe.getMessage());
                    System.out.println("Second parameter should be the maximum number of files to Index");
                }
            }

        scanDirectory(directory, maxFiles);

        try {
            TerminalBuilder builder = TerminalBuilder.builder();

            Terminal terminal = builder.build();

            LineReaderImpl reader = (LineReaderImpl)LineReaderBuilder.builder()
                    .terminal(terminal)
                    .variable(LineReader.SECONDARY_PROMPT_PATTERN, "%M%P > ")
                    .build();

            QueryParser qp = new QueryParser(stemmer);

            while (true) {
                String line = null;
                try {
                    line = reader.readLine(prompt, rightPrompt,  null, null);
                } catch (UserInterruptException e) {
                    // Ignore
                } catch (EndOfFileException e) {
                    return;
                }
                if (line == null) {
                    continue;
                }

                line = line.trim();

                terminal.flush();

                if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                    break;
                }
                else if (line.equalsIgnoreCase("sleep")) {
                    Thread.sleep(3000);
                    break;
                } else {
                    int[] results = searchForString(qp, line);
                    printResults(results, documentList);
                }

            }

        } catch (Throwable t) {
            t.printStackTrace();
        }


    }

    static void scanDirectory(String directory, Integer maxFiles) {
        documentList = new DocumentList();
        index = new PositionalIndex();
        try {
            stemmer = new EnglishSnowballStemmer();
        } catch (Exception ex) {
            System.out.println("Stemmer failed to initialize, searching w/o stemmer");
        }
        FileIndexer fileIndexer = new FileIndexer(index, stemmer);
        Indexer indexer = new Indexer(documentList, fileIndexer);

        System.out.println("Scanning Directory "+ directory);
        //todo verify a good directory before scanning
        //todo time scanning
        Integer numberScanned = 0;
        try {
            if (null == maxFiles) {
                numberScanned = indexer.indexDirectory(directory);
            } else {
                System.out.println("Files limited to: " + maxFiles);
                numberScanned = indexer.indexDirectory(directory, maxFiles);
            }
        } catch (InvalidDirectoryException ide) {
            System.out.println(directory+ " is not a valid directory");
            System.exit(7);
        }
        System.out.println("Scanned files: " + numberScanned);
    }

    static int[] searchForString(QueryParser qp, String line) {
        Query query;
        int[] results = null;
        try {
            query = qp.parse(line);
            results = query.search(index);
        } catch (ParseFailureException pfe) {
            printQueryInstructions();
        } catch (NullPointerException npe) {
            System.out.println(npe.getMessage());
        }
        return results;
    }

    private static void printQueryInstructions() {
         System.out.println("Query Failed, query should be one of these \n1. Single term 'word' \n2. Two term boolean AND 'one word'\n3. Proximity query 'near /4 annother'");

    }

    static void printResults(int[] results, DocumentList documentList) {
         if (null == results) return;
         if (0 == results.length) {
             System.out.println("Nothing Found");
         } else {
             for (int i = 0; i < results.length; i++) {
                 System.out.println("Document #" + i + " is : " + documentList.getFileName(results[i]));
             }
         }
    }
}
