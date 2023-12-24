package com.szaredko.gaussian;

public class COperation extends Operation {
    private final int j;

    public COperation(
        int i,
        int j,
        int k,
        double[][] matrix,
        double[][] mMatrix,
        double[][] nMatrix
    ) {
        super(i, k, matrix, mMatrix, nMatrix);
        this.j = j;
    }

    @Override
    public double[][] call(){
        matrix[k][j] = matrix[k][j] - nMatrix[k][j];
        return matrix;
    }
}
