module com.example.proyectoaccesoadatos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;


    opens com.example.proyectoaccesoadatos to javafx.fxml, java.xml.bind;
    exports com.example.proyectoaccesoadatos;
}