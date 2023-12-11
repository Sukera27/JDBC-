package com.example.proyectoaccesoadatos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApplication extends Application {
    //Método principal que se ejecuta al iniciar la aplicación.
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        stage.setTitle("");
        stage.setScene(scene);
        stage.show();


    }
    @Override
    public void stop() {
        BaseDeDatos.cerrarConexion();

    }

}
