/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import junit.framework.TestCase;
import hatalskan.Student;
import hatalskan.StudentExam;

/**
 *
 * @author Door Guardians
 */
public class StudentExamTest extends TestCase {
    
    public StudentExamTest(String testName) {
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

    public void testSetOwner()
    {
        String[] stud1Name = {"stud", "1"};
        String[] stud2Name = {"stud", "2"};
        String[] stud3Name = {"stud", "3"};
        
        Student stud1 = new Student("stud1ID", stud1Name);
        StudentExam studExam = new StudentExam("examFileName", "examFilePath", null, null);
        studExam.setOwner(stud1);
        assertEquals(stud1, studExam.getOwner());
        
        Student stud2 = new Student("stud2ID", stud2Name);
        StudentExam studExam2 = new StudentExam("filename", "filepath", null, null);
        studExam2.setOwner(stud2);
        Student stud3 = new Student("stud3ID", stud3Name);
        studExam2.setOwner(stud3);
        assertEquals(stud3, studExam2.getOwner());
    }
}
