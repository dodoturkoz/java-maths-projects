package matrix_statistics;

/*
 * PROJECT III: Project3.java
 *
 * This file contains a template for the class Project3. None of methods are
 * implemented and they do not have placeholder return statements. Make sure 
 * you have carefully read the project formulation before starting to work 
 * on this file. You will also need to have completed the Matrix class, as 
 * well as GeneralMatrix and TriMatrix.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
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

public class Project3 {
    /**
     * Calculates the variance of the distribution defined by the determinant
     * of a random matrix. See the formulation for a detailed description.
     *
     * @param matrix The matrix object that will be filled with random
     *               samples.
     * @param nSamp  The number of samples to generate when calculating
     *               the variance.
     * @return The variance of the distribution.
     */
    public static double matVariance(Matrix matrix, int nSamp) {

        double sum = 0, sumsquare = 0;

        for (int i = 0; i < nSamp; i++) {
            // randomize the matrices
            matrix.random();

            // obtain the total sum of all matrices
            sum += matrix.determinant();

            // tally the sums of the squares of the determinants
            sumsquare += (matrix.determinant() * matrix.determinant());
        }
        // use the expression for variance in the assignment sheet
        return (sumsquare / nSamp - (sum / nSamp) * (sum / nSamp));
    }

    /**
     * This function should calculate the variances of matrices for matrices
     * of size 2 <= n <= 50 and print the results to the output. See the
     * formulation for more detail.
     */
    public static void main(String[] args) {
        for (int n = 2; n <= 50; n++) {
            // create nxn general matrices and trimatrices
            Matrix A = new GeneralMatrix(n, n);
            Matrix B = new TriMatrix(n);
            // for each n call matVariance for 20000 times for GeneralMatrix and
            // call matVariance for 200000 times for TriMatrix
            System.out.println(n + "  " + matVariance(A, 20000) + "  " + matVariance(B, 200000));
        }
    }
}