package hatalskan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;
 
/**
 * Represents a PolyLearn file that can be read from and written to.
 * Names can be read from the file to associate with student IDs, and 
 * feed back can be written to students. 
 * @author Door Guardians
 */
public class PolyLearnFile 
{
    
    final static int kIdNdx = 3;
    final static int kNameNdx = 1;
    final static int kUserNdx = 2;
   
    private File polyFile;
    private BufferedReader br;
    private String line;
    private String csvSplitBy;
   
   /**
    * Constructs a PolyLearnFile to parse a csv file 
    * @param csv file to parse
    */
    public PolyLearnFile(File csv)
    {
        // Should probably pass CSV File to the object
        this.polyFile = csv; 
        this.br = null;
    }
    
    /**
     * This function searches through the PolyLearn csv file 
     * for the first and last names of the Student passed in.
     * @param userName Student to look for in the PolyLearn csv file 
     * @return name of student of the form [firstName, lastName]
     */
    public String[] findStudentName(String userName) 
    {
        String input;
        
        String[] name = {"", ""};
        
        try 
        {
            //Reader for the PolyLearn file
            br = new BufferedReader(new FileReader(polyFile));
            //Reads the first line of the PolyLearn file
            input = br.readLine();
            //LOOP through file until the EOF
            while(input != null) 
            {
                input = br.readLine();
                //IF line is not empty of EOF
                if(input != null) 
                {
                    String[] lineInfo = input.split(",");
                    //IF current line of the poly file contains the student id
                    if(lineInfo[3].equals(userName.toLowerCase() 
                            + "@calpoly.edu")) 
                    {
                        //Assign first and last name to the student.
                        name[0] = lineInfo[0];
                        name[1] = lineInfo[1];
                    }
                    //END IF
                }
                //END IF
            }
            //END LOOP
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            // IF file is empty
            if (br != null) 
            {
                try 
                {
                    //Close reader
                    br.close();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
            // END IF
        }
        
        return name;
    }
    
        /**
     * Creates and writes the csv output file for PolyLearn. It includes
     * the student's name, the student's score, and possible feedback on
     * the student's performance on the exam.
     * @param exams - List of different exams
     * @param showAnswers - Boolean if correct answers to exam will be included
     * @param outputDir - Output directory to store output PolyLearn file
     * @param tempDir - Temporary directory to hold temp objects
     * @return content of polylearn file
     */
    public String writePolyLearnFile(ArrayList<Exam> exams,
                                    boolean showAnswers, File outputDir,
                                    String tempDir) 
    {
        String outputString = "";
        BufferedReader buffReader;
        String input = "";
        String newLine = "";
        
        try 
        {
            //Creates temporary csv file for editing
            File outputCSV = new File(outputDir + 
                    File.separator + "Hatalskan_PolyLearn.csv");
            //Writer to that temp csv file
            FileWriter writer = new FileWriter(outputCSV);
            //Reader to the PolyLearn file
            buffReader = new BufferedReader(new FileReader(polyFile));
            //Append the first line; header of the PolyLearn file
            writer.append(buffReader.readLine() + "\n");
            
            boolean tookExam = false;
            StringBuilder strbuild = new StringBuilder("");
            //LOOP until EOF
            while((input = buffReader.readLine()) != null) 
            {
                tookExam = false;
                strbuild = new StringBuilder(input);

                //FOR every version of the exam
                for(Exam exs : exams) 
                {
                    ArrayList<StudentExam> stExams = exs.getStudentExams();
                    //FOR every exam taken by a student
                    for(StudentExam stExam: stExams) 
                    {
                        //IF the student is in the PolyLearn File
                        if(input.contains("," + stExam.getOwner()
                                    .getId().toLowerCase() + "@calpoly.edu,")) 
                        {
                            //Write student name
                            //CREATE new string builder to append to CSV
                            newLine = input.substring(0, input.length()-2);
                            strbuild = new StringBuilder(newLine);

                            //FORMAT student's score for PolyLearn format
                            NumberFormat format = new DecimalFormat("0.00");

                            //SET student score
                            strbuild.append(format.format(
                                    stExam.getCorrectCount()) + ",");

                            //Total number of questions in exam
                            int numQuestions = stExam.getNumberOfQuestions();

                            // Append student answers and 
                            // correct answers in feedback
                            strbuild.append("\"");
                            //FOR each question in the exam
                            for(int ndx = 0; ndx < numQuestions; ndx++) 
                            {
                                // Print the question number (starting from 1)
                                strbuild.append((ndx+1) + ":" + 
                                        stExam.getAnswers().get(ndx));
                                // IF the showAnswers flag is set, give the 
                                // correct answer                            
                                if(showAnswers) 
                                {
                                    strbuild.append("[" + 
                                        exs.getKey().getAnswers().get(ndx) 
                                        + "]");
                                }
                                //END IF

                                //IF it's the last question do not append comma
                                if (ndx < numQuestions - 1) 
                                {
                                    strbuild.append(",");
                                //END IF
                                }
                            }
                            strbuild.append("\"");
                            tookExam = true;
                        }
                        //END IF
                    }
                    // END FOR LOOP
                }
                // END LOOP for Versions
                //IF exam was not taken
                if(!tookExam)
                {
                    strbuild.append("\"Did not take exam\"");
                }
                //END IF
                writer.write(strbuild.toString());
                writer.write("\n");
            }
            //END LOOP
            
            //Flush the stream of writing and close Writer
            writer.flush();
            writer.close();

            // This fixes defect #95
            System.gc();
            
            //Assigns the contents of the output file to a string to return
            outputString = new Scanner(outputCSV).useDelimiter("\\Z").next();
            //System.out.println(outputString);
        }
        catch(IOException e) 
        {
            e.printStackTrace();
        }
        return outputString;
    }
    
}
