package com.szaredko.gaussian.operation;

public class BOperation extends Operation {
    private int j;

    public BOperation(int i, int j, int k) {
        super(i, k);
        this.j = j - 1;
    }

    @Override
    public double[][] call(){
        nMatrix[k][j] = matrix[i][j] * mMatrix[k][i];
        return matrix;
    }
}
