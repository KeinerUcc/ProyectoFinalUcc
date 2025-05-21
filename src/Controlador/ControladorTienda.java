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
import javafx.scene.control.Separator;
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
    public Pane PaneCarrito;
    public Pane panelPostCarrito;
    public Pane panelPostCarrito2;
    public ScrollPane ScrollPaneCarrito;
    public Nodo<Producto> inicioHistorial;
    public Label lblCantidadCarrito;
    public Pane panelMenu;
    public Pane panelHistorial;
    public Pane panelPedidos;
    public ScrollPane scrollHistorial;

    public ControladorTienda ControladorPrincipal;

    public ControladorTienda() {
        cabezaCarrito = null;
        inicioHistorial = null;
    }

    public void setListaCarrito(Nodo<Producto> cabeza) {
        this.cabezaCarrito = cabeza;
    }

    public void setInicioHistorial(Nodo<Producto> inicio) {
        this.inicioHistorial = inicio;
    }

    public Nodo<Producto> getFinHistorial() {
        if (inicioHistorial == null) {
            return null;
        } else {
            Nodo<Producto> p = inicioHistorial;
            while (p.sig != null) {
                p = p.sig;
            }
            return p;
        }
    }

    public boolean getHistorialEstaVacio() {
        return inicioHistorial == null ? true : false;
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

        Nodo<Producto> lista;
        if (ControladorPrincipal != null) {
            lista = ControladorPrincipal.cabezaCarrito;
        } else {
            lista = cabezaCarrito;
        }

        Nodo<Producto> p = lista;
        while (p != null) {
            if (p.dato.equals(producto)) {
                p.cantidad++;
                actualizarContadorCarrito();
                return;
            }
            p = p.sig;
        }

        Nodo<Producto> nuevoNodo = new Nodo(producto);
        nuevoNodo.cantidad = 1;

        if (ControladorPrincipal != null) {
            if (ControladorPrincipal.cabezaCarrito == null) {
                ControladorPrincipal.cabezaCarrito = nuevoNodo;
            } else {
                Nodo<Producto> ultimo = ControladorPrincipal.cabezaCarrito;
                while (ultimo.sig != null) {
                    ultimo = ultimo.sig;
                }
                ultimo.sig = nuevoNodo;
            }
        } else {
            if (cabezaCarrito == null) {
                cabezaCarrito = nuevoNodo;
            } else {
                Nodo<Producto> ultimo = cabezaCarrito;
                while (ultimo.sig != null) {
                    ultimo = ultimo.sig;
                }
                ultimo.sig = nuevoNodo;
            }
        }
        actualizarContadorCarrito();
    }

    public void actualizarContadorCarrito() {
        int totalItems = 0;
        Nodo<Producto> actual = (ControladorPrincipal != null)
                ? ControladorPrincipal.cabezaCarrito : cabezaCarrito;

        while (actual != null) {
            totalItems += actual.cantidad;
            actual = actual.sig;
        }
        lblCantidadCarrito.setText(String.valueOf(totalItems));

        if (totalItems > 0) {
            lblCantidadCarrito.setVisible(true);
        } else {
            lblCantidadCarrito.setVisible(false);
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

        ControladorTienda controlador;
        if (ControladorPrincipal != null) {
            controlador = ControladorPrincipal;
        } else {
            controlador = this;
        }

        if (controlador.getHistorialEstaVacio()) {
            controlador.inicioHistorial = nuevoNodo;
        } else {
            Nodo<Producto> fin = controlador.getFinHistorial();
            fin.sig = nuevoNodo;
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

    @FXML
    public void mostrarCarrito() {
        VBox contenidoCarrito = new VBox(15);
        contenidoCarrito.setPadding(new Insets(20));

        if (cabezaCarrito == null) {
            contenidoCarrito.getChildren().add(new Label("El carrito está vacío"));
        } else {
            Nodo<Producto> actual = cabezaCarrito;
            while (actual != null) {
                if (actual.dato != null) {
                    final Producto productoActual = actual.dato;
                    final Nodo<Producto> nodoActual = actual;

                    HBox item = new HBox(4);
                    item.setAlignment(Pos.CENTER_LEFT);
                    item.setStyle("-fx-padding: 5; -fx-background-color: #f9f9f9;");

                    ImageView img = new ImageView(productoActual.imagen);
                    img.setFitHeight(60);
                    img.setFitWidth(60);

                    VBox info = new VBox(5);
                    Label nombreLabel = new Label(productoActual.nombre);
                    nombreLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                    HBox cantidadBox = new HBox(4);
                    cantidadBox.setAlignment(Pos.CENTER_LEFT);

                    Button btnMenos = new Button("-");
                    btnMenos.setStyle("-fx-background-color: #ff4444; -fx-text-fill: "
                            + "white; -fx-min-width: 10; -fx-min-height: 10");
                    btnMenos.setOnAction(e -> {
                        if (nodoActual.cantidad > 1) {
                            nodoActual.cantidad--;
                            mostrarCarrito();
                        } else {
                            eliminarDelCarrito(productoActual);
                            mostrarCarrito();
                        }
                    });

                    Label lblCantidad = new Label("Cant:" + nodoActual.cantidad);
                    lblCantidad.setStyle("-fx-font-size: 12px;");

                    Button btnMas = new Button("+");
                    btnMas.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; "
                            + " -fx-min-width: 10; -fx-min-height: 10");
                    btnMas.setOnAction(e -> {
                        nodoActual.cantidad++;
                        mostrarCarrito();
                    });

                    cantidadBox.getChildren().addAll(btnMenos, lblCantidad, btnMas);

                    Label precioTotal = new Label(String.format("Total: $%,.2f", productoActual.precio * nodoActual.cantidad));
                    precioTotal.setStyle("-fx-text-fill: #2e8b57; -fx-font-weight: bold; -fx-font-size: 12px;");

                    Button btnEliminar = new Button("Eliminar");
                    btnEliminar.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
                    btnEliminar.setOnAction(e -> {
                        eliminarDelCarrito(productoActual);
                        mostrarCarrito(); 
                    });

                    info.getChildren().addAll(nombreLabel, cantidadBox);
                    item.getChildren().addAll(img, info, precioTotal, btnEliminar);
                    HBox.setHgrow(info, Priority.ALWAYS);

                    contenidoCarrito.getChildren().add(item);

                    if (actual.sig != null) {
                        contenidoCarrito.getChildren().add(new Separator());
                    }
                }
                actual = actual.sig;
            }

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
                    Nodo<Producto> productoActual = cabezaCarrito;
                    while (productoActual != null) {
                        for (int i = 0; i < productoActual.cantidad; i++) {
                            añadirHistorial(productoActual.dato);
                        }
                        productoActual = productoActual.sig;
                    }

                    new Alert(Alert.AlertType.INFORMATION,
                            "¡Compra exitosa! Total: $" + calcularTotal()).showAndWait();

                    cabezaCarrito = null;
                    mostrarCarrito();
                    actualizarContadorCarrito();
                }
            });

            footer.getChildren().addAll(lblTotal, btnComprar);
            contenidoCarrito.getChildren().add(footer);
        }

        ScrollPaneCarrito.setContent(contenidoCarrito);
        PaneCarrito.setVisible(true);
        panelPostCarrito2.setOpacity(0.6);
        actualizarContadorCarrito();
    }

    @FXML
    public void mostrarHistorial() {
        panelMenu.setVisible(false);
        panelHistorial.setVisible(true);

        VBox contenidoHistorial = new VBox(25);
        contenidoHistorial.setPadding(new Insets(30, 30, 30, 30));
        contenidoHistorial.setStyle("-fx-background-color: black;");

        ControladorTienda controlador = (ControladorPrincipal != null) ? ControladorPrincipal : this;

        if (!controlador.getHistorialEstaVacio()) {
            Nodo<Producto> actual = controlador.inicioHistorial;
            double totalGeneral = 0;

            while (actual != null) {
                if (actual.dato != null) {
                    boolean yaMostrado = false;
                    Nodo<Producto> temp = controlador.inicioHistorial;
                    while (temp != actual) {
                        if (temp.dato != null && temp.dato.equals(actual.dato)) {
                            yaMostrado = true;
                            break;
                        }
                        temp = temp.sig;
                    }

                    if (!yaMostrado) {
                        Producto producto = actual.dato;
                        int cantidadTotal = 0;
                        double totalProducto = 0;
                        Nodo<Producto> calculador = controlador.inicioHistorial;
                        while (calculador != null) {
                            if (calculador.dato != null && calculador.dato.equals(producto)) {
                                cantidadTotal += calculador.cantidad;
                                totalProducto += calculador.dato.precio * calculador.cantidad;
                            }
                            calculador = calculador.sig;
                        }

                        HBox item = new HBox(30);  
                        item.setAlignment(Pos.CENTER_LEFT);
                        item.setStyle("-fx-padding: 25; -fx-background-color: #AA9866; "
                                + "-fx-background-radius: 15; -fx-min-height: 200;");
                        
                        ImageView img = new ImageView(producto.imagen);
                        img.setFitHeight(200);
                        img.setFitWidth(200);
                        img.setPreserveRatio(true);

                        VBox info = new VBox(20);
                        info.setAlignment(Pos.CENTER_LEFT);

                        Label nombreLabel = new Label(producto.nombre);
                        nombreLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 24px;");

                        Label cantidadLabel = new Label("Cantidad: " + cantidadTotal);
                        cantidadLabel.setStyle("-fx-font-size: 20px;");

                        Label precioUnitario = new Label(String.format("Precio c/u: $%,.2f", producto.precio));
                        precioUnitario.setStyle("-fx-font-size: 20px;");

                        Label precioTotal = new Label(String.format("Total: $%,.2f", totalProducto));
                        precioTotal.setStyle("-fx-text-fill: #2e8b57; -fx-font-weight: bold; -fx-font-size: 22px;");

                        info.getChildren().addAll(nombreLabel, cantidadLabel, precioUnitario, precioTotal);
                        item.getChildren().addAll(img, info);
                        contenidoHistorial.getChildren().add(item);

                        totalGeneral += totalProducto;

                        Separator separator = new Separator();
                        separator.setStyle("-fx-background-color: #555555; -fx-padding: 8 0 8 0;");
                        contenidoHistorial.getChildren().add(separator);
                    }
                }
                actual = actual.sig;
            }

            HBox footer = new HBox();
            footer.setAlignment(Pos.CENTER_RIGHT);
            footer.setPadding(new Insets(30, 20, 20, 0));
            footer.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 3 0 0 0;");

            Label lblTotalGeneral = new Label(String.format("TOTAL GENERAL: $%,.2f", totalGeneral));
            lblTotalGeneral.setStyle("-fx-font-weight: bold; -fx-font-size: 26px; -fx-text-fill: white;");

            footer.getChildren().add(lblTotalGeneral);
            contenidoHistorial.getChildren().add(footer);
        } else {
            Label lblVacio = new Label("No hay compras en el historial");
            lblVacio.setStyle("-fx-font-size: 26px; -fx-text-fill: white;");
            contenidoHistorial.getChildren().add(lblVacio);
        }

        if (scrollHistorial != null) {
            scrollHistorial.setContent(contenidoHistorial);
            scrollHistorial.setStyle("-fx-background: black; -fx-background-color: transparent;");
            scrollHistorial.setFitToWidth(true);
        }
    }

    @FXML
    public void volver() {
        PaneCarrito.setVisible(false);
        panelPostCarrito2.setOpacity(0);
        panelMenu.setVisible(false);
        panelHistorial.setVisible(false);
    }

    @FXML
    public void mostrarMenu() {
        panelMenu.setVisible(true);
    }

    public void cambiarEscena(ActionEvent event, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        ControladorTienda Controlador = loader.getController();
        Controlador.setListaCarrito(this.cabezaCarrito);
        Controlador.actualizarContadorCarrito();
        Controlador.setInicioHistorial(this.inicioHistorial);

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

    public void AnilloRoyalStar() {
        añadirCarrito(AnilloRoyalStar);
    }

    public void PulseraCrazy() {
        añadirCarrito(PulseraCrazy);
    }

    public void AretesNudoGold() {
        añadirCarrito(AretesNudoGold);
    }

    public void CadenaItaliana() {
        añadirCarrito(CadenaItaliana);
    }

    public void CollarGalaxy() {
        añadirCarrito(CollarGalaxy);
    }

    public void PulseraVanCleef() {
        añadirCarrito(PulseraVanCleef);
    }

    public void DijeMar() {
        añadirCarrito(DijeMar);
    }

    public void RelojConquest() {
        añadirCarrito(RelojConquest);
    }

    public void DijeOsoPanda() {
        añadirCarrito(DijeOsoPanda);
    }

    public void AnilloChaosDouble() {
        añadirCarrito(AnilloChaosDouble);
    }

    public void CollarFinobolit() {
        añadirCarrito(CollarFinobolit);
    }

    public void Aretescelestial() {
        añadirCarrito(Aretescelestial);
    }

    public void CadenaEsclava() {
        añadirCarrito(CadenaEsclava);
    }

    public void PulseraArrastrada() {
        añadirCarrito(PulseraArrastrada);
    }

    public void PulserasCombLuxury() {
        añadirCarrito(PulserasCombLuxury);
    }

    public void RelojTissot() {
        añadirCarrito(RelojTissot);
    }

    public void ComboAretesSweet() {
        añadirCarrito(ComboAretesSweet);
    }

    public void DijeOsito() {
        añadirCarrito(DijeOsito);
    }

    public void AnilloGoldenLuz() {
        añadirCarrito(AnilloGoldenLuz);
    }

    public void DijeToyota() {
        añadirCarrito(DijeToyota);
    }

    public void RelojDolceVita() {
        añadirCarrito(RelojDolceVita);
    }

    public void CadenaGold() {
        añadirCarrito(CadenaGold);
    }

    public void AnilloGoldenluz() {
        añadirCarrito(AnilloGoldenluz);
    }

    public void CollarRama() {
        añadirCarrito(CollarRama);
    }

    public void AretesCoquet() {
        añadirCarrito(AretesCoquet);
    }

    public void CollarPlataMain() {
        añadirCarrito(CollarPlataMain);
    }

    public void RelojLadyAutomatic() {
        añadirCarrito(RelojLadyAutomatic);
    }

    public void CollarCrisFlower() {
        añadirCarrito(CollarCrisFlower);
    }

    public void DijeOsitoenPie() {
        añadirCarrito(DijeOsitoenPie);
    }

    public void AnilloGreenPow() {
        añadirCarrito(AnilloGreenPow);
    }

    public void CollarLuzFugaz() {
        añadirCarrito(CollarLuzFugaz);
    }

    public void AretesBoldHuggies() {
        añadirCarrito(AretesBoldHuggies);
    }

    public void CadenaSingapur() {
        añadirCarrito(CadenaSingapur);
    }

    public void AnilloFlowers() {
        añadirCarrito(AnilloFlowers);
    }

    public void PulseraDestello() {
        añadirCarrito(PulseraDestello);
    }

    public void CollarPearl() {
        añadirCarrito(CollarPearl);
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
