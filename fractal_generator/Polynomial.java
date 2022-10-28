package fractal_generator;

import java.util.*;

/*
 * PROJECT II: Polynomial.java
 *
 * This file contains a template for the class Polynomial. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file.
 *
 * This class is designed to use Complex in order to represent polynomials
 * with complex co-efficients. It provides very basic functionality and there
 * are very few methods to implement! The project formulation contains a
 * complete description.
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

public class Polynomial {

    /**
     * An array storing the complex co-efficients of the polynomial.
     */
    Complex[] coeff;

    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * General constructor: assigns this polynomial a given set of
     * co-efficients.
     *
     * @param coeff The co-efficients to use for this polynomial.
     */
    public Polynomial(Complex[] coeff) {

        // check if we are given an array of zeroes
        if (checknull(coeff, coeff.length)) {

            // if so we just return the zero polynomial
            this.coeff = new Complex[1];

            this.coeff[0] = coeff[0];

        } else {
            // convert array to arraylist
            ArrayList<Complex> coefflist = new ArrayList<>(Arrays.asList(coeff));

            // remove zeroes at the end
            while ((coefflist.get(coefflist.size() - 1)).getReal() == 0
                    && (coefflist.get(coefflist.size() - 1)).getImag() == 0) {
                int last_index = coefflist.size() - 1;
                coefflist.remove(last_index);
            }

            // convert arraylist to array
            Complex[] arr = coefflist.toArray(new Complex[coefflist.size()]);

            // form and fill in the coeff array
            this.coeff = new Complex[arr.length];

            for (int i = 0; i < arr.length; i++) {
                this.coeff[i] = arr[i];
            }
        }
    }

    /**
     * Default constructor: sets the Polynomial to the zero polynomial.
     */
    public Polynomial() {
        this.coeff = new Complex[] { new Complex() };
    }

    // ========================================================
    // Operations and functions with polynomials.
    // ========================================================

    /**
     * Return the coefficients array.
     *
     * @return The coefficients array.
     */
    public Complex[] getCoeff() {
        return coeff;
    }

    /**
     * Create a string representation of the polynomial.
     * Use z to represent the variable. Include terms
     * with zero co-efficients up to the degree of the
     * polynomial.
     *
     * For example: (-5.000+5.000i) + (2.000-2.000i)z + (-1.000+0.000i)z^2
     */

    public String toString() {
        // initialise a string
        String str = "";
        // consider constant polynomials
        if (coeff.length == 1) {
            str += "(" + coeff[0] + ") ";
            return str;
        } else {
            // consider higher degree polynomials
            str += "(" + coeff[0] + ") + ";
            str += "(" + coeff[1] + ")z";
            for (int i = 2; i < coeff.length; i++) {
                str += " + (" + coeff[i] + ")z^" + i;
            }
            return str;
            // return "(-5.000+5.000i) + (2.000-2.000i)z + (-1.000+0.000i)z^2";
        }
    }

    /**
     * Returns the degree of this polynomial.
     */
    public int degree() {
        return coeff.length - 1;
    }

    /**
     * Evaluates the polynomial at a given point z.
     *
     * @param z The point at which to evaluate the polynomial
     * @return The complex number P(z).
     */

    public Complex evaluate(Complex z) {

        // Initialize result
        Complex result = coeff[coeff.length - 1];

        // Evaluate value of polynomial by factoring at each level
        for (int i = coeff.length - 2; i >= 0; i--)
            result = (result.multiply(z)).add(coeff[i]);

        return result;
    }

    // boolean to check if we are given an array of zeroes
    boolean checknull(Complex array[], int length) {
        for (int i = 0; i < length; i++) {
            if (array[i].getImag() != 0 || array[i].getReal() != 0) {
                return false;
            }
        }
        return true;
    }

    // ========================================================
    // Tester function.
    // ========================================================

    public static void main(String[] args) {
        // Complex A = new Complex(2.0, 3.0);
        // Complex B = new Complex(3.0, 4.0);
        // Complex U = new Complex(1.890, 0);
        // Complex C = new Complex();
        // Complex[] deneme = { A, B, C, B, C, C };
        // Complex[] emre = { A, B, B };
        // Complex[] coeffd = new Complex[] { new Complex(-30.0, 0.0), new Complex(),
        // new Complex(),
        // new Complex(1.0, 0.0) };
        // Complex[] single = {};
        // Polynomial q = new Polynomial(coeffd);
        // Polynomial X = new Polynomial(deneme);
        // Polynomial Y = new Polynomial(emre);
        // Polynomial Z = new Polynomial();

        // System.out.println(Z);
        // System.out.println(q);
        // // System.out.println(q.evaluate(U));
        // // System.out.println(X.degree());
        // System.out.println(A);
        // System.out.println(Y);
        // // System.out.println(Y.evaluate(A));
        // System.out.println(X);

        // (2+3i) + (3+4i)(2+3i) + (3+4i)(2+3i)^2 = -67 + 36i

        // Complex[] x5 = new Complex[] { new Complex(1.0, 0.0), new Complex(-1.0, 0.0),
        // new Complex(1.0, 0.0),
        // new Complex(), new Complex(), new Complex(1.0, 0.0) };
        // Polynomial x5p = new Polynomial(x5);
        // System.out.println(x5p.degree());
        // System.out.println(x5p);

        // System.out.println(Z.degree());

        // Complex[] x5 = new Complex[] { new Complex(0.0, 0.0), new Complex(315.0,
        // 0.0),
        // new Complex(0.0, 0.0), new Complex(-420.0, 0.0), new Complex(0.0, 0.0), new
        // Complex(378.0, 0.0),
        // new Complex(0.0, 0.0), new Complex(-180.0, 0.0),
        // new Complex(0.0, 0.0), new Complex(35.0, 0.0) };
        // Polynomial x5p = new Polynomial(x5);
        // System.out.println(x5p.evaluate(A));

    }
}