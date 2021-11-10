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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author leonardo
 */
public class VentanaDimensionesController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private int nFilas;
    private int nColumnas;
    private ArrayList<CircularLinkedList> filas;
    private ArrayList<CircularLinkedList> columnas;
    private ArrayList<ArrayList> diagonal1;
    private ArrayList<ArrayList> diagonal2;
    @FXML
    private ChoiceBox<String> choiceBoxDimensiones;

    private String[] dimensiones = {"7x7", "8x8", "9x9", "10x10", "11x11", "12x12",
        "13x13", "14x14", "15x15", "16x16", "17x17", "18x18", "19x19", "20x20"};
    @FXML
    private Label labelTamaño;

    @FXML
    private Button btnEmpezar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBoxDimensiones.getItems().addAll(dimensiones);
        choiceBoxDimensiones.setOnAction(this::getDimension);
    }

    public void getDimension(ActionEvent event) {
        String[] dim = choiceBoxDimensiones.getValue().split("x");
        nFilas = Integer.valueOf(dim[0]);
        nColumnas = Integer.valueOf(dim[1]);
        System.out.println(nFilas + "  " + nColumnas);
    }
    @FXML
    private void switchToSopaLetras(ActionEvent event) throws IOException {
        //CREANDO LA SOPA DE LETRAS
        creacionDeSopa();
        //CAMBIANDO DE ESCENA AL JUEGO
        root = FXMLLoader.load(getClass().getResource("VentanaSopaDeLetras.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void creacionDeSopa() {
        //banco de letras de ejemplo, crear un metodo cargar banco
        //que lea un archivo y cargue las palabras en un arraylist
        ArrayList<String> banco = cargarBanco();
        String[] bancoEjemplo = {"uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez"};
        
    }
    
    private ArrayList cargarBanco(){
        ArrayList<String> banco = new ArrayList<>();
        //banco de capitales del mundo por ejemplo
        return banco;
    }

    //getters para obtener en ventana sopa de letras para que sean ubicados
    //como una sopa de letras
    public ArrayList<CircularLinkedList> getFilas() {
        return filas;
    }

    public ArrayList<CircularLinkedList> getColumnas() {
        return columnas;
    }

    public ArrayList<ArrayList> getDiagonal1() {
        return diagonal1;
    }

    public ArrayList<ArrayList> getDiagonal2() {
        return diagonal2;
    }

    
    

}
