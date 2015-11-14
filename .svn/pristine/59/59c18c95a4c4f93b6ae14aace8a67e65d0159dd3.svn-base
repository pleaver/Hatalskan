package hatalskan;

import java.util.*;
import java.io.*;

/**
 * Responsible for generating the answer keys, answers, and exams.
 * The class generates them by accessing the .dat files and other files.
 * 
 * @author Door Guardians
 */
public class ExamAnalyzer 
{ 
    private boolean[] isCorrect;
    private ArrayList<String> answerKey;
    private ArrayList<String> studentExam;
    private ArrayList<ArrayList<String>> studentExams;
    
    /**
     * Constructs a ExamAnalyzer instance.
     */
    public ExamAnalyzer()
    {
        studentExams = new ArrayList<ArrayList<String>>();
    }
    
    /**
     * Reads in the data file that contains the correct answers.
     * @param dataFile File containing the answers.
     * @return The list of answers in order.
     */
    public ArrayList<String> answerList(String dataFile)
    {
        ArrayList<String> answer = new ArrayList<String>();
        String line;
        try 
        {
            FileInputStream fis = new FileInputStream(dataFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            
            line = br.readLine();
            // WHILE BufferedReader doesn't reach EOF and line is not empty
            while (line != null && !line.equals(""))
            {
                // IF first character of line is Q
                if (line.charAt(0) == 'Q')
                {
                    int ndx = 0;
                    // WHILE the line's character not equal to =
                    while (line.charAt(ndx) != '=')
                    {
                        // INCREMENT counter
                        ndx++;
                    }
                    // END WHILE
                    // INCREMENT Counter
                    ndx++;
                    
                    // IF line is greater than counter
                    if (line.length() != ndx) 
                    {
                        // ADD substring of the appropriate length
                        answer.add(line.substring(ndx));
                    }
                    // ELSE question was not answered
                    else 
                    {
                        answer.add("");
                    }
                    // END IF
                }
                // END IF
                
                line = br.readLine();
            }
            // END WHILE
            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
        //RETURN list of answers
        return answer;
    }
    
    /**
     * Gets the exam ID, also known as the student ID, for each individual
     * exam that was graded.
     * @param dataFile File that contains the test information.
     * @return Exam ID.
     */
    public String getExamId(String dataFile)
    {
        StringBuilder studentId = new StringBuilder();
        String line;
        try 
        {
            FileInputStream fis = new FileInputStream(dataFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            
            line = br.readLine();
            // WHILE BufferedReader doesn't reach EOF and line is not empty
            while (line != null && !line.equals(""))
            {
                // IF the first character of the line is I (exam ID number)
                if (line.charAt(0) == 'I')
                {
                    // SET exam ID number
                    char idNum = line.charAt(line.length()-1);
                    // IF exam ID is actually a number
                    if (idNum != '=' && idNum != 'l')
                    {
                        // ADD exam ID to the student ID
                        studentId.append(idNum);
                    }
                    // END IF
                }
                // END IF
                line = br.readLine();
            }
            // END WHILE
            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
        
        String id = studentId.toString().toLowerCase();
        // RETURN Student/Exam ID
        return id;
    }

    
    /**
     * Creates a list from the answer key.
     * @param answerKeyFile File name that contains the answer key.
     * @return List of correct answers.
     */
    public ArrayList<String> createAnswerKey(String answerKeyFile)
    {
        answerKey = answerList(answerKeyFile + ".dat");
        return answerKey;
    }
    
     /**
     * Creates a list of student answers.
     * @param studentExamFile Exam file that contains student answers.
     * @return List of student answers.
     */
    public ArrayList<String> createStudentAnswers(File studentExamFile)
    {
        ArrayList<String> answers
            = answerList(studentExamFile.getAbsolutePath() + ".dat");
        return answers;
    }
}
