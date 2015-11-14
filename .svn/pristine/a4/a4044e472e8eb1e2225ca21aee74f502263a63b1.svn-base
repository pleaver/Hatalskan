package userInterface;

/**
 * AppMain starts either the GUI or command line methods, depending on the
 * arguments given
 * @author Door Guardian
 */
public class AppMain {
    
    /**
     * Runs Hatalskan.
     * @param args command line arguments. If there are no arguments, the GUI 
     * will be initialized, otherwise it uses the terminal version.
     */
    public static void main(String args[])
    {
        // IF there are no command arguments
        if (args.length == 0)
        {
            // Display the graphical interface
            startGUI();
        }
        // ELSE
        else
        {
            // Start the command line interface
            startCommandLine(args);
        }
    }
    
    private static void startGUI()
    {
        // Create a new instance of the GUI
        GUIMain GUI = new GUIMain();
        // Make the GUI visible
        GUI.setVisible(true);
    }
    
    private static void startCommandLine(String args[])
    {   
        // Create a new instance of the command line interface
        CmdMain CMD = new CmdMain(args);
        // Run the commands through the that interface
        CMD.execute();
    }
}
