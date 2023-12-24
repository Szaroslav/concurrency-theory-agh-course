package com.szaredko.gaussian;

public class BOperation extends Operation {
    private int j;

    public BOperation(
        int i,
        int k,
        double[][] matrix,
        double[][] mMatrix,
        double[][] nMatrix
    ) {
        super(i, k, matrix, mMatrix, nMatrix);
    }

    @Override
    public double[][] call(){
        nMatrix[k][j] = matrix[i][j] * mMatrix[k][i];
        return matrix;
    }
}
