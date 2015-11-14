package udai;

/*
 * ImageUtil.java
 *
 * Created on June 30, 2007, 7:55 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */



import net.sourceforge.jiu.codecs.*;
import net.sourceforge.jiu.data.*;
import net.sourceforge.jiu.color.reduction.*;
import net.sourceforge.jiu.filters.*;

/**
 * Holds functions to deal with images.
 * @author Aaditeshwar Seth
 * @author modified by Door Guardians, 2015
 */
public class ImageUtil {

    /**
     * Reads and converts a file to a gray8 image
     * @param filename file path
     * @return Returns gray8 image
     */
    public static Gray8Image readImage(String filename) {
        Gray8Image grayimage = null;
        RGB24Image redimage = null;
        try {
            PixelImage image = ImageLoader.load(filename);
            if(image.getImageType().toString().indexOf("RGB") != -1) {
                redimage = (RGB24Image)(ImageLoader.load(filename));
                RGBToGrayConversion rgbtogray = new RGBToGrayConversion();
                rgbtogray.setInputImage(redimage);
                // adjust this if needed
                // rgbtogray.setColorWeights(0.3f, 0.3f, 0.4f);
                rgbtogray.process();
                grayimage = (Gray8Image)(rgbtogray.getOutputImage());    
            }
            else if(image.getImageType().toString().indexOf("Gray") != -1) {
                grayimage = (Gray8Image)(image);
            }
            else {
                grayimage = null;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.exit(-1);
        }

        return grayimage;
    }
    
    /**
     * Saves an image as a PNG file
     * @param img pixel image
     * @param filename name of file to be saved
     */
    public static void saveImage(PixelImage img, String filename) {
        try {
            PNGCodec codec = new PNGCodec();    
            codec.setFile(filename, CodecMode.SAVE);
            codec.setImage(img);
            codec.setCompressionLevel(0);
            codec.process();
        } catch(Exception ex) {
            ex.printStackTrace(System.out);
        } 
    }
    
    /**
     * Places a mark on an Gray8 image at a pixel coordinate
     * @param img gray8 image
     * @param x x-pixel coordinate
     * @param y y-pixel coordinate
     * @param color True : places a black mark, otherwise a white mark
     */
    public static void putMark(Gray8Image img, int x, int y, boolean color) {
        if(color) {
            img.putBlack(x, y);
            img.putBlack(x + 1, y + 1);
            img.putBlack(x - 1, y - 1);
            img.putBlack(x + 1, y);
            img.putBlack(x - 1, y);
            img.putBlack(x, y + 1);
            img.putBlack(x, y - 1);        
            img.putBlack(x + 1, y - 1);        
            img.putBlack(x - 1, y + 1);
            img.putBlack(x - 1, y - 1);        
        } else {
            img.putWhite(x, y);
            img.putWhite(x + 1, y + 1);
            img.putWhite(x - 1, y - 1);
            img.putWhite(x + 1, y);
            img.putWhite(x - 1, y);
            img.putWhite(x, y + 1);
            img.putWhite(x, y - 1);        
            img.putWhite(x + 1, y - 1);        
            img.putWhite(x - 1, y + 1);
            img.putWhite(x - 1, y - 1);        
        }
    }

}
