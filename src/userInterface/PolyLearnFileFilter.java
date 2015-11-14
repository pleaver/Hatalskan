package userInterface;

import java.io.File;

/**
 * Filters for only Csv files
 * @author Door Guardians
 */
public class PolyLearnFileFilter extends javax.swing.filechooser.FileFilter {
    
        /**
         * Checks if file type is allowed.
         * @param file file to be verified.
         * @return Returns true if file is an acceptable .tsv; false otherwise
         */
        @Override
        public boolean accept(File file) {
            return file.isDirectory() ||
                    file.getAbsolutePath().toLowerCase().endsWith(".csv");
        }
        
        /**
         * Get the types that can be accepted.
         * @return Returns .tsv acceptance description.
         */
        @Override
        public String getDescription() {
            // This description will be displayed in the dialog,
            return "CSV files (*.csv)";
        }
    }
