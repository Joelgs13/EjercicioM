module com.example.ejerciciol {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql.rowset;


    opens com.example.ejerciciom to javafx.fxml;
    opens Model to javafx.base;

    exports com.example.ejerciciom;
    exports Model;
}