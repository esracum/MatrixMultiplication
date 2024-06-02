package javaVizeProjesi;

public class Matrix {
    private final double[][] data;
    private final int rows;
    private final int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new double[rows][cols];
    }

    public Matrix(Matrix m) {
        this.rows = m.rows;
        this.cols = m.cols;
        data = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(m.data[i], 0, this.data[i], 0, cols);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return cols;
    }

    public void set(int row, int col, double value) {
        data[row][col] = value;
    }

    public double get(int row, int col) {
        return data[row][col];
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Matrix matrix = (Matrix) obj;
        if (rows != matrix.rows || cols != matrix.cols) return false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (Double.compare(matrix.data[i][j], data[i][j]) != 0) return false;
            }
        }
        return true;
    }
}
