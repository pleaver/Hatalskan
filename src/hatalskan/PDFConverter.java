package hatalskan;


import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Observable;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 * Converts PDF types
 * @author Door Guardians
 */
public class PDFConverter extends Observable 
{
    private File[] examFiles;
    private File examKeyFile;
    
    private int numConverted;
    private int totalExams;
    
    /**
     * Default Constructor
     */
    public PDFConverter()
    {
        totalExams = 0;
        numConverted = 0;
    }
    
    /**
     * Get list of exam files
     * @return Returns array list of File objects
     * for all created image files from pdf
     */
    public File[] getExamFiles()
    {
        return examFiles;
    }
    
    /**
     * Get exam key
     * @return Returns the File object of the exam key from the pdf
     */
    public File getExamKeyFile()
    {
        return examKeyFile;
    }
    
    /**
     * Converts a .pdf to .png
     * @param sourceDir source directory    
     * @param destinationDir destination directory
     */
    public void convertToPng(String sourceDir, String destinationDir)
    {
        try 
        {
            numConverted = 0;
            // FIND file at source directory
            File sourceFile = new File(sourceDir);
            /* System print for pdf file path
                System.out.println("filpath " + sourceFile.getAbsolutePath());*/
            // FIND file at destination directory
            File destinationFile = new File(destinationDir);
            // IF destination directory does not exist
            if (!destinationFile.exists()) 
            {
                // CREATE a file at destination directory
                destinationFile.mkdir();
                System.out.println(
                       "Folder Created -> "+ destinationFile.getAbsolutePath());
            }
            // ENDIF
            
            // IF source directory exists
            if (sourceFile.exists()) 
            {
                /* System print for copying to temp folder
                    System.out.println(
                        "Images copied to Folder: "+ destinationFile.getName());
                */
                // LOAD file at source Directory
                PDDocument document = PDDocument.load(sourceDir);
                // GET list of files
                List<PDPage> list = document.getDocumentCatalog().getAllPages();
                
                /* Set the total number of PDFs to convert and report to
                    observer
                */
                totalExams = list.size();
                // CALL setChanged()
                setChanged();
                // CALL notifyObservers()
                notifyObservers();
                
                /* System print to count total number of exams
                    System.out.println(
                        "Total files to be converted -> "+ totalExams); */
                

                String fileName = sourceFile.getName().replace(".pdf", "");         
                int pageNumber = 1;
                
                int studentExamCount = 0;
                examFiles = new File[list.size() - 1];
                
                // FOR each file in the list
                for (PDPage page : list) 
                {
                    // CONVERT file to image file
                    BufferedImage image = page.convertToImage();
                    // CREATE output file as .png
                    File outputfile = new File(
                            destinationDir + File.separator + 
                                    fileName +"_"+ pageNumber +".png");
                    
                    /* Status print for png creation
                        System.out.println(
                            "Image Created -> "+ outputfile.getName()); */
                    
                    /*
                        Increment number of PDFs converted and report back
                        to observer
                    */
                    // Increment number of files converted
                    numConverted++;
                    // CALL setChanged()
                    setChanged();
                    // CALL notifyObservers()
                    notifyObservers();
       
                    // ADD .png file to destination directory
                    ImageIO.write(image, "png", outputfile);
                    
                    // IF first file converted
                    if (pageNumber == 1)
                    {
                        // MARK file as exam key
                        examKeyFile = outputfile;
                    }
                    // ELSE
                    else 
                    {
                        // ADD image files to list of exam files
                        examFiles[studentExamCount++] = outputfile;
                    }
                    //ENDIF
                    
                    //INCREMENT page number
                    pageNumber++;
                }
                // END LOOP
                
                document.close();
                /*  Status print for temp folder 
                    System.out.println("Converted Images are saved at -> "
                        + destinationFile.getAbsolutePath());*/
            }
            // ELSE throw an file not exist message
            else 
            {
                System.err.println(sourceFile.getName() +" File not exists");
            }
            // ENDIF

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Gets the number of converted files
     * @return Returns number of conversions
     */
    public int getNumConverted()
    {
        return this.numConverted;
    }
    
    /**
     * Gets the total number of exams
     * @return Returns number of exams
     */
    public int getTotalExams()
    {
        return this.totalExams;
    }
}
