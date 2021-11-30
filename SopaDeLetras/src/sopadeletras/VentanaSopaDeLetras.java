/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sopadeletras;

import Estructuras.ArrayList;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author leonardo
 */
public class VentanaSopaDeLetras implements Initializable {

    Scene sceneSopa;
    BorderPane root = new BorderPane();
    Button retroceder = new Button("Retroceder");
    Button btnNuevo = new Button("Nuevo");
    Button mostrar = new Button("Mostrar");
    Button ayuda = new Button("Ayuda");
    Label info = new Label("Ejemplo: elimine la fila 2");
    Button btnVolver = new Button("Volver");

    Stage stage = new Stage();
    Scene scene2;
    Parent root2;

    public VentanaSopaDeLetras() {
        buildSopa();
        btnNuevo.setOnAction(this::btnNuevo);
        btnVolver.setOnAction(this::volver);
        mostrar.setOnAction(this::mostrar);
        //palabrasBuscarSopa.clear();

    }

    public void buildSopa() {

        retroceder.setMinSize(75, 45);
        btnNuevo.setMinSize(75, 45);
        mostrar.setMinSize(75, 45);
        ayuda.setMinSize(75, 45);
        VBox vBoxOp = new VBox();
        vBoxOp.getChildren().add(retroceder);
        vBoxOp.getChildren().add(btnNuevo);
        vBoxOp.getChildren().add(mostrar);
        vBoxOp.getChildren().add(ayuda);
        vBoxOp.getChildren().add(info);
        vBoxOp.setAlignment(Pos.CENTER);
        vBoxOp.setSpacing(20);

        HBox puntuacion = new HBox();
        Label lblPuntuacion = new Label("Puntuacion:  ");
        lblPuntuacion.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
        puntuacion.getChildren().add(lblPuntuacion);

        agregandoCuadriculas(false);

        HBox opBotton = new HBox();
        btnVolver.setMinSize(75, 40);
        opBotton.getChildren().add(btnVolver);
        opBotton.setAlignment(Pos.CENTER);

        root.setTop(puntuacion);
        root.setRight(vBoxOp);

        root.setBottom(opBotton);
        sceneSopa = new Scene(root, 850, 850);
    }

    public void agregandoCuadriculas(boolean mostrarSolucion) {
        addPalabrasBuscar();
        //agregando cuadriculas a la sopa de letras
        GridPane cuadriculas = new GridPane();
        int nFilas = VentanaDimensionesController.filas.size();
        int nColumnas = VentanaDimensionesController.columnas.size();
        System.out.println("FILAS:  " + nFilas + "COLUMNAS:  " + nColumnas);
        cuadriculas.setHgap(2);
        cuadriculas.setVgap(2);
        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                String[] pala = VentanaDimensionesController.filas.get(i).get(j).split("-");
                Casilla btn1 = new Casilla(pala[0], i, j);
                if (pala[1].equals("s")) {
                    btn1.setEsSolucion(true);
                }
                /*if(){
                    si el pintado es true, pintar de azul el background
                }else{
                    btn1.setStyle("-fx-text-fill: White; -fx-font: bold italic 10pt Arial; -fx-background-color: #454545;");
  
                }*/
                if (mostrarSolucion) {
                    if (btn1.isEsSolucion()) {
                        btn1.setStyle("-fx-text-fill: White; -fx-font: bold italic 9pt Arial; -fx-background-color: #454545;");
                    } else {
                        btn1.setStyle("-fx-text-fill: #808B96 ; -fx-font: bold italic 9pt Arial; -fx-background-color: #454545;");
                    }
                } else {
                    btn1.setStyle("-fx-text-fill: White; -fx-font: bold italic 9pt Arial; -fx-background-color: #454545;");
                }
                btn1.setMinSize(30, 30);
                //btn1.setMaxSize(20, 20);
                cuadriculas.add(btn1, j, i);
            }
        }
        cuadriculas.setAlignment(Pos.CENTER);
        root.setCenter(cuadriculas);
    }

    public void addPalabrasBuscar() {
        VBox opciones = new VBox();
        Label pal = new Label("Palabras  ");
        pal.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
        opciones.getChildren().add(pal);
        System.out.println("SIZE " + VentanaDimensionesController.palabrasBuscar.size());
        for (int ix = 0; ix < VentanaDimensionesController.palabrasBuscar.size(); ix++) {
            Label lbl = new Label(VentanaDimensionesController.palabrasBuscar.get(ix));
            lbl.setStyle("-fx-text-fill: #34495E; -fx-font: italic 15pt Arial;");
            opciones.getChildren().add(lbl);
        }
        opciones.setAlignment(Pos.CENTER);
        opciones.setSpacing(20);
        root.setLeft(opciones);
    }

    public void btnNuevo(ActionEvent e) {
        VentanaDimensionesController vdc = new VentanaDimensionesController();
        vdc.construccionSopa(e);
        agregandoCuadriculas(false);

        //palabrasBuscarSopa.clear();
    }

    private void volver(ActionEvent event) {
        try {
            root2 = FXMLLoader.load(getClass().getResource("VentanaDimensiones.fxml"));
        } catch (IOException ex) {
            System.out.println(ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene2 = new Scene(root2);
        stage.setScene(scene2);
        stage.show();
        VentanaDimensionesController.valorSopa = true;
    }

    private void mostrar(ActionEvent event) {
        agregandoCuadriculas(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
