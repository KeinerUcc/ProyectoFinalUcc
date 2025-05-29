/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package Vista;

import Controlador.ControladorLogin;
import Controlador.PilaUsuarios;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/login.fxml")); 
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Imagenes/LOGO GOLDSTYLE.png"))); 

            primaryStage.setTitle("Tienda Golden Style");
            primaryStage.setScene(scene);
            primaryStage.show();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}



