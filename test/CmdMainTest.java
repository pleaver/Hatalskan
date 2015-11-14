/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import userInterface.CmdMain;
import userInterface.InterfaceStrings;
import java.io.File;
import java.nio.file.Paths;
import junit.framework.TestCase;

/**
 * Test suite for CmdMain
 * @author Door Guardians
 */
public class CmdMainTest extends TestCase {
    
    public CmdMainTest(String testName) {
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
    
    public void testHandleInputOption()
    {
        CmdMain testMain = new CmdMain();
        String inputTest1 = "TestFiles_External" +
                            File.separator + "inputTest1.pdf";
        String inputTest2 = "TestFiles_External" +
                            File.separator + "inputTest2.pdf";
        
        File[] testFiles1;
        File[] testFiles2;
        
        File[] expectedFiles1 = {Paths.get(inputTest1).toFile(),
                                 Paths.get(inputTest2).toFile()};
        
        testFiles1 = testMain.handleInputOption(inputTest1 + "," + inputTest2);
        testFiles2 = testMain.handleInputOption(null);
        
        assertEquals(testFiles1[0], expectedFiles1[0]);
        assertEquals(testFiles1[1], expectedFiles1[1]);
        assertEquals(testFiles2, null);
    }
    
    public void testHandlePolyOption()
    {
        CmdMain testMain = new CmdMain();
        String polyTest = "TestFiles_External" +
                            File.separator + "poly.csv";
        File testFile1;
        File testFile2;
        
        File expectedFile1 = Paths.get(polyTest).toFile();
        
        testFile1 = testMain.handlePolyOption(polyTest);
        testFile2 = testMain.handlePolyOption(null);
        
        assertEquals(testFile1, expectedFile1);
        assertEquals(testFile2, null);
    }
    
    public void testHandleOutputOption()
    {
        CmdMain testMain = new CmdMain();
        String outputTest = "Results";
        File testFile1;
        File testFile2;
        
        File expectedFile1 = Paths.get(outputTest).toFile();
        File expectedFile2 = new File("").getAbsoluteFile();
        
        testFile1 = testMain.handleOutputOption(outputTest);
        testFile2 = testMain.handleOutputOption(null);
        
        assertEquals(testFile1, expectedFile1);
        assertEquals(testFile2, expectedFile2);
    }
    
    public void testHandleFlagOptions()
    {
        CmdMain testMain = new CmdMain();
        String testString;
        String expectedString;
        
        // Test all flags true
        testString = testMain.handleFlagOptions(true, true, true);
        expectedString = InterfaceStrings.kCmdVersion +
                InterfaceStrings.kCmdHelp + 
                "true";
        assertEquals(expectedString, testString);
        
        // Test all flags false
        testString = testMain.handleFlagOptions(false, false, false);
        expectedString = "false";
        assertEquals(expectedString, testString);
        
        // Test all flags false
        testString = testMain.handleFlagOptions(true, false, false);
        expectedString = InterfaceStrings.kCmdVersion + "false";
        assertEquals(expectedString, testString);
        
        // Test just help
        testString = testMain.handleFlagOptions(false, true, false);
        expectedString = InterfaceStrings.kCmdHelp + "false";
        assertEquals(expectedString, testString);
        
        // Test just version
        testString = testMain.handleFlagOptions(true, false, false);
        expectedString = InterfaceStrings.kCmdVersion + "false";
        assertEquals(expectedString, testString);
        
        // Test version and help
        testString = testMain.handleFlagOptions(true, true, false);
        expectedString = InterfaceStrings.kCmdVersion +
                InterfaceStrings.kCmdHelp + "false";
        assertEquals(expectedString, testString);
        
        // Test just help and answers
        testString = testMain.handleFlagOptions(false, true, true);
        expectedString = InterfaceStrings.kCmdHelp + "true";
        assertEquals(expectedString, testString);
        
        // Test just version and answers
        testString = testMain.handleFlagOptions(true, false, true);
        expectedString = InterfaceStrings.kCmdVersion + "true";
        assertEquals(expectedString, testString);
        
    }
    
    public void testExecute() {
        String []args = new String[1];
        
        // tests that its false when no grading happens
        args[0] = "-v";
        CmdMain cmd = new CmdMain(args);
        assertFalse(cmd.execute());
        
        // tests illegal flags
        args[0] = "-x";
        cmd = new CmdMain(args);
        assertFalse(cmd.execute());
        
        // tests bad value
        args[0] = "-i notPdf.txt";
        cmd = new CmdMain(args);
        assertFalse(cmd.execute());
    }
    
}
