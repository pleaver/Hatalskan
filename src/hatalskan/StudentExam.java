package hatalskan;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Represents a marked student exam owned by a student
 * @author Door Guardians
 */
public class StudentExam extends ExamKey 
{
    private int correctCount;
    private int incorrectCount;
    private double percent;
    private Student owner;
    
    /**
     * Default Constructor
     */
    /*public StudentExam()
    {
        correctCount = 0;
        incorrectCount = 0;
    }*/
    
    /**
     * Constructor
     * @param name exam name
     * @param path file path to exam
     */
    /*public StudentExam(String name, String path)
    {
        //super(name, path);
        super();
        super.setFileName(name);
        super.setFilePath(path);
        correctCount = 0;
        incorrectCount = 0;
    }*/
    
    /**
     * Constructor
     * @param name exam name
     * @param path file path to exam
     * @param owner student
     */
    /*public StudentExam(String name, String path, Student owner)
    {
        //super(name, path);
        super();
        super.setFileName(name);
        super.setFilePath(path);
        this.owner = owner;
        correctCount = 0;
        incorrectCount = 0;
    }*/
    
    /**
     * Constructor
     * @param name exam name
     * @param path file path to exam
     * @param answers list of responses
     */
    /*public StudentExam(String name, String path, ArrayList<String> answers)
    {
        super(name, path, answers);
        correctCount = 0;
        incorrectCount = 0;
    }*/
    
    /**
     * Constructor
     * @param name exam name
     * @param path file path to exam
     * @param answers list of responses
     * @param owner student
     */
    public StudentExam(String name, String path, 
                        ArrayList<String> answers, Student owner)
    {
        super(name, path, answers);
        correctCount = 0;
        incorrectCount = 0;
        this.owner = owner;
    }
    
    /**
     * Gets the number of correct responses
     * @return Returns correct responses
     */
    public int getCorrectCount()
    {
        return correctCount;
    }
    
    /**
     * Gets the number of incorrect responses
     * @return Returns incorrect responses
     */
    public int getIncorrectCount()
    {
        return incorrectCount;
    }
    
    /**
     * Gets the student who marked the exam
     * @return Returns Student owner
     */
    public Student getOwner()
    {
        return owner;
    }
    
    /**
     * Gets the percent points acquired
     * @return Returns percent points in a formatted string
     */
    public String getPercent()
    {
        DecimalFormat formatter = new DecimalFormat("0.00");
        String percentFormatted = formatter.format(this.percent);
        return percentFormatted;
    }
    
    /**
     * Sets the number of correct responses
     * @param corrCount number of correct responses
     */
    public void setCorrectCount(int corrCount)
    {
        correctCount = corrCount;
    }
    
    /**
     * Sets the number of incorrect responses
     * @param incorrCount number of incorrect responses
     */
    public void setIncorrectCount(int incorrCount)
    {
        incorrectCount = incorrCount;
    }
    
    /**
     * Sets the student owner
     * @param owner student owner
     */
    public void setOwner(Student owner)
    {
        this.owner = owner;
    }
    
    /**
     * Sets the percent points
     * @param grade new percent points
     */
    public void setPercent(double grade)
    {
        this.percent = grade;
    }
}
