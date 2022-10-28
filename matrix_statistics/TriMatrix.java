package matrix_statistics;

/*
 * PROJECT III: TriMatrix.java
 *
 * This file contains a template for the class TriMatrix. Not all methods are
 * implemented and they do not have placeholder return statements. Make sure 
 * you have carefully read the project formulation before starting to work 
 * on this file. You will also need to have completed the Matrix class.
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

public class TriMatrix extends Matrix {
    /**
     * An array holding the diagonal elements of the matrix.
     */
    private double[] diagonal;

    /**
     * An array holding the upper-diagonal elements of the matrix.
     */
    private double[] upperDiagonal;

    /**
     * An array holding the lower-diagonal elements of the matrix.
     */
    private double[] lowerDiagonal;

    /**
     * Constructor function: should initialise iDim and jDim through the Matrix
     * constructor and set up the values array.
     *
     * @param dimension The dimension of the array.
     */
    public TriMatrix(int dimension) {
        // access the superclass (Matrix) constructor
        super(dimension, dimension);
        if (dimension <= 0)
            throw new MatrixException("Cannot form matrix with dimensions less than or equal to 0");

        // initialize sizes of the diagonal arrays
        diagonal = new double[iDim];
        upperDiagonal = new double[iDim - 1];
        lowerDiagonal = new double[iDim - 1];
    }

    /**
     * Getter function: return the (i,j)'th entry of the matrix.
     *
     * @param i The location in the first co-ordinate.
     * @param j The location in the second co-ordinate.
     * @return The (i,j)'th entry of the matrix.
     */
    public double getIJ(int i, int j) {
        // check whether element is out of bounds
        if (i < 0 || i >= this.iDim || j < 0 || j >= this.jDim)
            throw new MatrixException("element is not contained in the matrix");
        // if the element is contained, return it
        if (j == i + 1) {
            return upperDiagonal[i];
        } else if (j == i) {
            return diagonal[i];
        } else if (j == i - 1) {
            return lowerDiagonal[j];
        } else
            return 0.00;
    }

    /**
     * Setter function: set the (i,j)'th entry of the data array.
     *
     * @param i     The location in the first co-ordinate.
     * @param j     The location in the second co-ordinate.
     * @param value The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double value) {
        // check whether element is out of bounds
        // element is not on the diagonals if abs(j-i)>1
        if (i < 0 || i >= this.iDim || j < 0 || j >= this.jDim || j - i > 1 || j - i < -1)
            throw new MatrixException("element is not on the main three diagonals of the matrix");
        if (j == i + 1) {
            upperDiagonal[i] = value;
        } else if (j == i) {
            diagonal[i] = value;
        } else if (j == i - 1) {
            lowerDiagonal[j] = value;
        }
    }

    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        // we already check for singularity in LUdecomp method
        // create the sign array to pass in LUdecomp

        // obtain the LU decomposition of our matrix
        TriMatrix A = this.LUdecomp();

        // initialise determinant with element in (0,0)
        double determinant = A.getIJ(0, 0);

        // we know that the det of upper or lower triangular matrix
        // is the product of the entries in main diagonal
        // since l_ii = 1 for all i we only need to get product of
        // the main diagonal in the output of LUdecomp
        for (int i = 1; i < this.iDim; i++) {
            determinant *= A.getIJ(i, i);
        }

        return determinant;
    }

    /**
     * Returns the LU decomposition of this matrix. See the formulation for a
     * more detailed description.
     * 
     * @return The LU decomposition of this matrix.
     */
    public TriMatrix LUdecomp() {
        // equating the coefficients we get equality for the elements in position (0,0)
        // we also obtain linear relationships for the other elements

        // create a matrix to contain the LUdecomp
        TriMatrix A = new TriMatrix(this.iDim);
        A.setIJ(0, 0, this.getIJ(0, 0));

        // set the diagonal elements in a combined way using the aforementioned
        // relations
        for (int i = 1; i < this.iDim; i++) {
            A.setIJ(i - 1, i, this.getIJ(i - 1, i));
            A.setIJ(i, i - 1, this.getIJ(i, i - 1) / A.getIJ(i - 1, i - 1));
            A.setIJ(i, i, this.getIJ(i, i) - (A.getIJ(i, i - 1) * A.getIJ(i - 1, i)));
        }
        return A;
    }

    /**
     * Add the matrix to another second matrix.
     *
     * @param second The Matrix to add to this matrix.
     * @return The sum of this matrix with the second matrix.
     */
    public Matrix add(Matrix second) {
        if (second instanceof TriMatrix) {
            // check whether dimensions match for addition
            if (this.iDim != second.iDim || this.jDim != second.jDim)
                throw new MatrixException("dimensions don't match for addition");

            // create a matrix to contain the sum
            Matrix F = new TriMatrix(iDim);

            // set the main diagonal with the relevant sums
            for (int i = 0; i < this.iDim; i++) {
                F.setIJ(i, i, this.getIJ(i, i) + second.getIJ(i, i));
            }

            // set the upper and lower diagonals with the relevant sums
            for (int i = 0; i < this.iDim - 1; i++) {
                F.setIJ(i, i + 1, this.getIJ(i, i + 1) + second.getIJ(i, i + 1));
                F.setIJ(i + 1, i, this.getIJ(i + 1, i) + second.getIJ(i + 1, i));
            }

            return F;

            // if the given matrix is a GeneralMatrix we already have a method for that
        } else if (second instanceof GeneralMatrix)
            return second.add(this);
        else {
            throw new MatrixException("Please give me a proper matrix.");
        }
    }

    /**
     * Multiply the matrix by another matrix A. This is a _left_ product,
     * i.e. if this matrix is called B then it calculates the product BA.
     *
     * @param A The Matrix to multiply by.
     * @return The product of this matrix with the matrix A.
     */
    public Matrix multiply(Matrix A) {
        // we repeat the exact process we used in GeneralMatrix

        // matrix multiplication is only defined when this.jDim = A.iDim
        if (this.jDim != A.iDim)
            throw new MatrixException("dimensions don't match for multiplication");

        // matrix multiplication must yield a matrix of the form
        // this.iDim x A.jDim.
        Matrix F = new GeneralMatrix(this.iDim, A.jDim);

        // We know that matrix multiplication is defined as
        // F_ij = sum(this_ik + A_kj) from k=1[0] to this.jDim
        for (int i = 0; i < this.iDim; i++) {
            for (int j = 0; j < A.jDim; j++) {
                // initialize elements as zero
                double elements = 0.00;
                for (int k = 0; k < this.jDim; k++)
                    // compute individual elements using the sum formula above
                    elements += this.getIJ(i, k) * A.getIJ(k, j);

                F.setIJ(i, j, elements);

            }
        }
        return F;
    }

    /**
     * Multiply the matrix by a scalar.
     *
     * @param scalar The scalar to multiply the matrix by.
     * @return The product of this matrix with the scalar.
     */
    public Matrix multiply(double scalar) {

        // create a matrix to contain the products
        Matrix F = new TriMatrix(iDim);

        // set the main diagonal with the relevant products
        for (int i = 0; i < this.iDim; i++) {
            F.setIJ(i, i, this.getIJ(i, i) * scalar);
        }

        // set the upper and lower diagonals with the relevant products
        for (int i = 0; i < this.iDim - 1; i++) {
            F.setIJ(i, i + 1, this.getIJ(i, i + 1) * scalar);

            F.setIJ(i + 1, i, this.getIJ(i + 1, i) * scalar);
        }

        return F;

    }

    /**
     * Populates the matrix with random numbers which are uniformly
     * distributed between 0 and 1.
     */
    public void random() {

        // randomize the main diagonal
        for (int i = 0; i < this.iDim; i++)
            this.setIJ(i, i, Math.random());

        // randomize the upper and lower diagonals
        for (int i = 0; i < this.iDim - 1; i++) {
            this.setIJ(i, i + 1, Math.random());
            this.setIJ(i + 1, i, Math.random());
        }

    }

    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {
        // Test your class implementation using this method.

        // TriMatrix S = new TriMatrix(4);
        // S.setIJ(0, 0, 2);
        // S.setIJ(0, 1, 4);
        // S.setIJ(1, 0, 6);
        // S.setIJ(1, 1, 8);
        // S.setIJ(1, 2, 10);
        // S.setIJ(2, 1, 12);
        // S.setIJ(2, 2, 14);

        // TriMatrix X = new TriMatrix(3);
        // X.setIJ(0, 0, 2);
        // X.setIJ(0, 1, 4);
        // X.setIJ(1, 0, 6);
        // X.setIJ(1, 1, 8);
        // X.setIJ(1, 2, 10);
        // X.setIJ(2, 1, 12);
        // X.setIJ(2, 2, 14);

        // TriMatrix L = X.LUdecomp();
        // System.out.println("L = \n" + L);

        // TriMatrix Y = new TriMatrix(3);
        // Y.setIJ(0, 0, 1);
        // Y.setIJ(0, 1, 2);
        // Y.setIJ(1, 0, 3);
        // Y.setIJ(1, 1, 4);
        // Y.setIJ(1, 2, 5);
        // Y.setIJ(2, 1, 6);
        // Y.setIJ(2, 2, 7);

        // Matrix B = new GeneralMatrix(2, 2);
        // B.setIJ(0, 0, 0.5);
        // B.setIJ(0, 1, 0.6);
        // B.setIJ(1, 0, 0.5);
        // B.setIJ(1, 1, 0.6);

        // try {
        // Matrix Z = new TriMatrix(0);
        // } catch (MatrixException exception) {
        // System.out.println(exception.getMessage());
        // }
        // System.out.println("X = \n" + S);
        // System.out.println("Y = \n" + Y);

        // GeneralMatrix A = new GeneralMatrix(3, 4);
        // A.random();
        // System.out.println("A = \n" + A);

        // System.out.println("X[0][1] = " + X.getIJ(0, 1));
        // System.out.println("X[1][1] = " + X.getIJ(1, 1));
        // try {
        // S.setIJ(1, 0, 8);
        // } catch (MatrixException exception) {
        // System.out.println(exception.getMessage());
        // }
        // System.out.println("S = \n" + S);
        // System.out.println("3*X = \n" + X.multiply(3));

        // System.out.println("X * Y = \n" + X.multiply(Y));
        // try {
        // System.out.println("X * A = \n" + X.multiply(A));
        // } catch (MatrixException exception) {
        // System.out.println(exception.getMessage());
        // }

        // System.out.println("X + Y = \n" + X.add(Y));

        // System.out.println("X * Y = \n" + X.multiply(Y));
        // try {
        // System.out.println("X + A = \n" + X.add(A));
        // } catch (MatrixException exception) {
        // System.out.println(exception.getMessage());
        // }

        // System.out.println("det(Y)= " + Y.determinant()); //
        // System.out.println("det(X)= " + X.determinant()); //

        // try {
        // System.out.println("det(B)= " + B.determinant());
        // } catch (MatrixException exception) {
        // System.out.println(exception.getMessage());
        // }
        // }
    }
}