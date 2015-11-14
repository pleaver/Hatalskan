package userInterface;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Holds static messages.
 * @author Door Guardians
 */
public class InterfaceStrings {
    /** Command line strings */
    
    /**
     * The string printed when the about flag is set
     */
    public static final String kCmdVersion = 
            "Hatalskan V2.0 (rev 279) by Door Guardians\n";
    
    /**
     * The string to be printed when the help flag is set
     */
    public static final String kCmdHelp = 
        "Command Argument Help:\n"
        + "-v  Display version and team info\n"
        + "-h  Display syntax help info\n"
        + "-a  Write correct answers in PolyLearn feedback\n"
            + "\tIf omitted, defaults to false\n"
        + "-i  [Path to file(s) of scanned exams in PDF format]\n"
            + "\tMultiple files may be specified by a comma separated list\n"
            + "\tAccepts relative and absolute paths\n"
        + "-p  [Path to the PolyLearn csv file] \n"
            + "\tAccepts relative and absolute paths\n"
            + "\tIf omitted, defaults to none\n"
        + "-o  [Path to directory in which result files will be placed]\n"
            + "\tAccepts relative and absolute paths\n"
            + "\tIf omitted, defaults to current directory\n";
    
    /**
     * The string to be printed when no input file is given
     */
    public static final String kNoFileErr = 
        "Error: Bad option, \"%s\" for flag \"-%s\".\n"
        + "\nFor help, please use the [-h] flag\n";
    
    /** UI pop-up strings */
    
    /**
     * Help text for using the sortable table
     */
    public static final String kTableHelp = 
            "Grade Table Help\n\n"
                    + "This table shows the grades of each student\n"
                    + "in the order in which the exams were scanned.\n\n"
                    + "Sorting\n"
                    + "If you would like to sort the data by name, ID, \n"
                        + "grade, or scan order, "
                        + "it is as simple as clicking the name!\n";
    
    /** 
     * Error text if no output directory is chosen
     */
    public static final String kNoOutputDir = 
            "No output directory was selected.\n"
                + "Please select an output directory\n"
                + "using the \"Browse...\" button.";
    
    /**
     * Warning text if no roster file is chosen
     */
    public static final String kNoPolyLearnFile =
            "No file was selected.\n\n"
                + "If you continue with no PolyLearn file, no PolyLearn\n"
                + "file will be output to upload to PolyLearn and names\n"
                + "will be assigned as CalPoly usernames.\n\n"
                + "If you would like names and a PolyLearn grade file,\n"
                + "please select a PolyLearn file. "
                + "using the \"Browse...\" button.\n\n"
                
                + "Will you continue with no file?";
    
    /**
     * GUI Help text
     */
    public static final String kGUIHelp = 
            "Help\n\n"
                    + "Hatalskan Version 2.0\n\n"
                    + "Last Updated: 05/09/15\n\n"
                    + "\n"
                    + "Each step of the program will prompt "
                        + "you to follow an action!\n"
                    + "When you have completed that action, "
                        + "click on next to move on.\n"
                    + "Don't worry, an error will correct you "
                        + "if you do something wrong.\t\n\n";
    
    /**
     * Warning text for grading another test
     */
    public static final String kGradeAgain = 
            "Are you sure you would like to grade another?\n\n"
                        + "You will be unable to view "
                            + "the current exam results if you leave this screen,\n"
                        + "but your data will be saved "
                            + "and accessable "
                            + "through the .csv file:\n";
    
    /**
     * Error text if no exam files were selected
     */
    public static final String kNoExamFiles = 
            "No exam file(s) was selected.\n\n"
                + "Please select at least one exam file\n"
                + "using the \"Browse...\" button.";
    
    /**
     * Warning text for quitting the GUI
     */
    public static final String kGUIQuit = 
            "Are you sure you would like to quit?\n\n"
                        + "You will be unable to view "
                            + "the current exam results through this program"
                                + " if you quit,\n"
                        + "but your data will be saved "
                            + "and accessable "
                            + "through the .csv file:\n";
    
    /**
     * Error text for bad files in CMD arguments
     */
    public static final String kFileNotFoundErr = 
            "ERROR: File not found: %s\n"
            + "Please note that the command line parser library ends arguments"
            + " on spaces, inlcuding directory names with spaces. When entering"
            + " multiple file arguments, please use comma separated values"
            + " with no spaces.\n"
            + "For help with command flags, please use the \"-h\" command\n";
    
    /**
     * Error text for bad files in CMD arguments
     */
    public static final String kUnknownOptionString = 
            "ERROR: Option \"%s\" is not a command.\n" +
                "If you are unfamiliar with Hatalskan commands please use"
                + " the \"-h\" flag for help.\n";
    
}
