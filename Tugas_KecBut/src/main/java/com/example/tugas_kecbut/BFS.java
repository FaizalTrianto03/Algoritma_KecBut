package com.example.tugas_kecbut;

import java.util.*;

// Kelas BFS untuk melakukan pencarian jalur menggunakan algoritma Breadth-First Search (BFS)
public class BFS {
    private int steps; // Jumlah langkah yang diperlukan untuk menemukan jalur

    // Method untuk mencari jalur menggunakan algoritma BFS
    public List<int[]> cariPola(char[][] peta, int[] start, int[] goal) {
        steps = 0; // Reset jumlah langkah
        int rows = peta.length; // Jumlah baris pada peta
        int cols = peta[0].length; // Jumlah kolom pada peta
        Queue<int[]> queue = new LinkedList<>(); // Queue untuk menyimpan node yang akan dieksplorasi
        Set<String> visited = new HashSet<>(); // Set untuk menyimpan posisi yang sudah dieksplorasi
        Map<String, String> parent = new HashMap<>(); // Map untuk menyimpan posisi parent dari setiap posisi

        queue.add(start); // Menambahkan posisi awal ke queue
        visited.add(Arrays.toString(start)); // Menandai posisi awal sebagai sudah dieksplorasi
        parent.put(Arrays.toString(start), null); // Tidak ada parent untuk posisi awal

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Arah gerak yang mungkin

        // Melakukan iterasi sampai queue kosong atau jalur ditemukan
        while (!queue.isEmpty()) {
            int[] current = queue.poll(); // Mengambil posisi dari queue
            steps++; // Increment langkah

            // Jika posisi saat ini sama dengan goal, jalur ditemukan
            if (Arrays.equals(current, goal)) {
                break;
            }

            // Melakukan iterasi untuk semua arah gerak yang mungkin
            for (int[] d : directions) {
                int[] neighbor = {current[0] + d[0], current[1] + d[1]};
                String neighborStr = Arrays.toString(neighbor);
                // Memeriksa apakah tetangga valid dan belum dieksplorasi
                if (0 <= neighbor[0] && neighbor[0] < rows && 0 <= neighbor[1] && neighbor[1] < cols &&
                        peta[neighbor[0]][neighbor[1]] != '#' && !visited.contains(neighborStr)) {
                    queue.add(neighbor); // Menambahkan tetangga ke queue
                    visited.add(neighborStr); // Menandai tetangga sebagai sudah dieksplorasi
                    parent.put(neighborStr, Arrays.toString(current)); // Menyimpan posisi parent
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

    // Method untuk mendapatkan jumlah langkah yang diperlukan untuk menemukan jalur
    public int getSteps() {
        return steps;
    }
}
