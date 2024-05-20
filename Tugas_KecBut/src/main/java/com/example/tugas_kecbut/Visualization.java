package com.example.tugas_kecbut;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.List;

// Kelas Visualization untuk menampilkan visualisasi peta dan animasi pencarian jalur
public class Visualization {
    private static final int CELL_SIZE = 40; // Ukuran sel pada peta

    // Method untuk membuat visualisasi peta
    public static void buatPeta(Pane root, char[][] peta, List<int[]> path, String title, int[] start, int[] goal, Text stepsText) {
        int rows = peta.length; // Jumlah baris pada peta
        int cols = peta[0].length; // Jumlah kolom pada peta

        // Iterasi untuk menggambar setiap sel pada peta
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Rectangle rect = new Rectangle(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                if (peta[r][c] == '#') {
                    rect.setFill(Color.BLACK); // Warna hitam untuk dinding
                } else if (r == start[0] && c == start[1]) {
                    rect.setFill(Color.BLUE); // Warna biru untuk titik awal
                } else if (r == goal[0] && c == goal[1]) {
                    rect.setFill(Color.GREEN); // Warna hijau untuk titik akhir
                } else {
                    rect.setFill(Color.WHITE); // Warna putih untuk sel lainnya
                }
                rect.setStroke(Color.BLACK); // Warna garis pembatas hitam
                root.getChildren().add(rect); // Menambahkan sel ke root node
            }
        }

        // Menambahkan teks judul
        Text text = new Text(10, rows * CELL_SIZE + 20, title);
        text.setFill(Color.RED);
        root.getChildren().add(text);

        // Menambahkan teks jumlah langkah
        stepsText.setFill(Color.BLUE);
        stepsText.setLayoutX(10);
        stepsText.setLayoutY(rows * CELL_SIZE + 40);
        root.getChildren().add(stepsText);
    }

    // Method untuk melakukan animasi pencarian jalur
    public static void animasiPola(Pane root, List<int[]> path, Text stepsText) {
        final int[] index = {0}; // Indeks langkah saat ini
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            if (index[0] < path.size()) { // Memeriksa apakah masih ada langkah dalam jalur
                int[] step = path.get(index[0]); // Mendapatkan langkah pada indeks tertentu
                Rectangle rect = new Rectangle(step[1] * CELL_SIZE, step[0] * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                rect.setFill(Color.DEEPPINK); // Warna ungu untuk langkah
                root.getChildren().add(rect); // Menambahkan langkah ke root node
                index[0]++; // Increment indeks
                stepsText.setFill(Color.BLACK);
                stepsText.setText("Jumlah Jalur: " + index[0]); // Update teks jumlah langkah
            }
        }));

        timeline.setCycleCount(path.size()); // Jumlah langkah dalam jalur menjadi jumlah siklus animasi
        timeline.play(); // Memulai animasi
    }
}
