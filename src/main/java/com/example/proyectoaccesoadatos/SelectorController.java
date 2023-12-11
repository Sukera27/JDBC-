package com.example.proyectoaccesoadatos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectorController implements Initializable {


    @FXML
    private ComboBox<String> tableComboBox;
    @FXML
    private Button nextButton;

    //Método que se ejecuta al inicializar el controlador.
    //Inicializa el ComboBox con los nombres de las tablas de la base de datos.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Este metodo funciona");
        populateTableComboBox();
    }

    //Método para llenar el ComboBox con los nombres de las tablas de la base de datos.
    @FXML
    public void populateTableComboBox() {
        try {
            // Obtiene la información de la base de datos
            DatabaseMetaData metaData = BaseDeDatos.conexion.getMetaData();
            ResultSet resultSet = metaData.getTables("Northwind",null,null,null);
            List<String> tableNames = new ArrayList<>();
            // Recorre el conjunto de resultados para obtener los nombres de las tablas
            while (resultSet.next()) {
                String tableName = resultSet.getString(3);
                tableNames.add(tableName);
            }
            // Agrega los nombres de las tablas al ComboBox
            tableComboBox.getItems().addAll(tableNames);
            // Agrega un mensaje de depuración para verificar si el método se ejecuta correctamente
            System.out.println("Nombres de tablas cargados en ComboBox: " + tableNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Método que se ejecuta al hacer clic en el botón "Next".
    @FXML
    private void goToNextScreen() {
        String selectedTable = tableComboBox.getValue();
        // Verifica si se ha seleccionado una tabla antes de abrir la siguiente ventana
        if (selectedTable != null && !selectedTable.isEmpty()) {
            // Abre la tercera ventana
            openTableDataScreen(selectedTable);
        }
    }
    //Método privado que abre la tercera ventana con los datos de la tabla seleccionada.
    private void openTableDataScreen(String tableName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("table.fxml"));
            Parent root = loader.load();

            // Obtén el controlador de la tercera pantalla (TableDataController)
            TableController tableController = loader.getController();

            // Configura el controlador con la tabla seleccionada
            tableController.initializeTableData(tableName);

            // Establece la nueva escena
            Scene scene = new Scene(root);
            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
