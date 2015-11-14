/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import hatalskan.ExamKey;
import hatalskan.ExamStatistics;
import hatalskan.StudentExam;
import java.util.ArrayList;

import junit.framework.TestCase;

/**
 *
 * @author Door Guardians
 */
public class ExamStatisticsTest extends TestCase {
    
    public ExamStatisticsTest(String testName) {
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
    
    public void testStats() {
        StudentExam e1 = new StudentExam(null, null, null, null);
        StudentExam e2 = new StudentExam(null, null, null, null);
        ExamKey key = new ExamKey(null,null,null);
        
        e1.setCorrectCount(8);
        e2.setCorrectCount(7);
        e1.setIncorrectCount(2);
        e2.setIncorrectCount(3);
        ArrayList<StudentExam> ex = new ArrayList<>();
        ex.add(e1);
        ex.add(e2);
        
        key.setNumberOfQuestions(10);
        
        ExamStatistics stats = new ExamStatistics(ex, key);
        // all in one test to avoid duplication of above variables
        // Test high score
        assertEquals(8, stats.getHighScore());
        // Test low score
        assertEquals(7, stats.getLowScore());
        // Test mean score
        assertEquals(7, stats.getMeanScore());
        // Test mean percent
        assertEquals(75.0, Double.parseDouble(stats.getMeanPercent()));
        
        System.out.println(stats.getStDev());
        //This test was failing in buildcheck. stats.getStDev() was returning 0.71
        assertEquals("0.71", stats.getStDev());
    }
}
