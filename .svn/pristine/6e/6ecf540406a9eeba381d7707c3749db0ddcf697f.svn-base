/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatalskan;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * An Exam represents a single exam made up of student responses, statistics,
 * and a version. It can report any of its fields.
 * @author Door Guardians
 */
public class Exam
{
    // A list of student responses that make up an exam
    private ArrayList<StudentExam> studentExams;
    // The key the exam uses to grade
    private ExamKey key;
    // Statitics that are associated with an exam
    private ExamStatistics stats;
    // The exam's version
    private String ver;
    // The exam's file name
    private String fileName;
    
    /** 
     * Constructor for Exam object.
     * Does not set any statistics.
     * For TESTING purposes ONLY
     */
    public Exam()
    {
        ver = "";
    }
    
    /**
     * Constructor for Exam object
     * @param stExams ArrayList of StudentExams that make up the exam
     * @param key The ExamKey that was used to grade the student responses
     */
    public Exam(ArrayList<StudentExam> stExams, ExamKey key)
    {
        studentExams = stExams;
        this.key = key;
        ver = "";
        stats = new ExamStatistics(this.studentExams, key);
    }
    
    /**
     * Gets the exam key
     * @return ExamKey Key used to grade the exam
     */
    public ExamKey getKey()
    {
        return this.key;
    }
    
    /**
     * Gets the student exam responses
     * @return ArrayList<StudentExam> student exam responses
     */
    public ArrayList<StudentExam> getStudentExams()
    {
        return this.studentExams;
    }
    
    /**
     * Gets the exam statistics
     * @return ExamStatistics object
     */
    public ExamStatistics getStats()
    {   
        return this.stats;
    }
    
    /**
     * Gets the version
     * @return String version
     */
    public String getVersion()
    {
        return this.ver;
    }
    
    /**
     * Sets the exam's version
     * @param version String to set the version to
     */
    public void setVersion(String version)
    {
        this.ver = version;
    }
    
    /**
     * Sets the exam's file name
     * @param name String to set the name to
     */
    public void setFileName(String name)
    {
        this.fileName = name;
    }
    
    /**
     * Gets the exam's file name
     * @return Returns file name
     */
    public String getFileName()
    {
        return this.fileName;
    }
    
    /**
     * Creates a CSV output for the this Exam a the specified output directory
     * @param outputDir The output directory to write the files to
     * @param incorrect The int array with the number of incorrect answers.
     * If null, does not write an incorrect answers section
     *  per question
     * @return Returns content of output csv file
     */
    public String outputExamCSV(File outputDir, int[] incorrect)
    {
        String outputPath = null;
        
        FileWriter writer;
        try
        {
            outputPath = outputDir.getAbsolutePath() + 
                                        File.separator + fileName;
            // CREATE FileWriter to write to file
            writer = new FileWriter(outputPath);
            // WRITE headings for exam-score format of output file
            writer.append("Student,ID,Score,Percent");
            writer.append("\n");
           
            // FOR EACH student response in this exam
            for (StudentExam exam : this.studentExams)
            {
                // WRITE the name of the student
                writer.append(exam.getOwner().getFullName() + ",");
                // WRITE the ID of the student
                writer.append(exam.getOwner().getId() + ",");
                // WRITE the number correct
                writer.append(exam.getCorrectCount() + ",");
                // WRITE the grade
                writer.append(exam.getPercent() + "\n");
            }
            
            // WRITE the total points
            writer.append("Total questions," + 
                    key.getNumberOfQuestions() + "\n");
            
            // WRITE the class average
            writer.append("Class average," + stats.getMeanScore() + ","
                    + stats.getMeanPercent() + "\n");
            
            // WRITE the standard deviation
            writer.append("Standard deviation," + stats.getStDev()+ "\n");
            
            // WRITE the exam high score
            writer.append("High Score," + stats.getHighScore() + "\n");
            
            // WRITE the exam low score
            writer.append("Low Score," + stats.getLowScore() + "\n");
            
            
            // IF an array of incorrect questions is given 
            if (incorrect != null)
            {
                // WRITE the missed question header
                writer.append("Question #"+ "," + "Missed count\n");
                // FOR all the questions in the exam
                for (int idx = 0; idx < incorrect.length; idx++)
                {   
                    // WRITE the question and the number of incorrect responses
                    String questionNum = Integer.toString(idx+1);
                    String numMissed = Integer.toString(incorrect[idx]);
                    writer.append(questionNum + "," + numMissed + "\n");
                }
            }
            // Flush the writer
            writer.flush();
            // Close the writer
            writer.close();
        }
        catch (IOException e)
        {
            System.err.println("Error in writing " + this.fileName
                    + " csv file.");
        }
        
        return outputPath;
    }
}
