package edu.ecu.cs.csci6030.search;

import org.junit.*;
import org.junit.contrib.java.lang.system.Assertion;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

public class SearchTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private Search search;

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Before
    public void setUpStreams() {
        search = new Search();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void noArgsPrintsInstructions() throws Exception {
        search.main(new String[]{});
        assertThat(outContent.toString(), containsString("Usage: java edu.ecu.cs.csci6030.search.Search [pathToDirectory [maxFiles]]\n"));
    }

    @Test
    public void badPathPrintsError() throws Exception {
        exit.expectSystemExitWithStatus(7);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertThat(outContent.toString(), containsString("notDirectory is not a valid directory"));
            }
        });
        search.main(new String[]{"notDirectory"});
    }

    @Test @Ignore //cannot test in while loop because doesn't return
    public void oneArgsScans() throws Exception {
        search.main(new String[]{"src/test/resources"});
        assertThat(outContent.toString(), containsString("Scanning Directory src/test/resources\nScanned files: 7"));
    }
}