package com.szaredko.gaussian;

public class AOperation extends Operation {
    public AOperation(int i, int k) {
        super(i, k);
    }

    @Override
    public double[][] call() {
        mMatrix[k][i] = matrix[k][i] / matrix[i][i];
        return matrix;
    }
}
