package com.szaredko.gaussian;

import java.util.concurrent.Callable;

public abstract class Operation implements Callable<double[][]> {
    protected final int i;
    protected final int k;
    protected final double[][] matrix;
    protected final double[][] mMatrix;
    protected final double[][] nMatrix;

    public Operation(
        int i,
        int k,
        double[][] matrix,
        double[][] mMatrix,
        double[][] nMatrix
    ) {
        this.i       = i - 1;
        this.k       = k - 1;
        this.matrix  = matrix;
        this.mMatrix = mMatrix;
        this.nMatrix = nMatrix;
    }
}
