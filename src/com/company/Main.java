/*
    Description : Simple program that implement a basic algorithm of simplexe.
    Author      : DrCoc
 */

package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    // Struct of  matrix cell coordinates
    static class Cell {
        int x;
        int y;
    }

    public static void main(String[] args) {
        // Data of simplexe matrix
        float[][] matrice = {
                {1f  , 0.5f , 1f, 0f, 0f, 100f},
                {0.2f, 0.15f, 0f, 1f, 0f, 25f},
                {0f  , 1f   , 0f, 0f, 1f, 160f},
                {1.5f, 0.9f , 0f, 0f, 0f, 0f}
        };

        // Showing initial formatted matrix in console
        showMatrice(matrice);

        // Creating a list to store matrix and reuse the previous data
        List<float[][]> matriceList = new ArrayList<>();
        matriceList.add(deepCopyFloatMatrice(matrice));
        int matriceId = 0;

        // Initializing matrix with initial data
        float[][] oldMatrice, newMatrice;
        oldMatrice = matriceList.get(matriceId);

        // Iterate while "entrance variable" is available
        while(getEntranteCell(oldMatrice) != null) {

            // Initializing a new matrix with the oldest to keep the format
            newMatrice = deepCopyFloatMatrice(oldMatrice);

            // Get "Entrante variable" and "Pivot"
            Cell entrante = getEntranteCell(oldMatrice);
            Cell pivot = getPivotCell(oldMatrice, entrante);

            // Replace value in cell by 0 for the "Pivot" column
            for (int j = 0; j < newMatrice.length; j++) {
                newMatrice[j][pivot.x] = 0;
            }

            // Calculate the new values for the "Pivot" row
            for (int i = 0; i < newMatrice[pivot.y].length; i++) {
                newMatrice[pivot.y][i] = oldMatrice[pivot.y][i] / oldMatrice[pivot.y][pivot.x];
            }

            // Calculating the all other values of the matrix
            for (int j = 0; j < newMatrice.length; j++) {
                for (int i = 0; i < newMatrice[j].length; i++) {
                    if (i != pivot.x && pivot.y != j) {
                        newMatrice[j][i] = oldMatrice[j][i] - oldMatrice[pivot.y][i] * oldMatrice[j][pivot.x] / oldMatrice[pivot.y][pivot.x];
                    }
                }
            }

            showMatrice(newMatrice);

            // Incrementing the matrix
            matriceId++;
            matriceList.add(deepCopyFloatMatrice(newMatrice));
            oldMatrice = matriceList.get(matriceId);
        }

    }

    // Return the "Entrance" cell position
    public static Cell getEntranteCell(float[][] matrice) {
        int lastRow    = matrice.length - 1;
        int lastColumn = matrice[0].length - 1;
        float[] array  = matrice[lastRow];
        float maxValue = array[0];
        Cell entrante  = new Cell();
        entrante.y     = lastRow;

        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxValue && i < lastColumn) {
                maxValue   = array[i];
                entrante.x = i;
            }
        }
        if (maxValue > 0f) {
            return entrante;
        } else {
            return null;
        }

    }

    // Return the "Pivot" cell position
    private static Cell getPivotCell(float[][] matrice, Cell entrante) {
        int lastColumn = matrice[0].length - 1;
        float minValue = matrice[0][lastColumn];
        Cell pivot     = new Cell();
        pivot.x        = entrante.x;

        for (int j = 1; j < matrice.length; j++) {

            float calculedValue = matrice[j][lastColumn]/matrice[j][entrante.x];
            if ( calculedValue < minValue && calculedValue > 0) {
                minValue = calculedValue;
                pivot.y  = j;
            }
        }
        return pivot;
    }

    // Show formatted matrix in console
    private static void showMatrice(float[][] matrice){
        for (int j = 0; j < matrice.length; j++) {
            for (int i = 0; i < matrice[j].length; i++) {

                System.out.printf("%15f", matrice[j][i]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Copying the matrix and drop the reference of nested array
    public static float[][] deepCopyFloatMatrice(float[][] matrice) {
        if (matrice == null)
            return null;
        float[][] result = new float[matrice.length][];
        for (int r = 0; r < matrice.length; r++) {
            result[r] = matrice[r].clone();
        }
        return result;
    }
}
