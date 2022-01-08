module com.example.ihavetrylma {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.ihavetrylma to javafx.fxml;
    exports com.example.ihavetrylma;
    exports Server;
    opens Server to javafx.fxml;
}