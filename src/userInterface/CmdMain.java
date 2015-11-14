package userInterface;


import jargs.gnu.CmdLineParser;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import hatalskan.ExamScorer;

/**
 * This is the class for running the command line version
 * CmdMain holds an array of command line arguments and processes them to
 * run the standard functions of the application.
 * @author Door Guardians
 */
public class CmdMain{
    /** Strings from the command line */
    private String[] args;
    
    /** The parser used to get the command line arguments */
    private CmdLineParser parser = new CmdLineParser();

    /** Version info option */
    private CmdLineParser.Option versionOp = 
            parser.addBooleanOption('v', "version");
    /** Help option */
    private CmdLineParser.Option helpOp = 
            parser.addBooleanOption('h', "help");
    /** Input option, takes file path string 
        absolute / relative, mandatory */
    private CmdLineParser.Option inputOp =
            parser.addStringOption('i', "input");
    /** PolyLearn option, takes file path string absolute / relative */
    private CmdLineParser.Option polyOp =
            parser.addStringOption('p', "polylearn");
    /** Output option, takes file path string absolute / relative */
    private CmdLineParser.Option outputOp =
            parser.addStringOption('o', "output");
    /** PolyLearn answers option */
    private CmdLineParser.Option answersOp =
            parser.addBooleanOption('a', "answers");

    /** ExamScorer object that will score the given exams and output the
     * scores
     */
    private ExamScorer examScorer;

    /** Array of exam pdf files to be passed to the ExamScorer */
    private File[] examFiles;
    /** PolyLearn file to to be passed to the ExamScorer */
    private File polyFile;
    /** Output directory to be passed to the ExamScorer */
    private File outputDir;
    /** Flag for PolyLearn feedback answers */
    private boolean pLAnswers = false;
    
    /**
     * Empty constructor for CmdMain. FOR TEST PURPOSES ONLY.
     */
    public CmdMain()
    {
        
    }
    
    /**
     * Constructor for CmdMain
     * @param args String arguments from the command line
     */
    public CmdMain(String[] args)
    {
        this.args = args;
    }
    
    /**
     * Runs the command line version of this software using the argument given
     * in the constructor
     * @return Returns true if the run was successful
     */
    public boolean execute()
    {
        /* Try to parse the arguments */
        try {
            parser.parse(args);
        } catch (CmdLineParser.IllegalOptionValueException ex) {
            System.err.format(InterfaceStrings.kNoFileErr, ex.getValue(),
                    ex.getOption().shortForm());
            System.exit(-1);
            
            // This fixes defect #80
        } catch (CmdLineParser.UnknownOptionException ex) {
            System.err.format(InterfaceStrings.kUnknownOptionString,
                    ex.getOptionName());
        }
        
        /* Handle all command line flags */
        handleFlagOptions((boolean)parser.getOptionValue(versionOp, false),
                          (boolean)parser.getOptionValue(helpOp, false),
                          (boolean)parser.getOptionValue(answersOp, false));
        // Assing the examfile(s)
        examFiles = 
                handleInputOption((String)parser.getOptionValue(inputOp));
        // IF there were no exam files, return false because it was not run
        if (examFiles == null)
        {
            return false;
        }
        // Assign the PolyLearn file
        polyFile = 
                handlePolyOption((String)parser.getOptionValue(polyOp));
        // Assign the outputDirectory
        outputDir =
                handleOutputOption((String)parser.getOptionValue(outputOp));
        
        // FOR every exam file, announce it
        System.out.print("Input: ");
        for (int idx = 0; idx < examFiles.length; idx++)
        {
            System.out.print(examFiles[idx].getAbsolutePath() + "\n");
        }
        // Announce the output directory
        System.out.print("Output: " + outputDir.getAbsolutePath() + "\n");
        
        /* Create the main process */
        examScorer = new ExamScorer(examFiles, outputDir,
                                    polyFile, pLAnswers);
        
        /* Run the backend */
        return examScorer.cmdExecute();
    }
    
    /**
     * Handles the flags passed by the command line. Public for testing purposes
     * @param version Flag set to display version
     * @param help Flag set to display help
     * @param answers Flag set to display correct answers on PolyLearn files
     * @return Concatenated string of the output
     */
    public String handleFlagOptions(boolean version,
                                   boolean help,
                                   boolean answers)
    {
        String test = "";
        
        /* IF the version flag was set */
        if (version)
        {
            /* Print the version information */
            System.out.println(InterfaceStrings.kCmdVersion);
            test += InterfaceStrings.kCmdVersion;
        }
        
        /* IF the help flag was set */
        if (help)
        {
            /* Print the help information */
            System.out.print(InterfaceStrings.kCmdHelp);
            test += InterfaceStrings.kCmdHelp;
        }
        
        /* Set the flag to the value of the command flag */
        pLAnswers = answers;
        test += Boolean.toString(pLAnswers);
        
        return test;
    }
    
    public File[] handleInputOption(String input)
    {
        File[] examFiles;
        
        /* IF no input was given */
        if (input == null)
        {
            // Set the return value to null
            examFiles = null;
        }
        // ELSE
        else
        {
            // Parse the files passed out of the string
            examFiles = getExamFiles(input);
        }
        
        return examFiles;
    }
    
    public File[] getExamFiles(String input)
    {
        /** Exam files to be returned */
        ArrayList<File> exams = new ArrayList<>();
        /** Index variable for the first ',' in the file string */
        int splitIdx = 0;
        /** Character that splits file names */
        char split = ',';
            
        /*
            WHILE there are ',' in the string
        */
        while (input.contains(String.valueOf(split)))
        {
            /** String contains file name */
            String fileString;
            
            /** Get the index of the first comma in the string */
            splitIdx = input.indexOf(split);
            
            /** String of the beginning of the string to one before the comma */
            fileString = input.substring(0, splitIdx);
            
            exams.add(createFile(fileString));
            
            /* Cut off the part that we just read */
            input = input.substring(++splitIdx);
        }
        
        /* Add the final file */
        exams.add(createFile(input));
        File[] retType = new File[exams.size()];
        /* Return the array of files */
        return exams.toArray(retType);
    }
    
    public File handlePolyOption(String poly)
    {
        /** The poly File to be returned */
        File polyFile;
        
        // IF there is a poly flag set
        if (poly != null)
        {
            // Get the corresponding file indicated by the String
            polyFile = createFile(poly);
        }
        // ELSE
        else
        {
            // Set the poly file return value to null
            polyFile = null;
        }
        
        return polyFile;
    }
    
    public File handleOutputOption(String output)
    {
        /** The output directory File to be returned */
        File outputDir;
        
        /* 
            IF the the output flag is set
        */
        if (output != null)
        {
            // Get the output directory indicated by the string
            outputDir = createFile(output);
        }
        // ELSE
        else
        {
            // Set the output directory return value to the current directory
            outputDir = new File("").getAbsoluteFile();
        }
        
        // RETURN the output directory
        return outputDir;
    }
    
    private static File createFile(String path)
    {   
        Path filePath;
        // Get the path indicated by the string
        filePath = Paths.get(path);
        
        // IF the file does not exist
        // This fixes defect #78
        if (!Files.exists(filePath))
        {
            // Print an error message
            System.err.format(InterfaceStrings.kFileNotFoundErr,
                               filePath.toFile().getAbsolutePath());
            // Exit with a bad status
            System.exit(-1);
        }
        
        // RETURN that path as a file
        return filePath.toFile();
    }
}
