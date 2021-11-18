/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sopadeletras;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author leonardo
 */
public class VentanaSopaDeLetras implements Initializable {

    Scene sceneSopa;
    BorderPane root = new BorderPane();
    Button[] buttons;

    public VentanaSopaDeLetras() {
        buildSopa();
    }

    public void buildSopa() {
        
        GridPane cuadriculas = new GridPane();
        VBox opciones = new VBox();
        
        Button ayuda = new Button("Ayuda");
        ayuda.setMinSize(75, 50);
        HBox hBoxAyuda= new HBox();
        hBoxAyuda.getChildren().add(ayuda);
        hBoxAyuda.setAlignment(Pos.TOP_CENTER);
        
        HBox puntuacion =  new HBox();
        Label lblPuntuacion = new Label("Puntuacion:  ");
        lblPuntuacion.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
        puntuacion.getChildren().add(lblPuntuacion);
        
        Label pal = new Label("Palabras  ");
        pal.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
        cuadriculas.setHgap(2);
        cuadriculas.setVgap(2);
        //agregando cuadriculas a la sopa de letras
        int nFilas = VentanaDimensionesController.columnas.size();
        int nColumnas = VentanaDimensionesController.filas.size();
        for(int i=0; i<nFilas; i++){
            for(int j=0; j<nColumnas; j++){
                 Button btn1 = new Button("x");
                 btn1.setStyle("-fx-text-fill: White; -fx-font: bold italic 10pt Arial; -fx-background-color: dimgray;");
                 cuadriculas.add(btn1, i, j);
            }
        }
        opciones.getChildren().add(pal);
        opciones.setAlignment(Pos.CENTER);
        opciones.setSpacing(20);
        cuadriculas.setAlignment(Pos.CENTER);
        root.setTop(puntuacion);
        root.setBottom(hBoxAyuda);
        root.setCenter(cuadriculas);
        root.setLeft(opciones);
        sceneSopa = new Scene(root, 1024, 850);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
