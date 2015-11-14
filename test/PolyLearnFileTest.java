/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import hatalskan.Exam;
import hatalskan.ExamKey;
import hatalskan.PolyLearnFile;
import hatalskan.Student;
import hatalskan.StudentExam;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import junit.framework.TestCase;

/**
 *
 * @author Ryan
 */
public class PolyLearnFileTest extends TestCase {
    
    public PolyLearnFileTest(String testName) {
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
    public void testFindStudentName(){
        PolyLearnFile pl = new PolyLearnFile(new File("." +
                File.separator + "TestFiles_External" + 
                File.separator + "testPolyLearn.csv"));
        String stud1ID = "stud1";
        String blankID = "blah";
        
        String[] stud1Name;
        String[] blankName;
        
        stud1Name = pl.findStudentName(stud1ID);
        Student one = new Student(stud1ID, stud1Name);
        assertEquals("One", one.getFirstName());
        assertEquals("Student",one.getLastName());
        blankName = pl.findStudentName(blankID);
        Student blank = new Student(blankID, blankName);
        assertEquals("", blank.getFirstName());
        assertEquals("", blank.getLastName());
    }
    
    public void testWriteFeedback() {
        String expected = "First name,Last name,ID number,"
                + "Email address,Assignment:Test Assignment,"
                + "Assignment:Test Assignment (Feedback)\n"
                + "One,Student,EB6DCCDC14D41E21400045474181C308,stud1@calpoly.edu,"
                + "3.00,\"1:A,2:B,3:C\"\n"
                + "Two,Student,8142B90AF3A21E014000418145470FEB,stud2@calpoly.edu,"
                + "-,\"Did not take exam\"\n"
                + "Three,Student,E2AA120636A01E214000454741817943,stud3@calpoly.edu,"
                + "-,\"Did not take exam\"";
        
        File poly = new File("." + File.separator + "TestFiles_External" + 
                File.separator + "testPolyLearn.csv");
        String[] stud1Name = {"One","Student"};
        Student bob = new Student("stud1", stud1Name);
        ArrayList<String> ans = new ArrayList<>(Arrays.asList("A","B","C"));
        StudentExam st = new StudentExam("testFeed", "." + File.separator +
                "TestFiles_External", ans, bob);
        st.setCorrectCount(3);
        st.setNumberOfQuestions(3);
        
        ExamKey key = new ExamKey("testFeedKey","." + File.separator +
                "TestFiles_External",ans);
        key.setNumberOfQuestions(3);
        Exam ex = new Exam(new ArrayList<StudentExam>(Arrays.asList(st)), key);
        PolyLearnFile pl = new PolyLearnFile(poly);
        String actual = pl.writePolyLearnFile(new ArrayList<Exam>
                                              (Arrays.asList(ex)), 
               false, new File("." + File.separator + "TestFiles_External"), 
               "." + File.separator + "TestFiles_External");
        
        assertEquals(expected, actual);
    }
    
    public void testWriteWithCorrect() {
        String expected = "First name,Last name,ID number,Email address,"
                + "Assignment:Test Assignment,"
                + "Assignment:Test Assignment (Feedback)\n"
                + "One,Student,EB6DCCDC14D41E21400045474181C308,stud1@calpoly.edu,"
                + "3.00,\"1:A[A],2:B[B],3:C[C]\"\n"
                + "Two,Student,8142B90AF3A21E014000418145470FEB,stud2@calpoly.edu,"
                + "-,\"Did not take exam\"\n"
                + "Three,Student,E2AA120636A01E214000454741817943,stud3@calpoly.edu,"
                + "-,\"Did not take exam\"";
        
        File poly = new File("." + File.separator + "TestFiles_External" + 
                File.separator + "testPolyLearn.csv");
        String[] stud1Name = {"One","Student"};
        Student bob = new Student("stud1", stud1Name);
        ArrayList<String> ans = new ArrayList<>(Arrays.asList("A","B","C"));
        StudentExam st = new StudentExam("testFeed", "." + File.separator + 
                "TestFiles_External", ans, bob);
        st.setCorrectCount(3);
        st.setNumberOfQuestions(3);
        
        ExamKey key = new ExamKey("testFeedKey","." + File.separator + 
                "TestFiles_External",ans);
        key.setNumberOfQuestions(3);
        Exam ex = new Exam(new ArrayList<StudentExam>(Arrays.asList(st)), key);
        PolyLearnFile pl = new PolyLearnFile(poly);
        String actual = pl.writePolyLearnFile(new ArrayList<Exam>
                                             (Arrays.asList(ex)), 
               true, new File("." + File.separator + "TestFiles_External"), 
               "." + File.separator + "TestFiles_External");
        
        assertEquals(expected, actual);
    }
}