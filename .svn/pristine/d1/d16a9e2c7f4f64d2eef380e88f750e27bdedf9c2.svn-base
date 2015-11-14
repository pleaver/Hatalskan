package hatalskan;

import java.util.ArrayList;

/**
 * Represents a structure to store an exam's list of responses
 * including the name and location of the file.
 * @author Door Guardians
 */
public class ExamKey 
{
    private String fileName;
    private String filePath;
    private ArrayList<String> answers;
    private int numberOfQuestions;
    
    /**
     * Constructor for Exam that has no marks.
     * @param name name of the exam
     * @param path filepath to the exam
     */
    /*public ExamKey(String name, String path)
    {
        fileName = name;
        filePath = path;
    }*/
    
    /**
     * Constructor for Exam that has been marked.
     * @param name name of the exam
     * @param path filepath to the exam
     * @param answers An array of student answers
     */
    public ExamKey(String name, String path, ArrayList<String> answers)
    {
        fileName = name;
        filePath = path;
        this.answers = answers;
    }
    
    /** 
     * Constructor for Exam with marks and a specified number of questions.
     * @param name name of the exam
     * @param path filepath to the exam
     * @param answers the responses to the exam
     * @param count max number of questions
     */
    public ExamKey(String name, String path, 
                ArrayList<String> answers, int count)
    {
        fileName = name;
        filePath = path;
        this.answers = answers;
        numberOfQuestions = count;
    }
    
    /**
     * Gets the name of exam file.
     * @return Returns name of exam file
     */
    public String getFileName()
    {
        return fileName;
    }
    
    /**
     * Gets the file path of exam.
     * @return Returns file path to exam
     */
    public String getFilePath()
    {
        return filePath;
    }
    
    /**
     * Gets the exam responses.
     * @return Returns ArrayList of response
     */
    public ArrayList<String> getAnswers()
    {
        return answers;
    }
    
    /**
     * Gets the number of questions on exam.
     * @return Returns max number of questions
     */
    public int getNumberOfQuestions()
    {
        return numberOfQuestions;
    }
    
    /**
     * Sets file name.
     * @param name new file name
     */
    /*public void setFileName(String name)
    {
        fileName = name;
    }*/
    
    /**
     * Sets file path.
     * @param path new file path
     */
    /*public void setFilePath(String path)
    {
        filePath = path;
    }*/
    
    /**
     * Sets the list of answers.
     * @param answers new list of answers
     */
    public void setAnswers(ArrayList<String> answers)
    {
        this.answers = answers;
    }
    
    /**
     * Sets the number of questions on exam.
     * @param numQues new max number of questions
     */
    public void setNumberOfQuestions(int numQues)
    {
        numberOfQuestions = numQues;
    }
}
