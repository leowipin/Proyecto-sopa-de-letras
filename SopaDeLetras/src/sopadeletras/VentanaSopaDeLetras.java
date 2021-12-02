/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sopadeletras;

import Estructuras.ArrayList;
import Estructuras.CircularLinkedList;
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
import static sopadeletras.VentanaDimensionesController.filas;

/**
 * FXML Controller class
 *
 * @author leonardo
 */
public class VentanaSopaDeLetras implements Initializable {

    Stage window;
    Scene sceneSopa;
    BorderPane root = new BorderPane();
    Button restablecer = new Button("Restablecer");
    Button btnNuevo = new Button("Nuevo");
    Button mostrar = new Button("Mostrar");
    Button ayuda = new Button("Ayuda");
    Label info = new Label("");
    Button btnVolver = new Button("Volver");
    ArrayList<Integer> posiciones = new ArrayList<>();
    ArrayList<Integer> noPosiciones = new ArrayList<>();
    ArrayList<ArrayList<Casilla>> casillas = new ArrayList<>();
    ArrayList<BotonControl> botonesControl = new ArrayList<>();
    VentanaDimensionesController vdc = new VentanaDimensionesController();
    Label puntaje = new Label("0");
    GridPane cuadriculas = new GridPane();
    Stage stage = new Stage();
    Scene scene2;
    Parent root2;
    Label gameOver = new Label("");
    boolean agregarAyuda=false;

    public VentanaSopaDeLetras() {
        buildSopa();
        btnNuevo.setOnAction(this::btnNuevo);
        btnVolver.setOnAction(this::volver);
        mostrar.setOnAction(this::mostrar);
        restablecer.setOnAction(this::restablecer);
        ayuda.setOnAction(e->{
            agregarAyuda=true;
            restablecer(e);
        });

        //palabrasBuscarSopa.clear();
    }

    public void buildSopa() {

        restablecer.setMinSize(75, 45);
        btnNuevo.setMinSize(75, 45);
        mostrar.setMinSize(75, 45);
        ayuda.setMinSize(75, 45);
        VBox vBoxOp = new VBox();
        vBoxOp.getChildren().add(restablecer);
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
        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
        puntuacion.getChildren().add(puntaje);
        puntuacion.getChildren().add(gameOver);

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

    public void accionBotonC(BotonControl bt) {
        VentanaDimensionesController vdc = new VentanaDimensionesController();
        vdc.agregarFila(bt.numero, "letra");
        vdc.creacionS();
        agregandoCuadriculas(false);
    }

    public void agregandoCuadriculas(boolean mostrarSolucion) {

        addPalabrasBuscar();
        vdc.agregarControles();
        //agregando cuadriculas a la sopa de letras
        int nFilas = VentanaDimensionesController.filas.size();
        int nColumnas = VentanaDimensionesController.columnas.size();
        System.out.println("FILAS:  " + nFilas + "COLUMNAS:  " + nColumnas);
        cuadriculas.setHgap(2);
        cuadriculas.setVgap(2);
        int f = 1;
        int c = 1;
        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                String[] pala = VentanaDimensionesController.filas.get(i).get(j).split("-");
                if (pala[1].equals("s")) {
                    Casilla btn1 = new Casilla(pala[0], i - 3, j - 3);
                    btn1.setEsSolucion(true);
                    btn1.setMinSize(30, 30);
                    btn1.setStyle("-fx-text-fill: White; -fx-font: bold italic 9pt Arial; -fx-background-color: #454545;");
                    btn1.setOnAction(e -> {
                        posiciones.addLast(btn1.posicionx);
                        posiciones.addLast(btn1.posiciony);
                        if (posiciones.size() == 4) {
                            int pos0 = posiciones.get(0);
                            int pos1 = posiciones.get(1);
                            int pos2 = posiciones.get(2);
                            int pos3 = posiciones.get(3);
                            posiciones.clear();
                            System.out.println("IF SIZE=4");
                            if (pos0 == pos2) {//en fila
                                System.out.println("IF 2 EN FILA");
                                if (pos3 > pos1) {//de iz a der
                                    System.out.println("DE IZQ A DER");
                                    String palabra = "";
                                    for (int ix = pos1; ix <= pos3; ix++) {
                                        palabra = palabra + casillas.get(pos0).get(ix).getContenido();
                                    }
                                    System.out.println("PALABRA: " + palabra);
                                    System.out.println(VentanaDimensionesController.palabrasBuscar);
                                    if (VentanaDimensionesController.palabrasBuscar.contains(palabra)) {
                                        int sze = palabra.length();
                                        int punt = Integer.parseInt(puntaje.getText());
                                        puntaje.setText(String.valueOf(punt + sze));
                                        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
                                        System.out.println("PINTADA");
                                        for (int ix = pos1; ix <= pos3; ix++) {
                                            casillas.get(pos0).get(ix).setStyle("-fx-text-fill: White; -fx-font: bold italic 9pt Arial; -fx-background-color: #154360;");

                                            for (BotonControl btcc : botonesControl) {
                                                if (btcc.numero == pos0 && btcc.isHorizontal) {
                                                    btcc.bloqueado = true;
                                                    btcc.setDisable(true);
                                                } else if (btcc.numero == ix && btcc.isVertical) {
                                                    btcc.bloqueado = true;
                                                    btcc.setDisable(true);
                                                }
                                            }
                                        }
                                    }else{
                                        int op=Integer.parseInt(puntaje.getText());
                                        puntaje.setText(String.valueOf(op-4));
                                        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
                                    }
                                } else if (pos3 < pos1) {// de der a iz
                                    String palabra = "";
                                    for (int ix = pos1; ix >= pos3; ix--) {
                                        palabra = palabra + casillas.get(pos0).get(ix).getContenido();
                                    }
                                    if (VentanaDimensionesController.palabrasBuscar.contains(palabra)) {
                                        int sze = palabra.length();
                                        int punt = Integer.parseInt(puntaje.getText());
                                        puntaje.setText(String.valueOf(punt + sze));
                                        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
                                        for (int ix = pos1; ix >= pos3; ix--) {
                                            casillas.get(pos0).get(ix).setStyle("-fx-text-fill: White; -fx-font: bold italic 9pt Arial; -fx-background-color: #154360;");
                                            for (BotonControl btcc : botonesControl) {
                                                if (btcc.numero == pos0 && btcc.isHorizontal) {
                                                    btcc.bloqueado = true;
                                                    btcc.setDisable(true);
                                                } else if (btcc.numero == ix && btcc.isVertical) {
                                                    btcc.bloqueado = true;
                                                    btcc.setDisable(true);
                                                }
                                            }
                                        }
                                    }else{
                                        int op=Integer.parseInt(puntaje.getText());
                                        puntaje.setText(String.valueOf(op-4));
                                        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
                                    }
                                }
                            } else if (pos1 == pos3) {
                                if (pos2 > pos0) {//de arrib a aba
                                    String palabra = "";
                                    for (int ix = pos0; ix <= pos2; ix++) {
                                        palabra = palabra + casillas.get(ix).get(pos1).getContenido();
                                    }
                                    System.out.println("PALABRA: " + palabra);
                                    System.out.println(VentanaDimensionesController.palabrasBuscar);
                                    if (VentanaDimensionesController.palabrasBuscar.contains(palabra)) {
                                        int sze = palabra.length();
                                        int punt = Integer.parseInt(puntaje.getText());
                                        puntaje.setText(String.valueOf(punt + sze));
                                        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
                                        System.out.println("PINTADA");
                                        for (int ix = pos0; ix <= pos2; ix++) {
                                            casillas.get(ix).get(pos1).setStyle("-fx-text-fill: White; -fx-font: bold italic 9pt Arial; -fx-background-color: #154360;");
                                            for (BotonControl btcc : botonesControl) {
                                                if (btcc.numero == pos1 && btcc.isVertical) {
                                                    btcc.bloqueado = true;
                                                    btcc.setDisable(true);
                                                } else if (btcc.numero == ix && btcc.isHorizontal) {
                                                    btcc.bloqueado = true;
                                                    btcc.setDisable(true);
                                                }
                                            }
                                        }
                                    }else{
                                        int op=Integer.parseInt(puntaje.getText());
                                        puntaje.setText(String.valueOf(op-4));
                                        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
                                    }
                                } else if (pos2 < pos0) {// de der a iz
                                    String palabra = "";
                                    for (int ix = pos0; ix >= pos2; ix--) {
                                        palabra = palabra + casillas.get(ix).get(pos1).getContenido();
                                    }
                                    if (VentanaDimensionesController.palabrasBuscar.contains(palabra)) {
                                        int sze = palabra.length();
                                        int punt = Integer.parseInt(puntaje.getText());
                                        puntaje.setText(String.valueOf(punt + sze));
                                        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
                                        for (int ix = pos0; ix >= pos2; ix--) {
                                            casillas.get(ix).get(pos1).setStyle("-fx-text-fill: White; -fx-font: bold italic 9pt Arial; -fx-background-color: #154360;");
                                            for (BotonControl btcc : botonesControl) {
                                                if (btcc.numero == pos1 && btcc.isHorizontal) {
                                                    btcc.bloqueado = true;
                                                    btcc.setDisable(true);
                                                } else if (btcc.numero == ix && btcc.isVertical) {
                                                    btcc.bloqueado = true;
                                                    btcc.setDisable(true);
                                                }
                                            }
                                        }
                                    }else{
                                        int op=Integer.parseInt(puntaje.getText());
                                        puntaje.setText(String.valueOf(op-4));
                                        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
                                    }
                                }
                            } else if (pos0 - pos2 == pos1 - pos3) {//es diaganal1
                                if (pos2 + pos3 > pos0 + pos1) {// de arriba a abajo
                                    String pal = "";
                                    for (int ix = pos0; ix <= pos2; ix++) {
                                        pal = pal + casillas.get(ix).get(ix + (pos1 - pos0)).getContenido();
                                    }
                                    if (VentanaDimensionesController.palabrasBuscar.contains(pal)) {
                                        int sze = pal.length();
                                        int punt = Integer.parseInt(puntaje.getText());
                                        puntaje.setText(String.valueOf(punt + sze));
                                        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
                                        for (int ix = pos0; ix <= pos2; ix++) {
                                            casillas.get(ix).get(ix + (pos1 - pos0)).setStyle("-fx-text-fill: White; -fx-font: bold italic 9pt Arial; -fx-background-color: #154360;");
                                            for (BotonControl bc : botonesControl) {
                                                if (bc.numero == ix && bc.isHorizontal) {
                                                    bc.bloqueado = true;
                                                    bc.setDisable(true);
                                                } else if (bc.numero == ix + (pos1 - pos0) && bc.isVertical) {
                                                    bc.bloqueado = true;
                                                    bc.setDisable(true);
                                                }
                                            }
                                        }
                                    }else{
                                        int op=Integer.parseInt(puntaje.getText());
                                        puntaje.setText(String.valueOf(op-4));
                                        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
                                    }

                                } else if (pos0 + pos1 > pos2 + pos3) { //de abajo a arriba
                                    String pal = "";
                                    for (int ix = pos0; ix >= pos2; ix--) {
                                        pal = pal + casillas.get(ix).get(ix + (pos1 - pos0));
                                    }
                                    if (VentanaDimensionesController.palabrasBuscar.contains(pal)) {
                                        int sze = pal.length();
                                        int punt = Integer.parseInt(puntaje.getText());
                                        puntaje.setText(String.valueOf(punt + sze));
                                        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
                                        for (int ix = pos0; ix >= pos2; ix--) {
                                            casillas.get(ix).get(ix + (pos1 - pos0)).setStyle("-fx-text-fill: White; -fx-font: bold italic 9pt Arial; -fx-background-color: #154360;");

                                            for (BotonControl bc : botonesControl) {
                                                if (bc.numero == ix && bc.isHorizontal) {
                                                    bc.bloqueado = true;
                                                    bc.setDisable(true);
                                                } else if (bc.numero == ix + (pos1 - pos0) && bc.isVertical) {
                                                    bc.bloqueado = true;
                                                    bc.setDisable(true);
                                                }
                                            }
                                        }
                                    } else{
                                        int op=Integer.parseInt(puntaje.getText());
                                        puntaje.setText(String.valueOf(op-4));
                                        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
                                    }
                                }
                            } else if (pos0 + pos1 == pos2 + pos3) {// es diagonal2
                                if (pos0 > pos2 && pos1 < pos3) {//abajo a arriba
                                    String pal = "";
                                    for (int ix = pos0; ix >= pos2; ix--) {
                                        pal = pal + casillas.get(ix).get(ix - (pos0 - pos1));
                                    }
                                    if (VentanaDimensionesController.palabrasBuscar.contains(pal)) {
                                        int sze = pal.length();
                                        int punt = Integer.parseInt(puntaje.getText());
                                        puntaje.setText(String.valueOf(punt + sze));
                                        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
                                        for (int ix = pos0; ix >= pos2; ix--) {
                                            casillas.get(ix).get(ix - (pos0 - pos1)).setStyle("-fx-text-fill: White; -fx-font: bold italic 9pt Arial; -fx-background-color: #154360;");
                                            for (BotonControl bc : botonesControl) {
                                                if (bc.numero == ix && bc.isHorizontal) {
                                                    bc.bloqueado = true;
                                                    bc.setDisable(true);
                                                } else if (bc.numero == ix - (pos0 - pos1) && bc.isVertical) {
                                                    bc.bloqueado = true;
                                                    bc.setDisable(true);
                                                }
                                            }
                                        }
                                    }else{
                                        int op=Integer.parseInt(puntaje.getText());
                                        puntaje.setText(String.valueOf(op-4));
                                        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
                                    }
                                } else if (pos0 < pos2 && pos3 < pos1) {//arriba abajo
                                    String pal = "";
                                    for (int ix = pos0; ix <= pos2; ix++) {
                                        pal = pal + casillas.get(ix).get(ix + (pos1 - pos0));
                                    }
                                    if (VentanaDimensionesController.palabrasBuscar.contains(pal)) {
                                        int sze = pal.length();
                                        int punt = Integer.parseInt(puntaje.getText());
                                        puntaje.setText(String.valueOf(punt + sze));
                                        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
                                        for (int ix = pos0; ix <= pos2; ix++) {
                                            casillas.get(ix).get(ix + (pos1 - pos0)).setStyle("-fx-text-fill: White; -fx-font: bold italic 9pt Arial; -fx-background-color: #154360;");
                                            for (BotonControl bc : botonesControl) {
                                                if (bc.numero == ix && bc.isHorizontal) {
                                                    bc.bloqueado = true;
                                                    bc.setDisable(true);
                                                } else if (bc.numero == ix + (pos1 - pos0) && bc.isVertical) {
                                                    bc.bloqueado = true;
                                                    bc.setDisable(true);
                                                }
                                            }
                                        }
                                    }else{
                                        int op=Integer.parseInt(puntaje.getText());
                                        puntaje.setText(String.valueOf(op-4));
                                        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
                                    }

                                }
                            }
                        }

                        System.out.println("Posicionx: " + btn1.posicionx + " Posiciony: " + btn1.posiciony);
                    });
                    cuadriculas.add(btn1, j, i);
                    if (i > 2 && i < nFilas - 3) {
                        if (casillas.size() <= i - 3) {
                            ArrayList<Casilla> cas = new ArrayList<>();
                            cas.addLast(btn1);
                            casillas.addLast(cas);
                        } else {
                            casillas.get(i - 3).addLast(btn1);
                        }
                    }

                } else if (pala[1].equals("n")) {
                    Casilla btn1 = new Casilla(pala[0], i - 3, j - 3);
                    btn1.setMinSize(30, 30);
                    if (mostrarSolucion) {
                        btn1.setStyle("-fx-text-fill: #808B96 ; -fx-font: bold italic 9pt Arial; -fx-background-color: #454545;");
                    } else {
                        btn1.setStyle("-fx-text-fill: White; -fx-font: bold italic 9pt Arial; -fx-background-color: #454545;");
                    }
                    btn1.setOnAction(e->{
                        noPosiciones.addLast(btn1.posicionx);
                        noPosiciones.addLast(btn1.posiciony);
                        if(noPosiciones.size()==4){
                            noPosiciones.clear();
                            int op=Integer.parseInt(puntaje.getText());
                            puntaje.setText(String.valueOf(op-4));
                            puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
                        }
                    });

                    cuadriculas.add(btn1, j, i);
                    if (i > 2 && i < nFilas - 3) {
                        if (casillas.size() <= i - 3) {
                            System.out.println(i - 3);
                            ArrayList<Casilla> cas = new ArrayList<>();
                            cas.addLast(btn1);
                            casillas.addLast(cas);
                        } else {
                            casillas.get(i - 3).addLast(btn1);
                        }
                    }
                } else if (pala[1].equals("b")) {
                    Label l = new Label("");
                    l.setMinSize(30, 30);
                    cuadriculas.add(l, j, i);
                } else if (pala[0].equals("lbl")) {
                    Label l = new Label(pala[1]);
                    l.setMinSize(30, 30);
                    cuadriculas.add(l, j, i);
                } else if (pala[0].equals("añadirColumna")) {
                    System.out.println(j - 3);
                    BotonControl btn1 = new BotonControl("+", j - 3, "añadirColumna", true, false);
                    VentanaDimensionesController.agregarPila = true;
                    btn1.setMinSize(30, 30);
                    btn1.setOnAction((e) -> {
                        VentanaDimensionesController.indice = btn1.numero;
                        VentanaDimensionesController.filas.clear();
                        VentanaDimensionesController.columnas.clear();
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.filasSinC) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.filas.addLast(nuevo);
                        }
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.columnasSinC) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.columnas.addLast(nuevo);
                        }
                        vdc = new VentanaDimensionesController();
                        vdc.agregarColumna(btn1.numero, "letra");
                        VentanaDimensionesController.filasSinC.clear();
                        VentanaDimensionesController.columnasSinC.clear();
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.filas) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.filasSinC.addLast(nuevo);
                        }
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.columnas) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.columnasSinC.addLast(nuevo);
                        }
                        //vdc.agregarControles();
                        cuadriculas.getChildren().clear();
                        casillas.clear();
                        agregandoCuadriculas(mostrarSolucion);
                    });
                    cuadriculas.add(btn1, j, i);
                    botonesControl.addLast(btn1);
                } else if (pala[0].equals("añadirFila")) {
                    System.out.println(i - 3);
                    BotonControl btn1 = new BotonControl("+", i - 3, "añadirFila", false, true);
                    btn1.setMinSize(30, 30);
                    VentanaDimensionesController.agregarPila = true;
                    btn1.setOnAction((e) -> {
                        VentanaDimensionesController.indice = btn1.numero;
                        VentanaDimensionesController.filas.clear();
                        VentanaDimensionesController.columnas.clear();
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.filasSinC) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.filas.addLast(nuevo);
                        }
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.columnasSinC) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.columnas.addLast(nuevo);
                        }
                        vdc = new VentanaDimensionesController();
                        vdc.agregarFila(btn1.numero, "letra");
                        VentanaDimensionesController.filasSinC.clear();
                        VentanaDimensionesController.columnasSinC.clear();
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.filas) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.filasSinC.addLast(nuevo);
                        }
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.columnas) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.columnasSinC.addLast(nuevo);
                        }
                        //vdc.agregarControles();
                        casillas.clear();
                        cuadriculas.getChildren().clear();
                        agregandoCuadriculas(mostrarSolucion);
                    });
                    cuadriculas.add(btn1, j, i);
                    botonesControl.addLast(btn1);
                } else if (pala[0].equals("quitarColumna")) {
                    BotonControl btn1 = new BotonControl("-", Integer.parseInt(pala[1]), "quitarColumna", true, false);
                    btn1.setMinSize(30, 30);
                    btn1.setOnAction((e) -> {
                        VentanaDimensionesController.indice = btn1.numero;
                        VentanaDimensionesController.filas.clear();
                        VentanaDimensionesController.columnas.clear();
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.filasSinC) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.filas.addLast(nuevo);
                        }
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.columnasSinC) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.columnas.addLast(nuevo);
                        }
                        vdc = new VentanaDimensionesController();
                        vdc.quitarColumna(btn1.numero);
                        VentanaDimensionesController.filasSinC.clear();
                        VentanaDimensionesController.columnasSinC.clear();
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.filas) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.filasSinC.addLast(nuevo);
                        }
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.columnas) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.columnasSinC.addLast(nuevo);
                        }
                        //vdc.agregarControles();
                        casillas.clear();
                        cuadriculas.getChildren().clear();
                        agregandoCuadriculas(mostrarSolucion);
                    });
                    cuadriculas.add(btn1, j, i);
                    botonesControl.addLast(btn1);
                } else if (pala[0].equals("quitarFila")) {
                    BotonControl btn1 = new BotonControl("-", Integer.parseInt(pala[1]), "quitarFila", false, true);
                    btn1.setMinSize(30, 30);
                    btn1.setOnAction((e) -> {
                        VentanaDimensionesController.indice = btn1.numero;
                        VentanaDimensionesController.filas.clear();
                        VentanaDimensionesController.columnas.clear();
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.filasSinC) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.filas.addLast(nuevo);
                        }
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.columnasSinC) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.columnas.addLast(nuevo);
                        }
                        vdc = new VentanaDimensionesController();
                        vdc.quitarFila(btn1.numero);
                        VentanaDimensionesController.filasSinC.clear();
                        VentanaDimensionesController.columnasSinC.clear();
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.filas) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.filasSinC.addLast(nuevo);
                        }
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.columnas) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.columnasSinC.addLast(nuevo);
                        }
                        //vdc.agregarControles();
                        casillas.clear();
                        cuadriculas.getChildren().clear();
                        agregandoCuadriculas(mostrarSolucion);
                    });

                    cuadriculas.add(btn1, j, i);
                    botonesControl.addLast(btn1);
                } else if (pala[0].equals("desplazarFilaDerecha")) {
                    BotonControl btn1 = new BotonControl("R", i - 3, "desplazarFilaDerecha", false, true);
                    btn1.setMinSize(30, 30);
                    btn1.setOnAction(e -> {
                        VentanaDimensionesController.filas.clear();
                        VentanaDimensionesController.columnas.clear();
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.filasSinC) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.filas.addLast(nuevo);
                        }
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.columnasSinC) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.columnas.addLast(nuevo);
                        }
                        vdc = new VentanaDimensionesController();
                        vdc.desplazarFilaDerecha(btn1.numero);
                        VentanaDimensionesController.filasSinC.clear();
                        VentanaDimensionesController.columnasSinC.clear();
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.filas) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.filasSinC.addLast(nuevo);
                        }
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.columnas) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.columnasSinC.addLast(nuevo);
                        }
                        //vdc.agregarControles();
                        casillas.clear();
                        cuadriculas.getChildren().clear();
                        agregandoCuadriculas(mostrarSolucion);
                    });

                    cuadriculas.add(btn1, j, i);
                    botonesControl.addLast(btn1);
                } else if (pala[0].equals("desplazarFilaIzquierda")) {
                    BotonControl btn1 = new BotonControl("L", Integer.parseInt(pala[1]), "desplazarFilaIzquierda", false, true);
                    btn1.setMinSize(30, 30);
                    btn1.setOnAction(e -> {
                        VentanaDimensionesController.filas.clear();
                        VentanaDimensionesController.columnas.clear();
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.filasSinC) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.filas.addLast(nuevo);
                        }
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.columnasSinC) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.columnas.addLast(nuevo);
                        }
                        vdc = new VentanaDimensionesController();
                        vdc.desplazarFilaIzquierda(btn1.numero);
                        VentanaDimensionesController.filasSinC.clear();
                        VentanaDimensionesController.columnasSinC.clear();
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.filas) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.filasSinC.addLast(nuevo);
                        }
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.columnas) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.columnasSinC.addLast(nuevo);
                        }
                        //vdc.agregarControles();
                        casillas.clear();
                        cuadriculas.getChildren().clear();
                        agregandoCuadriculas(mostrarSolucion);
                    });
                    botonesControl.addLast(btn1);
                    cuadriculas.add(btn1, j, i);
                } else if (pala[0].equals("desplazarColumnaArriba")) {
                    BotonControl btn1 = new BotonControl("U", Integer.parseInt(pala[1]), "desplazarColumnaArriba", true, false);
                    btn1.setMinSize(30, 30);
                    btn1.setOnAction(e -> {
                        VentanaDimensionesController.filas.clear();
                        VentanaDimensionesController.columnas.clear();
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.filasSinC) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.filas.addLast(nuevo);
                        }
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.columnasSinC) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.columnas.addLast(nuevo);
                        }
                        vdc = new VentanaDimensionesController();
                        vdc.desplazarColumnaArriba(btn1.numero);
                        VentanaDimensionesController.filasSinC.clear();
                        VentanaDimensionesController.columnasSinC.clear();
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.filas) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.filasSinC.addLast(nuevo);
                        }
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.columnas) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.columnasSinC.addLast(nuevo);
                        }
                        //vdc.agregarControles();
                        casillas.clear();
                        cuadriculas.getChildren().clear();
                        agregandoCuadriculas(mostrarSolucion);
                    });
                    cuadriculas.add(btn1, j, i);
                    botonesControl.addLast(btn1);
                } else if (pala[0].equals("desplazarColumnaAbajo")) {
                    BotonControl btn1 = new BotonControl("D", Integer.parseInt(pala[1]), "desplazarColumnaAbajo", true, false);
                    btn1.setMinSize(30, 30);
                    btn1.setOnAction(e -> {
                        VentanaDimensionesController.filas.clear();
                        VentanaDimensionesController.columnas.clear();
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.filasSinC) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.filas.addLast(nuevo);
                        }
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.columnasSinC) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.columnas.addLast(nuevo);
                        }
                        vdc = new VentanaDimensionesController();
                        vdc.desplazarColumnaAbajo(btn1.numero);
                        VentanaDimensionesController.filasSinC.clear();
                        VentanaDimensionesController.columnasSinC.clear();
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.filas) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.filasSinC.addLast(nuevo);
                        }
                        for (CircularLinkedList<String> fi : VentanaDimensionesController.columnas) {
                            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
                            for (String s : fi) {
                                nuevo.addLast(s);
                            }
                            VentanaDimensionesController.columnasSinC.addLast(nuevo);
                        }
                        //vdc.agregarControles();
                        casillas.clear();
                        cuadriculas.getChildren().clear();
                        agregandoCuadriculas(mostrarSolucion);
                    });
                    cuadriculas.add(btn1, j, i);
                    botonesControl.addLast(btn1);
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
        System.out.println(VentanaDimensionesController.pila);
        System.out.println(VentanaDimensionesController.instrucciones);
        /*for (BotonControl btc: botonesControl) {
            System.out.println(btc.numero);
        }*/

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
        VentanaDimensionesController.pila.clear();
        VentanaDimensionesController.instrucciones.clear();
        cuadriculas.getChildren().clear();
        vdc = new VentanaDimensionesController();
        VentanaDimensionesController.agregarPila = false;
        vdc.construccionSopa(e);
        casillas.clear();
        puntaje.setText("0");
        gameOver.setText("");
        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
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
        VentanaDimensionesController.agregarPila = false;
    }

    private void mostrar(ActionEvent event) {
        VentanaDimensionesController.filas.clear();
        VentanaDimensionesController.columnas.clear();
        for (CircularLinkedList<String> fi : VentanaDimensionesController.filasSinC) {
            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
            for (String s : fi) {
                nuevo.addLast(s);
            }
            VentanaDimensionesController.filas.addLast(nuevo);
        }
        for (CircularLinkedList<String> fi : VentanaDimensionesController.columnasSinC) {
            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
            for (String s : fi) {
                nuevo.addLast(s);
            }
            VentanaDimensionesController.columnas.addLast(nuevo);
        }
        //vdc.agregarControles();
        gameOver.setText("         GAME OVER");
        gameOver.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
        puntaje.setText("0");
        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
        casillas.clear();
        cuadriculas.getChildren().clear();
        agregandoCuadriculas(true);
    }

    public void restablecer(ActionEvent e) {
        VentanaDimensionesController.filas.clear();
        VentanaDimensionesController.columnas.clear();
        VentanaDimensionesController.filasSinC.clear();
        VentanaDimensionesController.columnasSinC.clear();
        for (CircularLinkedList<String> f : VentanaDimensionesController.filasInicial) {
            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
            for (String s : f) {
                nuevo.addLast(s);
            }
            VentanaDimensionesController.filas.addLast(nuevo);
        }
        for (CircularLinkedList<String> f : VentanaDimensionesController.columnasInicial) {
            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
            for (String s : f) {
                nuevo.addLast(s);
            }
            VentanaDimensionesController.columnas.addLast(nuevo);
        }
        for (CircularLinkedList<String> f : VentanaDimensionesController.filas) {
            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
            for (String s : f) {
                nuevo.addLast(s);
            }
            VentanaDimensionesController.filasSinC.addLast(nuevo);
        }
        for (CircularLinkedList<String> f : VentanaDimensionesController.columnas) {
            CircularLinkedList<String> nuevo = new CircularLinkedList<>();
            for (String s : f) {
                nuevo.addLast(s);
            }
            VentanaDimensionesController.columnasSinC.addLast(nuevo);
        }
        if(agregarAyuda){
            info.setText(VentanaDimensionesController.instruccionesCopia);
        } 
        if(!agregarAyuda){
            gameOver.setText("");
        }
        puntaje.setText("0");
        puntaje.setStyle("-fx-text-fill: Black; -fx-font: bold italic 15pt Arial;");
        cuadriculas.getChildren().clear();
        casillas.clear();
        agregarAyuda=false;
        agregandoCuadriculas(false);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
