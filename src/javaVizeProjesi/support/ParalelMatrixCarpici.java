package javaVizeProjesi.support;
import java.util.Objects;

import javaVizeProjesi.Matrix;
import javaVizeProjesi.MatrixCarpici;

public final class ParalelMatrixCarpici implements MatrixCarpici {
    
    private static final int MINIMUM_WORKLOAD = 10_000;

    @Override
    public Matrix multiply(Matrix left, Matrix right) {
        Objects.requireNonNull(left, "The left matrix is null.");
        Objects.requireNonNull(right, "The right matrix is null.");
        checkDimensions(left, right);

        Matrix result = new Matrix(left.getRows(), right.getColumns());

        int workload = left.getRows() * right.getColumns() * right.getRows();
        int numberOfThreads = Runtime.getRuntime().availableProcessors();

        numberOfThreads = Math.min(numberOfThreads, workload / MINIMUM_WORKLOAD);
        numberOfThreads = Math.min(numberOfThreads, left.getRows());
        numberOfThreads = Math.max(numberOfThreads, 1);

        if (numberOfThreads == 1) {
            return new SiralilMatrixCarpici().multiply(left, right);
        }

        MultiplierThread[] threads = new MultiplierThread[numberOfThreads - 1];

        int basicRowsPerThreads = left.getRows() / numberOfThreads;
        int startRow = 0;

        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new MultiplierThread(left, right, result, startRow, basicRowsPerThreads);
            threads[i].start();
            startRow += basicRowsPerThreads;
        }

        MultiplierThread mainThread = new MultiplierThread(left, right, result, startRow, basicRowsPerThreads + left.getRows() % basicRowsPerThreads);
        mainThread.run();

        for (MultiplierThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException("A thread was interrupted", ex);
            }
        }

        for (MultiplierThread thread : threads) {
            System.out.println("Thread execution time: " + thread.getExecutionTime() + " ms");
        }
        System.out.println("Main thread execution time: " + mainThread.getExecutionTime() + " ms");

        return result;
    }

    private static final class MultiplierThread extends Thread {
        
        private final Matrix left;
        private final Matrix right;
        private final Matrix result;
        private final int startRow;
        private final int rows;
        private long executionTime;

        public MultiplierThread(Matrix left, Matrix right, Matrix result, int startRow, int rows) {
            this.left = left;
            this.right = right;
            this.result = result;
            this.startRow = startRow;
            this.rows = rows;                        
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            for (int y = startRow; y < startRow + rows; ++y) {
                for (int x = 0; x < right.getColumns(); ++x) {
                    double sum = 0.0;
                    for (int i = 0; i < left.getColumns(); ++i) {
                        sum += left.get(y, i) * right.get(i, x);
                    }
                    result.set(y, x, sum);
                }
            }
            long end = System.currentTimeMillis();
            executionTime = end - start;
        }

        public long getExecutionTime() {
            return executionTime;
        }
    }

    private void checkDimensions(Matrix left, Matrix right) {
        if (left.getColumns() != right.getRows()) {
            throw new IllegalArgumentException("The number of columns of the left matrix must equal the number of rows of the right matrix.");
        }
    }
}
