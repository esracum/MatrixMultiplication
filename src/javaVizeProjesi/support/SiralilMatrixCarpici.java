package javaVizeProjesi.support;

import java.util.Objects;

import javaVizeProjesi.Matrix;
import javaVizeProjesi.MatrixCarpici;

public class SiralilMatrixCarpici implements MatrixCarpici {
    @Override
    public Matrix multiply(Matrix left, Matrix right) {
        Objects.requireNonNull(left, "The left matrix is null.");
        Objects.requireNonNull(right, "The right matrix is null.");
        checkDimensions(left, right);

        Matrix result = new Matrix(left.getRows(), right.getColumns());

        for (int i = 0; i < left.getRows(); i++) {
            for (int j = 0; j < right.getColumns(); j++) {
                double sum = 0;
                for (int k = 0; k < left.getColumns(); k++) {
                    sum += left.get(i, k) * right.get(k, j);
                }
                result.set(i, j, sum);
            }
        }
        return result;
    }

    private void checkDimensions(Matrix left, Matrix right) {
        if (left.getColumns() != right.getRows()) {
            throw new IllegalArgumentException("The number of columns of the left matrix must equal the number of rows of the right matrix.");
        }
    }
}
