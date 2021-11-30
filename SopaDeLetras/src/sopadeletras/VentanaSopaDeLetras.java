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
    ArrayList<Casilla> casillas = new ArrayList<>();
    ArrayList<BotonControl> botonesControl = new ArrayList<>();

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
        int f = 1;
        int c = 1;
        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                System.out.println(VentanaDimensionesController.filas.get(i).get(j));
                String[] pala = VentanaDimensionesController.filas.get(i).get(j).split("-");
                if (pala[1].equals("s")) {
                    Casilla btn1 = new Casilla(pala[0], Integer.parseInt(pala[2]), Integer.parseInt(pala[3]));
                    btn1.setEsSolucion(true);
                    btn1.setMinSize(30, 30);
                    btn1.setStyle("-fx-text-fill: White; -fx-font: bold italic 9pt Arial; -fx-background-color: #454545;");
                    cuadriculas.add(btn1, j, i);
                    casillas.addLast(btn1);
                } else if (pala[1].equals("n")) {
                    for (String pala1 : pala) {
                        //System.out.println(pala1);
                    }
                    Casilla btn1 = new Casilla(pala[0], Integer.parseInt(pala[2]), Integer.parseInt(pala[3]));
                    btn1.setMinSize(30, 30);
                    if (mostrarSolucion) {
                        btn1.setStyle("-fx-text-fill: #808B96 ; -fx-font: bold italic 9pt Arial; -fx-background-color: #454545;");
                    } else {
                        btn1.setStyle("-fx-text-fill: White; -fx-font: bold italic 9pt Arial; -fx-background-color: #454545;");
                    }
                    cuadriculas.add(btn1, j, i);
                    casillas.addLast(btn1);
                } else if (pala[1].equals("b")) {
                    Label l = new Label("");
                    l.setMinSize(30, 30);
                    cuadriculas.add(l, j, i);
                } else if (pala[0].equals("lbl")) {
                    Label l = new Label(pala[1]);
                    l.setMinSize(30, 30);
                    cuadriculas.add(l, j, i);
                } else if (pala[0].equals("añadirColumna")) {
                    BotonControl btn1 = new BotonControl("+", Integer.parseInt(pala[1]), "añadirColumna");
                    btn1.setMinSize(30, 30);
                    cuadriculas.add(btn1, j, i);
                } else if (pala[0].equals("añadirFila")) {
                    BotonControl btn1 = new BotonControl("+", Integer.parseInt(pala[1]), "añadirFila");
                    btn1.setMinSize(30, 30);
                    cuadriculas.add(btn1, j, i);
                } else if (pala[0].equals("quitarColumna")) {
                    BotonControl btn1 = new BotonControl("-", Integer.parseInt(pala[1]), "quitarColumna");
                    btn1.setMinSize(30, 30);
                    cuadriculas.add(btn1, j, i);
                } else if (pala[0].equals("quitarFila")) {
                    BotonControl btn1 = new BotonControl("-", Integer.parseInt(pala[1]), "quitarFila");
                    btn1.setMinSize(30, 30);
                    cuadriculas.add(btn1, j, i);
                } else if (pala[0].equals("desplazarFilaDerecha")) {
                    BotonControl btn1 = new BotonControl("R", Integer.parseInt(pala[1]), "desplazarFilaDerecha");
                    btn1.setMinSize(30, 30);
                    cuadriculas.add(btn1, j, i);
                } else if (pala[0].equals("desplazarFilaIzquierda")) {
                    BotonControl btn1 = new BotonControl("L", Integer.parseInt(pala[1]), "desplazarFilaIzquierda");
                    btn1.setMinSize(30, 30);
                    cuadriculas.add(btn1, j, i);
                } else if(pala[0].equals("desplazarColumnaArriba")){
                    BotonControl btn1 = new BotonControl("U",Integer.parseInt(pala[1]),"desplazarColumnaArriba");
                    btn1.setMinSize(30, 30);
                    cuadriculas.add(btn1, j, i);
                } else if(pala[0].equals("desplazarColumnaAbajo")){
                    BotonControl btn1 = new BotonControl("D",Integer.parseInt(pala[1]),"desplazarColumnaAbajo");
                    btn1.setMinSize(30, 30);
                    cuadriculas.add(btn1, j, i);
                }
                /*if (mostrarSolucion) {
                    if (btn1.isEsSolucion()) {
                        btn1.setStyle("-fx-text-fill: White; -fx-font: bold italic 9pt Arial; -fx-background-color: #454545;");
                    } else {
                        btn1.setStyle("-fx-text-fill: #808B96 ; -fx-font: bold italic 9pt Arial; -fx-background-color: #454545;");
                    }
                } else {
                    btn1.setStyle("-fx-text-fill: White; -fx-font: bold italic 9pt Arial; -fx-background-color: #454545;");
                }*/

                //AÑADIR FORMATO
                c++;

                f++;
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
