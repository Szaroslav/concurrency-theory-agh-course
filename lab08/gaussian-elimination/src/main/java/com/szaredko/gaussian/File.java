package com.szaredko.gaussian;

import com.szaredko.gaussian.operation.AOperation;
import com.szaredko.gaussian.operation.BOperation;
import com.szaredko.gaussian.operation.COperation;
import com.szaredko.gaussian.operation.Operation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class File {
    public static List<List<Operation>> readFoataNF() throws FileNotFoundException {
        InputStream inputStream = File.class.getClassLoader()
            .getResourceAsStream("fnf.txt");
        if (inputStream == null) {
            throw new FileNotFoundException("Foata Normal Form input not found.");
        }

        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader reader = new BufferedReader(inputStreamReader)
        ) {
            List<List<Operation>> operations = new ArrayList<>();
            Pattern numberPattern = Pattern.compile("[0-9]+");
            int[] indexes = new int[3];

            String fnfGroup;
            while ((fnfGroup = reader.readLine()) != null) {
                operations.add(new ArrayList<>());
                List<Operation> currentOperations = operations.get(operations.size() - 1);

                for (String symbol : fnfGroup.split(";")) {
                    int i, j, k;
                    i = j = k = 0;

                    i = 0;
                    Matcher matcher = numberPattern.matcher(symbol);
                    while (matcher.find()) {
                        indexes[i++] = Integer.parseInt(matcher.group(), 10);
                    }

                    char symbolType = symbol.charAt(0);
                    switch (symbolType) {
                        case 'A' -> {
                            i = indexes[0];
                            k = indexes[1];
                        }
                        case 'B', 'C' -> {
                            i = indexes[0];
                            j = indexes[1];
                            k = indexes[2];
                        }
                        default -> {
                            System.out.println("Unknown symbol type.");
                        }
                    }
                    switch (symbolType) {
                        case 'A' -> currentOperations.add(new AOperation(i, k));
                        case 'B' -> currentOperations.add(new BOperation(i, j, k));
                        case 'C' -> currentOperations.add(new COperation(i, j, k));
                        default  -> System.out.println("Unknown symbol type.");
                    }
                }
            }

            return operations;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static double[][] readTest() throws IOException {
        InputStream inputStream = File.class.getClassLoader()
            .getResourceAsStream("test.txt");
        if (inputStream == null) {
            throw new FileNotFoundException("Input test file not found.");
        }

        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader reader = new BufferedReader(inputStreamReader)
        ) {
            int n = Integer.parseInt(reader.readLine());
            double[][] matrix = new double[n][n + 1];

            for (int j = 0; j < n + 1; j++) {
                String line        = reader.readLine();
                String[] lineSplit = line.split(" ");
                for (int i = 0; i < lineSplit.length; i++) {
                    matrix[i][j] = Double.parseDouble(lineSplit[i]);
                }
            }

            return matrix;
        }
    }
}
