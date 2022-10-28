package fractal_generator;

/*
 * PROJECT II: Project2.java
 *
 * This file contains a template for the class Project2. Not all methods
 * are implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file.
 *
 * There are a lot of functions in this class, as it deals with creating an
 * image using purely Java. I have already completed a lot of the technical
 * aspects for you, so there should not be a huge amount for you to do in this
 * class! 
 *
 * At the bottom of this class there is a section of functions which I have
 * already written and deal with the more complicated tasks. You should make
 * sure that you read through the function descriptions, but DO NOT ALTER
 * THEM! Also, remember to call the setupFractal() function from your
 * constructor!
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file! You should also test this class using the main()
 * function.
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 * 
 * Tasks:
 *
 * 1) Complete this class with the indicated methods and instance variables.
 *
 * 2) Fill in the following fields:
 *
 * NAME: Dogukan Eftal Turkoz
 * UNIVERSITY ID: 2109828
 * DEPARTMENT: Mathematics
 */

// These next lines import the relevant classes needed to output an image and
// *SHOULD NOT* be changed. You do not need to worry about their definitions.
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.ImageIO;

import java.util.ArrayList;

public class Project2 {
    /**
     * A reference to the Secant iterator object.
     */
    private Secant iterator;

    /**
     * The top-left corner of the square in the complex plane to examine.
     */
    private Complex origin;

    /**
     * The width of the square in the complex plane to examine.
     */
    private double width;

    /**
     * A list of roots of the polynomial.
     */
    private ArrayList<Complex> roots;

    /**
     * A two dimensional array holding the colours of the plot.
     */
    private Color[][] colours;

    /**
     * A flag indicating the type of plot to generate. If true, colourPixel will
     * choose darker colours if a particular root takes longer to converge.
     */
    private boolean colourIterations;

    /**
     * A standard Java object which allows us to store a simple image in
     * memory. This will be set up by setupFractal -- you do not need to worry
     * about it!
     */
    private BufferedImage fractal;

    /**
     * This object is another standard Java object which allows us to perform
     * basic graphical operations (drawing lines, rectangles, pixels, etc) on
     * the BufferedImage. This will be set up by setupFractal -- you do not
     * need to worry about it!
     */
    private Graphics2D g2;

    /**
     * Defines the width (in pixels) of the BufferedImage and hence the
     * resulting image. normal = 400
     */
    public static final int NUMPIXELS = 400;

    // ========================================================
    // Constructor function.
    // ========================================================

    /**
     * Constructor function which initialises the instance variables
     * above. IMPORTANT: Remember to call setupFractal at the end of this
     * function!!
     *
     * @param p      The polynomial to generate the fractal of.
     * @param origin The top-left corner of the square to image.
     * @param width  The width of the square to image.
     */
    public Project2(Polynomial p, Complex origin, double width) {
        this.iterator = new Secant(p);
        this.roots = new ArrayList<Complex>();
        this.origin = origin;
        this.width = width;
        setupFractal();
    }

    // ========================================================
    // Basic operations.
    // ========================================================

    /**
     * Print out all of the roots found so far, which are contained in the
     * roots ArrayList.
     */
    public void printRoots() {
        // print arraylist
        for (int i = 0; i < roots.size(); i++) {
            System.out.println(roots.get(i));
        }
    }

    /**
     * 
     * @return an ArrayList of roots for the polynomial
     */
    public ArrayList<Complex> getRoots() {
        // This method is complete.
        return roots;
    }

    /**
     * Check to see if root is in the roots ArrayList (up to tolerance).
     *
     * @param root Root to find in this.roots.
     * @return The index of root within roots (-1 if the root is not found)
     */
    public int index(Complex root) {

        // initialise indexvalue
        int indexvalue = -1;

        // if we have root in roots update indexvalue
        for (int i = 0; i < roots.size(); i++) {
            if (((roots.get(i)).subtract(root)).abs() < Secant.TOL) {
                indexvalue = i;
            }
        }
        return indexvalue;
    }

    /**
     * Convert from pixel indices (i,j) to the complex number (origin.real +
     * i*dz, origin.imag - j*dz).
     *
     * @param i x-axis co-ordinate of the pixel located at (i,j)
     * @param j y-axis co-ordinate of the pixel located at (i,j)
     * 
     */
    public Complex pixelToComplex(int i, int j) {
        double dz = width / (NUMPIXELS - 1);
        Complex conversion = new Complex(origin.getReal() + i * dz, origin.getImag() - j * dz);
        return conversion;
    }

    // ========================================================
    // Fractal generating function.
    // ========================================================

    /**
     * Generate the fractal image. Use colourPixel() to add coloured pixels
     * to the image for this fractal.
     */
    public void createFractal(boolean colourIterations) {
        // Copy colourIterations to the instance variable.,
        this.colourIterations = colourIterations;
        for (int k = 0; k < NUMPIXELS; k++) {
            for (int l = 0; l < NUMPIXELS; l++) {
                // iterate using 0 and pixelToComplex
                // System.out.println(iterator.getRoot());
                iterator.iterate(new Complex(), pixelToComplex(k, l));
                // System.out.println(iterator.getRoot());
                // System.out.println(iterator.getError());
                // only keep the root if err is OK
                if (iterator.getError() == Secant.Error.OK) {
                    Complex rootcuk = iterator.getRoot();
                    // if this is a new root add it to roots
                    if (index(rootcuk) == -1)
                        roots.add(rootcuk);
                    // call the coloring method
                    colourPixel(k, l, index(rootcuk), iterator.getNumIterations());
                }

            }
        }
    }

    // ========================================================
    // Tester function.
    // ========================================================

    public static void main(String[] args) {
        // Here is some example code which generates the two images seen in
        // figure 1 of the formulation.
        Complex[] x5 = new Complex[] { new Complex(1.0, 0.0), new Complex(-1.0, 0.0),
                new Complex(1.0, 0.0),
                new Complex(), new Complex(), new Complex(1.0, 0.0) };
        Polynomial x5p = new Polynomial(x5);
        Project2 projectx5 = new Project2(x5p, new Complex(-1.5, 1.5), 3.0);

        // The following lines of code will raise Exceptions initially
        // because the createFractal and the constructor are incomplete

        projectx5.createFractal(true);
        projectx5.saveFractal("fractal-dark.png");
        projectx5.printRoots();

        // // the polynomial in 3b1b's video named "Newton's Fractal"
        // Complex[] x5 = new Complex[] { new Complex(0.0, 0.0), new Complex(315.0,
        // 0.0), new Complex(0.0, 0.0), new Complex(-420.0, 0.0), new Complex(0.0, 0.0),
        // new Complex(378.0, 0.0),
        // new Complex(0.0, 0.0), new Complex(-180.0, 0.0), new Complex(0.0, 0.0), new
        // Complex(35.0, 0.0) };
        // Polynomial x5p = new Polynomial(x5);
        // Project2 projectx5 = new Project2(x5p, new Complex(-1.0, 1.0), 100);

        // projectx5.createFractal(true);
        // projectx5.saveFractal("fractal-dark.png");
        // projectx5.printRoots();
        // System.out.println(x5p);

    }

    // ====================================================================
    // OTHER FUNCTIONS
    //
    // The rest of the functions in this class are COMPLETE (with the
    // exception of the main function) since they involve quite complex Java
    // code to deal with the graphics. This means they *do not* and *should
    // not* need to be altered! But you should read their descriptions so you
    // know how to use them.
    // ====================================================================

    /**
     * Sets up all the fractal image. Make sure that your constructor calls
     * this function!
     */
    private void setupFractal() {
        // This function is complete!
        int i, j;

        if (iterator.getF().degree() < 3 || iterator.getF().degree() > 10)
            throw new RuntimeException("Degree of polynomial must be between 3 and 5 inclusive!");

        this.colours = new Color[13][Secant.MAXITER];
        this.colours[0][0] = Color.RED;
        this.colours[1][0] = Color.GREEN;
        this.colours[2][0] = Color.BLUE;
        this.colours[3][0] = Color.CYAN;
        this.colours[4][0] = Color.MAGENTA;
        this.colours[5][0] = new Color(18, 43, 126);
        this.colours[6][0] = new Color(155, 126, 211);
        this.colours[7][0] = new Color(221, 203, 255);
        this.colours[8][0] = new Color(255, 33, 144);
        this.colours[9][0] = new Color(240, 0, 0);
        this.colours[10][0] = new Color(240, 0, 0);
        this.colours[11][0] = new Color(240, 41, 63);
        this.colours[12][0] = new Color(20, 41, 200);

        for (i = 0; i < 5; i++) {
            float[] components = colours[i][0].getRGBComponents(null);
            float[] delta = new float[3];

            for (j = 0; j < 3; j++)
                delta[j] = 0.8f * components[j] / Secant.MAXITER;

            for (j = 1; j < Secant.MAXITER; j++) {
                float[] tmp = colours[i][j - 1].getRGBComponents(null);
                colours[i][j] = new Color(tmp[0] - delta[0], tmp[1] - delta[1],
                        tmp[2] - delta[2]);
            }
        }

        fractal = new BufferedImage(NUMPIXELS, NUMPIXELS, BufferedImage.TYPE_INT_RGB);
        g2 = fractal.createGraphics();
    }

    /**
     * Colours a pixel in the image.
     *
     * @param i          x-axis co-ordinate of the pixel located at (i,j)
     * @param j          y-axis co-ordinate of the pixel located at (i,j)
     * @param rootColour An integer between 0 and 4 inclusive indicating the
     *                   root number.
     * @param numIter    Number of iterations at this root.
     */
    private void colourPixel(int i, int j, int rootColour, int numIter) {
        // This function is complete!
        if (colourIterations)
            g2.setColor(colours[rootColour][numIter - 1]);
        else
            g2.setColor(colours[rootColour][0]);
        g2.fillRect(i, j, 1, 1);
    }

    /**
     * Saves the fractal image to a file.
     *
     * @param fileName The filename to save the image as. Should end in .png.
     */
    public void saveFractal(String fileName) {
        // This function is complete!
        try {
            File outputfile = new File(fileName);
            ImageIO.write(fractal, "png", outputfile);
        } catch (IOException e) {
            System.out.println("I got an error trying to save! Maybe you're out of space?");
        }
    }
}