/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import junit.framework.TestCase;
import hatalskan.ExamKey;
import java.util.ArrayList;

/**
 *
 * @author Door Guardians
 */
public class ExamKeyTest extends TestCase {
    
    public ExamKeyTest(String testName) {
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

    /*public void testSetFileName()
    {
        //ExamKey examKey = new ExamKey("gradeKey");
        ExamKey examKey = new ExamKey(null,null,null);
        examKey.setFileName("gradeKey");
        examKey.setFileName("gradeTestForm");
        assertEquals("gradeTestForm", examKey.getFileName());
    }*/
    
    public void testSetFilePath()
    {
        //ExamKey examKey = new ExamKey("keyFileName", "originPath");
        ExamKey examKey = new ExamKey(null,"pathToFile",null);
        //examKey.setFileName("keyFileName");
        //examKey.setFilePath("originPath");
        //examKey.setFilePath("pathToFile");
        assertEquals("pathToFile", examKey.getFilePath());
    }
    
    public void testSetAnswers()
    {
        String[] answers = new String[] {"A", "C D", "C", "D", "D E" };
        ArrayList<String> answerList = new ArrayList<String>();
        for (String answer : answers)
        {
            answerList.add(answer);
        }
        ExamKey examKey = new ExamKey(null,null,null);
        examKey.setAnswers(answerList);
        assertEquals(answerList, examKey.getAnswers());
    }
}
