package com.szaredko.gaussian;

public class AOperation extends Operation {
    public AOperation(
        int i,
        int k,
        double[][] matrix,
        double[][] mMatrix,
        double[][] nMatrix
    ) {
        super(i, k, matrix, mMatrix, nMatrix);
    }

    @Override
    public double[][] call() {
        mMatrix[k][i] = matrix[k][i] / matrix[i][i];
        return matrix;
    }
}
