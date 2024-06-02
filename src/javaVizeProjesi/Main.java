package javaVizeProjesi;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import javaVizeProjesi.support.ParalelMatrixCarpici;
import javaVizeProjesi.support.SiralilMatrixCarpici;
import javaVizeProjesi.support.MatrixOkuyucu;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Matrisleri dosyadan mı yoksa konsoldan mı almak istersiniz? (dosya/konsol): ");
        String inputMethod = scanner.next();

        Matrix m1, m2;
        if (inputMethod.equalsIgnoreCase("dosya")) {
            System.out.print("Dosya yolunu giriniz: ");
            String filePath = scanner.next();

            Matrix[] matrices = MatrixOkuyucu.readMatricesFromFile(filePath);
            if (matrices == null || matrices.length != 2) {
                System.out.println("Matrisler dosyadan okunamadı veya dosya formatı hatalı.");
                return;
            }
            m1 = matrices[0];
            m2 = matrices[1];
        } else {
            System.out.print("İlk matrisin satır sayısını giriniz: ");
            int rows1 = scanner.nextInt();

            System.out.print("İlk matrisin sütun sayısını giriniz (aynı zamanda ikinci matrisin de satır sayısı): ");
            int commonDimension = scanner.nextInt();

            System.out.print("İkinci matrisin sütun sayısını giriniz: ");
            int cols2 = scanner.nextInt();

            Random random = new Random();
            m1 = getRandomMatrix(rows1, commonDimension, random);
            m2 = getRandomMatrix(commonDimension, cols2, random);
        }

        // Matrix multiplication requires compatible dimensions
        if (m1.getColumns() != m2.getRows()) {
            throw new IllegalArgumentException("The number of columns of the left matrix must equal the number of rows of the right matrix.");
        }

        System.out.println("Matrix 1:");
        printMatrix(m1);
        System.out.println("Matrix 2:");
        printMatrix(m2);

        Matrix m1b = new Matrix(m1);
        Matrix m2b = new Matrix(m2);

        long start = System.currentTimeMillis();
        Matrix result1 = new SiralilMatrixCarpici().multiply(m1b, m2b);
        long end = System.currentTimeMillis();
        System.out.println("Sıralı matris çarpımı işleminin toplam süresi: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        Matrix result1b = new ParalelMatrixCarpici().multiply(m1b, m2b);
        end = System.currentTimeMillis();
        System.out.println("Paralel matris çarpımı işleminin toplam süresi: " + (end - start) + " ms");

        System.out.println("İki çarpımın sonucu aynı mı?: " + result1.equals(result1b));

        System.out.println("Sonuç Matrisi:");
        printMatrix(result1);
    }

    private static Matrix getRandomMatrix(int rows, int cols, Random random) {
        Matrix m = new Matrix(rows, cols);
        for (int x = 0; x < cols; ++x) {
            for (int y = 0; y < rows; ++y) {
                m.set(y, x, random.nextDouble());
            }
        }
        return m;
    }

    private static void printMatrix(Matrix m) {
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getColumns(); j++) {
                System.out.print(m.get(i, j) + " ");
            }
            System.out.println();
        }
    }
}
