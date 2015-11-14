/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import hatalskan.ExamAnalyzer;
import hatalskan.ExamKey;
import hatalskan.ExamScorer;
import hatalskan.StudentExam;
import hatalskan.Student;
import hatalskan.Exam;
import hatalskan.PolyLearnFile;
import java.io.File;
import java.util.ArrayList;
import junit.framework.TestCase;

/**
 * Test suite for ExamScorer
 * @author Door Guardians
 */
public class ExamScorerTest extends TestCase {
    
    public ExamScorerTest(String testName) {
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

    public void testCreateTempDirectory()
    {
        ExamScorer examScorer;
        
        examScorer = new ExamScorer();
        
        File tempDir = new File("." + File.separator + "temp");
        
        assertEquals(examScorer.createTempDirectory(),
                tempDir.getAbsolutePath());
    }
    
    private ArrayList<String> convertArrayToList(String[] answers)
    {
        ArrayList<String> answerList = new ArrayList<String>();
        for (String ans : answers)
        {
            answerList.add(ans);
        }
        return answerList;
    }
    
    public void testCreateExamKey()
    {
        ExamScorer examScorer;
        
        examScorer = new ExamScorer();
        
        File examKeyFile = new File("TestFiles_External" + File.separator + "testExamKey.png");
        String examTemplate = "newscantron_v1.8.png";
        ExamAnalyzer analyzeData = new ExamAnalyzer();
        
        ExamKey examKeyResult = 
               examScorer.createExamKey(examKeyFile, examTemplate, analyzeData);
        
        int numQuestions = 10;
        
        String[] answers = new String[] {"A", "B C", "D", "A E",
            "B D", "C", "A B", "D", "C", "B"};
        ArrayList<String> keyAnswers = convertArrayToList(answers);
        
        String examKeyName = examKeyFile.getName();
        String filePath = examKeyFile.getAbsolutePath();
        
        assertEquals(keyAnswers, examKeyResult.getAnswers());
        assertEquals(numQuestions, examKeyResult.getNumberOfQuestions());
        assertEquals(examKeyName, examKeyResult.getFileName());
        assertEquals(filePath, examKeyResult.getFilePath());
    }
    
    /*
    public void testCreateStudentExams()
    {
        ExamScorer examScorer = new ExamScorer();
        
        ExamAnalyzer analyzeData = new ExamAnalyzer();
        File[] studentExamFiles = new File[] { new File("TestFiles_External" + File.separator + "testStudentExam1.png"),
            new File("TestFiles_External" + File.separator + "testStudentExam2.png") };
        String examTemplate = "newscantron_v1.8.png";
        
        ArrayList<StudentExam> studentExamsResult = 
            examScorer.createStudentExams(studentExamFiles, examTemplate, analyzeData);
        
        String[] examIds = new String[] { "bob45", "ackerman" };
        String[] answers1 = new String[] {"A", "D E", "B C", "A C E",
            "A", "B C", "B", "C", "D E", "B" };
        String[] answers2 = new String[] {"A", "B C", "D", "E", "A E",
            "C D", "D", "B", "C", "B"};
        ArrayList<String> answerList1 = convertArrayToList(answers1);
        ArrayList<String> answerList2 = convertArrayToList(answers2);
        ArrayList<ArrayList<String>> testAnswers = new ArrayList<ArrayList<String>>();
        testAnswers.add(answerList1);
        testAnswers.add(answerList2);
        
        int count = 0;
        for (StudentExam exam : studentExamsResult)
        {
            assertEquals(examIds[count], exam.getOwner().getId());
            assertEquals(0, exam.getCorrectCount());
            assertEquals(0, exam.getIncorrectCount());
            assertEquals(0.0, exam.getPercent());
            assertEquals(studentExamFiles[count].getName(), exam.getFileName());
            assertEquals(studentExamFiles[count].getAbsolutePath(), exam.getFilePath());
            assertEquals(testAnswers.get(count), exam.getAnswers());            
            count++;
        }
    }
    */
    
    public void testSetStudentExamCorrectCount()
    {
        ExamScorer examScorer = new ExamScorer();
        
        File key = new File("TestFiles_External" + File.separator + "testExamKey.png");
        File exam = new File("TestFiles_External" + File.separator + "testStudentExam1.png");
        
        String[] keyAnswers = new String[] {"A", "B", "C", "D", "E"};
        String[] examAnswers = new String[] {"A", "B", "C", "B", "C E"};
        ArrayList<String> keyAnswerList = convertArrayToList(keyAnswers);
        ArrayList<String> examAnswerList = convertArrayToList(examAnswers);
        
        ExamKey testKey = new ExamKey(key.getName(), key.getAbsolutePath(), keyAnswerList, 5);
        StudentExam testExam = new StudentExam(exam.getName(), exam.getAbsolutePath(), examAnswerList, null);
        
        examScorer.setStudentExamCorrectCount(testExam, testKey);
        
        assertEquals(3, testExam.getCorrectCount());
    }
    
    public void testSetStudentExamGrade()
    {
        ExamScorer examScorer = new ExamScorer();
        
        StudentExam testExam = new StudentExam(null, null, null, null);
        testExam.setCorrectCount(8);
        examScorer.setStudentExamGrade(testExam, 10);
        assertEquals("80.00", testExam.getPercent());
    }
    
    public void testStartScoring()
    { 
        File[] pdfFiles = new File[] { new File("PDF" + File.separator + "v1.8_test.pdf") };
        File outputDir = new File("Results");
        File csv = new File("TestFiles_External" + File.separator + "testPoly.csv");
        
        ExamScorer examScorer = new ExamScorer(pdfFiles, outputDir, 
            csv, Boolean.FALSE);
        
        examScorer.startScoring();
        
        Exam cumulativeExam = examScorer.getCumulative();
        ArrayList<StudentExam> studentExams = examScorer.getStudentExams();
        
        //check the contents of studentexam 1
        String[] answers1 = new String[] { "A", "B C", "D", "A",
            "A", "A E", "B C", "A B", "A C", "B" };
        ArrayList<String> answerList1 = convertArrayToList(answers1);
        assertEquals(answerList1, studentExams.get(0).getAnswers());
        assertEquals("ypan01", studentExams.get(0).getOwner().getId());
        assertEquals("Yang", studentExams.get(0).getOwner().getFirstName());
        assertEquals("Pang", studentExams.get(0).getOwner().getLastName());
        assertEquals(4, studentExams.get(0).getCorrectCount());
        assertEquals(6, studentExams.get(0).getIncorrectCount());
        assertEquals("40.00", studentExams.get(0).getPercent());
        
        //check the contents of studentexam 2
        String[] answers2 = new String[] { "A", "D E", "B C", "A C E",
            "A", "B C", "B", "C", "D E", "B" };
        ArrayList<String> answerList2 = convertArrayToList(answers2);
        assertEquals(answerList2, studentExams.get(1).getAnswers());
        assertEquals("bob45", studentExams.get(1).getOwner().getId());
        assertEquals("Bob", studentExams.get(1).getOwner().getFirstName());
        assertEquals("Clark", studentExams.get(1).getOwner().getLastName());
        assertEquals(2, studentExams.get(1).getCorrectCount());
        assertEquals(8, studentExams.get(1).getIncorrectCount());
        assertEquals("20.00", studentExams.get(1).getPercent());
        
        //check the contents of studentexam 3
        String[] answers3 = new String[] { "A", "B C", "D", "E", 
            "A E", "C D", "D", "B", "C", "B" };
        ArrayList<String> answerList3 = convertArrayToList(answers3);
        assertEquals(answerList3, studentExams.get(2).getAnswers());
        assertEquals("ackerman", studentExams.get(2).getOwner().getId());
        assertEquals("William", studentExams.get(2).getOwner().getFirstName());
        assertEquals("Book", studentExams.get(2).getOwner().getLastName());
        assertEquals(5, studentExams.get(2).getCorrectCount());
        assertEquals(5, studentExams.get(2).getIncorrectCount());
        assertEquals("50.00", studentExams.get(2).getPercent());
        
        String[] cumulativeKeyAnswers = new String[] { "A", "B C", "D", "A E", 
            "B D", "C", "A B", "D", "C", "B" };
        ArrayList<String> cumulativeKeyAnswerList = convertArrayToList(cumulativeKeyAnswers);
        assertEquals(cumulativeKeyAnswerList, cumulativeExam.getKey().getAnswers());
        assertEquals(10, cumulativeExam.getKey().getNumberOfQuestions());
        assertEquals(5, cumulativeExam.getStats().getHighScore());
        assertEquals(2, cumulativeExam.getStats().getLowScore());
        assertEquals(3, cumulativeExam.getStats().getMeanScore());
        assertEquals("Cumulative", cumulativeExam.getVersion());
        
        
    }
    
    public void testCmdExecute()
    {
        File[] pdfFiles = new File[] { new File("PDF" + File.separator + "v1.8_test.pdf") };
        File outputDir = new File("Results");
        File csv = new File("TestFiles_External" + File.separator + "testPoly.csv");
        
        ExamScorer examScorer = new ExamScorer(pdfFiles, outputDir, 
            csv, Boolean.FALSE);
        boolean didExecute = examScorer.cmdExecute();
        assertTrue(didExecute);
    }
    
    
}
