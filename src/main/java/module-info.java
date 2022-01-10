module com.example.ihavetrylma {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens Client to javafx.fxml;
    exports Client;
    exports Server;
    opens Server to javafx.fxml;
}