package com.example.tugas_kecbut;

import java.util.*;

// Kelas AStar untuk melakukan pencarian jalur menggunakan algoritma A*
public class AStar {
    private int steps; // Jumlah langkah yang diperlukan untuk menemukan jalur

    // Method untuk mencari jalur menggunakan algoritma A*
    public List<int[]> findPath(char[][] peta, int[] start, int[] goal) {
        steps = 0; // Reset jumlah langkah
        int rows = peta.length; // Jumlah baris pada peta
        int cols = peta[0].length; // Jumlah kolom pada peta
        // PriorityQueue untuk menyimpan node yang belum dieksplorasi, diurutkan berdasarkan nilai fScore
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.fScore));
        Map<String, Integer> gScore = new HashMap<>(); // Menyimpan nilai gScore untuk setiap posisi
        Map<String, Integer> fScore = new HashMap<>(); // Menyimpan nilai fScore untuk setiap posisi
        Map<String, String> parent = new HashMap<>(); // Menyimpan posisi parent untuk setiap posisi

        // Membuat node start dan menambahkannya ke openSet
        Node startNode = new Node(start, 0, heuristic(start, goal));
        openSet.add(startNode);
        gScore.put(Arrays.toString(start), 0);
        fScore.put(Arrays.toString(start), heuristic(start, goal));
        parent.put(Arrays.toString(start), null);

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Arah gerak yang mungkin

        // Melakukan iterasi sampai openSet kosong atau jalur ditemukan
        while (!openSet.isEmpty()) {
            Node current = openSet.poll(); // Mengambil node dengan fScore terendah
            steps++; // Increment langkah

            // Jika posisi saat ini sama dengan goal, jalur ditemukan
            if (Arrays.equals(current.position, goal)) {
                break;
            }

            // Melakukan iterasi untuk semua arah gerak yang mungkin
            for (int[] d : directions) {
                int[] neighbor = {current.position[0] + d[0], current.position[1] + d[1]};
                String neighborStr = Arrays.toString(neighbor);
                // Memeriksa apakah tetangga valid dan tidak berada di dinding ('#')
                if (0 <= neighbor[0] && neighbor[0] < rows && 0 <= neighbor[1] && neighbor[1] < cols &&
                        peta[neighbor[0]][neighbor[1]] != '#') {
                    int tentativeGScore = gScore.getOrDefault(Arrays.toString(current.position), Integer.MAX_VALUE) + 1;
                    // Memperbarui nilai gScore jika jalur baru lebih baik
                    if (tentativeGScore < gScore.getOrDefault(neighborStr, Integer.MAX_VALUE)) {
                        parent.put(neighborStr, Arrays.toString(current.position));
                        gScore.put(neighborStr, tentativeGScore);
                        fScore.put(neighborStr, tentativeGScore + heuristic(neighbor, goal));
                        openSet.add(new Node(neighbor, tentativeGScore, fScore.get(neighborStr)));
                    }
                }
            }
        }

        // Menghasilkan jalur dari titik akhir ke titik awal menggunakan posisi parent
        List<int[]> path = new ArrayList<>();
        String step = Arrays.toString(goal);
        while (step != null) {
            path.add(0, Arrays.stream(step.substring(1, step.length() - 1).split(", "))
                    .mapToInt(Integer::parseInt)
                    .toArray());
            step = parent.get(step);
        }
        return path;
    }

    // Method untuk menghitung heuristic (jarak estimasi) antara dua titik
    private int heuristic(int[] a, int[] b) {
        return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
    }

    // Kelas untuk merepresentasikan node pada algoritma A*
    private static class Node {
        int[] position; // Posisi node
        int gScore; // Nilai gScore (biaya sejauh ini dari titik awal ke node)
        int fScore; // Nilai fScore (gScore + heuristic)

        // Konstruktor Node
        Node(int[] position, int gScore, int fScore) {
            this.position = position;
            this.gScore = gScore;
            this.fScore = fScore;
        }
    }

    // Method untuk mendapatkan jumlah langkah yang diperlukan untuk menemukan jalur
    public int getSteps() {
        return steps;
    }
}
