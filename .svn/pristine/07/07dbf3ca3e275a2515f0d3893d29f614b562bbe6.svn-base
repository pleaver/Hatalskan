/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import junit.framework.TestCase;
import hatalskan.Student;

/**
 *
 * @author Door Guardians
 */
public class StudentTest extends TestCase {
    
    public StudentTest(String testName) {
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
    
    public void testStudent()
    {
        String testID = "test";
        String testFirstName = "first";
        String testLastName = "last";
        String[] testName = {testFirstName, testLastName};
        
        Student testStudent = new Student(testID, testName);
        
        assertEquals(testStudent.getFirstName(), testFirstName);
        assertEquals(testStudent.getLastName(), testLastName);
        assertEquals(testStudent.getFullName(), 
                testFirstName + " " + testLastName);
        assertEquals(testStudent.getId(), testID);
    }
}
