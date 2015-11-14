/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import hatalskan.Exam;
import hatalskan.ExamKey;
import hatalskan.ExamStatistics;
import hatalskan.Student;
import hatalskan.StudentExam;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

/**
 * Test suite for ExamTest
 * @author Door Guardians
 */
public class ExamTest extends TestCase {
    
    public ExamTest(String testName) {
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

    // TODO getKey, getStats, getStudentExams
    public void testGetKey()
    {
        Exam testExam;
        StudentExam testStExam;
        ArrayList<StudentExam> testStExams;
        ExamKey testKey;
        ExamStatistics testStats;
        String[] studentName = {"first", "last"};
        ArrayList<String> responses = new ArrayList<>();
        responses.add("A");
        
        testStExam = new StudentExam("TestExam", "/this/test", 
                responses, new Student("testID", studentName));
        testStExams = new ArrayList();
        testStExams.add(testStExam);
        
        testKey = new ExamKey("testKey", "/key/path", responses, 1);
        
        testExam = new Exam(testStExams, testKey);
        
        testStats = new ExamStatistics(testStExams, testKey);
        
        assertEquals(testExam.getKey(), testKey);
        assertEquals(testExam.getStudentExams(), testStExams);
        // Test stats
        assertEquals(testExam.getStats().getHighScore(),
                testStats.getHighScore());
        assertEquals(testExam.getStats().getLowScore(),
                testStats.getLowScore());
        assertEquals(testExam.getStats().getMeanScore(),
                testStats.getMeanScore());
        
    }
    
    public void testGetSetVersion()
    {
        Exam testExam = new Exam();
        String test;
        
        testExam.setVersion("TestVersion");
        test = testExam.getVersion();
        
        assertEquals(test, "TestVersion");
    }
    
    public void testGetSetFileName()
    {
        Exam testExam = new Exam();
        String test;
        
        testExam.setFileName("TestFileName");
        test = testExam.getFileName();
        
        assertEquals(test, "TestFileName");
    }
    
    public void testOutputExamCSV()
    {
        FileWriter testWriter;
        Exam testExam;
        ArrayList<StudentExam> stExams = new ArrayList<>();
        int[] incorrect = {1};
        File testOutputDir;
        String[] studentName = {"First", "Last"};
        
        // Output Directory of the tests
        testOutputDir = Paths.get("TestFiles_External").toFile();
        
        Student student1 = new Student("id", studentName);
        
        // Set up the studentExam
        StudentExam stExam = new StudentExam("stExam1", null, null, student1);
        stExam.setCorrectCount(0);
        stExam.setIncorrectCount(1);
        stExam.setPercent(0.0);
        // Add to the array
        stExams.add(stExam);
        
        // Set up the key
        ExamKey key = new ExamKey("testKey", null, null, 1);
        
        // Create the Exam
        testExam = new Exam(stExams, key);
        
        // Test with an incorrect answers array
        testExam.setFileName("testExam1_output.csv");
        String testOneOutput = testExam.outputExamCSV(testOutputDir, incorrect);
        
        // Test without an incorrect array
        testExam.setFileName("testExam2_output.csv");
        String testTwoOutput = testExam.outputExamCSV(testOutputDir, null);
        
        try {
            // Assert that testOneOutput is the same as an expected file
            assertTrue(FileUtils.contentEquals(new File(
                    testOutputDir.getAbsolutePath() + File.separator +
                    "examOutTest1.csv"), new File(testOneOutput)));
        } catch (IOException ex) {
            Logger.getLogger(ExamTest.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        
        try {
            // Assert that testTwoOutput is the same as an expected file
            assertTrue(FileUtils.contentEquals(new File(
                    testOutputDir.getAbsolutePath() + File.separator +
                    "examOutTest2.csv"), new File(testTwoOutput)));
        } catch (IOException ex) {
            Logger.getLogger(ExamTest.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
}
