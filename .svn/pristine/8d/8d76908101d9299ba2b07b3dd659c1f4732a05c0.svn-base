package hatalskan;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.SwingWorker;
import net.sourceforge.jiu.data.Gray8Image;
import org.apache.commons.io.FilenameUtils;
import udai.ImageUtil;
import udai.ImageManipulation;


/**
 * Computes a set of exams and provides statistics
 * @author Door Guardians
 */
public class ExamScorer extends SwingWorker<Void, Void>
                        implements Observer
{
    // Array of pdf exam files
    private File[] examPDFFiles;
    // Directory file for outputing files
    private File outputDir;
    // PolyLearnFile to get names and create an output PL csv
    private PolyLearnFile polyLearnFile;
    
    // Boolean to tell if the console version is running
    private boolean consolePercent = false;
    
    // PDF converter object
    private PDFConverter convertPDF;
    // Number of exams converted by convertPDF
    private int examsConverted;
    // Number of exams in the PDF to convert
    private int totalExams;
    private int totalQuestions;
    
    private ArrayList<String> studentResponses;
    private ArrayList<String> answerKey;
    private int numberOfQuestions;
    /* The amount of progress converting 
       PDFs contributes to the overall progress
    */
    private float pdfProg;
    // The progress of the percentage bar
    private float prog;
    // The amount of progress one exam file will make up
    private float fileProg;
    
    // File that has all response data 
    private File overallFile;
    
    /**
     * Boolean that indicates if the correct answers should be given in 
     * PolyLearn file feedback
     */
    private boolean polyLearnAnswer;
    
    // List of a list of StudentExams, each list representing a version
    private ArrayList<Exam> exams;
    // The cumulative exam, representing scores from all exams
    private Exam cumulativeExam;
    //list of studentexam objects to be graded
    private ArrayList<StudentExam> studentExams;
    // Version character
    private char ver;
    
    //exam key to be graded against
    private ExamKey examKey;
    //list of students that took the exam
    private ArrayList<Student> students;
    //array of raw scores of studentexams
    private int[] examScores;
    // List of correct answers
    private ArrayList<String> isCorrectList;
    // Number incorrect for each question
    private int[] incorrectByQuestion;
    
    // List of paths of created files
    private ArrayList<String> fileList;
    
    /**
     * Default Constructor for Exam Scorer object
     */
    public ExamScorer()
    {
        
    }
    
    /**
     * Constructor 
     * @param examPDFFiles list of exam files in pdf format
     * @param outputDir output directory
     * @param csv PolyLearn file 
     * @param pLAnswer Polylearn Answers
     */
    public ExamScorer(File[] examPDFFiles, File outputDir,
            File csv, boolean pLAnswer)
    {
        this.examPDFFiles = examPDFFiles;
        this.outputDir = outputDir;
        
        // Checks if csv file is valid
        if (csv != null) 
        {
            this.polyLearnFile = new PolyLearnFile(csv);
        }
        this.polyLearnAnswer = pLAnswer;
        this.prog = 0;
        this.examsConverted = 0;
        this.exams = new ArrayList();
        this.ver = 'A';
        this.fileList = new ArrayList<String>();
    }
    
    /**
     * Get the list of files created
     * @return Returns the string list of files that were created
     */
    public ArrayList<String> getFileList()
    {
        return fileList;
    }
    
    /**
     * Get cumulative Exam file object
     * @return Returns cumulative exam
     */
    public Exam getCumulative()
    {
        return this.cumulativeExam;
    }
    
    /**
     * Returns a String of the path of the newly created temp directory 
     * @return Returns temporary directory to hold temporary data
     */
    public String createTempDirectory()
    {
        String tempDirPath = "NullPointerException thrown";
        boolean tempDirMade;
        try
        {
            // CREATE new file and directory
            Path tempDir = Paths.get("." + File.separator + "temp");
            // IF the temp directory doesn't exist, create it
            // This fixes defect #54
            if (!Files.exists(tempDir))
            {
                // Make a new temp directory
                tempDirMade = tempDir.toFile().mkdir();
            }
            else 
            {
                tempDirMade = true;
            }
             
            // IF the directory was not successfully created
            if (!tempDirMade)
            {
                System.out.println("temp directory was not created");
                tempDirPath = "";
            }
            // ELSE the directory was successfully created
            else
            {
                // SET the path of the new directory
                tempDirPath = tempDir.toFile().getAbsolutePath();
            }
            // END IF
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        
        // RETURN path to the temporary directory
        return tempDirPath;
    }
    
    /**
     * Processes an exam file against a template which creates a .dat file
     * @param examFile file path to exam file
     * @param templateFile file path to the template file
     */
    // needs unit test?
    public static void processForm(String examFile, String templateFile) 
    {        
        Gray8Image grayimage = ImageUtil.readImage(examFile);

        // SET image boundries
        ImageManipulation image = new ImageManipulation(grayimage);
        image.locateConcentricCircles();
        
        // READ in all the files and process the image
        image.readConfig(templateFile + ".config");
        image.readFields(templateFile + ".fields");
        image.readAscTemplate(templateFile + ".asc");
        image.searchMarks();
        image.saveData(examFile + ".dat");
    }
    
    /**      
     * It has fileName, filePath, answers, and numberOfQuestions
     * @param examKeyFile file object of exam key
     * @param examTemplate file object of exam template
     * @param analyzer reference to ExamAnalyzer to create key
     * @return Returns an Exam that represents the ExamKey
     */
    // needs unit test
    public ExamKey createExamKey(File examKeyFile, String examTemplate,
                                ExamAnalyzer analyzer)
    {   
        // SET the name and path of the exam key
        String examKeyFilePath = examKeyFile.getAbsolutePath();
        String examKeyName = examKeyFile.getName();
        
        // PROCESS the exam key
        processForm(examKeyFilePath, examTemplate);
        ArrayList<String> examKeyAnswers = 
                analyzer.createAnswerKey(examKeyFilePath);
        totalQuestions = countAnswers(examKeyAnswers);
        examKeyAnswers
            = new ArrayList<String>(examKeyAnswers.subList(0, totalQuestions));
        
        // CREATE an exam that holds all the info above
        ExamKey newKey = new ExamKey(examKeyName, examKeyFilePath,
                                examKeyAnswers, totalQuestions);

        // RETURN the exam key
        return newKey;
    }
    
    /**
     * Creates an array of student exams.
     * Each StudentExam has a fileName, filePath, answers, and owner
     * @param examFiles list of student exams
     * @param examTemplate exam template 
     * @param analyzer ExamAnalyzer to evaluate exams
     * @return Returns an arrayList of graded StudentExams. 
     */
    // needs unit test
    private ArrayList<StudentExam> createStudentExams(File[] examFiles,
                                                     String examTemplate,
                                                     ExamAnalyzer analyzer)
    {
        ArrayList<StudentExam> studentExamsList = new ArrayList<StudentExam>();
        
        // FOR every exam file
        for (File examFile : examFiles)
        {
            // PROCESS the exam
            processForm(examFile.getAbsolutePath(), examTemplate);
            
            // CREATE a student with this exam ID
            String examId = analyzer.getExamId(
                    examFile.getAbsolutePath() + ".dat");
            
            // CREATE a student name string array
            String[] name = {"", ""};
            
            // IF a PolyLearn File is given, get the student's name
            if (polyLearnFile != null)
            {
                name = polyLearnFile.findStudentName(examId);
            }
            
            Student examOwner = new Student(examId, name);
            
            // SET path and name of the file
            String fileName = examFile.getName();
            String filePath = examFile.getAbsolutePath();
            // CREATE the exam answers
            ArrayList<String> examAnswers;
            examAnswers = 
                new ArrayList(analyzer.createStudentAnswers(examFile).subList(0,
                        totalQuestions));
            
            // CREATE a student exam that holds all the info above
            StudentExam singleExam = 
                    new StudentExam(fileName, filePath, examAnswers, examOwner);
            
            // Checks if exam key file exists
            if (examKey != null)
            {
                singleExam.setNumberOfQuestions(examKey.getNumberOfQuestions());
            }
            else
            {
                singleExam.setNumberOfQuestions(examAnswers.size());
            }
            
            // ADD student exam to the list of other student exams
            studentExamsList.add(singleExam);
            
            /* 
                Total progress to be made in one file divided by the
                total number of studentExams = the amount of progress one
                student exam is worth
             */
            prog += (fileProg - pdfProg) / examFiles.length;
            setPercent(prog);
        }
        // END LOOP
        
        // RETURN list of student exams
        return studentExamsList;
    }
    
    /**
     * Sets number of correct marks on a student exam
     * @param exam Student exam 
     * @param examKeyObj Key to the exam
     */
    public void setStudentExamCorrectCount(StudentExam exam, ExamKey examKeyObj)
    {
        answerKey = examKeyObj.getAnswers();
        studentResponses = exam.getAnswers();
        totalQuestions = examKeyObj.getNumberOfQuestions();
        
        int correctCount = 0;
        int incorrectCount = 0;                
        
        // FOR the number of questions
        for (int question = 0; question < totalQuestions; question++)
        {
            // IF student answer matches the answer key
            if (studentResponses.get(question).equals(answerKey.get(question)))
            {
                // INCREMENT correct count and add correct to the list
                correctCount++;
                
                //Checks if list exists
                if (isCorrectList != null)
                {
                    isCorrectList.add("correct");
                }
            }
            // ELSE if it's a mismatch
            else
            {
                // INCREMENT incorrect count and incorrect per question
                incorrectCount++;
                // CHECKS if incorrectList and correctList structures exist
                if (incorrectByQuestion != null && isCorrectList != null)
                {
                    incorrectByQuestion[question]++;
                    isCorrectList.add("incorrect");
                }
            }
            // END IF
        }
        // END LOOP
        
        // SET the correct and incorrect counts of the exams
        exam.setCorrectCount(correctCount);
        exam.setIncorrectCount(incorrectCount);
    }
    
    /**
     *  Set the grade for both the StudentExam
     *  and the Student owner of the StudentExam 
     *  @param exam Student exam
     *  @param numQuestions max number of questions
     */
    public void setStudentExamGrade(StudentExam exam, int numQuestions)
    {
        int numCorrect = exam.getCorrectCount();
        // SET the grade that the student got on the exam
        double grade = (double)numCorrect / (double)numQuestions * 100;
        exam.setPercent(grade);
    }
    
    /**
     * Deletes all png converted exam files placed in the temp folder
     * and then deletes the temp folder
     */
    private void deleteTempDirectory(String tempDirPath)
    {
        File tempDirectory = new File(tempDirPath);
        String[] tempDirEntries = tempDirectory.list();
        
        // For each file in the temp directory, delete it
        for (String entry : tempDirEntries)
        {
            File tempDirFile = new File(tempDirectory.getAbsolutePath(), entry);
            tempDirFile.delete();
        }
        //delete the directory itself
        tempDirectory.delete();
    }
    
    /**
     * For each studentExam, compare it to the exam key 
     * and set its correct and incorrect scores
     * Update the examScores attribute to keep track of all studentexam scores
     * @param destinationDir - path to place the csv files
     */
    private void gradeStudentExams(String destinationDir)
    {
        students = new ArrayList<Student>();
        examScores = new int[studentExams.size()];
        int examScoresCount = 0;
        
        // FOR each exam 
        for (StudentExam exam : studentExams)
        {
            isCorrectList = new ArrayList<String>();
            setStudentExamCorrectCount(exam, examKey);
            setStudentExamGrade(exam, examKey.getNumberOfQuestions());
            students.add(exam.getOwner());
            /* This should probably be changed to appending a string in the
                StudentExam for PolyLearn comments/feedback for a PolyLearn
                File
            */
            

            examScores[examScoresCount++] = exam.getCorrectCount();
        }
    }
    
    /**
     * Calculate the progress for grading the exam
     */
    private void calculateGradingProgress()
    {
        /* Calculate the total amount of progress one exam file will add */
        fileProg = 100 / examPDFFiles.length;

        /* Reset the number of exams converted for this file */
        examsConverted = 0;

        /* 
            Converting PDFs is 20% of the total file progress.
            Set progress for PDFs completed.
        */
        pdfProg = fileProg * 0.2f;
    }
    
    /**
     * Creates examkey and studentexam objects to grade
     * @param examTemplate the filename of the template tool to use for the tool
     */
    private void createExamObjects(String examTemplate)
    {
        ExamAnalyzer examAnalyzer = new ExamAnalyzer();
        examKey = createExamKey(
            convertPDF.getExamKeyFile(), examTemplate, examAnalyzer);            
        studentExams = createStudentExams(
                    convertPDF.getExamFiles(), examTemplate, examAnalyzer);
    }
    
    /**
     * Computes a list of exam .pdf files and outputs to a directory.
     * Each .pdf should correspond to a student read from the .csv roster file.
     */
    // needs unit testing (after any refactoring)
    public void startScoring()
    {
        convertPDF = new PDFConverter();
        /* Allows this to observe the PDFConveter */
        convertPDF.addObserver(this);
        
        String destinationDir = outputDir.getAbsolutePath();
        String tempDirPath = createTempDirectory();
        
        /* FOR ecach exam*/
        for (File examPDFFile : examPDFFiles)
        {
            //initialize progress variables to track grading progress in the GUI
            calculateGradingProgress();
            
            // CREATE temporary file directory
            String sourceDir = examPDFFile.getAbsolutePath();
            
            // CREATE .png files fromm the pdf files
            convertPDF.convertToPng(sourceDir, tempDirPath);
            
            //create examKey and list of studentexams
            createExamObjects("newscantron_v1.8.png");
            
            // SET the number of incorrect questions
            int numQuestions = examKey.getNumberOfQuestions();
            incorrectByQuestion = new int[numQuestions];
            // FOR the all the questions in the exam
            for (int question = 0; question < numQuestions; question++)
            {
                // ADD zero to the 
                incorrectByQuestion[question] = 0;
            }
            
            //set the score and grade for each studentexam
            gradeStudentExams(destinationDir);
            
            // GET the file name
            String examName = examPDFFile.getName();
            
            // Create a new exam from the current exam file
            Exam exam = new Exam(studentExams, examKey);
            // Set the version for the exam
            exam.setVersion("" + ver++);
            String fileName;
            fileName = FilenameUtils.removeExtension(examPDFFile.getName());
            // Set the exam's file name
            exam.setFileName(fileName + "_scores.csv");
            
            // Create the csv output for this exam
            String fileString = 
                    exam.outputExamCSV(outputDir, incorrectByQuestion);
            fileList.add(fileString);
            
            /* Add this list of student exams as a version */
            exams.add(exam);
        }
        
        //Write the new polylearn file with scores and comments.
        if (polyLearnFile != null)
        {
            polyLearnFile.writePolyLearnFile(exams, polyLearnAnswer, 
                                            outputDir, tempDirPath);
            fileList.add(outputDir.getAbsolutePath() + File.separator
                    + "Hatalskan_PolyLearn.csv");
        }
        
        // CREATE the over all output file
        cumulativeExam = createCumulativeExam();

        // Create a cumulative list of all StudentExams
        String cumulativeName =  cumulativeExam.outputExamCSV(outputDir, null);
        
        // Add cumulative file of list of files crated
        fileList.add(cumulativeName);
        
        // Add the cumulative to the front
        exams.add(0, cumulativeExam);
        
        /* Deletes all png and .dat files in the temp directory 
        * and then deletes the temp directory */
        deleteTempDirectory(tempDirPath);
    }
    
    /**
     * Gets the list of student exams
     * @return Returns list of graded student exam
     */
    public ArrayList<StudentExam> getStudentExams()
    {
        return studentExams;
    }
    
    /**
     * Return the arraylist of exams created by the scorer. Must run 
     * @return The arraylist of exams
     */
    public ArrayList<Exam> getExams()
    {
        return this.exams;
    }
    
    private Exam createCumulativeExam()
    {
        ArrayList<StudentExam> cumulativeStudentExams = new ArrayList<StudentExam>();
        // FOR EACH Exam in exams
        for (Exam exam : exams)
        {
            // FOR EACH StudentExam in exam
            for (StudentExam stExam: exam.getStudentExams())
            {
                cumulativeStudentExams.add(stExam);
            }
        }
        Exam ret = new Exam(cumulativeStudentExams, examKey);
        ret.setVersion("Cumulative");
        ret.setFileName("Hatalskan_cumulative.csv");
        
        return ret;
    }
    
    /**
     * Sets the current percent completion of scoring
     * @param progress percent completion of scoring
     */
    private void setPercent(float progress)
    {
        int set = Math.min(Math.round(progress), 99);
        setProgress(set);
        // IF the console version is running
        if (consolePercent)
        {
            // Print the percent on the console
            System.out.println("Grading... "+ set + "%");
        }
            
    }
    
    /**
     * Counts how many answers the key has
     * @param ans list of answers from the key, with all unanswered fields
     *  populated by empty strings
     * @return count is the number of non empty strings in ans
     */
    
    private int countAnswers(ArrayList<String> ans) 
    {
        int count = 0;
        
        // FOR each question's response
        for (String str : ans) 
        {
            // CHECK if there exist a filled in bubble
            if(!str.equals(""))
            {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Starts the backend processing for the command line version
     * @return true if successful
     */
    public boolean cmdExecute()
    {
        prog = 0;
        consolePercent = true;
        
        startScoring();
        
        done();
        return true;
    }

    /* 
        Override method in SwingWorker
        Dictates what the class will do when called in the background
        of the UI.
    */
    @Override
    protected Void doInBackground() throws Exception 
    {
        prog = 0;
        setProgress((int)prog);
        
        startScoring();
        
        return null;
    }
    
    /* 
        Override done method in SwingWorker
        Dictates what actions are taken when doInBackground() is done
    */
    @Override
    protected void done()
    {
         /* Setting bar progress to 100 */
        int set = 100;
        setProgress(set);
        // IF running through the console
        if (consolePercent)
        {
            // PRINT percent complete
            System.out.println("Grading... "+ set + "%");
            System.out.println("Grading Complete!");
            System.out.println("Created files:\n");
            // PRINT each file in the fileList
            for (String file : fileList)
            {
                System.out.println(file);
            }
        }
    }

    /**
     * Overrides observable update
     * @param o Observable object
     * @param arg arguments
     */
    @Override
    public void update(Observable o, Object arg) 
    {
        // IF the object matches the PDFConverter class
        if (o == convertPDF)
        {
            this.totalExams = convertPDF.getTotalExams();
            // IF number of converted is greater than zero and exams covered 
            if (convertPDF.getNumConverted() != 0 &&
                    examsConverted + 1 <= convertPDF.getNumConverted())
            {
                 /* 
                    Add the percent value of one exam in one pdf
                    out of total PDF percent contribution
                */
                prog += pdfProg * 1.0f / (float)(totalExams);
                setPercent(prog);
                examsConverted++;
            }
            // END IF
        }
        // END IF
    }
}
