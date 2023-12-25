package com.szaredko.gaussian.operation;

import java.util.concurrent.Callable;
import lombok.Setter;


public abstract class Operation implements Callable<double[][]> {
    protected final int i;
    protected final int k;
    @Setter
    protected double[][] matrix;
    @Setter
    protected double[][] mMatrix;
    @Setter
    protected double[][] nMatrix;

    protected Operation(int i, int k) {
        this.i = i - 1;
        this.k = k - 1;
    }
}
