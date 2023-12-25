package com.szaredko.gaussian;

import com.szaredko.gaussian.operation.Operation;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    private static int n;
    private static double[][] matrix;
    private static double[][] mMatrix;
    private static double[][] nMatrix;

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        matrix  = File.readTest();
        n       = matrix.length;
        mMatrix = new double[n][n + 1];
        nMatrix = new double[n][n + 1];

        List<List<Operation>> operations = File.readFoataNF();
        initOperations(operations);

        eliminateGauss(operations);
        finishElimination();

        for(double[] m : matrix){
            System.out.println(Arrays.toString(m));
        }

    }

    private static void initOperations(List<List<Operation>> operations) {
        for (List<Operation> groupOfOperations : operations) {
            for (Operation operation : groupOfOperations) {
                operation.setMatrix(matrix);
                operation.setMMatrix(mMatrix);
                operation.setNMatrix(nMatrix);
            }
        }
    }

    private static void eliminateGauss(
        List<List<Operation>> operations
    ) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (List<Operation> groupOfOperations : operations) {
            List<Future<double[][]>> futures = executorService.invokeAll(groupOfOperations);
            // Wait for all futures to finish.
            for (Future<double[][]> future : futures) {
               future.get(); // Don't need to store the result.
            }
        }
        executorService.shutdownNow();
    }

    private static void finishElimination() {
        for (int i = n - 1; i >= 0; i--) {
            double m;

            m = matrix[i][i];
            matrix[i][i] /= m;
            matrix[i][n] /= m;

            for (int i1 = i - 1; i1 >= 0; i1--) {
                m = matrix[i1][i];
                for (int j1 = n; j1 > i1; j1--) {
                    matrix[i1][j1] -= m * matrix[i][j1];
                }
            }
        }
    }
}
