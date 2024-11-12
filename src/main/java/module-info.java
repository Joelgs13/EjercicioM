module com.example.ejerciciol {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql.rowset;


    opens es.joel.ejerciciom to javafx.fxml;
    opens es.joel.ejerciciom.Model to javafx.base;

    exports es.joel.ejerciciom;
    exports es.joel.ejerciciom.Model;
}