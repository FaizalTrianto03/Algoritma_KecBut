module com.example.tugas_kecbut {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tugas_kecbut to javafx.fxml;
    exports com.example.tugas_kecbut;
}