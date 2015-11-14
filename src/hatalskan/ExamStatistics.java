package hatalskan;


import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Analyzes a list of student exams associated with a given test. The 
 * list of student exams are then processed to computer overall statistic
 * related to the exam including highest score, lowest score, average score,
 * and standard deviation.
 * @author Door Guardians
 */
public class ExamStatistics 
{
    
    // The highest score on the exams
    private int highScore;
    // The lowest score on the exams
    private int lowScore;
    // The average score on the exams
    private int meanScore;
    // The average percent on the exams
    private double meanPercent;
    // The standard deviation of the exams
    private double stDev;
    
    // A formater for the output
    private DecimalFormat format = new DecimalFormat("0.00");
    
    // The exams on which to base the statistics on
    private ArrayList<StudentExam> exams;
    // The key the exams were graded on
    private ExamKey key;
    
    /**
     * Constructs an ExamStatistic from a list of student exams.
     * @param exams list of graded student exams
     * @param key Key the student exams were graded with
     */
    public ExamStatistics(ArrayList<StudentExam> exams, ExamKey key)
    {
        this.exams = exams;
        this.key = key;
        this.highScore = calcHigh();
        this.lowScore = calcLow();
        this.meanScore = calcMeanScore();
        this.meanPercent = calcMeanPercent();
        this.stDev = calcStDev();
    }
    
    /**
     * Gets the highest score on the exam
     * @return Returns highest score
     */
    public int getHighScore()
    {
        return this.highScore;
    }
    
    /**
     * Gets the lowest score on the exam
     * @return Returns lowest score
     */
    public int getLowScore()
    {
        return this.lowScore;
    }
    
    /**
     * Gets the average score of the exam
     * @return Returns mean score
     */
    public int getMeanScore()
    {
        return this.meanScore;
    }
    
    /**
     * Gets the average score percent of the exam
     * @return Returns mean percent score
     */
    public String getMeanPercent()
    {   
        // Apply the pattern
        String percent = format.format(this.meanPercent);
        return percent;
    }
    
    /**
     * Gets the standard deviation on the exam
     * @return Returns standard deviation 
     */
    public String getStDev()
    {
        // Apply the pattern
        String sDev = new DecimalFormat("0.00").format(this.stDev);
        return sDev;
    }
    
    // needs unit test
    private int calcHigh()
    {
        int high = 0;
        
        // FOR all the student exams
        for (StudentExam exam : this.exams)
        {
            // IF exam score is higher than the current max score
            if (exam.getCorrectCount() > high)
            {
                // SET exam score as the new highscore 
                high = exam.getCorrectCount();
            }
            // END IF
        }
        // END LOOP
        
        // RETURN highest test score
        return high;
    }
    
    // needs unit test
    private int calcLow()
    {
        int low = Integer.MAX_VALUE;
        
        // FOR all the student exams
        for (StudentExam exam : this.exams)
        {
            // IF exam score is less than the current min score
            if (exam.getCorrectCount() < low)
            {
                // SET exam score as the new lowest score
                low = exam.getCorrectCount();
            }
        }
        // END LOOP
        
        // RETURN lowest test score
        return low;
    }
    
    // needs unit test
    private int calcMeanScore()
    {
        int mean;
        int total = 0;
        
        // FOR all the student exams
        for (StudentExam exam : this.exams)
        {
            // ADD up score to the total
            total += exam.getCorrectCount();
        }
        // END LOOP
        
        // SET mean score by dividing total score by number of tests
        mean = total / this.exams.size();
        
        // RETURN mean score
        return mean;
    }
    
    private double calcMeanPercent()
    {
        double mean;
        double total = 0;
        
        // FOR all the student exams
        for (StudentExam exam : this.exams)
        {
            // ADD up score to the total
            total += exam.getCorrectCount();
        }
        // END LOOP
        
        // SET mean score by dividing total score by number of tests
        mean = total / this.exams.size();
        
        // Set the mean percent by dividing the total mean score by
        // the total number of questions
        mean = mean / this.key.getNumberOfQuestions();
        
        // Make the mean a percentage
        mean *= 100;
        
        // RETURN mean percent
        return mean;
    }
    
    // needs unit test
    private double calcStDev()
    {
        double stdDev;
        double meanTotal = 0;
        
        // FOR all the student exams
        for (StudentExam exam : this.exams)
        {
            // CALCULATE the mean total
            meanTotal += Math.pow(exam.getCorrectCount() - this.meanScore, 2);
        }
        
        // SET the variance
        double variance = meanTotal / this.exams.size();
        
        // SET the standard deviation
        stdDev = Math.sqrt(variance);
        
        // RETURN standard deviation of the exams
        return stdDev;
    }
    
}
