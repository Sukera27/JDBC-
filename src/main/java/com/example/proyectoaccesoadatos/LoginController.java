package com.example.proyectoaccesoadatos;

import java.io.IOException;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;


    //Metodo para logearte en la aplicacion que conecta con la credenciales de la base de datos
    @FXML
    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            // Intenta conectar a la base de datos
            BaseDeDatos.conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/Northwind",
                    username, password);

            // Si la conexión se establece con éxito, almacena la conexión para su uso posterior
            if (BaseDeDatos.conexion != null) {
                // Cierra la ventana actual
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();

                // Llama a la función para abrir la ventana de SelectorApplication
                openSelectorWindow();

            } else {

            }
        } catch (SQLException e) {
            // Muestra el mensaje de error en la etiqueta
            errorLabel.setText("Error connecting to the database");
            e.printStackTrace();
        }
    }
    //Método privado que abre la ventana de Selector después de una conexión exitosa.
    private void openSelectorWindow() {
        try {
            // Cargar el archivo FXML de la pantalla de SelectorController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Selector.fxml"));
            Parent root = loader.load();

            // Obtén el controlador de la pantalla de SelectorController
            SelectorController selectorController = loader.getController();

            // Configura el escenario y la escena
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            // Muestra la nueva ventana
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
