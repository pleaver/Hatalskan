/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import hatalskan.ExamAnalyzer;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import junit.framework.TestCase;

/**
 *
 * @author Door Guardians
 */
public class ExamAnalyzerTest extends TestCase {
    
    public ExamAnalyzerTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
    public void testAnswerList() {
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("a", 
                "b", "c"));
        ExamAnalyzer analyzer = new ExamAnalyzer();
        // test file is included
        ArrayList<String> actual = analyzer.createAnswerKey("TestFiles_External" + File.separator + "testAnswerList");
        assertEquals(expected, actual);
    }
    
    public void testGetExamID() {
        String expected = "12345";
        ExamAnalyzer lyzer = new ExamAnalyzer();
        String actual = lyzer.getExamId("TestFiles_External" + File.separator + "testGetExamID.dat");
        assertEquals(expected, actual);
    }
    
}
