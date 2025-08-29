package ua.od.zakhariya;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FXMLExampleMainTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FXMLExampleMainTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(FXMLExampleMainTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue( true );
    }

}