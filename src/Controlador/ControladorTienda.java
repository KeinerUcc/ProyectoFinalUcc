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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author DELL
 */
public class ControladorTienda extends Productos {

    public Nodo<Producto> cabezaCarrito;
    public Nodo<Producto> inicioHistorial;
    public Pane PaneCarrito;
    public Pane panelPostCarrito;
    public Pane panelPostCarrito2;
    public ScrollPane ScrollPaneCarrito;

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
        Nodo<Producto> lista = (ControladorPrincipal != null) ? ControladorPrincipal.cabezaCarrito : cabezaCarrito;
        Nodo<Producto> actual = lista;

        while (actual != null) {
            if (actual.dato.equals(producto)) {
                actual.cantidad++; 
                return;
            }
            actual = actual.sig;
        }

        // Si no existe, añadirlo con cantidad 1
        Nodo<Producto> nuevo = new Nodo<>(producto);
        nuevo.cantidad = 1;

        if (ControladorPrincipal != null) {
            nuevo.sig = ControladorPrincipal.cabezaCarrito;
            ControladorPrincipal.cabezaCarrito = nuevo;
        } else {
            nuevo.sig = cabezaCarrito;
            cabezaCarrito = nuevo;
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

    public double calcularTotal() {
        double total = 0;
        Nodo<Producto> actual = cabezaCarrito;
        while (actual != null) {
            if (actual.dato != null) {
                total += actual.dato.precio * actual.cantidad;
            }
            actual = actual.sig;
        }
        return total;
    }

    public void AnilloRoyalStar() {
        añadirCarrito(AnilloRoyalStar);
    }

    @FXML
    public void mostrarCarrito() {
        VBox contenidoCarrito = new VBox(10);
        contenidoCarrito.setPadding(new Insets(15));

        if (cabezaCarrito == null) {
            contenidoCarrito.getChildren().add(new Label("El carrito está vacío"));
        } else {
            Nodo<Producto> actual = cabezaCarrito;
            while (actual != null) {
                if (actual.dato != null) {
                    final Producto productoActual = actual.dato;
                    final Nodo<Producto> nodoActual = actual; // Variable final para el lambda

                    HBox item = new HBox(15);
                    item.setAlignment(Pos.CENTER_LEFT);

                    // Imagen
                    ImageView img = new ImageView(productoActual.imagen);
                    img.setFitHeight(60);
                    img.setFitWidth(60);

                    // Info (nombre + precio unitario)
                    VBox info = new VBox(5);
                    Label nombreLabel = new Label(productoActual.nombre);
                    nombreLabel.setStyle("-fx-font-weight: bold;");
                    Label precioUnitario = new Label(String.format("$%,.2f c/u", productoActual.precio));
                    precioUnitario.setStyle("-fx-text-fill: #666;");

                    // Controles de cantidad (+/-)
                    HBox cantidadBox = new HBox(10);
                    cantidadBox.setAlignment(Pos.CENTER_LEFT);

                    Button btnMenos = new Button("-");
                    btnMenos.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white; -fx-min-width: 30;");
                    btnMenos.setOnAction(e -> {
                        if (nodoActual.cantidad > 1) {
                            nodoActual.cantidad--; // Usamos nodoActual en lugar de actual
                            mostrarCarrito();
                        }
                    });

                    Label lblCantidad = new Label("Cant: " + nodoActual.cantidad);

                    Button btnMas = new Button("+");
                    btnMas.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-min-width: 30;");
                    btnMas.setOnAction(e -> {
                        nodoActual.cantidad++; // Usamos nodoActual
                        mostrarCarrito();
                    });

                    cantidadBox.getChildren().addAll(btnMenos, lblCantidad, btnMas);

                    // Precio total (precio * cantidad)
                    Label precioTotal = new Label(String.format("$%,.2f", productoActual.precio * nodoActual.cantidad));
                    precioTotal.setStyle("-fx-text-fill: #2e8b57; -fx-font-weight: bold;");

                    info.getChildren().addAll(nombreLabel, precioUnitario, cantidadBox);

                    // Botón eliminar
                    Button btnEliminar = new Button("Eliminar");
                    btnEliminar.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
                    btnEliminar.setOnAction(e -> {
                        eliminarDelCarrito(productoActual);
                        mostrarCarrito();
                    });

                    item.getChildren().addAll(img, info, precioTotal, btnEliminar);
                    HBox.setHgrow(info, Priority.ALWAYS);
                    contenidoCarrito.getChildren().add(item);
                }
                actual = actual.sig;
            }

            // Footer (total + botón comprar)
            HBox footer = new HBox(20);
            footer.setAlignment(Pos.CENTER_RIGHT);
            footer.setPadding(new Insets(20, 10, 10, 0));
            footer.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 1 0 0 0;");

            Label lblTotal = new Label(String.format("Total: $%,.2f", calcularTotal()));
            lblTotal.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

            Button btnComprar = new Button("Comprar todo");
            btnComprar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
            btnComprar.setOnAction(e -> {
                if (cabezaCarrito == null) {
                    new Alert(Alert.AlertType.WARNING, "El carrito está vacío").showAndWait();
                } else {
                    new Alert(Alert.AlertType.INFORMATION,
                            "¡Compra exitosa! Total: $" + calcularTotal()).showAndWait();
                    cabezaCarrito = null;
                    mostrarCarrito();
                }
            });

            footer.getChildren().addAll(lblTotal, btnComprar);
            contenidoCarrito.getChildren().add(footer);
        }

        ScrollPaneCarrito.setContent(contenidoCarrito);
        PaneCarrito.setVisible(true);
        panelPostCarrito2.setOpacity(0.6);
    }

    @FXML
    public void volver() {
        PaneCarrito.setVisible(false);
        panelPostCarrito2.setOpacity(0);
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
