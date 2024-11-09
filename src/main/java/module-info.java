module com.example.ejerciciol {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql.rowset;


    opens com.example.ejerciciom to javafx.fxml;
    opens com.example.ejerciciom.Model to javafx.base;

    exports com.example.ejerciciom;
    exports com.example.ejerciciom.Model;
}