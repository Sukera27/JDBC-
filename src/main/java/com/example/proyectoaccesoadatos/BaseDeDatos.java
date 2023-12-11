package com.example.proyectoaccesoadatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDeDatos {

    // Atributo estático para almacenar la conexión a la base de datos
    public static Connection conexion=null;



    //Método para cerrar la conexión a la base de datos.
    public static void cerrarConexion() {
        try {
            if (conexion != null) {
                conexion.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
