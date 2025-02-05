package udai;

/*
 * InputImage.java
 *
 * Created on June 30, 2007, 9:56 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */



import net.sourceforge.jiu.codecs.*;
import net.sourceforge.jiu.data.*;
import net.sourceforge.jiu.color.reduction.*;
import net.sourceforge.jiu.filters.*;
import net.sourceforge.jiu.geometry.*;
import java.util.*;
import java.io.*;

/**
 * Original source is credited to the UDAI team. The file has been modified to
 * achieved functionalities needed for Hatalskan.
 * 
 * ImageManipulation identifies the locations of concentric circles placed
 * on an image file, given a .asc template file. A .asc file depicts the fields
 * where marks are expected.
 * 
 * @author Aaditeshwar Seth
 * @author Modified by Door Guardians, 2015
 */
public class ImageManipulation {    
    Gray8Image grayimage, scaledImage;
    int height, width;
    ConcentricCircle topleftpos, bottomrightpos;
    int topleftX, topleftY, bottomrightX, bottomrightY;
    double currAngle, currDiag, realAngle, realDiag;
    int scaleFactor;
    int markLocations[], realMarkLocations[]; // x * 10000 + y
    int ascTemplateLocations[]; // same order as markLocations: x * 1000 + y
    int[][] ascTemplate;
    int nummarks, realNummarks;
    int numfields;
    LinkedHashMap fields;
    Field[] ascTemplateFields;
    
    /**
     * Constructs an ImageManipulation instance.
     * @param grayimage gray8 image file to be analyzed
     */
    public ImageManipulation(Gray8Image grayimage) {
        this.grayimage = grayimage;
        
        height = grayimage.getHeight();
        width = grayimage.getWidth();
        // 1700 x 2339 --> 426 x 560
        //System.out.println("width = " + width + ": height = " + height);
        
        // 5 --> each mark becomes around 3 pixels wide
        scaleFactor = width / 340; 
    }
    
    /**
     * Locates the concentric circles indicators
     */
    public void locateConcentricCircles() {
        int[] topleft = new int[((int)(height/4) + 1) * ((int)(width/4) + 1)];
        int[] bottomright
                = new int[((int)(height/4) + 1) * ((int)(width/4) + 1)];
        grayimage.getSamples(0, 0, 0, (int)(width/4) + 1,
                (int)(height/4) + 1, topleft, 0);
        grayimage.getSamples(0, width - (int)(width/4) - 1,
                height - (int)(height/4) - 1, (int)(width/4) + 1,
                (int)(height/4) + 1, bottomright, 0);
        
        Gray8Image topleftimg
                = new MemoryGray8Image((int)(width/4) + 1, (int)(height/4) + 1);
        topleftimg.putSamples(0, 0, 0,
                (int)(width/4) + 1, (int)(height/4) + 1, topleft, 0);
        Gray8Image bottomrightimg
                = new MemoryGray8Image((int)(width/4) + 1, (int)(height/4) + 1);
        bottomrightimg.putSamples(0, 0, 0, (int)(width/4) + 1,
                (int)(height/4) + 1, bottomright, 0);

        topleftpos = new ConcentricCircle(topleftimg, width, height);
        topleftpos.process();
        bottomrightpos = new ConcentricCircle(bottomrightimg, width, height);
        bottomrightpos.process();

        // ImageUtil.saveImage(topleftpos.getImg(), "topleft.png");
        // ImageUtil.saveImage(bottomrightpos.getImg(), "bottomright.png");
        // ImageUtil.saveImage(topleftpos.getBestFit().getTemplate(),
        //                     "template.png");
        
        bottomrightpos.getBestFit().setX(width - (int)(width/4) - 1
                + bottomrightpos.getBestFit().getX());
        bottomrightpos.getBestFit().setY(height - (int)(height/4) - 1
                + bottomrightpos.getBestFit().getY());
        
        topleftX = topleftpos.getBestFit().getX()
                + topleftpos.getBestFit().getTemplate().getWidth() / 2;
        topleftY = topleftpos.getBestFit().getY()
                + topleftpos.getBestFit().getTemplate().getHeight() / 2;
        ImageUtil.putMark(grayimage, topleftX, topleftY, true);
        
        bottomrightX = bottomrightpos.getBestFit().getX()
                + bottomrightpos.getBestFit().getTemplate().getWidth() / 2;
        bottomrightY = bottomrightpos.getBestFit().getY()
                + bottomrightpos.getBestFit().getTemplate().getHeight() / 2;
        ImageUtil.putMark(grayimage, bottomrightX, bottomrightY, true);

        // System.out.println(topleftX + ":" + topleftY + ":"
        //      + bottomrightX + ":" + bottomrightY);
        // ImageUtil.saveImage(grayimage, "grayimage.png");
        
        currAngle = Math.toDegrees(Math.atan2((bottomrightX - topleftX), 
                (bottomrightY - topleftY)));
        currDiag = Math.sqrt(
                Math.pow((bottomrightY - topleftY), 2)
                + Math.pow((bottomrightX - topleftX), 2));
        //System.out.println("curr angle = " + currAngle);
        //System.out.println("curr diag = " + currDiag);
    }
    
    /**
     * Finds the location of all marks
     */
    public void locateMarks() {
        rescale();
        // XXX do not read from file
        // Gray8Image scaledImage = ImageUtil.readImage("scaled.png"); 
        int scaledtopleftX = topleftX / scaleFactor;
        int scaledtopleftY = topleftY / scaleFactor;
        int scaledbottomrightX = bottomrightX / scaleFactor;
        int scaledbottomrightY = bottomrightY / scaleFactor;

        //System.out.println("scaledtop: " + scaledtopleftX + ":"
        //                    + scaledtopleftY);
        //System.out.println("scaledbot: " + scaledbottomrightX + ":"
        //                    + scaledbottomrightY);

        int[] marks = new int[100 * 100 * 10];
        int nummarks = 0;
        for (int i = scaledtopleftX; i <= scaledbottomrightX; i++) {
            for (int j = scaledtopleftY + 20;
                    j <= scaledbottomrightY - 20; j++) {
                int val =
                       (scaledImage.getSample(i, j)
                         + scaledImage.getSample(i - 1, j) +
                        scaledImage.getSample(i + 1, j)
                         + scaledImage.getSample(i, j - 1) +
                        scaledImage.getSample(i, j + 1)
                         + scaledImage.getSample(i - 1, j - 1) +
                        scaledImage.getSample(i + 1, j + 1)
                         + scaledImage.getSample(i + 1, j - 1) +
                        scaledImage.getSample(i - 1, j + 1)) / 9;
                if(val < 200) {         // XXX
                    marks[nummarks++] = i * 1000 + j;
                }
            }
        }

        //System.out.println("nummarks = " + nummarks);
        
        int[] dupmarks = new int[100 * 100 * 10];
        nummarks = filter(marks, dupmarks, nummarks);
        marks = dupmarks;

        //System.out.println("nummarks = " + nummarks);

        int t;
        Gray8Image markedImage = (Gray8Image)(grayimage.createCopy());
        // XScale = 80.95 pixel/cm
        double approxXscale = width / ConcentricCircle.a4width;
        // YScale = 78.75 pixel/cm
        double approxYscale = height / ConcentricCircle.a4height;    
        int markdispX = (int)(ConcentricCircle.markDiam * approxXscale / 4);
        int markdispY = (int)(ConcentricCircle.markDiam * approxYscale / 4);
        for(int i = 0; i < nummarks; i++) {
            //System.out.println(marks[i]);
            ImageUtil.putMark(scaledImage,
                    marks[i] / 1000, marks[i] % 1000, true);
            t = marks[i];
            marks[i] = ((t / 1000) * scaleFactor + markdispX)
                    * 10000 + (t % 1000) * scaleFactor + markdispY;   // XXX
            ImageUtil.putMark(markedImage,
                    marks[i] / 10000, marks[i] % 10000, false);
        }        
        // ImageUtil.saveImage(scaledImage, "markedscaled.png");
        ImageUtil.saveImage(markedImage, "marked.png");

        this.markLocations = marks;
        this.nummarks = nummarks;
    }

    /**
     * Creates the .asc template
     * @param filename file path
     */
    public void writeAscTemplate(String filename) {
        int[] dupmarks = new int[nummarks];
        int scaleFactor = this.scaleFactor * 3;
        int width = (bottomrightX - topleftX) / scaleFactor;
        int height = (bottomrightY - topleftY) / scaleFactor;
        ascTemplate = new int[height][width];
        boolean linesOccupied[] = new boolean[height];
        ascTemplateLocations = new int[nummarks];
        
        for(int i = 0; i < nummarks; i++) {
            dupmarks[i] = (markLocations[i] / 10000 - topleftY) / scaleFactor
                    + ((markLocations[i] % 10000 - topleftX) / scaleFactor)
                    * 10000;
            ascTemplateLocations[i] = i;
        }
        sort(dupmarks, ascTemplateLocations, nummarks);
 
        //System.out.print("ascTemplateLocations -- ");
        for(int i = 0; i < nummarks; i++) {
            //System.out.print(ascTemplateLocations[i] + " ");
        }
        //System.out.println();
        
        try {
            int prevrow = 0;
            int i = 0;
            while(i < nummarks) {
                for(int j = prevrow; j < (dupmarks[i] / 10000); j++) {
                    linesOccupied[j] = false;
                    for(int k = 0; k < width; k++) {
                        ascTemplate[j][k] = -1;
                    }
                }
                int prevcol = 0;
                int j = i;
                prevrow = (dupmarks[i] / 10000);
                for(; j < nummarks && (dupmarks[j] / 10000) == prevrow; j++) {
                    for(int k = prevcol; k < (dupmarks[j] % 10000); k++) {
                        ascTemplate[prevrow][k] = -1;
                    }
                    ascTemplate[prevrow][dupmarks[j] % 10000] = j;
                    linesOccupied[prevrow] = true;
                    prevcol = (dupmarks[j] % 10000) + 1;
                }
                for(int k = prevcol; k < width; k++) {
                    ascTemplate[prevrow][k] = -1;
                }
                i = j;
                prevrow ++;
            }
            for(int j = prevrow; j < height; j++) {
                for(int k = 0; k < width; k++) {
                    ascTemplate[j][k] = -1;
                }
            }
            
            int markcount = 0;
            for(int m = 1; m < height; m++) {
                if(linesOccupied[m] && linesOccupied[m - 1]) {
                    for(int n = 0; n < width; n++) {
                        if(ascTemplate[m - 1][n] > -1) {
                            ascTemplate[m][n] = ascTemplate[m - 1][n];
                            ascTemplate[m - 1][n] = -1;
                        }
                    }
                    linesOccupied[m - 1] = false;
                }
            }

            int[][] ascTemplateDup = new int[height][width];
            boolean[] linesOccupiedDup = new boolean[height];
            int heightdup = height;
            boolean skip = false;
            for (int n = 0; n < width; n++)
                { ascTemplateDup[0][n] = ascTemplate[0][n]; }
            int dupm = 0;
            for(int m = 1; m < height; m++) {
                if(!skip && !linesOccupied[m] && !linesOccupied[m - 1]) {
                    skip = true;
                    heightdup--;
                }
                else {
                    skip = false;
                    dupm++;
                    for (int n = 0; n < width; n++)
                        { ascTemplateDup[dupm][n] = ascTemplate[m][n]; }            
                    linesOccupiedDup[dupm] = linesOccupied[m];
                }
            }
            ascTemplate = ascTemplateDup;
            linesOccupied = linesOccupiedDup;
            height = heightdup;
            
            dumpAscTemplate(ascTemplate, width, height, linesOccupied);

            PrintWriter out = new PrintWriter(new FileOutputStream(filename));
            int[] ascTemplateLocationsDup = new int[nummarks];
            //char[] ascKeys = {'a', 'b', 'c', 'd', 'e', 'f'};
            //int[] keyCounts = { 2, 2, 6, 5, 3, 3};
            char[] ascKeys = new char[fields.size()];
            int[] keyCounts = new int[fields.size()];
            int numKeys = 0;
            Iterator<Map.Entry<Character, Field>> it
                    = fields.entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry entry = (Map.Entry)it.next();
                char key = (char)entry.getKey();
                Field val = (Field)entry.getValue();
                String name = val.getName();
                ascKeys[numKeys] = key;
                keyCounts[numKeys++] = val.choices.length;
            }
            int count = 0;
            int index = 0;
            for(int m = 0; m < height; m++) {
                for(int n = 0; n < width; n++) {
                    if(ascTemplate[m][n] > -1) {
                        ascTemplateLocationsDup[ascTemplateLocations
                                [ascTemplate[m][n]]]
                                = m * 1000 + n;
                        /*System.out.println(
                            (markLocations[ascTemplateLocations
                                [ascTemplate[m][n]]] % 10000) + ":"
                                + (markLocations[ascTemplateLocations
                                [ascTemplate[m][n]]] / 10000));*/
                        //Field f = (Field)fields.get(ascKeys[index]);
                        //int size = f.getChoices().length;
                        count++;
                        if (index < ascKeys.length)
                            out.print(ascKeys[index]);
                        else
                            break;
                        //System.out.print("\n\n" + ascKeys[index]
                        // + " at index " + index + "\n\n");
                        if (count == keyCounts[index])
                        {
                            index++;
                            count = 0;
                        }
                        //out.print("0");
                    }
                    else {
                        out.print("-");
                    }
                }
                out.println();
            }
            out.close();
            ascTemplateLocations = ascTemplateLocationsDup;
            
            //System.out.print("ascTemplateLocations -- ");
            for(int q = 0; q < nummarks; q++) {
                //System.out.print(ascTemplateLocations[q] + " ");
            }
            //System.out.println();
                        
        } catch(Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    private void dumpAscTemplate(int[][] ascTemplate, int width, int height,
            boolean[] linesOccupied) {
        for(int i = 0; i < height; i++) {
            //System.out.print(linesOccupied[i] + " ");
            for(int j = 0; j < width; j++) {
                if(ascTemplate[i][j] > -1) {
                    //System.out.print(ascTemplate[i][j]);
                }
                else {
                    //System.out.print("-");
                }
            }
            //System.out.println();
        }
    }
    
    private void sort(int[] marks, int[] ascTemplateLocations, int nummarks) {
        int t;
        for(int i = 0; i < nummarks; i++) {
            for(int j = i + 1; j < nummarks; j++) {
                if(marks[i] > marks[j]) {
                    t = marks[i];
                    marks[i] = marks[j];
                    marks[j] = t;
                    t = ascTemplateLocations[i];
                    ascTemplateLocations[i] = ascTemplateLocations[j];
                    ascTemplateLocations[j] = t;
                }
            }
        }        
    }

    private int filter(int[] marks, int[] dupmarks, int nummarks) {
        int numdupmarks = 0;
        //may need to change array capacity
        int[] cluster = new int[5000000];
        int numin;
        int i = 0;
        while(i < nummarks) {
            numin = 0;
            cluster[numin++] = marks[i];
            int j = 0;
            //System.out.print("i->" + marks[i] + ":");
            marks[i] = -1;
            while(j < nummarks) {
                if(marks[j] != -1) {
                    int k = 0;
                    while(k < numin) {
                        if(Math.abs(marks[j] / 1000 - cluster[k] / 1000)
                                < 2 && Math.abs(marks[j] % 1000 - cluster[k]
                                        % 1000) < 2) {
                            cluster[numin++] = marks[j];
                            //System.out.print("Found j->" + marks[j] + ":");
                            marks[j] = -1;
                            j = i + 1;
                            k = numin + 1;
                            break;
                        }
                        else {
                            k++;
                        }
                    }
                }
                j++;
            }

            int sumx = 0, sumy = 0;
            for(int l = 0; l < numin; l++) {
                sumx += (cluster[l] / 1000);
                sumy += (cluster[l] % 1000);
            }
            sumx /= numin; sumy /= numin;
            dupmarks[numdupmarks++] = sumx * 1000 + sumy;

            i++;
            while(marks[i] == -1) {
                i++;
            }
            //System.out.println("New: i->" + marks[i]);
        }

        return numdupmarks;
    }
    
    private void rescale() {
        try {
            MedianFilter filter = new MedianFilter();
            filter.setArea((int)((width / 1700 * 25) / 2) * 2 + 1, 
                    (int)(height / 2339 * 25 / 2) * 2 + 1);
            filter.setInputImage(grayimage);
            filter.process();
            Gray8Image medianimage = (Gray8Image)(filter.getOutputImage());
//            ImageUtil.saveImage(medianimage, "median.png");
            
            ScaleReplication scale = new ScaleReplication();
            scale.setInputImage(medianimage);
            scale.setSize(width / scaleFactor, height / scaleFactor);
            scale.process();
            PixelImage scaledImage = scale.getOutputImage();
            
            int scaledtopleftX = topleftX / scaleFactor;
            int scaledtopleftY = topleftY / scaleFactor;
            int scaledbottomrightX = bottomrightX / scaleFactor;
            int scaledbottomrightY = bottomrightY / scaleFactor;

            ImageUtil.putMark((Gray8Image)scaledImage, scaledtopleftX,
                    scaledtopleftY, true);
            ImageUtil.putMark((Gray8Image)scaledImage, scaledbottomrightX,
                    scaledbottomrightY, true);

            ImageUtil.saveImage(scaledImage, "scaled.png");
            this.scaledImage = (Gray8Image)scaledImage;
        } catch(Exception excep) {
            excep.printStackTrace(System.out);
        }
    }
    
    /**
     * Outputs a .config file to a given filename
     * @param filename name of .config file
     */
    public void writeConfig(String filename) {
        try {
            PrintWriter out = new PrintWriter(new FileOutputStream(filename));
            out.println("#top left");
            out.println(topleftX + " " + topleftY);
            out.println("#bottom right");
            out.println(bottomrightX + " " + bottomrightY);
            out.println("#angle");
            out.println(currAngle);
            out.println("#diag");
            out.println(currDiag);
            out.println("#nummarks");
            out.println(nummarks);
            out.println("#marks");
            
            int x, y;
            double r1, theta1, r2, theta2;
            for(int i = 0; i < nummarks; i++) {
                x = markLocations[i] / 10000; y = markLocations[i] % 10000;
                r1 = Math.sqrt((x - topleftX)
                        * (x - topleftX) + (y - topleftY) * (y - topleftY));
                r2 = Math.sqrt((x - bottomrightX) * (x - bottomrightX)
                        + (y - bottomrightY) * (y - bottomrightY));
                theta1 = Math.toDegrees(Math.atan2(x - topleftX, y - topleftY));
                theta2 = Math.toDegrees(
                        Math.atan2(bottomrightX - x, bottomrightY - y));
                out.println(r1 + " " + theta1 + " " + r2 + " " + theta2 + " "
                        + (ascTemplateLocations[i] / 1000) + " "
                        + (ascTemplateLocations[i] % 1000));
            }
            out.close();
        } catch(Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    /**
     * Parses a .config file
     * @param filename file name of .config
     */
    public void readConfig(String filename) {
        int scaleFactor = this.scaleFactor * 3;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    getClass().getResourceAsStream(filename)));
            in.readLine(); String topleft = in.readLine();
            int index = topleft.indexOf(" ");
            int realTopleftX = Integer.parseInt(topleft.substring(0, index));
            int realTopleftY = Integer.parseInt(topleft.substring(index + 1));
            in.readLine(); String bottomright = in.readLine();
            index = bottomright.indexOf(" ");
            int realBottomrightX
                    = Integer.parseInt(bottomright.substring(0, index));
            int realBottomrightY
                    = Integer.parseInt(bottomright.substring(index + 1));
            in.readLine(); realAngle = Double.parseDouble(in.readLine());
            in.readLine(); realDiag = Double.parseDouble(in.readLine());
            in.readLine(); 
            realNummarks = Integer.parseInt(in.readLine());
            in.readLine(); String line;
            realMarkLocations = new int[realNummarks];

            ascTemplate
                    = new int[(realBottomrightY - realTopleftY) / scaleFactor]
                             [(realBottomrightX - realTopleftX) / scaleFactor];
            for(int i = 0;
                    i < (realBottomrightY - realTopleftY) / scaleFactor; i++) {
                for(int j = 0;
                        j < (realBottomrightX - realTopleftX) / scaleFactor;
                        j++) {
                    ascTemplate[i][j] = -1;
                }
            }
            
            Gray8Image markedImage = (Gray8Image)(grayimage.createCopy());

            int i = 0;
            while((line = in.readLine()) != null && !line.equals("")) {
                int x1, x2, y1, y2;
                
                StringTokenizer st = new StringTokenizer(line, " ");
                double r1 = Double.parseDouble(st.nextToken());
                double theta1 = Double.parseDouble(st.nextToken());
                double r2 = Double.parseDouble(st.nextToken());
                double theta2 = Double.parseDouble(st.nextToken());
                int m = Integer.parseInt(st.nextToken());
                int n = Integer.parseInt(st.nextToken());
                theta1 += (currAngle - realAngle);
                theta2 += (currAngle - realAngle);
                r1 *= (currDiag / realDiag);
                r2 *= (currDiag / realDiag);
                x1 = (int)(topleftX + r1 * Math.sin(Math.toRadians(theta1)));
                y1 = (int)(topleftY + r1 * Math.cos(Math.toRadians(theta1)));
                x2 = (int)(bottomrightX - r2
                        * Math.sin(Math.toRadians(theta2)));
                y2 = (int)(bottomrightY - r2
                        * Math.cos(Math.toRadians(theta2)));
                realMarkLocations[i++]
                        = ((x1 + x2) / 2) * 10000 + ((y1 + y2) / 2);
                ascTemplate[m][n] = i - 1;
                ImageUtil.putMark(
                        markedImage, (x1 + x2) / 2, (y1 + y2) / 2, true);
            }
            in.close();
            // ImageUtil.saveImage(markedImage, "markedform.png");
        } catch(Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Parses a field
     * @param filename name of file
     */
    public void readFields(String filename) {
        String line;
        fields = new LinkedHashMap();
        try {
            // Added UTF8 unicode charset to InputStreamReader to fix defect #97
            // That was causing special characters to be read incorrectly
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    getClass().getResourceAsStream(filename), "UTF8"));
            while((line = in.readLine()) != null && !line.equals("")) {
                Field field = new Field(line);
                int size = field.getChoices().length;
                char charac = field.getCh();
                fields.put(new Character(field.getCh()), field);
            }
            in.close();
            numfields = fields.size();
        } catch(Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    /**
     * Parses an .asc template
     * @param filename name of .asc file
     */
    public void readAscTemplate(String filename) {
        ascTemplateLocations = new int[realNummarks];
        ascTemplateFields = new Field[realNummarks];
        int m = 0, n;
        try {
            // Added UTF8 unicode charset to InputStreamReader to fix defect #97
            // That was causing special characters to be read incorrectly
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    getClass().getResourceAsStream(filename), "UTF8"));
            String line;
            while((line = in.readLine()) != null && !line.equals("")) {
                n = 0;
                for(int i = 0; i < line.length(); i++) {
                    char ch = line.charAt(i);
                    if(ch != '-' && ch != '0') {
                        ascTemplateLocations[ascTemplate[m][n]] = ch;
                        Field field = (Field)(fields.get(new Character(ch)));
                        ascTemplateFields[ascTemplate[m][n]] = field;
                        // always added in row, column order
                        field.addPos(ascTemplate[m][n]);    
                        //System.out.println("added " + m + ":" + n + ":"
                        // + ascTemplate[m][n] + ":"
                        // + realMarkLocations[ascTemplate[m][n]]
                        // + ":" + (char)(ch));
                    }
//                    else {
//                        ascTemplateLocations[ascTemplate[m][n]] = -1;
//                    }
                    n++;
                }
                m++;
            }
            in.close();
        } catch(Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    /**
     * Looks for marks within the concentric circles.
     */
    public void searchMarks() {
        for(int i = 0; i < realNummarks; i++) {
            //System.out.print((char)ascTemplateLocations[i] + " ");
        }
        //System.out.println();
        
        int x, y;
        SolidMark mark
                = new SolidMark(grayimage, width / ConcentricCircle.a4width,
                                height / ConcentricCircle.a4height);
        Gray8Image markedImage = (Gray8Image)(grayimage.createCopy());
        for(int i = 0; i < realNummarks; i++) {
            x = realMarkLocations[i] / 10000;
            y = realMarkLocations[i] % 10000;
            if(mark.isMark(x, y)) {
                //int tloc = ascTemplateLocations[i];
                //Character ch = new Character((char)(ascTemplateLocations[i]));
                Field field = (Field)(fields.get(
                               new Character((char)(ascTemplateLocations[i]))));
                //System.out.println("*** " + i + ":"
                // + (char)(ascTemplateLocations[i]) + ":" + field);
                field.putValue(i);
                //System.out.println("Found mark at " + x + "," + y + ":"
                // + (char)(ascTemplateLocations[i]) + ":" + field.getName()
                // + "=" + field.getValue(i));
                mark.putMarkOnImage(markedImage);
            }
        }
        //remove if anything.
        //ImageUtil.saveImage(markedImage, "marksfoundform.png");        
    }
    
    /**
     * Saves the manipulated file 
     * @param filename name of file
     */
    public void saveData(String filename) {
        try {
            PrintWriter out = new PrintWriter(new FileOutputStream(filename));
            Enumeration e = new Vector(fields.keySet()).elements();
            while(e.hasMoreElements()) {
                Field field = (Field)(fields.get(e.nextElement()));
                //System.out.println(field.getName()
                // + "=" + field.getFieldValues());
                out.println(field.getName() + "=" + field.getFieldValues());
            }
            out.close();
        } catch(Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    int ROW_CHOICE = 0, COLUMN_CHOICE = 1, GRID_CHOICE = 2;
    int SINGLE = 0, MULTIPLE = 1, COLUMN = 2, ROW = 3;
    
    /**
     * Represents a group of marks associated with a question
     */
    class Field {
        char ch;
        int type, subtype;
        String name;
        String[] choices;
        Hashtable positions;
        String[] values;
        int numValues = 0;
        boolean[] singleDone;
        
        /**
         * Constructor.
         * @param line type of question/area
         */
        public Field(String line) {
            StringTokenizer st = new StringTokenizer(line, " ");
            ch = st.nextToken().charAt(0);
            String typestr = st.nextToken();
            if(typestr.equalsIgnoreCase("row")) {
                type = ROW_CHOICE;
            } else if(typestr.equalsIgnoreCase("column")) {
                type = COLUMN_CHOICE;
            } else if(typestr.equalsIgnoreCase("grid")) {
                type = GRID_CHOICE;
            }
            String subtypestr = st.nextToken();
            if(subtypestr.equalsIgnoreCase("single")) {
                subtype = SINGLE;
            } else if(subtypestr.equalsIgnoreCase("multiple")) {
                subtype = MULTIPLE;
            } else if(subtypestr.equalsIgnoreCase("column")) {
                subtype = COLUMN;
            } else if(subtypestr.equalsIgnoreCase("row")) {
                subtype = ROW;
            }
            name = st.nextToken();
            ArrayList choicearr = new ArrayList();
            while(st.hasMoreTokens()) {
                choicearr.add(st.nextToken());
            }
            choices = new String[choicearr.size()];
            for(int i = 0; i < choicearr.size(); i++) {
                choices[i] = (String)(choicearr.get(i));
            }
            if(type != GRID_CHOICE) {
                values = new String[choices.length];
                singleDone = null;
            } else {
                values = new String[100];
                singleDone = new boolean[100];
            }
            positions = new Hashtable();
        }
        
        /**
         * Gets the character associated with the field
         * @return Returns field character
         */
        public char getCh() {
            return ch;
        }
        
        /**
         * Gets the field format type
         * @return Returns field type
         */
        public int getType() {
            return type;
        }
        
        /**
         * Gets the type of question
         * @return Returns question type
         */
        public int getSubtype() {
            return subtype;
        }
        
        /**
         * Gets the name
         * @return Returns name
         */
        public String getName() {
            return name;
        }
        
        /**
         * Get the choices
         * @return Returns list of string choices
         */
        public String[] getChoices() {
            return choices;
        }

        int currpos = 0;
        /**
         * Adds the position of a choice
         * @param i index
         */
        public void addPos(int i) {
            if(type == ROW_CHOICE) {
                positions.put(new Integer(i), new Integer(currpos++));
            } else if(type == COLUMN_CHOICE) {
                positions.put(new Integer(i), new Integer(currpos++));
            } else if(type == GRID_CHOICE && subtype == ROW) {
                positions.put(new Integer(i),
                        new Integer(currpos % choices.length));
                currpos++;
            } else if(type == GRID_CHOICE && subtype == COLUMN) {
                //System.out.println("addpos -- " + i + ":" + currpos);
                positions.put(new Integer(i), new Integer(currpos++));
            }
        }
        
        /**
         * Gets the value
         * @param i index value
         * @return Returns choice value
         */
        public String getValue(int i) {
            if(type == GRID_CHOICE && subtype == COLUMN) {
                int mod = currpos / choices.length;
                return choices[((Integer)
                        (positions.get(new Integer(i)))).intValue() / mod];
            } else {
                return choices[((Integer)
                        (positions.get(new Integer(i)))).intValue()];
            }
        }

        /**
         * Sets the value of a choice
         * @param i index of choice
         */
        public void putValue(int i) {
            if(type == GRID_CHOICE && subtype == COLUMN) {
                int posi = ((Integer)
                        (positions.get(new Integer(i)))).intValue();
                //System.out.println("currpos = " + currpos + ":"
                // + choices.length + ":" + i + ":" + posi + ":"
                // + (posi % (currpos / choices.length)) + ":"
                // + singleDone[posi % (currpos / choices.length)]);
                if(!singleDone[posi % (currpos / choices.length)]) {
                    values[numValues++] = getValue(i);
                    singleDone[posi % (currpos / choices.length)] = true;
                }
            } else if(type == GRID_CHOICE && subtype == ROW) {
                int posi = ((Integer)
                        (positions.get(new Integer(i)))).intValue();
                if(!singleDone[posi / choices.length]) {
                    values[numValues++] = getValue(i);
                    singleDone[posi / choices.length] = true;
                }
            } else {
                values[numValues++] = getValue(i);
            }
        }
        
        /**
         * Gets the field value
         * @return Returns field value
         */
        public String getFieldValues() {
            if(subtype == SINGLE) {
                return values[0];
            }
            else if(subtype == MULTIPLE) {
                String retval = "";
                for(int i = 0; i < numValues; i++) {
                    retval = retval + " " + values[i];
                }
                if(retval.length() > 0) {
                    return retval.substring(1);
                } else {
                    return "";
                }
            }
            else if(type == GRID_CHOICE) {
                String retval = "";
                for(int i = 0; i < numValues; i++) {
                    retval = retval + values[i];
                }
                if(retval.length() > 0) {
                    return retval;
                } else {
                    return "";
                }
            }
            return "";
        }
    }

}
