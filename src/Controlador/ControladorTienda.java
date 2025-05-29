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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.util.Duration;

/**
 *
 * @author DELL
 */
public class ControladorTienda extends Productos {

    public Circle circuloCarrito;
    public ControladorTienda ControladorPrincipal;

    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public Usuario usuarioActual;

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public PilaUsuarios pilaUsuarios;

    public void setPilaUsuarios(PilaUsuarios pilaUsuarios) {
        this.pilaUsuarios = pilaUsuarios;
    }

    @FXML
    public void mostrarPerfil() {

        TextField campoNombre = new TextField(usuarioActual.nombre);
        campoNombre.setEditable(false);
        TextField campoCorreo = new TextField(usuarioActual.correo);
        campoCorreo.setEditable(false);
        TextField campoUsuario = new TextField(usuarioActual.user);
        campoUsuario.setEditable(false);
        PasswordField campoContra = new PasswordField();
        campoContra.setText(usuarioActual.contra);
        campoContra.setEditable(false);

        TextField[] campos = {campoNombre, campoCorreo, campoUsuario, campoContra};

        String estiloCampos = "-fx-font-size: 20px; -fx-background-color: #222; -fx-text-fill: white; "
                + "-fx-border-color: #444; -fx-border-radius: 10px; -fx-background-radius: 10px;";

        DropShadow sombraHover = new DropShadow(20, javafx.scene.paint.Color.CYAN);
        Glow glowHover = new Glow(0.4);

        for (TextField campo : campos) {
            campo.setStyle(estiloCampos);
            campo.setFocusTraversable(false);
            campo.setOnMouseEntered(e -> campo.setEffect(new javafx.scene.effect.Blend(
                    javafx.scene.effect.BlendMode.SRC_OVER, glowHover, sombraHover
            )));
            campo.setOnMouseExited(e -> campo.setEffect(null));
        }

        Label lblNombre = new Label("Nombre:");
        Label lblCorreo = new Label("Correo:");
        Label lblUsuario = new Label("Usuario:");
        Label lblContra = new Label("Contraseña:");

        Label[] labels = {lblNombre, lblCorreo, lblUsuario, lblContra};
        for (Label lbl : labels) {
            lbl.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        }

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setStyle("-fx-font-size: 20px; -fx-background-color: #333; -fx-text-fill: white; "
                + "-fx-background-radius: 10px; -fx-border-color: #555; -fx-border-radius: 10px;");
        btnCerrar.setOnMouseEntered(e -> btnCerrar.setEffect(new DropShadow(20, javafx.scene.paint.Color.WHITE)));
        btnCerrar.setOnMouseExited(e -> btnCerrar.setEffect(null));
        btnCerrar.setOnAction(e -> ((Stage) btnCerrar.getScene().getWindow()).close());

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: black;");
        layout.getChildren().addAll(
                lblNombre, campoNombre,
                lblCorreo, campoCorreo,
                lblUsuario, campoUsuario,
                lblContra, campoContra,
                btnCerrar
        );

        // Escena y ventana
        Scene escena = new Scene(layout, 600, 600);
        Stage ventanaPerfil = new Stage();
        ventanaPerfil.setTitle("Perfil");
        ventanaPerfil.setScene(escena);
        ventanaPerfil.initModality(Modality.APPLICATION_MODAL);
        ventanaPerfil.show();
    }

    public ControladorTienda() {
        cabezaCarrito = null;
        inicioHistorial = null;
        cabezaFavoritos = null;
    }

    public void setListaCarrito(Nodo<Producto> cabeza) {
        this.cabezaCarrito = cabeza;
    }

    public void setInicioHistorial(Nodo<Producto> inicio) {
        this.inicioHistorial = inicio;
    }

    public void setListaFavoritos(Nodo<Producto> cabeza) {
        this.cabezaFavoritos = cabeza;
    }

    public void setControladorPrincipal(ControladorTienda ControladorPrincipal) {
        this.ControladorPrincipal = ControladorPrincipal;
    }

    public void setProductos(
            Producto anilloRoyalStar,
            Producto pulseraCrazy,
            Producto aretesNudoGold,
            Producto cadenaItaliana,
            Producto collarGalaxy,
            Producto pulseraVanCleef,
            Producto dijeMar,
            Producto relojConquest,
            Producto dijeOsoPanda,
            Producto anilloChaosDouble,
            Producto collarFinobolit,
            Producto aretesCelestial,
            Producto cadenaEsclava,
            Producto pulseraArrastrada,
            Producto pulserasCombLuxury,
            Producto relojTissot,
            Producto comboAretesSweet,
            Producto dijeOsito,
            Producto anilloGoldenLuz,
            Producto dijeToyota,
            Producto relojDolceVita,
            Producto cadenaGold,
            Producto anilloGoldenNature,
            Producto collarRama,
            Producto aretesCoquet,
            Producto collarPlataMain,
            Producto relojLadyAutomatic,
            Producto collarCrisFlower,
            Producto dijeOsitoenPie,
            Producto anilloGreenPow,
            Producto collarLuzFugaz,
            Producto aretesBoldHuggies,
            Producto cadenaSingapur,
            Producto anilloFlowers,
            Producto pulseraDestello,
            Producto collarPearl) {

        this.AnilloRoyalStar = anilloRoyalStar;
        this.PulseraCrazy = pulseraCrazy;
        this.AretesNudoGold = aretesNudoGold;
        this.CadenaItaliana = cadenaItaliana;
        this.CollarGalaxy = collarGalaxy;
        this.PulseraVanCleef = pulseraVanCleef;
        this.DijeMar = dijeMar;
        this.RelojConquest = relojConquest;
        this.DijeOsoPanda = dijeOsoPanda;
        this.AnilloChaosDouble = anilloChaosDouble;
        this.CollarFinobolit = collarFinobolit;
        this.Aretescelestial = aretesCelestial;
        this.CadenaEsclava = cadenaEsclava;
        this.PulseraArrastrada = pulseraArrastrada;
        this.PulserasCombLuxury = pulserasCombLuxury;
        this.RelojTissot = relojTissot;
        this.ComboAretesSweet = comboAretesSweet;
        this.DijeOsito = dijeOsito;
        this.AnilloGoldenLuz = anilloGoldenLuz;
        this.DijeToyota = dijeToyota;
        this.RelojDolceVita = relojDolceVita;
        this.CadenaGold = cadenaGold;
        this.AnilloGoldenNature = anilloGoldenNature;
        this.CollarRama = collarRama;
        this.AretesCoquet = aretesCoquet;
        this.CollarPlataMain = collarPlataMain;
        this.RelojLadyAutomatic = relojLadyAutomatic;
        this.CollarCrisFlower = collarCrisFlower;
        this.DijeOsitoenPie = dijeOsitoenPie;
        this.AnilloGreenPow = anilloGreenPow;
        this.CollarLuzFugaz = collarLuzFugaz;
        this.AretesBoldHuggies = aretesBoldHuggies;
        this.CadenaSingapur = cadenaSingapur;
        this.AnilloFlowers = anilloFlowers;
        this.PulseraDestello = pulseraDestello;
        this.CollarPearl = collarPearl;

        inicializarMapeoProductos();
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
        Nodo<Producto> nuevoNodo = new Nodo(producto);
        nuevoNodo.cantidad = 1;

        if (ControladorPrincipal != null) {

            Nodo<Producto> actual = ControladorPrincipal.cabezaCarrito;
            while (actual != null) {
                if (actual.dato != null && actual.dato.equals(producto)) {
                    actual.cantidad++;
                    actualizarContadorCarrito();
                    return;
                }
                actual = actual.sig;
            }

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
            Nodo<Producto> actual = cabezaCarrito;
            while (actual != null) {
                if (actual.dato != null && actual.dato.equals(producto)) {
                    actual.cantidad++;
                    actualizarContadorCarrito();
                    return;
                }
                actual = actual.sig;
            }

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
        if (lblCantidadCarrito == null) {
            return;
        }
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
            circuloCarrito.setVisible(true);
        } else {
            lblCantidadCarrito.setVisible(false);
            circuloCarrito.setVisible(false);
        }
        if (totalItems > 9) {
            lblCantidadCarrito.setLayoutX(716);
        } else {
            lblCantidadCarrito.setLayoutX(710);
        }
    }

    public void eliminarDelCarrito(Producto producto) {
        Nodo<Producto> cabezaActual;
        if (ControladorPrincipal != null) {
            cabezaActual = ControladorPrincipal.cabezaCarrito;
        } else {
            cabezaActual = cabezaCarrito;
        }
        if (cabezaActual == null) {
            return;
        }

        if (cabezaActual.dato.equals(producto)) {
            if (ControladorPrincipal != null) {
                ControladorPrincipal.cabezaCarrito = cabezaActual.sig;
            } else {
                cabezaCarrito = cabezaActual.sig;
            }
        } else {
            Nodo<Producto> actual = cabezaActual;
            while (actual.sig != null && !actual.sig.dato.equals(producto)) {
                actual = actual.sig;
            }
            if (actual.sig != null) {
                actual.sig = actual.sig.sig;
            }
        }
        actualizarContadorCarrito();
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
        ControladorTienda controlador = (ControladorPrincipal != null) ? ControladorPrincipal : this;
        Nodo<Producto> actual = (controlador != null) ? controlador.cabezaCarrito : this.cabezaCarrito;

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
        VBox contenidoCarrito = new VBox(20);
        contenidoCarrito.setPadding(new Insets(15));

        Glow glowEffect = new Glow(0.7);
        DropShadow shadow = new DropShadow(10, Color.LIGHTGRAY);

        ControladorTienda controlador = (ControladorPrincipal != null) ? ControladorPrincipal : this;
        Nodo<Producto> listaCarrito = (controlador != null) ? controlador.cabezaCarrito : this.cabezaCarrito;

        if (listaCarrito == null) {
            Label vacioLabel = new Label("El carrito está vacío");
            vacioLabel.setEffect(shadow);
            contenidoCarrito.getChildren().add(vacioLabel);
        } else {
            Nodo<Producto> actual = listaCarrito;
            while (actual != null) {
                if (actual.dato != null) {
                    final Producto productoActual = actual.dato;
                    final Nodo<Producto> nodoFinal = actual;
                    Nodo<Producto> nodoModificable = actual;

                    HBox item = new HBox(22);
                    item.setAlignment(Pos.CENTER_LEFT);
                    item.setStyle("-fx-padding: 5; -fx-background-color: #f9f9f9;");
                    item.setEffect(shadow);

                    ImageView img = new ImageView(productoActual.imagen);
                    img.setFitHeight(75);
                    img.setFitWidth(75);

                    img.setOnMouseEntered(e -> {
                        img.setEffect(shadow);
                        img.setScaleX(1.2);
                        img.setScaleY(1.2);
                    });
                    img.setOnMouseExited(e -> {
                        img.setEffect(null);
                        img.setScaleX(1.0);
                        img.setScaleY(1.0);
                    });

                    VBox info = new VBox(10);
                    Label nombreLabel = new Label(productoActual.nombre);
                    nombreLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                    HBox cantidadBox = new HBox(5);
                    cantidadBox.setAlignment(Pos.CENTER_LEFT);

                    Button btnMenos = new Button("-");
                    btnMenos.setPrefWidth(25);
                    btnMenos.setCursor(Cursor.HAND);
                    btnMenos.setStyle("-fx-background-color: #FF6357; -fx-text-fill: white;");
                    btnMenos.setOnAction(e -> {
                        if (nodoModificable.cantidad > 1) {
                            nodoModificable.cantidad--;
                            mostrarCarrito();
                        } else {
                            eliminarDelCarrito(productoActual);
                            mostrarCarrito();
                        }
                    });
                    btnMenos.setOnMouseEntered(e -> {
                        btnMenos.setEffect(glowEffect);
                        zoomButton(btnMenos, 1.2);
                    });
                    btnMenos.setOnMouseExited(e -> {
                        btnMenos.setEffect(null);
                        zoomButton(btnMenos, 1.0);
                    });

                    Label lblCantidad = new Label("Cant:" + nodoFinal.cantidad);
                    lblCantidad.setStyle("-fx-font-size: 12px;");

                    Button btnMas = new Button("+");
                    btnMas.setPrefWidth(25);
                    btnMas.setCursor(Cursor.HAND);
                    btnMas.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                    btnMas.setOnAction(e -> {
                        nodoModificable.cantidad++;
                        mostrarCarrito();
                    });
                    btnMas.setOnMouseEntered(e -> {
                        btnMas.setEffect(glowEffect);
                        zoomButton(btnMas, 1.2);
                    });
                    btnMas.setOnMouseExited(e -> {
                        btnMas.setEffect(null);
                        zoomButton(btnMas, 1.0);
                    });

                    cantidadBox.getChildren().addAll(btnMenos, lblCantidad, btnMas);

                    Label precioTotal = new Label(String.format("Total: $%,.2f",
                            productoActual.precio * nodoFinal.cantidad));
                    precioTotal.setStyle("-fx-text-fill: #2e8b57; -fx-font-weight: bold; -fx-font-size: 12px;");

                    Button btnEliminar = new Button("Eliminar");
                    btnEliminar.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
                    btnEliminar.setCursor(Cursor.HAND);
                    btnEliminar.setOnAction(e -> {
                        eliminarDelCarrito(productoActual);
                        mostrarCarrito();
                    });
                    btnEliminar.setOnMouseEntered(e -> {
                        btnEliminar.setEffect(glowEffect);
                        zoomButton(btnEliminar, 1.2);
                    });
                    btnEliminar.setOnMouseExited(e -> {
                        btnEliminar.setEffect(null);
                        zoomButton(btnEliminar, 1.0);
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
            footer.setEffect(shadow);

            Label lblTotal = new Label(String.format("Total: $%,.2f", calcularTotal()));
            lblTotal.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

            Button btnComprar = new Button("Comprar todo");
            btnComprar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
            btnComprar.setCursor(Cursor.HAND);
            btnComprar.setOnMouseEntered(e -> {
                btnComprar.setEffect(glowEffect);
                zoomButton(btnComprar, 1.2);
            });
            btnComprar.setOnMouseExited(e -> {
                btnComprar.setEffect(null);
                zoomButton(btnComprar, 1.0);
            });

            btnComprar.setOnAction(e -> {
                if (listaCarrito == null) {
                    Alert alerta = new Alert(Alert.AlertType.WARNING, "El carrito está vacío");
                    alerta.getDialogPane().setEffect(shadow);
                    alerta.showAndWait();
                } else {
                    mostrarFormularioPago();
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

    private void zoomButton(Button button, double scale) {
        ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
        st.setToX(scale);
        st.setToY(scale);
        st.play();
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
                        img.setFitHeight(300);
                        img.setFitWidth(300);
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
                        precioTotal.setStyle("-fx-text-fill: #2e8b57; "
                                + "-fx-font-weight: bold; -fx-font-size: 22px;");

                        info.getChildren().addAll(nombreLabel, cantidadLabel, precioUnitario, precioTotal);
                        item.getChildren().addAll(img, info);
                        contenidoHistorial.getChildren().add(item);

                        totalGeneral += totalProducto;
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

    public void añadirFavoritos(Producto producto) {
        if (!getEstaEnFavoritos(producto)) {
            Nodo<Producto> nuevoNodo = new Nodo(producto);

            if (ControladorPrincipal != null) {
                if (ControladorPrincipal.cabezaFavoritos == null) {
                    ControladorPrincipal.cabezaFavoritos = nuevoNodo;
                } else {
                    Nodo<Producto> actual = ControladorPrincipal.cabezaFavoritos;
                    while (actual.sig != null) {
                        actual = actual.sig;
                    }
                    actual.sig = nuevoNodo;
                }
            } else {
                if (cabezaFavoritos == null) {
                    cabezaFavoritos = nuevoNodo;
                } else {
                    Nodo<Producto> actual = cabezaFavoritos;
                    while (actual.sig != null) {
                        actual = actual.sig;
                    }
                    actual.sig = nuevoNodo;
                }
            }
            actualizarToggleButton(producto, true);
        }
    }

    public void eliminarFavoritos(Producto producto) {
        Nodo<Producto> cabezaActual;
        if (ControladorPrincipal != null) {
            cabezaActual = ControladorPrincipal.cabezaFavoritos;
        } else {
            cabezaActual = cabezaFavoritos;
        }
        if (cabezaActual == null) {
            return;
        }

        if (cabezaActual.dato.equals(producto)) {
            if (ControladorPrincipal != null) {
                ControladorPrincipal.cabezaFavoritos = cabezaActual.sig;
            } else {
                cabezaFavoritos = cabezaActual.sig;
            }
        } else {
            Nodo<Producto> actual = cabezaActual;
            while (actual.sig != null && !actual.sig.dato.equals(producto)) {
                actual = actual.sig;
            }
            if (actual.sig != null) {
                actual.sig = actual.sig.sig;
            }
        }
        actualizarToggleButton(producto, false);
    }

    private void actualizarToggleButton(Producto producto, boolean estaEnFavoritos) {
        ToggleButton toggle = productoToToggle.get(producto);
        if (toggle != null) {
            Platform.runLater(() -> {
                toggle.setSelected(estaEnFavoritos);
                toggle.setOpacity(estaEnFavoritos ? 1 : 0);
            });
        }
    }

    public void sincronizarTodosToggleButtons() {
        productoToToggle.forEach((producto, toggle) -> {
            if (toggle != null) {
                boolean estaEnFavoritos = getEstaEnFavoritos(producto);
                Platform.runLater(() -> {
                    toggle.setSelected(estaEnFavoritos);
                    toggle.setOpacity(estaEnFavoritos ? 1 : 0);
                });
            }
        });
    }

    public boolean getEstaEnFavoritos(Producto producto) {
        Nodo<Producto> lista;
        if (ControladorPrincipal != null) {
            lista = ControladorPrincipal.cabezaFavoritos;
        } else {
            lista = cabezaFavoritos;
        }
        if (lista == null) {
            return false;
        }

        Nodo<Producto> actual = lista;
        while (actual != null) {
            if (actual.dato != null && actual.dato.equals(producto)) {
                return true;
            }
            actual = actual.sig;
        }
        return false;
    }

    @FXML
    public void mostrarDeseos() {

        panelMenu.setVisible(false);
        panelPostCarrito2.setOpacity(0.6);
        panelDeseos.setVisible(true);

        Glow glowEffect = new Glow(0.7);
        DropShadow shadow = new DropShadow(10, Color.LIGHTGRAY);

        VBox contenidoFavoritos = new VBox(20);
        contenidoFavoritos.setPadding(new Insets(15));
        contenidoFavoritos.setStyle("-fx-background-color: black;");
        contenidoFavoritos.setFillWidth(true);

        ControladorTienda controlador = (ControladorPrincipal != null) ? ControladorPrincipal : this;
        Nodo<Producto> actual = (controlador != null) ? controlador.cabezaFavoritos : this.cabezaFavoritos;

        if (actual != null) {
            while (actual != null) {
                if (actual.dato != null) {
                    Producto producto = actual.dato;

                    HBox item = new HBox(15);
                    item.setAlignment(Pos.CENTER_LEFT);
                    item.setMaxWidth(Double.MAX_VALUE);
                    item.setStyle("-fx-padding: 10; -fx-background-color: #AA9866; "
                            + "-fx-background-radius: 15; -fx-min-height: 100;");
                    item.setEffect(shadow);

                    ImageView img = new ImageView(producto.imagen);
                    img.setFitHeight(150);
                    img.setFitWidth(150);
                    img.setPreserveRatio(true);

                    img.setOnMouseEntered(e -> {
                        img.setEffect(shadow);
                        img.setScaleX(1.2);
                        img.setScaleY(1.2);
                    });
                    img.setOnMouseExited(e -> {
                        img.setEffect(null);
                        img.setScaleX(1.0);
                        img.setScaleY(1.0);
                    });

                    VBox info = new VBox(10);
                    info.setAlignment(Pos.CENTER_LEFT);

                    Label nombreLabel = new Label(producto.nombre);
                    nombreLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                    Label precioLabel = new Label(String.format("Precio: $%,.2f", producto.precio));
                    precioLabel.setStyle("-fx-font-size: 14px;");

                    Button btnAnadirCarrito = new Button("Añadir al carrito");
                    btnAnadirCarrito.setStyle("-fx-background-color: #634A2C; -fx-text-fill: white; -fx-font-weight: bold;");
                    btnAnadirCarrito.setCursor(Cursor.HAND);
                    btnAnadirCarrito.setOnAction(e -> {
                        añadirCarrito(producto);
                        new Alert(Alert.AlertType.INFORMATION, producto.nombre + " añadido al carrito").showAndWait();
                    });
                    btnAnadirCarrito.setOnMouseEntered(e -> {
                        btnAnadirCarrito.setEffect(glowEffect);
                        zoomButton(btnAnadirCarrito, 1.2);
                    });
                    btnAnadirCarrito.setOnMouseExited(e -> {
                        btnAnadirCarrito.setEffect(null);
                        zoomButton(btnAnadirCarrito, 1.0);
                    });

                    Button btnEliminarFavoritos = new Button("Eliminar de lista de deseos");
                    btnEliminarFavoritos.setStyle("-fx-background-color: #634A2C; -fx-text-fill: white; -fx-font-weight: bold;");
                    btnEliminarFavoritos.setCursor(Cursor.HAND);
                    btnEliminarFavoritos.setOnAction(e -> {
                        eliminarFavoritos(producto);
                        mostrarDeseos();
                        new Alert(Alert.AlertType.INFORMATION, producto.nombre + " eliminado de favoritos").showAndWait();
                    });
                    btnEliminarFavoritos.setOnMouseEntered(e -> {
                        btnEliminarFavoritos.setEffect(glowEffect);
                        zoomButton(btnEliminarFavoritos, 1.2);
                    });
                    btnEliminarFavoritos.setOnMouseExited(e -> {
                        btnEliminarFavoritos.setEffect(null);
                        zoomButton(btnEliminarFavoritos, 1.0);
                    });

                    HBox botonesBox = new HBox(7.5);
                    botonesBox.setAlignment(Pos.CENTER_LEFT);
                    botonesBox.getChildren().addAll(btnAnadirCarrito, btnEliminarFavoritos);

                    info.getChildren().addAll(nombreLabel, precioLabel, botonesBox);
                    item.getChildren().addAll(img, info);
                    contenidoFavoritos.getChildren().add(item);
                }
                actual = actual.sig;
            }
        } else {
            Label lblVacio = new Label("No hay productos en tu lista de deseos");
            lblVacio.setStyle("-fx-font-size: 13px; -fx-text-fill: white;");
            lblVacio.setEffect(shadow);
            contenidoFavoritos.getChildren().add(lblVacio);
        }

        if (scrollDeseos != null) {
            scrollDeseos.setContent(contenidoFavoritos);
            scrollDeseos.setStyle("-fx-background: black; -fx-background-color: transparent;");
            scrollDeseos.setFitToWidth(true);
        }
    }

    @FXML
    public void guardarHistorialEnArchivo() {
        try {
            File carpetaPaquete = new File("src/ArchivoTexto");
            if (!carpetaPaquete.exists()) {
                carpetaPaquete.mkdirs();
            }
            File archivoPaquete = new File(carpetaPaquete, "historial_compras.txt");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoPaquete, true))) {
                ControladorTienda controlador = (ControladorPrincipal != null) ? ControladorPrincipal : this;
                Nodo<Producto> actual = controlador.cabezaCarrito;

                writer.write("\n=== NUEVA ENTRADA === " + new Date() + "\n\n");

                if (actual == null) {
                    writer.write("El carrito estaba vacío\n");
                } else {
                    while (actual != null) {
                        if (actual.dato != null) {
                            writer.write(String.format(
                                    "Producto: %s | Cantidad: %d | Precio: $%,.2f | Total: $%,.2f\n",
                                    actual.dato.nombre,
                                    actual.cantidad,
                                    actual.dato.precio,
                                    actual.dato.precio * actual.cantidad
                            ));
                        }
                        actual = actual.sig;
                    }
                }

                double total = controlador.calcularTotal();
                writer.write(String.format("\nSUBTOTAL: $%,.2f\n", total));
                writer.write("====================================\n");
            }
            Platform.runLater(() -> {
                new Alert(Alert.AlertType.INFORMATION,
                        "Historial actualizado en el archivo dentro del proyecto:\n" + archivoPaquete.getAbsolutePath()
                ).showAndWait();
            });

        } catch (IOException e) {
            Platform.runLater(() -> {
                new Alert(Alert.AlertType.ERROR,
                        "Error al guardar el archivo dentro del proyecto: " + e.getMessage()
                ).show();
            });
        }

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar Carpeta para Guardar el Historial");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        Stage stage = (Stage) ScrollPaneCarrito.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            String rutaCompleta = selectedDirectory.getAbsolutePath() + "/historial_compras.txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaCompleta, true))) {
                ControladorTienda controlador = (ControladorPrincipal != null) ? ControladorPrincipal : this;
                Nodo<Producto> actual = controlador.cabezaCarrito;

                writer.write("\n=== NUEVA ENTRADA === " + new Date() + "\n\n");

                if (actual == null) {
                    writer.write("El carrito estaba vacío\n");
                } else {
                    while (actual != null) {
                        if (actual.dato != null) {
                            writer.write(String.format(
                                    "Producto: %s | Cantidad: %d | Precio: $%,.2f | Total: $%,.2f\n",
                                    actual.dato.nombre,
                                    actual.cantidad,
                                    actual.dato.precio,
                                    actual.dato.precio * actual.cantidad
                            ));
                        }
                        actual = actual.sig;
                    }
                }

                double total = controlador.calcularTotal();
                writer.write(String.format("\nSUBTOTAL: $%,.2f\n", total));
                writer.write("====================================\n");

                Platform.runLater(() -> {
                    new Alert(Alert.AlertType.INFORMATION,
                            "Historial actualizado en:\n" + rutaCompleta
                    ).showAndWait();
                });
            } catch (IOException e) {
                Platform.runLater(() -> {
                    new Alert(Alert.AlertType.ERROR,
                            "Error al guardar: " + e.getMessage()
                    ).show();
                });
            }
        }
    }

    @FXML
    public void guardarHistorialProducto(Producto producto) {
        if (producto == null) {
            Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Producto inválido.").show());
            return;
        }

        try {
            File carpeta = new File("src/ArchivoTexto");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            File archivo = new File(carpeta, "historial_compras.txt");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
                writer.write("\n=== NUEVA ENTRADA === " + new Date() + "\n\n");
                writer.write(String.format(
                        "Producto: %s | Descripción: %s | Precio: $%,.2f\n",
                        producto.nombre,
                        producto.descripcion,
                        producto.precio
                ));
                writer.write("====================================\n");
            }

            Platform.runLater(() -> {
                new Alert(Alert.AlertType.INFORMATION,
                        "Historial actualizado en el archivo dentro del proyecto:\n" + archivo.getAbsolutePath()
                ).showAndWait();
            });

        } catch (IOException e) {
            Platform.runLater(() -> {
                new Alert(Alert.AlertType.ERROR,
                        "Error al guardar el archivo: " + e.getMessage()
                ).show();
            });
        }
    }

    @FXML
    public void mostrarFormularioPago() {
        String estiloCampos = "-fx-font-size: 16px; -fx-background-color: #f0f0f0; -fx-text-fill: black; "
                + "-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-background-radius: 5px;";

        Label lblNombreTitular = new Label("Nombre del Titular:");
        TextField campoNombreTitular = new TextField();
        campoNombreTitular.setPromptText("Ingrese el nombre del titular");
        campoNombreTitular.setStyle(estiloCampos);

        Label lblNumeroTarjeta = new Label("Número de Tarjeta:");
        TextField campoNumeroTarjeta = new TextField();
        campoNumeroTarjeta.setPromptText("Ingrese el número de tarjeta");
        campoNumeroTarjeta.setStyle(estiloCampos);

        Label lblFechaVencimiento = new Label("Fecha de expiracion:");
        ComboBox<String> comboMes = new ComboBox<>();
        comboMes.setPromptText("Mes");
        comboMes.getItems().addAll("01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12");
        comboMes.setStyle(estiloCampos);

        ComboBox<String> comboAnio = new ComboBox<>();
        comboAnio.setPromptText("Año");
        int anioActual = LocalDate.now().getYear();
        for (int i = 0; i < 10; i++) {
            comboAnio.getItems().add(String.valueOf(anioActual + i));
        }
        comboAnio.setStyle(estiloCampos);

        HBox boxVencimiento = new HBox(10, comboMes, comboAnio);
        boxVencimiento.setAlignment(Pos.CENTER_LEFT);

        Label lblCVV = new Label("CVV:");
        TextField campoCVV = new TextField();
        campoCVV.setPromptText("Ingrese el CVV");
        campoCVV.setStyle(estiloCampos);

        Label lblDireccion = new Label("Dirección de Envío:");
        TextField campoDireccion = new TextField();
        campoDireccion.setPromptText("Ingrese la dirección de envío");
        campoDireccion.setStyle(estiloCampos);

        Button btnPagar = new Button("Pagar");
        btnPagar.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white; "
                + "-fx-background-radius: 5px; -fx-border-color: #388E3C; -fx-border-radius: 5px;");
        btnPagar.setOnMouseEntered(e -> btnPagar.setEffect(new DropShadow(10, Color.LIME)));
        btnPagar.setOnMouseExited(e -> btnPagar.setEffect(null));

        btnPagar.setOnAction(e -> {
            if (campoNombreTitular.getText().isEmpty() || campoNumeroTarjeta.getText().isEmpty()
                    || comboMes.getValue() == null || comboAnio.getValue() == null
                    || campoCVV.getText().isEmpty() || campoDireccion.getText().isEmpty()) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Campos incompletos");
                alerta.setHeaderText(null);
                alerta.setContentText("Por favor, complete todos los campos.");
                alerta.showAndWait();
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Pago");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Desea confirmar el pago?");
            Optional<ButtonType> resultado = confirmacion.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                ControladorTienda controlador = (ControladorPrincipal != null) ? ControladorPrincipal : this;
                Nodo<Producto> listaCarrito = (controlador != null) ? controlador.cabezaCarrito : this.cabezaCarrito;

                Nodo<Producto> productoActual = listaCarrito;
                while (productoActual != null) {
                    for (int i = 0; i < productoActual.cantidad; i++) {
                        añadirHistorial(productoActual.dato);
                    }
                    productoActual = productoActual.sig;
                }

                guardarHistorialEnArchivo();

                if (controlador != null) {
                    controlador.cabezaCarrito = null;
                } else {
                    this.cabezaCarrito = null;
                }

                actualizarContadorCarrito();
                mostrarCarrito();

                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Pago realizado");
                alerta.setHeaderText(null);
                alerta.setContentText("¡Gracias por tu compra!");
                alerta.showAndWait();

                ((Stage) btnPagar.getScene().getWindow()).close();
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setStyle("-fx-background-color: white;");
        layout.getChildren().addAll(
                lblNombreTitular, campoNombreTitular,
                lblNumeroTarjeta, campoNumeroTarjeta,
                lblFechaVencimiento, boxVencimiento,
                lblCVV, campoCVV,
                lblDireccion, campoDireccion,
                btnPagar
        );

        Scene escena = new Scene(layout, 400, 500);
        Stage ventanaPago = new Stage();
        ventanaPago.setTitle("Formulario de Pago");
        ventanaPago.setScene(escena);
        ventanaPago.initModality(Modality.APPLICATION_MODAL);
        ventanaPago.show();
    }

    @FXML
    public void volver() {
        PaneCarrito.setVisible(false);
        panelPostCarrito2.setOpacity(0);
        panelMenu.setVisible(false);
        panelHistorial.setVisible(false);
        panelDeseos.setVisible(false);
    }

    @FXML
    public void mostrarMenu() {
        panelMenu.setVisible(true);
    }

    @FXML
    public void cambiarEscena(ActionEvent event, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        ControladorTienda nuevoControlador = loader.getController();

        nuevoControlador.setListaCarrito(this.cabezaCarrito);
        nuevoControlador.setInicioHistorial(this.inicioHistorial);
        nuevoControlador.setListaFavoritos(this.cabezaFavoritos);

        nuevoControlador.setUsuario(this.usuarioActual);

        nuevoControlador.setProductos(
                this.AnilloRoyalStar, this.PulseraCrazy, this.AretesNudoGold,
                this.CadenaItaliana, this.CollarGalaxy, this.PulseraVanCleef,
                this.DijeMar, this.RelojConquest, this.DijeOsoPanda,
                this.AnilloChaosDouble, this.CollarFinobolit, this.Aretescelestial,
                this.CadenaEsclava, this.PulseraArrastrada, this.PulserasCombLuxury,
                this.RelojTissot, this.ComboAretesSweet, this.DijeOsito,
                this.AnilloGoldenLuz, this.DijeToyota, this.RelojDolceVita,
                this.CadenaGold, this.AnilloGoldenNature, this.CollarRama,
                this.AretesCoquet, this.CollarPlataMain, this.RelojLadyAutomatic,
                this.CollarCrisFlower, this.DijeOsitoenPie, this.AnilloGreenPow,
                this.CollarLuzFugaz, this.AretesBoldHuggies, this.CadenaSingapur,
                this.AnilloFlowers, this.PulseraDestello, this.CollarPearl
        );
        nuevoControlador.setControladorPrincipal(this.ControladorPrincipal != null
                ? this.ControladorPrincipal : this);

        nuevoControlador.sincronizarTodosToggleButtons();
        nuevoControlador.actualizarContadorCarrito();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void inicializarMapeoProductos() {

        productoToToggle.clear();

        productoToToggle.put(AnilloRoyalStar, btnFavAnilloRoyalStar);
        productoToToggle.put(PulseraCrazy, btnFavPulseraCrazy);
        productoToToggle.put(AretesNudoGold, btnFavAretesNudoGold);
        productoToToggle.put(CadenaItaliana, btnFavCadenaItaliana);
        productoToToggle.put(CollarGalaxy, btnFavCollarGalaxy);
        productoToToggle.put(PulseraVanCleef, btnFavPulseraVanCleef);
        productoToToggle.put(DijeMar, btnFavDijeMar);
        productoToToggle.put(RelojConquest, btnFavRelojConquest);
        productoToToggle.put(DijeOsoPanda, btnFavDijeOsoPanda);
        productoToToggle.put(AnilloChaosDouble, btnFavAnilloChaosDouble);
        productoToToggle.put(CollarFinobolit, btnFavCollarFinobolit);
        productoToToggle.put(Aretescelestial, btnFavAretescelestial);
        productoToToggle.put(CadenaEsclava, btnFavCadenaEsclava);
        productoToToggle.put(PulseraArrastrada, btnFavPulseraArrastrada);
        productoToToggle.put(PulserasCombLuxury, btnFavPulserasCombLuxury);
        productoToToggle.put(RelojTissot, btnFavRelojTissot);
        productoToToggle.put(ComboAretesSweet, btnFavComboAretesSweet);
        productoToToggle.put(DijeOsito, btnFavDijeOsito);
        productoToToggle.put(AnilloGoldenLuz, btnFavAnilloGoldenLuz);
        productoToToggle.put(DijeToyota, btnFavDijeToyota);
        productoToToggle.put(RelojDolceVita, btnFavRelojDolceVita);
        productoToToggle.put(CadenaGold, btnFavCadenaGold);
        productoToToggle.put(AnilloGoldenNature, btnFavAnilloGoldenNature);
        productoToToggle.put(CollarRama, btnFavCollarRama);
        productoToToggle.put(AretesCoquet, btnFavAretesCoquet);
        productoToToggle.put(CollarPlataMain, btnFavCollarPlataMain);
        productoToToggle.put(RelojLadyAutomatic, btnFavRelojLadyAutomatic);
        productoToToggle.put(CollarCrisFlower, btnFavCollarCrisFlower);
        productoToToggle.put(DijeOsitoenPie, btnFavDijeOsitoenPie);
        productoToToggle.put(AnilloGreenPow, btnFavAnilloGreenPow);
        productoToToggle.put(CollarLuzFugaz, btnFavCollarLuzFugaz);
        productoToToggle.put(AretesBoldHuggies, btnFavAretesBoldHuggies);
        productoToToggle.put(CadenaSingapur, btnFavCadenaSingapur);
        productoToToggle.put(AnilloFlowers, btnFavAnilloFlowers);
        productoToToggle.put(PulseraDestello, btnFavPulseraDestello);
        productoToToggle.put(CollarPearl, btnFavCollarPearl);
    }

    @FXML
    public void initialize() {
        actualizarContadorCarrito();
        inicializarMapeoProductos();
        setImagenToogleButtons();
        aplicarEfectosBtns();
        sincronizarTodosToggleButtons();
    }

    public void aplicarEfectosBtns() {
        aplicarEfecto(btnAnilloRoyalStar);
        aplicarEfecto(btnPulseraCrazy);
        aplicarEfecto(btnAretesNudoGold);
        aplicarEfecto(btnCadenaItaliana);
        aplicarEfecto(btnCollarGalaxy);
        aplicarEfecto(btnPulseraVanCleef);
        aplicarEfecto(btnDijeMar);
        aplicarEfecto(btnRelojConquest);
        aplicarEfecto(btnDijeOsoPanda);
        aplicarEfecto(btnAnilloChaosDouble);
        aplicarEfecto(btnCollarFinobolit);
        aplicarEfecto(btnAretescelestial);
        aplicarEfecto(btnCadenaEsclava);
        aplicarEfecto(btnPulseraArrastrada);
        aplicarEfecto(btnPulserasCombLuxury);
        aplicarEfecto(btnRelojTissot);
        aplicarEfecto(btnComboAretesSweet);
        aplicarEfecto(btnDijeOsito);
        aplicarEfecto(btnAnilloGoldenLuz);
        aplicarEfecto(btnDijeToyota);
        aplicarEfecto(btnRelojDolceVita);
        aplicarEfecto(btnCadenaGold);
        aplicarEfecto(btnAnilloGoldenNature);
        aplicarEfecto(btnCollarRama);
        aplicarEfecto(btnAretesCoquet);
        aplicarEfecto(btnCollarPlataMain);
        aplicarEfecto(btnRelojLadyAutomatic);
        aplicarEfecto(btnCollarCrisFlower);
        aplicarEfecto(btnDijeOsitoenPie);
        aplicarEfecto(btnAnilloGreenPow);
        aplicarEfecto(btnCollarLuzFugaz);
        aplicarEfecto(btnAretesBoldHuggies);
        aplicarEfecto(btnCadenaSingapur);
        aplicarEfecto(btnAnilloFlowers);
        aplicarEfecto(btnPulseraDestello);
        aplicarEfecto(btnCollarPearl);

        aplicarEfecto(btnMenu);
        aplicarEfecto(btnDeseos);
        aplicarEfecto(btnCarrito);

    }

    public void aplicarEfecto(Button boton) {
        if (boton == null) {
            return;
        }
        Glow glow = new Glow(0.7);
        DropShadow sombra = new DropShadow(6, Color.web("#FFD700"));
        sombra.setInput(glow);

        String estiloOriginal = boton.getStyle();

        boton.setOnMouseEntered(e -> {
            boton.setEffect(sombra);

            ScaleTransition zoomIn = new ScaleTransition(Duration.millis(180), boton);
            zoomIn.setToX(1.05);
            zoomIn.setToY(1.08);
            zoomIn.play();

            boton.setStyle(estiloOriginal + "; -fx-background-color: rgba(255, 255, 255, 0.08);");
        });

        boton.setOnMouseExited(e -> {
            boton.setEffect(null);

            ScaleTransition zoomOut = new ScaleTransition(Duration.millis(180), boton);
            zoomOut.setToX(1.0);
            zoomOut.setToY(1.0);
            zoomOut.play();
            boton.setStyle(estiloOriginal);
        });
    }


    public void setImagenFavorito(ToggleButton toggleButton, Image imagen) {
        if (toggleButton == null) {
            return;
        }
        ImageView imageView = new ImageView(imagen);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        toggleButton.setGraphic(imageView);
    }

    @FXML
    public void setImagenToogleButtons() {
        setImagenFavorito(btnFavAnilloRoyalStar, coraLleno);
        setImagenFavorito(btnFavPulseraCrazy, coraLleno);
        setImagenFavorito(btnFavAretesNudoGold, coraLleno);
        setImagenFavorito(btnFavCadenaItaliana, coraLleno);
        setImagenFavorito(btnFavCollarGalaxy, coraLleno);
        setImagenFavorito(btnFavPulseraVanCleef, coraLleno);
        setImagenFavorito(btnFavDijeMar, coraLleno);
        setImagenFavorito(btnFavRelojConquest, coraLleno);
        setImagenFavorito(btnFavDijeOsoPanda, coraLleno);
        setImagenFavorito(btnFavAnilloChaosDouble, coraLleno);
        setImagenFavorito(btnFavCollarFinobolit, coraLleno);
        setImagenFavorito(btnFavAretescelestial, coraLleno);
        setImagenFavorito(btnFavCadenaEsclava, coraLleno);
        setImagenFavorito(btnFavPulseraArrastrada, coraLleno);
        setImagenFavorito(btnFavPulserasCombLuxury, coraLleno);
        setImagenFavorito(btnFavRelojTissot, coraLleno);
        setImagenFavorito(btnFavComboAretesSweet, coraLleno);
        setImagenFavorito(btnFavDijeOsito, coraLleno);
        setImagenFavorito(btnFavAnilloGoldenLuz, coraLleno);
        setImagenFavorito(btnFavDijeToyota, coraLleno);
        setImagenFavorito(btnFavRelojDolceVita, coraLleno);
        setImagenFavorito(btnFavCadenaGold, coraLleno);
        setImagenFavorito(btnFavAnilloGoldenNature, coraLleno);
        setImagenFavorito(btnFavCollarRama, coraLleno);
        setImagenFavorito(btnFavAretesCoquet, coraLleno);
        setImagenFavorito(btnFavCollarPlataMain, coraLleno);
        setImagenFavorito(btnFavRelojLadyAutomatic, coraLleno);
        setImagenFavorito(btnFavCollarCrisFlower, coraLleno);
        setImagenFavorito(btnFavDijeOsitoenPie, coraLleno);
        setImagenFavorito(btnFavAnilloGreenPow, coraLleno);
        setImagenFavorito(btnFavCollarLuzFugaz, coraLleno);
        setImagenFavorito(btnFavAretesBoldHuggies, coraLleno);
        setImagenFavorito(btnFavCadenaSingapur, coraLleno);
        setImagenFavorito(btnFavAnilloFlowers, coraLleno);
        setImagenFavorito(btnFavPulseraDestello, coraLleno);
        setImagenFavorito(btnFavCollarPearl, coraLleno);
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

    //-----------------Comprar Ahora------------------//
    @FXML
    public void mostrarYComprarProducto(Producto producto) {
        String estiloCampos = "-fx-font-size: 16px; -fx-background-color: #f0f0f0; -fx-text-fill: black; "
                + "-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-background-radius: 5px;";

        Label lblNombreTitular = new Label("Nombre del Titular:");
        TextField campoNombreTitular = new TextField();
        campoNombreTitular.setPromptText("Ingrese el nombre del titular");
        campoNombreTitular.setStyle(estiloCampos);

        Label lblNumeroTarjeta = new Label("Número de Tarjeta:");
        TextField campoNumeroTarjeta = new TextField();
        campoNumeroTarjeta.setPromptText("Ingrese el número de tarjeta");
        campoNumeroTarjeta.setStyle(estiloCampos);

        Label lblFechaVencimiento = new Label("Fecha de expiracion:");
        ComboBox<String> comboMes = new ComboBox<>();
        comboMes.setPromptText("Mes");
        comboMes.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        comboMes.setStyle(estiloCampos);

        ComboBox<String> comboAnio = new ComboBox<>();
        comboAnio.setPromptText("Año");
        int anioActual = LocalDate.now().getYear();
        for (int i = 0; i < 10; i++) {
            comboAnio.getItems().add(String.valueOf(anioActual + i));
        }
        comboAnio.setStyle(estiloCampos);

        HBox boxVencimiento = new HBox(10, comboMes, comboAnio);
        boxVencimiento.setAlignment(Pos.CENTER_LEFT);

        Label lblCVV = new Label("CVV:");
        TextField campoCVV = new TextField();
        campoCVV.setPromptText("Ingrese el CVV");
        campoCVV.setStyle(estiloCampos);

        Label lblDireccion = new Label("Dirección de Envío:");
        TextField campoDireccion = new TextField();
        campoDireccion.setPromptText("Ingrese la dirección de envío");
        campoDireccion.setStyle(estiloCampos);

        Button btnPagar = new Button("Pagar");
        btnPagar.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white; "
                + "-fx-background-radius: 5px; -fx-border-color: #388E3C; -fx-border-radius: 5px;");
        btnPagar.setOnMouseEntered(e -> btnPagar.setEffect(new DropShadow(10, Color.LIME)));
        btnPagar.setOnMouseExited(e -> btnPagar.setEffect(null));

        btnPagar.setOnAction(e -> {
            if (campoNombreTitular.getText().isEmpty() || campoNumeroTarjeta.getText().isEmpty()
                    || comboMes.getValue() == null || comboAnio.getValue() == null
                    || campoCVV.getText().isEmpty() || campoDireccion.getText().isEmpty()) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Campos incompletos");
                alerta.setHeaderText(null);
                alerta.setContentText("Por favor, complete todos los campos.");
                alerta.showAndWait();
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Pago");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Desea confirmar el pago?");
            Optional<ButtonType> resultado = confirmacion.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                if (producto != null) {
                    añadirHistorial(producto);

                }

                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Pago realizado");
                alerta.setHeaderText(null);
                alerta.setContentText("¡Gracias por tu compra!");
                alerta.showAndWait();

                ((Stage) btnPagar.getScene().getWindow()).close();
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setStyle("-fx-background-color: white;");
        layout.getChildren().addAll(
                lblNombreTitular, campoNombreTitular,
                lblNumeroTarjeta, campoNumeroTarjeta,
                lblFechaVencimiento, boxVencimiento,
                lblCVV, campoCVV,
                lblDireccion, campoDireccion,
                btnPagar
        );

        Scene escena = new Scene(layout, 400, 500);
        Stage ventanaPago = new Stage();
        ventanaPago.setTitle("Formulario de Pago");
        ventanaPago.setScene(escena);
        ventanaPago.initModality(Modality.APPLICATION_MODAL);
        ventanaPago.show();
    }

    @FXML
    public void ComprarAnilloRoyalStar() {
        mostrarYComprarProducto(AnilloRoyalStar);
        guardarHistorialProducto(AnilloRoyalStar);
    }

    @FXML
    public void ComprarPulseraCrazy() {
        mostrarYComprarProducto(PulseraCrazy);
    }

    @FXML
    public void ComprarAretesNudoGold() {
        mostrarYComprarProducto(AretesNudoGold);
    }

    @FXML
    public void ComprarCadenaItaliana() {
        mostrarYComprarProducto(CadenaItaliana);
    }

    @FXML
    public void ComprarCollarGalaxy() {
        mostrarYComprarProducto(CollarGalaxy);
    }

    @FXML
    public void ComprarPulseraVanCleef() {
        mostrarYComprarProducto(PulseraVanCleef);
    }

    @FXML
    public void ComprarDijeMar() {
        mostrarYComprarProducto(DijeMar);
    }

    @FXML
    public void ComprarRelojConquest() {
        mostrarYComprarProducto(RelojConquest);
    }

    @FXML
    public void ComprarDijeOsoPanda() {
        mostrarYComprarProducto(DijeOsoPanda);
    }

    @FXML
    public void ComprarAnilloChaosDouble() {
        mostrarYComprarProducto(AnilloChaosDouble);
    }

    @FXML
    public void ComprarCollarFinobolit() {
        mostrarYComprarProducto(CollarFinobolit);
    }

    @FXML
    public void ComprarAretescelestial() {
        mostrarYComprarProducto(Aretescelestial);
    }

    @FXML
    public void ComprarCadenaEsclava() {
        mostrarYComprarProducto(CadenaEsclava);
    }

    @FXML
    public void ComprarPulseraArrastrada() {
        mostrarYComprarProducto(PulseraArrastrada);
    }

    @FXML
    public void ComprarPulserasCombLuxury() {
        mostrarYComprarProducto(PulserasCombLuxury);
    }

    @FXML
    public void ComprarRelojTissot() {
        mostrarYComprarProducto(RelojTissot);
    }

    @FXML
    public void ComprarComboAretesSweet() {
        mostrarYComprarProducto(ComboAretesSweet);
    }

    @FXML
    public void ComprarDijeOsito() {
        mostrarYComprarProducto(DijeOsito);
    }

    @FXML
    public void ComprarAnilloGoldenLuz() {
        mostrarYComprarProducto(AnilloGoldenLuz);
    }

    @FXML
    public void ComprarDijeToyota() {
        mostrarYComprarProducto(DijeToyota);
    }

    @FXML
    public void ComprarRelojDolceVita() {
        mostrarYComprarProducto(RelojDolceVita);
    }

    @FXML
    public void ComprarCadenaGold() {
        mostrarYComprarProducto(CadenaGold);
    }

    @FXML
    public void ComprarAnilloGoldenNature() {
        mostrarYComprarProducto(AnilloGoldenNature);
    }

    @FXML
    public void ComprarCollarRama() {
        mostrarYComprarProducto(CollarRama);
    }

    @FXML
    public void ComprarAretesCoquet() {
        mostrarYComprarProducto(AretesCoquet);
    }

    @FXML
    public void ComprarCollarPlataMain() {
        mostrarYComprarProducto(CollarPlataMain);
    }

    @FXML
    public void ComprarRelojLadyAutomatic() {
        mostrarYComprarProducto(RelojLadyAutomatic);
    }

    @FXML
    public void ComprarCollarCrisFlower() {
        mostrarYComprarProducto(CollarCrisFlower);
    }

    @FXML
    public void ComprarDijeOsitoenPie() {
        mostrarYComprarProducto(DijeOsitoenPie);
    }

    @FXML
    public void ComprarAnilloGreenPow() {
        mostrarYComprarProducto(AnilloGreenPow);
    }

    @FXML
    public void ComprarCollarLuzFugaz() {
        mostrarYComprarProducto(CollarLuzFugaz);
    }

    @FXML
    public void ComprarAretesBoldHuggies() {
        mostrarYComprarProducto(AretesBoldHuggies);
    }

    @FXML
    public void ComprarCadenaSingapur() {
        mostrarYComprarProducto(CadenaSingapur);
    }

    @FXML
    public void ComprarAnilloFlowers() {
        mostrarYComprarProducto(AnilloFlowers);
    }

    @FXML
    public void ComprarPulseraDestello() {
        mostrarYComprarProducto(PulseraDestello);
    }

    @FXML
    public void ComprarCollarPearl() {
        mostrarYComprarProducto(CollarPearl);
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

    public void AnilloGoldenNature() {
        añadirCarrito(AnilloGoldenNature);
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

    // -------------------------Pa favoritos chichi--------------------------------------//
    public void añadirFav(ToggleButton toogleButton, Producto producto) {
        if (toogleButton.isSelected()) {
            añadirFavoritos(producto);
        } else {
            eliminarFavoritos(producto);
        }
    }

    @FXML
    public void añadirFavAnilloRoyalStar() {
        añadirFav(btnFavAnilloRoyalStar, AnilloRoyalStar);
    }

    @FXML
    public void añadirFavPulseraCrazy() {
        añadirFav(btnFavPulseraCrazy, PulseraCrazy);
    }

    @FXML
    public void añadirFavAretesNudoGold() {
        añadirFav(btnFavAretesNudoGold, AretesNudoGold);
    }

    @FXML
    public void añadirFavCadenaItaliana() {
        añadirFav(btnFavCadenaItaliana, CadenaItaliana);
    }

    @FXML
    public void añadirFavCollarGalaxy() {
        añadirFav(btnFavCollarGalaxy, CollarGalaxy);
    }

    @FXML
    public void añadirFavPulseraVanCleef() {
        añadirFav(btnFavPulseraVanCleef, PulseraVanCleef);
    }

    @FXML
    public void añadirFavDijeMar() {
        añadirFav(btnFavDijeMar, DijeMar);
    }

    @FXML
    public void añadirFavRelojConquest() {
        añadirFav(btnFavRelojConquest, RelojConquest);
    }

    @FXML
    public void añadirFavDijeOsoPanda() {
        añadirFav(btnFavDijeOsoPanda, DijeOsoPanda);
    }

    @FXML
    public void añadirFavAnilloChaosDouble() {
        añadirFav(btnFavAnilloChaosDouble, AnilloChaosDouble);
    }

    @FXML
    public void añadirFavCollarFinobolit() {
        añadirFav(btnFavCollarFinobolit, CollarFinobolit);
    }

    @FXML
    public void añadirFavAretescelestial() {
        añadirFav(btnFavAretescelestial, Aretescelestial);
    }

    @FXML
    public void añadirFavCadenaEsclava() {
        añadirFav(btnFavCadenaEsclava, CadenaEsclava);
    }

    @FXML
    public void añadirFavPulseraArrastrada() {
        añadirFav(btnFavPulseraArrastrada, PulseraArrastrada);
    }

    @FXML
    public void añadirFavPulserasCombLuxury() {
        añadirFav(btnFavPulserasCombLuxury, PulserasCombLuxury);
    }

    @FXML
    public void añadirFavRelojTissot() {
        añadirFav(btnFavRelojTissot, RelojTissot);
    }

    @FXML
    public void añadirFavComboAretesSweet() {
        añadirFav(btnFavComboAretesSweet, ComboAretesSweet);
    }

    @FXML
    public void añadirFavDijeOsito() {
        añadirFav(btnFavDijeOsito, DijeOsito);
    }

    @FXML
    public void añadirFavAnilloGoldenLuz() {
        añadirFav(btnFavAnilloGoldenLuz, AnilloGoldenLuz);
    }

    @FXML
    public void añadirFavDijeToyota() {
        añadirFav(btnFavDijeToyota, DijeToyota);
    }

    @FXML
    public void añadirFavRelojDolceVita() {
        añadirFav(btnFavRelojDolceVita, RelojDolceVita);
    }

    @FXML
    public void añadirFavCadenaGold() {
        añadirFav(btnFavCadenaGold, CadenaGold);
    }

    @FXML
    public void añadirFavAnilloGoldenNature() {
        añadirFav(btnFavAnilloGoldenNature, AnilloGoldenNature);
    }

    @FXML
    public void añadirFavCollarRama() {
        añadirFav(btnFavCollarRama, CollarRama);
    }

    @FXML
    public void añadirFavAretesCoquet() {
        añadirFav(btnFavAretesCoquet, AretesCoquet);
    }

    @FXML
    public void añadirFavCollarPlataMain() {
        añadirFav(btnFavCollarPlataMain, CollarPlataMain);
    }

    @FXML
    public void añadirFavRelojLadyAutomatic() {
        añadirFav(btnFavRelojLadyAutomatic, RelojLadyAutomatic);
    }

    @FXML
    public void añadirFavCollarCrisFlower() {
        añadirFav(btnFavCollarCrisFlower, CollarCrisFlower);
    }

    @FXML
    public void añadirFavDijeOsitoenPie() {
        añadirFav(btnFavDijeOsitoenPie, DijeOsitoenPie);
    }

    @FXML
    public void añadirFavAnilloGreenPow() {
        añadirFav(btnFavAnilloGreenPow, AnilloGreenPow);
    }

    @FXML
    public void añadirFavCollarLuzFugaz() {
        añadirFav(btnFavCollarLuzFugaz, CollarLuzFugaz);
    }

    @FXML
    public void añadirFavAretesBoldHuggies() {
        añadirFav(btnFavAretesBoldHuggies, AretesBoldHuggies);
    }

    @FXML
    public void añadirFavCadenaSingapur() {
        añadirFav(btnFavCadenaSingapur, CadenaSingapur);
    }

    @FXML
    public void añadirFavAnilloFlowers() {
        añadirFav(btnFavAnilloFlowers, AnilloFlowers);
    }

    @FXML
    public void añadirFavPulseraDestello() {
        añadirFav(btnFavPulseraDestello, PulseraDestello);
    }

    @FXML
    public void añadirFavCollarPearl() {
        añadirFav(btnFavCollarPearl, CollarPearl);
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
    @FXML

    public Nodo<Producto> cabezaCarrito;
    public Nodo<Producto> cabezaFavoritos;
    public Pane PaneCarrito;
    public Pane panelPostCarrito2;
    public ScrollPane ScrollPaneCarrito;
    public Nodo<Producto> inicioHistorial;
    public Label lblCantidadCarrito;
    public Pane panelMenu;
    public Pane panelHistorial;
    public Pane panelPedidos;
    public ScrollPane scrollHistorial;
    public Pane panelDeseos;
    public ScrollPane scrollDeseos;
    public static final Map<Producto, ToggleButton> productoToToggle = new HashMap<>();
    public Image coraLleno = new Image(getClass().getResourceAsStream("/Imagenes/coraLleno.png"));
    public Button btnMenu;
    public Button btnDeseos;
    public Button btnCarrito;
}
