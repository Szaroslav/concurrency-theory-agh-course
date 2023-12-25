package com.szaredko.gaussian;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static int n;
    private static double[][] matrix;
    private static double[][] mMatrix;
    private static double[][] nMatrix;

    public static void main(String[] args) throws IOException, InterruptedException {
        matrix  = File.readTest();
        n       = matrix.length;
        mMatrix = new double[n][n + 1];
        nMatrix = new double[n][n + 1];

        List<List<Operation>> operations = File.readFoataNF();
        for (List<Operation> groupOfOperations : operations) {
            for (Operation operation : groupOfOperations) {
                operation.setMatrix(matrix);
                operation.setMMatrix(mMatrix);
                operation.setNMatrix(nMatrix);
            }
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (List<Operation> groupOfOperations : operations) {
            executorService.invokeAll(groupOfOperations);
            executorService.awaitTermination(2500, TimeUnit.MILLISECONDS);
        }
        executorService.shutdown();

        for(double[] m : matrix){
            System.out.println(Arrays.toString(m));
        }

    }
}
