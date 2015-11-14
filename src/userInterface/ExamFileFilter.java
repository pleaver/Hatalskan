package userInterface;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;

/**
 * Filters out file types
 * @author Door Guardians
 */
public class ExamFileFilter extends javax.swing.filechooser.FileFilter {
    
        /**
         * Checks if file type is allowed.
         * @param file file to be verified.
         * @return true if file is acceptable; false otherwise
         */
        @Override
        public boolean accept(File file) {
            // Allow only directories, or files with ".txt" extension
            return file.isDirectory() ||
                    file.getAbsolutePath().toLowerCase().endsWith(".pdf");
        }
        
        /**
         * Get the types that can be accepted.
         * @return String of accepted filetypes.
         */
        @Override
        public String getDescription() {
            // This description will be displayed in the dialog,
            return "Exam image files (*.pdf)";
        }
    }
