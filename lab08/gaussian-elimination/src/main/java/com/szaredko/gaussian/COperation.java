package com.szaredko.gaussian;

public class COperation extends Operation {
    private final int j;

    public COperation(int i, int j, int k) {
        super(i, k);
        this.j = j - 1;
    }

    @Override
    public double[][] call(){
        matrix[k][j] = matrix[k][j] - nMatrix[k][j];
        return matrix;
    }
}
