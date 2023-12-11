package com.example.proyectoaccesoadatos;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TableController implements Initializable {

    @FXML
    private TableView<String[]> tableView;
    @FXML
    private Button backButton;

    @FXML
    private Button CSV;

    @FXML Button XML;

    //Método que se ejecuta al inicializar el controlador.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    //Método para exportar los datos de la TableView a un archivo CSV.
    @FXML
    private void exportToCSV() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar CSV");
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("Archivos CSV (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);

            // Mostrar el cuadro de diálogo para elegir la ubicación del archivo
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                // Escribir datos en el archivo CSV
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    for (String[] rowData : tableView.getItems()) {
                        StringBuilder line = new StringBuilder();
                        for (String value : rowData) {
                            if (line.length() > 0) {
                                line.append(";");
                            }
                            line.append(value);
                        }
                        writer.write(line.toString());
                        writer.newLine();
                    }
                }

                // Notificar al usuario que la exportación fue exitosa
                System.out.println("Exportación a CSV exitosa");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Método para exportar los datos de la TableView a un archivo XML.
    @FXML
    private void exportToXML() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar XML");
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("Archivos XML (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);

            // Muestra el cuadro de diálogo para elegir la ubicación del archivo
            File file = fileChooser.showSaveDialog(new Stage());

            //Crea una lista de objetos TableRow a partir de los datos de la tabla
            List<TablaFilas> rows = new ArrayList<>();
            for (String[] rowData : tableView.getItems()) {
                TablaFilas fila = new TablaFilas(rowData);  // Crear la instancia con los datos
                rows.add(fila);
            }

            //Crea un objeto que contenga la lista de TableRow
            TablaColumnas tableData = new TablaColumnas();
            tableData.setRows(rows);

            // Utilizar JAXB para escribir el objeto a XML
            JAXBContext context = JAXBContext.newInstance(TablaColumnas.class, TablaFilas.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(tableData, file);

            // Notificar al usuario que la exportación fue exitosa (puedes personalizar esto según tus necesidades)
            System.out.println("Exportación a XML exitosa");
            } catch (PropertyException ex) {
            throw new RuntimeException(ex);
        } catch (JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }
    //Método para inicializar los datos de la TableView con los datos de la tabla seleccionada.
    public void initializeTableData(String tableName) {
        // Configurar las columnas de la TableView
        setupTableColumns(tableName);

        // Cargar datos en la TableView
        loadData(tableName);
    }
    //Metodo privado para configurar las columnas de la TableView.
    private void setupTableColumns(String tableName) {
        try {
            // Limpiar las columnas existentes
            tableView.getColumns().clear();

            // Obtener metadatos de la tabla
            ResultSet resultSet = BaseDeDatos.conexion.createStatement().executeQuery("SELECT * FROM " + tableName);

            // Configurar las columnas de la TableView,devuleve los datos de la consulta.
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                int columnIndex = i;
                String columnName = resultSet.getMetaData().getColumnName(i);
                //Se crea un objeto TableColumn para representar la columna en la TableView.
                TableColumn<String[], String> column = new TableColumn<>(columnName);

                //Esta línea utiliza una expresión lambda que toma un parámetro cellData,
                // que representa la información de la celda
                column.setCellValueFactory(cellData -> {
                    //Obtiene el valor de la celda actual
                    String[] rowData = cellData.getValue();

                    return (rowData != null && rowData.length > columnIndex - 1) ?
                            new SimpleStringProperty(rowData[columnIndex - 1]) : null;
                });

                // Ajusta la configuración de la columna
                column.setMinWidth(100);
                column.setPrefWidth(150);
                column.setResizable(true);

                tableView.getColumns().add(column);
            }

            // Refrescar la TableView
            tableView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Metodo privado para cargar datos en al TableView desde la base de datos.
    private void loadData(String tableName) {
        try {
            // Obtener datos de la tabla
            ResultSet resultSet = BaseDeDatos.conexion.createStatement().executeQuery("SELECT * FROM " + tableName);

            // Limpiar datos existentes
            tableView.getItems().clear();

            // Cargar datos en la TableView
            while (resultSet.next()) {
                String[] rowData = new String[resultSet.getMetaData().getColumnCount()];
                for (int i = 1; i <= rowData.length; i++) {
                    rowData[i - 1] = resultSet.getString(i);
                }
                tableView.getItems().add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Método que se ejecuta al hacer clic en el botón de retroceso.
    @FXML
    private void goBackToTableSelection() {
        try {
            // Cargar el archivo FXML de la pantalla de selección de tabla
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Selector.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la pantalla de selección de tabla (SelectorController)
            SelectorController selectorController = loader.getController();

            // Establecer la nueva escena
            Scene scene = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);

            // Llamar al método para cargar las tablas nuevamente
            selectorController.populateTableComboBox();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
