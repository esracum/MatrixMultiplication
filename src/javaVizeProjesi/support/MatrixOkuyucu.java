package javaVizeProjesi.support;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javaVizeProjesi.Matrix;

public class MatrixOkuyucu {
    public static Matrix[] readMatricesFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.canRead()) {
            System.out.println("Dosya bulunamadı veya okunamıyor: " + filePath);
            return null;
        }
        try (Scanner fileScanner = new Scanner(file)) {
            System.out.println("Dosyadan matrisler okunuyor...");

            // Read first matrix dimensions and values
            int rows1 = fileScanner.nextInt();
            int cols1 = fileScanner.nextInt();
            System.out.println("İlk matris boyutları: " + rows1 + "x" + cols1);
            Matrix m1 = new Matrix(rows1, cols1);
            for (int i = 0; i < rows1; i++) {
                for (int j = 0; j < cols1; j++) {
                    if (fileScanner.hasNextDouble()) {
                        m1.set(i, j, fileScanner.nextDouble());
                    } else {
                         throw new InputMismatchException("Beklenen double değer bulunamadı.");
                    }
                }
            }

            int rows2 = fileScanner.nextInt();
            int cols2 = fileScanner.nextInt();
            System.out.println("İkinci matris boyutları: " + rows2 + "x" + cols2);
            Matrix m2 = new Matrix(rows2, cols2);
            for (int i = 0; i < rows2; i++) {
                for (int j = 0; j < cols2; j++) {
                    if (fileScanner.hasNextDouble()) {
                        m2.set(i, j, fileScanner.nextDouble());
                    } else {
                        throw new InputMismatchException("Beklenen double değer bulunamadı.");
                    }
                }
            }
            return new Matrix[]{m1, m2};
        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı: " + filePath);
            e.printStackTrace();
        } catch (InputMismatchException e) {
            System.out.println("Dosya formatı hatalı.");
            e.printStackTrace();
        }
        return null;
    }
}
