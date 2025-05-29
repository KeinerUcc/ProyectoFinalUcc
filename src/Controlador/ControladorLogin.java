/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.*;
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
import javafx.scene.control.Label;
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
        this.pila = new PilaUsuarios();
        this.pila.cargarUsuariosDesdeArchivo();
    }

    public void setPilaUsuarios(PilaUsuarios pila) {
        this.pila = pila;
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

    public Usuario usuarioRecuperado;
    public String codigoGenerado;

    @FXML
    private TextField correoRecuperacion;
    @FXML
    private Label labelCodigoGenerado;
    @FXML
    private TextField digit1, digit2, digit3, digit4, digit5, digit6;
    @FXML
    private TextField nuevaContraseña;

    @FXML
    public void enviarCodigoRecuperacion(ActionEvent event) throws IOException {
        String correoIngresado = correoRecuperacion.getText().trim();
        usuarioRecuperado = pila.buscarPorCorreo(correoIngresado);

        if (usuarioRecuperado == null) {
            mostrarAlerta("Error", "Correo no registrado", AlertType.ERROR);
        } else {
            codigoGenerado = String.format("%06d", (int) (Math.random() * 1000000));

            cambiarEscena(event, "/Vista/vista_codigoVerificacion.fxml", usuarioRecuperado);
        }
    }

    public void initializeCodigo() {
        if (labelCodigoGenerado != null) {
            labelCodigoGenerado.setText(codigoGenerado);
        }
    }

    @FXML
    public void verificarCodigo(ActionEvent event) throws IOException {
        String ingresado = digit1.getText() + digit2.getText() + digit3.getText()
                + digit4.getText() + digit5.getText() + digit6.getText();
        if (ingresado.equals(codigoGenerado)) {
            cambiarEscena(event, "/Vista/vista_cambiarContraseña.fxml", usuarioRecuperado);
        } else {
            mostrarAlerta("Error", "Código incorrecto", AlertType.ERROR);
        }
    }

    @FXML
    public void cambiarContraseñaFinal(ActionEvent event) throws IOException {
        String nuevaPass = nuevaContraseña.getText().trim();
        if (nuevaPass.isEmpty()) {
            mostrarAlerta("Error", "Contraseña no puede estar vacía", AlertType.ERROR);
        } else {
            usuarioRecuperado.contra = nuevaPass;
            pila.actualizarUsuario(usuarioRecuperado);
            pila.guardarUsuariosEnArchivo();
            mostrarAlerta("Éxito", "Contraseña actualizada correctamente", AlertType.INFORMATION);
            cambiarEscena(event, "/Vista/login.fxml", null);
        }
    }

    @FXML
    public void recuperarCuenta(ActionEvent event) throws IOException {
        cambiarEscena(event, "/Vista/vista_olvidarCorreo.fxml", null);
    }

    @FXML
    public TextField campoCorreoRecuperar;

    @FXML
    public void verificarCorreo(ActionEvent event) throws IOException {
        String correoIngresado = campoCorreoRecuperar.getText().trim();
        Usuario encontrado = pila.buscarPorCorreo(correoIngresado);

        if (encontrado != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/verificarCodigo.fxml"));
            Parent root = loader.load();

            ControladorLogin controlador = loader.getController();
            controlador.pila = this.pila;
            controlador.usuarioRecuperado = encontrado;

            controlador.enviarCodigoRecuperacion(event);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            mostrarAlerta("Error", "Correo no encontrado", AlertType.ERROR);
        }
    }

    public void cambiarEscena(Event event, String fxml, Usuario usuarioRecuperado) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();

        ControladorLogin controlador = loader.getController();
        controlador.setPilaUsuarios(this.pila);
        controlador.usuarioRecuperado = usuarioRecuperado;
        if (controlador.labelCodigoGenerado != null && codigoGenerado != null) {
            controlador.codigoGenerado = this.codigoGenerado;
            controlador.labelCodigoGenerado.setText(this.codigoGenerado);
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
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
            pila.aggUsuario(usuario, contraseña, nom, Correo);
            pila.guardarUsuariosEnArchivo();
            mostrarAlerta("Éxito", "Usuario registrado correctamente", AlertType.INFORMATION);
            cambiarEscena(event, "/Vista/login.fxml", null);
        }
    }

    @FXML
    public void cambiarSceneCrearCuenta(MouseEvent event) throws IOException {
        cambiarEscena(event, "/Vista/registro.fxml", null);
    }

    @FXML
    public void cambiarSceneIniciarSesion(MouseEvent event) throws IOException {
        cambiarEscena(event, "/Vista/login.fxml", null);
    }

    @FXML
    public void iniciarSesion(ActionEvent event) throws IOException {
        String user = usuarioLogin.getText().trim();
        String contra = contraseñaLogin.getText().trim();

        if (user.isEmpty() || contra.isEmpty()) {
            mostrarAlerta("Error", "Debe llenar todos los campos", AlertType.ERROR);
        } else {
            Usuario usuario = pila.obtenerUsuario(user, contra);

            if (usuario != null) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setContentText("Inicio de sesión exitoso, ¿deseas continuar?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/inicio.fxml"));
                    Parent root = loader.load();

                    ControladorTienda controlador = loader.getController();
                    controlador.setUsuario(usuario);
                    controlador.setPilaUsuarios(this.pila);

                    Stage stageTienda = new Stage();
                    stageTienda.setScene(new Scene(root));
                    stageTienda.show();

                    Stage stageLogin = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stageLogin.close();
                }
            } else {
                mostrarAlerta("Error", "Cuenta no encontrada", AlertType.ERROR);
            }
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
        initializeCodigo();
    }

    public void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
