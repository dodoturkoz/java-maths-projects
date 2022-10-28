package matrix_statistics;

/*
 * PROJECT III: GeneralMatrix.java
 *
 * This file contains a template for the class GeneralMatrix. Not all methods
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

public class GeneralMatrix extends Matrix {
    /**
     * This instance variable stores the elements of the matrix.
     */

    private double[][] values;

    /**
     * Constructor function: should initialise iDim and jDim through the Matrix
     * constructor and set up the data array.
     *
     * @param firstDim  The first dimension of the array.
     * @param secondDim The second dimension of the array.
     */
    public GeneralMatrix(int firstDim, int secondDim) {
        // access the superclass (Matrix) constructor
        super(firstDim, secondDim);

        if (firstDim <= 0 || secondDim <= 0) {
            throw new MatrixException("Cannot form matrix with dimensions less than or equal to 0");
        }
        values = new double[firstDim][secondDim];

    }

    /**
     * Constructor function. This is a copy constructor; it should create a
     * copy of the second matrix.
     *
     * @param second The matrix to create a copy of.
     */
    public GeneralMatrix(GeneralMatrix second) {
        // copy all elements one by one
        this(second.iDim, second.jDim);
        for (int i = 0; i < second.iDim; i++) {
            for (int j = 0; j < second.jDim; j++) {
                this.values[i][j] = second.getIJ(i, j);
            }
        }
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
        return values[i][j];
    }

    /**
     * Setter function: set the (i,j)'th entry of the values array.
     *
     * @param i     The location in the first co-ordinate.
     * @param j     The location in the second co-ordinate.
     * @param value The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double value) {
        // check whether element is out of bounds
        if (i < 0 || i >= this.iDim || j < 0 || j >= this.jDim)
            throw new MatrixException("element is not contained in the matrix");
        // if the element is contained, set it to value
        this.values[i][j] = value;
    }

    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        // we already check for the square matrix condition in LUdecomp method
        // we already check for singularity in LUdecomp method
        // create the sign array to pass in LUdecomp
        double[] arraycik = new double[1];

        // obtain the LU decomposition of our matrix
        try {
            Matrix A = this.LUdecomp(arraycik);

            // initialise determinant with element in (0,0)
            double determinant = A.getIJ(0, 0);

            // we know that the det of upper or lower triangular matrix
            // is the product of the entries in main diagonal
            // since l_ii = 1 for all i we only need to get product of
            // the main diagonal in the output of LUdecomp
            for (int i = 1; i < this.iDim; i++) {
                determinant *= A.getIJ(i, i);
            }

            // fix the sign of determinant
            determinant = determinant * arraycik[0];
            return determinant;

        } catch (MatrixException exception) {
            System.out.println(exception.getMessage());
            return 0.0;
        }
    }

    /**
     * Add the matrix to another second matrix.
     *
     * @param second The Matrix to add to this matrix.
     * @return The sum of this matrix with the second matrix.
     */

    public Matrix add(Matrix second) {
        // check whether dimensions match for addition
        if (this.iDim != second.iDim || this.jDim != second.jDim)
            throw new MatrixException("dimensions don't match for addition");

        // create a matrix to contain the sum
        Matrix F = new GeneralMatrix(iDim, jDim);

        // carry out elementwise addition with two matrices
        for (int i = 0; i < iDim; i++)
            for (int j = 0; j < jDim; j++)
                F.setIJ(i, j, this.getIJ(i, j) + second.getIJ(i, j));
        return F;
    }

    /**
     * Multiply the matrix by another matrix A. This is a _left_ product,
     * i.e. if this matrix is called B then it calculates the product BA.
     *
     * @param A The Matrix to multiply by.
     * @return The product of this matrix with the matrix A.
     */
    public Matrix multiply(Matrix A) {
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
    // You need to fill in this method.
    public Matrix multiply(double scalar) {
        // create a matrix to contain the after multiplied version
        Matrix F = new GeneralMatrix(iDim, jDim);

        // multiply all elements of the matrix with scalar
        for (int i = 0; i < iDim; i++)
            for (int j = 0; j < jDim; j++)
                F.setIJ(i, j, this.getIJ(i, j) * scalar);
        return F;
    }

    /**
     * Populates the matrix with random numbers which are uniformly
     * distributed between 0 and 1.
     */
    public void random() {

        // randomize every entry of matrix
        for (int i = 0; i < iDim; i++)
            for (int j = 0; j < jDim; j++)
                this.values[i][j] = Math.random();

    }

    /**
     * Returns the LU decomposition of this matrix; i.e. two matrices L and U
     * so that A = LU, where L is lower-diagonal and U is upper-diagonal.
     * 
     * On exit, decomp returns the two matrices in a single matrix by packing
     * both matrices as follows:
     *
     * [ u_11 u_12 u_13 u_14 ]
     * [ l_21 u_22 u_23 u_24 ]
     * [ l_31 l_32 u_33 u_34 ]
     * [ l_41 l_42 l_43 u_44 ]
     *
     * where u_ij are the elements of U and l_ij are the elements of l. When
     * calculating the determinant you will need to multiply by the value of
     * sign[0] calculated by the function.
     * 
     * If the matrix is singular, then the routine throws a MatrixException.
     * In this case the string from the exception's getMessage() will contain
     * "singular"
     *
     * This method is an adaptation of the one found in the book "Numerical
     * Recipies in C" (see online for more details).
     * 
     * @param sign An array of length 1. On exit, the value contained in here
     *             will either be 1 or -1, which you can use to calculate the
     *             correct sign on the determinant.
     * @return The LU decomposition of the matrix.
     */
    public GeneralMatrix LUdecomp(double[] sign) {
        // This method is complete. You should not even attempt to change it!!
        if (jDim != iDim)
            throw new MatrixException("Matrix is not square");
        if (sign.length != 1)
            throw new MatrixException("d should be of length 1");

        int i, imax = -10, j, k;
        double big, dum, sum, temp;
        double[] vv = new double[jDim];
        GeneralMatrix a = new GeneralMatrix(this);

        sign[0] = 1.0;

        for (i = 1; i <= jDim; i++) {
            big = 0.0;
            for (j = 1; j <= jDim; j++)
                if ((temp = Math.abs(a.values[i - 1][j - 1])) > big)
                    big = temp;
            if (big == 0.0)
                throw new MatrixException("Matrix is singular");
            vv[i - 1] = 1.0 / big;
        }

        for (j = 1; j <= jDim; j++) {
            for (i = 1; i < j; i++) {
                sum = a.values[i - 1][j - 1];
                for (k = 1; k < i; k++)
                    sum -= a.values[i - 1][k - 1] * a.values[k - 1][j - 1];
                a.values[i - 1][j - 1] = sum;
            }
            big = 0.0;
            for (i = j; i <= jDim; i++) {
                sum = a.values[i - 1][j - 1];
                for (k = 1; k < j; k++)
                    sum -= a.values[i - 1][k - 1] * a.values[k - 1][j - 1];
                a.values[i - 1][j - 1] = sum;
                if ((dum = vv[i - 1] * Math.abs(sum)) >= big) {
                    big = dum;
                    imax = i;
                }
            }
            if (j != imax) {
                for (k = 1; k <= jDim; k++) {
                    dum = a.values[imax - 1][k - 1];
                    a.values[imax - 1][k - 1] = a.values[j - 1][k - 1];
                    a.values[j - 1][k - 1] = dum;
                }
                sign[0] = -sign[0];
                vv[imax - 1] = vv[j - 1];
            }
            if (a.values[j - 1][j - 1] == 0.0)
                a.values[j - 1][j - 1] = 1.0e-20;
            if (j != jDim) {
                dum = 1.0 / a.values[j - 1][j - 1];
                for (i = j + 1; i <= jDim; i++)
                    a.values[i - 1][j - 1] *= dum;
            }
        }

        return a;
    }

    /*
     * tester function
     */
    public static void main(String[] args) {
        // Test your class implementation using this method.

        // Matrix X = new GeneralMatrix(2, 2);
        // X.setIJ(0, 0, 2);
        // X.setIJ(0, 1, 4);
        // X.setIJ(1, 0, 6);
        // X.setIJ(1, 1, 8);

        // Matrix Y = new GeneralMatrix(2, 2);
        // Y.setIJ(0, 0, 3);
        // Y.setIJ(0, 1, 6);
        // Y.setIJ(1, 0, 9);
        // Y.setIJ(1, 1, 12);

        // Matrix B = new GeneralMatrix(2, 2);
        // B.setIJ(0, 0, 0);
        // B.setIJ(0, 1, 0);
        // B.setIJ(1, 0, 3);
        // B.setIJ(1, 1, 3);

        // Matrix Same = new GeneralMatrix((GeneralMatrix) X);

        // try {
        // Matrix Z = new GeneralMatrix(0, 3);
        // } catch (MatrixException exception) {
        // System.out.println(exception.getMessage());
        // }
        // System.out.println("X = \n" + X);
        // System.out.println("Y = \n" + Y);
        // System.out.println("Same: Copy of X = \n" + Same);

        // GeneralMatrix A = new GeneralMatrix(3, 4);
        // A.random();
        // System.out.println("A = \n" + A);

        // System.out.println("X[1][1] = " + X.getIJ(1, 1));
        // X.setIJ(1, 1, 6.29);
        // System.out.println("X[1][1] = " + X.getIJ(1, 1));
        // try {
        // System.out.println(X.getIJ(3, 1));
        // } catch (MatrixException exception) {
        // System.out.println(exception.getMessage());
        // }

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

        // System.out.println("det(Y)= " + Y.determinant()); // -18
        // System.out.println("det(X)= " + X.determinant()); // -11.42
        // try {
        // System.out.println("det(A)= " + A.determinant());
        // } catch (MatrixException exception) {
        // System.out.println(exception.getMessage());
        // }
        // try {
        // System.out.println("det(B)= " + B.determinant());
        // } catch (MatrixException exception) {
        // System.out.println(exception.getMessage());
        // }
    }
}