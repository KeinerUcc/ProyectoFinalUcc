/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class ControladorTienda {

    @FXML
    Button btnCambiarAProductos;
    @FXML
    Button btnCambiarAProductos2;
    @FXML
    Button btnCambiarAProductos3;
    @FXML
    Button btnCambiarAProductos4;
    @FXML
    Button btnSiguiente1;
    @FXML
    Button btnSiguiente2;
    @FXML
    Button btnSiguiente3;

    public void cambiarEscena(ActionEvent event, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void cambiarAProductos(ActionEvent event) throws IOException {
        cambiarEscena(event, "/Vista/Productos.fxml");
    }
}
