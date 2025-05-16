/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class ControladorLogin {

    PilaUsuarios pila;

    public ControladorLogin() {
        pila = new PilaUsuarios();
    }

    @FXML
    public TextField usuarioLogin;
    @FXML
    public TextField contraseñaLogin;
    @FXML
    public TextField usuarioCrear;
    @FXML
    public TextField contraseñaCrear;
    @FXML
    public ComboBox<String> comboBox;
    @FXML
    public TextField nombre;
    @FXML
    public TextField correo;
    @FXML
    public TextField confirmarContraseña;
    @FXML
    public CheckBox checkbox;

    public void setListaUsuarios(PilaUsuarios lista) {
        pila = lista;
    }

    public void cambiarEscena(Event event, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();

        ControladorLogin controlador = loader.getController();
        controlador.setListaUsuarios(this.pila);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void registrarUsuario(ActionEvent event) throws IOException {
        String usuario = usuarioCrear.getText().trim();
        String contraseña = contraseñaCrear.getText().trim();
        String nom = nombre.getText().trim();
        String Correo = correo.getText().trim();
        String confirmarContra = confirmarContraseña.getText().trim();

        if (usuario.isEmpty() || contraseña.isEmpty() || nom.isEmpty()
                || Correo.isEmpty() || confirmarContra.isEmpty()) {
            mostrarAlerta("Error", "Debe llenar todos los campos", AlertType.ERROR);
        } else if (!contraseña.equals(confirmarContra)) {
            mostrarAlerta("Error", "Las contraseñas no coinciden", AlertType.ERROR);
        } else if (!checkbox.isSelected()) {
            mostrarAlerta("Error", "Debe aceptar los terminos y condiciones", AlertType.ERROR);
        } else {
            pila.aggUsuario(usuario, contraseña);
            mostrarAlerta("Éxito", "Usuario registrado correctamente", AlertType.INFORMATION);
            cambiarEscena(event, "/Vista/login.fxml");
        }
    }

    @FXML
    public void cambiarSceneCrearCuenta(MouseEvent event) throws IOException {
        cambiarEscena(event, "/Vista/registro.fxml");
    }

    @FXML
    public void cambiarSceneIniciarSesion(MouseEvent event) throws IOException {
        cambiarEscena(event, "/Vista/login.fxml");
    }

    @FXML
    public void iniciarSesion(ActionEvent event) throws IOException {
        String user = usuarioLogin.getText().trim();
        String contra = contraseñaLogin.getText().trim();

        if (user.isEmpty() || contra.isEmpty()) {
            mostrarAlerta("Error", "Debe llenar todos los campos", AlertType.ERROR);
        } else if (pila.validacion(user, contra)) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setContentText("Inicio de sesion exitoso, ¿deseas continuar?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Stage stageLogin = (Stage) ((Node) event.getSource()).getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/inicio.fxml"));
                Parent root = loader.load();

                Stage stageTienda = new Stage();
                stageTienda.setScene(new Scene(root));
                stageTienda.show();

                stageLogin.close();
            } else {
                usuarioLogin.clear();
                contraseñaLogin.clear();
            }
        } else {
            mostrarAlerta("Error", "Cuenta no encontrada", AlertType.ERROR);
            usuarioLogin.clear();
            contraseñaLogin.clear();
        }
    }

    @FXML
    public void initialize() {
        try {
            if (comboBox != null) {
                comboBox.getItems().clear();
                comboBox.getItems().addAll(
                        "Colombia",
                        "Argentina",
                        "Estados Unidos",
                        "Brasil",
                        "España"
                );
            }
        } catch (Exception e) {
            System.err.println("Error al cargar ComboBox: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
