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
import Modelo.*;

/**
 *
 * @author DELL
 */
public class ControladorTienda extends Productos {

    public Nodo<Producto> cabezaCarrito;
    public Nodo<Producto> inicioHistorial;

    public ControladorTienda ControladorPrincipal;

    public ControladorTienda() {
        cabezaCarrito = null;
    }

    public Nodo<Producto> getUltimo() {
        if (cabezaCarrito == null) {
            return null;
        } else {
            Nodo<Producto> p = cabezaCarrito;
            while (p.sig != null) {
                p = p.sig;
            }
            return p;
        }
    }

    public Nodo<Producto> getAntesNodo(Nodo q) {
        if (cabezaCarrito == null) {
            return null;
        } else {
            Nodo<Producto> p = cabezaCarrito;
            while (p.sig != q) {
                p = p.sig;
            }
            return p;
        }
    }

    public void añadirCarrito(Producto producto) {
        Nodo<Producto> p = new Nodo(producto);

        if (ControladorPrincipal != null) {
            if (ControladorPrincipal.cabezaCarrito == null) {
                ControladorPrincipal.cabezaCarrito = p;
            } else {
                Nodo<Producto> q = ControladorPrincipal.cabezaCarrito;
                while (q.sig != null) {
                    q = q.sig;
                }
                q.sig = p;
            }
        } else {
            if (cabezaCarrito == null) {
                cabezaCarrito = p;
            } else {
                Nodo<Producto> a = cabezaCarrito;
                while (a.sig != null) {
                    a = a.sig;
                }
                a.sig = p;
            }
        }
    }

    public void eliminarDelCarrito(Producto producto) {
        if (cabezaCarrito == null) {

        }
        Nodo<Producto> del = null;
        Nodo<Producto> p = cabezaCarrito;

        while (p != null) {
            if (p.dato.equals(producto)) {
                del = p;
            }
            p = p.sig;
        }
        if (del == null) {
            return;
        }
        if (del == cabezaCarrito) {
            cabezaCarrito = cabezaCarrito.sig;
        } else {
            Nodo<Producto> anterior = getAntesNodo(del);
            if (anterior != null) {
                anterior.sig = del.sig;
            }
        }
        del.sig = null;
        del.dato = null;
    }

    public void añadirHistorial(Producto producto) {
        Nodo<Producto> nuevoNodo = new Nodo<>(producto);

        if (ControladorPrincipal != null) {
            if (ControladorPrincipal.inicioHistorial == null) {
                ControladorPrincipal.inicioHistorial = nuevoNodo;
            } else {
                Nodo<Producto> actual = ControladorPrincipal.inicioHistorial;
                while (actual.sig != null) {
                    actual = actual.sig;
                }
                actual.sig = nuevoNodo;
            }
        } else {
            if (inicioHistorial == null) {
                inicioHistorial = nuevoNodo;
            } else {
                Nodo<Producto> actual = inicioHistorial;
                while (actual.sig != null) {
                    actual = actual.sig;
                }
                actual.sig = nuevoNodo;
            }
        }
    }

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

    @FXML
    public void cambiarAProductos2(ActionEvent event) throws IOException {
        cambiarEscena(event, "/Vista/Productos_pg2.fxml");
    }

    @FXML
    public void cambiarAProductos3(ActionEvent event) throws IOException {
        cambiarEscena(event, "/Vista/Productos_pg3.fxml");
    }

    @FXML
    public void cambiarAProductos4(ActionEvent event) throws IOException {
        cambiarEscena(event, "/Vista/Productos_pg4.fxml");
    }
    @FXML
    public Button btnCambiarAProductos;
    @FXML
    public Button btnCambiarAProductos2;
    @FXML
    public Button btnCambiarAProductos3;
    @FXML
    public Button btnCambiarAProductos4;
    @FXML
    public Button btnSiguiente1;
    @FXML
    public Button btnSiguiente2;
    @FXML
    public Button btnSiguiente3;
}
