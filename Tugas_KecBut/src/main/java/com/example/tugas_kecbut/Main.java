// File Main.java
// File utama dalam program ini, berisi kelas Main yang merupakan entry point aplikasi JavaFX.
package com.example.tugas_kecbut;

// Import statements yang diperlukan untuk aplikasi JavaFX
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.util.List;

// Deklarasi kelas Main yang meng-extend Application dari JavaFX
public class Main extends Application {

    // Atribut
    private Pane root; // Node utama untuk menempatkan elemen-elemen UI
    private char[][] peta = { // Representasi peta dalam bentuk array 2 dimensi
            {'#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', 'S', '.', '.', '.', '.', '.', '#'},
            {'#', '.', '#', '#', '#', '#', '.', '#'},
            {'#', '.', '#', 'G', '.', '#', '.', '#'},
            {'#', '.', '#', '.', '.', '#', '.', '#'},
            {'#', '.', '#', '.', '.', '.', '.', '#'},
            {'#', '.', '#', '.', '.', '.', '.', '#'},
            {'#', '.', '#', '.', '.', '.', '.', '#'},
            {'#', '.', '.', '.', '.', '.', '.', '#'},
            {'#', '.', '.', '.', '.', '.', '.', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#'}
    };
    private int[] start; // Koordinat titik awal
    private int[] goal; // Koordinat titik akhir
    private BFS bfs = new BFS(); // Objek algoritma BFS
    private AStar aStar = new AStar(); // Objek algoritma A*
    private List<int[]> path; // Jalur yang ditemukan
    private Text stepsText = new Text(); // Text untuk menampilkan langkah-langkah pencarian

    // Method start() - siklus hidup aplikasi JavaFX
    @Override
    public void start(Stage primaryStage) {
        detectStartAndGoal(); // Deteksi titik awal dan akhir pada peta

        root = new Pane(); // Inisialisasi root node
        int tileSize = 40; // Ukuran tile pada peta
        int mapWidth = peta[0].length; // Lebar peta
        int mapHeight = peta.length; // Tinggi peta
        int sceneWidth = mapWidth * tileSize; // Lebar scene
        int sceneHeight = mapHeight * tileSize + 100; // Tinggi scene + tambahan untuk tombol

        root.setPrefSize(sceneWidth, sceneHeight); // Set ukuran prefere root node

        // Membuat visualisasi peta dan menambahkan ke root node
        Visualization.buatPeta(root, peta, null, "", start, goal, stepsText);

        // Membuat grup untuk tombol algoritma
        ToggleGroup algorithmGroup = new ToggleGroup();
        // Membuat tombol radio untuk algoritma BFS
        RadioButton bfsButton = createRadioButton("BFS", algorithmGroup, 10, mapHeight * tileSize + 10, true);
        // Membuat tombol radio untuk algoritma A*
        RadioButton aStarButton = createRadioButton("A*", algorithmGroup, 70, mapHeight * tileSize + 10, false);

        // Membuat tombol Start
        Button tombolStart = createButton("Start", 10, mapHeight * tileSize + 60, "#1E90FF");
        // Event handler untuk tombol Start
        tombolStart.setOnAction(event -> {
            if (bfsButton.isSelected()) {
                path = bfs.cariPola(peta, start, goal); // Pencarian jalur menggunakan BFS
            } else {
                path = aStar.findPath(peta, start, goal); // Pencarian jalur menggunakan A*
            }
            Visualization.animasiPola(root, path, stepsText); // Animasi jalur yang ditemukan
        });

        // Membuat tombol Reset
        Button tombolReset = createButton("Reset", 70, mapHeight * tileSize + 60, "#FF0000");
        // Event handler untuk tombol Reset
        tombolReset.setOnAction(event -> resetVisualization(bfsButton, aStarButton, tombolStart, tombolReset));

        // Menambahkan elemen-elemen ke root node
        root.getChildren().addAll(bfsButton, aStarButton, tombolStart, tombolReset);

        // Membuat scene dan menampilkan primaryStage
        Scene hasilScene = new Scene(root, sceneWidth, sceneHeight);
        primaryStage.setTitle("Visualisasi Pencarian Jalur");
        primaryStage.setScene(hasilScene);
        primaryStage.show();
    }

    // Method untuk mendeteksi titik awal dan akhir pada peta
    private void detectStartAndGoal() {
        for (int y = 0; y < peta.length; y++) {
            for (int x = 0; x < peta[y].length; x++) {
                if (peta[y][x] == 'S') {
                    start = new int[] {y, x};
                } else if (peta[y][x] == 'G') {
                    goal = new int[] {y, x};
                }
            }
        }
    }

    // Method untuk membuat tombol radio
    private RadioButton createRadioButton(String text, ToggleGroup group, double x, double y, boolean selected) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setToggleGroup(group);
        radioButton.setSelected(selected);
        radioButton.setLayoutX(x);
        radioButton.setLayoutY(y);
        return radioButton;
    }

    // Method untuk membuat tombol
    private Button createButton(String text, double x, double y, String color) {
        Button button = new Button(text);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white;");
        return button;
    }

    // Method untuk mereset visualisasi
    private void resetVisualization(RadioButton bfsButton, RadioButton aStarButton, Button tombolStart, Button tombolReset) {
        root.getChildren().clear(); // Menghapus semua node dari root
        // Membuat kembali visualisasi peta dan menambahkan elemen-elemen
        Visualization.buatPeta(root, peta, null, "", start, goal, stepsText);
        root.getChildren().addAll(bfsButton, aStarButton, tombolStart, tombolReset); // Menambahkan tombol-tombol kembali
    }

    // Method main() - entry point aplikasi
    public static void main(String[] args) {
        launch(args); // Memulai aplikasi JavaFX
    }
}
