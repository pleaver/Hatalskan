package udai;

/*
 * BestFitCoords.java
 *
 * Created on June 30, 2007, 12:16 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */



import net.sourceforge.jiu.codecs.*;
import net.sourceforge.jiu.data.*;
import net.sourceforge.jiu.color.reduction.*;
import net.sourceforge.jiu.filters.*;

/**
 * Determines the best fit coordinates on an image.
 * @author Aaditeshwar Seth
 * @author modified by Door Guardians, 2015
 */
public class BestFitCoords 
{
    
    int xVal, yVal;
    double approxCircleOuterX, approxCircleInnerX, aspectScale;
    Gray8Image template;
    double maxsim = -1;

    /**
     * Constructs a best fit coordinates.
     * @param givenX x-pixel coordinate
     * @param givenY y-pixel coordinate
     * @param template Gray8 image of exam template
     * @param approxCircleOuterX ???
     * @param approxCircleInnerX ???
     * @param aspectScale aspect scale ratio
     */
    public BestFitCoords(int givenX, int givenY, Gray8Image template,
            double approxCircleOuterX, double approxCircleInnerX,
            double aspectScale) 
    {
        this.xVal = givenX;
        this.yVal = givenY;
        this.template = template;
        this.approxCircleOuterX = approxCircleOuterX;
        this.approxCircleInnerX = approxCircleInnerX;
        this.aspectScale = aspectScale;
    }

    /**
     * Get the x-coordinate
     * @return x-coordinate
     */
    public int getX() 
    {
        return xVal;
    }

    /**
     * Get the y-coordinate
     * @return y-coordinate
     */
    public int getY() 
    {
        return yVal;
    }

    /**
     * Get the Gray8 template image
     * @return gray8 image
     */
    public Gray8Image getTemplate() 
    {
        return template;
    }

    /**
     * Get the approximate radius for the outer circle.
     * @return radius
     */
    public double getApproxCircleOuterX() 
    {
        return approxCircleOuterX;
    }

    /**
     * Get the approximate radius for the inner circle.
     * @return radius
     */
    public double getApproxCircleInnerX() 
    {
        return approxCircleInnerX;
    }

    /**
     * Get the aspect scale ratio
     * @return ratio
     */
    public double getAspectScale() 
    {
        return aspectScale;
    }

    /**
     * Get the Sim.
     * @return Returns sim
     */
    public double getSim() 
    {
        return maxsim;
    }

    /**
     * Set the x-coordinate
     * @param newX new x-coordinate
     */
    public void setX(int newX) 
    {
        this.xVal = newX;
    }

    /**
     * Set the y-coordinate
     * @param newY new y-coordinate
     */
    public void setY(int newY) 
    {
        this.yVal = newY;
    }

    /**
     * Set the outer circle.
     * @param approxCircleOuterX new radius
     */
    public void setApproxCircleOuterX(double approxCircleOuterX) 
    {
        this.approxCircleOuterX = approxCircleOuterX;
    }

    /**
     * Set the inner circle.
     * @param approxCircleInnerX new radius
     */
    public void setApproxCircleInnerX(double approxCircleInnerX) 
    {
        this.approxCircleInnerX = approxCircleInnerX;
    }

    /**
     * Set the aspect scale
     * @param aspectscale new aspect scale
     */
    public void setAspectScale(double aspectscale) 
    {
        this.aspectScale = aspectscale;
    }

    /**
     * Set the gray8 template image
     * @param template new gray8 template 
     */
    public void setTemplate(Gray8Image template) 
    {
        this.template = template;
    }

    /**
     * Set the Sim.
     * @param maxsim max sim
     */
    public void setSim(double maxsim) 
    {
        this.maxsim = maxsim;
    }
    
}
