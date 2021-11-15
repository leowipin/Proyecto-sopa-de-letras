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
import javafx.util.Pair;

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
    private int nFilasSolucion;
    private int nColumnasSolucion;
    private ArrayList<CircularLinkedList<String>> filas = new ArrayList<>();
    private ArrayList<CircularLinkedList<String>> columnas = new ArrayList<>();
    private ArrayList<ArrayList<String>> diagonal1 = new ArrayList<>();//creciente
    private ArrayList<ArrayList<String>> diagonal2 = new ArrayList<>();//decreciente
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
        choiceBoxDimensiones.setOnAction(this::construccionSopa);// se construye la sopa incial cuando se elige la dimension
    }

    public void construccionSopa(ActionEvent event) {
        // se obtiene las dimensiones elegidas en el choiceBox
        String[] dim = choiceBoxDimensiones.getValue().split("x");
        nFilas = Integer.valueOf(dim[0]);
        nColumnas = Integer.valueOf(dim[1]);
        if(!filas.isEmpty()){
            //si se elige denuevo otra dimension, hay que limpiar las estructuras
            filas.clear();
            columnas.clear();
            diagonal1.clear();
            diagonal2.clear();
        }
        dimensionDeSolucion();
        creacionSopaSolucion();

    }

    @FXML
    private void switchToSopaLetras(ActionEvent event) throws IOException {
        //CAMBIANDO DE ESCENA AL JUEGO
        root = FXMLLoader.load(getClass().getResource("VentanaSopaDeLetras.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //metodo a eliminar
    private void creacionDeSopa() {
        //banco de letras de ejemplo, crear un metodo cargar banco
        //que lea un archivo y cargue las palabras en un arraylist
        //ArrayList<String> banco = cargarBanco();
        dimensionDeSolucion();//la dimension que va tener nuestra solucion

        String[] bancoEjemplo = {"uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez"};
        ArrayList<String> bancoEj = new ArrayList<>();
        for (int i = 0; i < bancoEjemplo.length; i++) {
            bancoEj.addLast(bancoEjemplo[i]);
        }
        int numAl;
        String palabra;
        for (int i = 0; i < 3; i++) {// 4 palabras seran agregadas a la sopa usar if de for para agregar mas dependiendo del tamaño
            numAl = (int) (Math.random() * bancoEj.size());//numero aleatorio para elegir la palabra del banco
            palabra = bancoEj.get(numAl);
            numAl = (int) (Math.random() * bancoEj.size());//num aleatorio para elegir la posicion a ubicar

        }
    }

    private ArrayList cargarBanco() {
        ArrayList<String> banco = new ArrayList<>();
        //banco de capitales del mundo por ejemplo
        return banco;
    }
    //como en la sopa puede eliminarse o añadirse filas y columnas un maximo de dos veces
    //en este metodo se crean todas las dimensiones posibles a partir de lo anterior.
    //y elige al azar una de esas dimensiones para que sea la dimension de la solucion
    //sin embargo la sopa se mostrar acorde a la dimension seleccionada, en este caso
    //el jugador deberia añadir o quitar filas o columnas correctamente para llegar a esta
    //dimension solucion
    private void dimensionDeSolucion() {
        ArrayList<Pair<Integer, Integer>> dimensiones = new ArrayList<>();
        Pair<Integer, Integer> dm = new Pair<>(nFilas, nColumnas + 1);
        Pair<Integer, Integer> dm2 = new Pair<>(nFilas, nColumnas + 2);
        Pair<Integer, Integer> dm3 = new Pair<>(nFilas + 1, nColumnas + 1);
        Pair<Integer, Integer> dm4 = new Pair<>(nFilas - 1, nColumnas);
        Pair<Integer, Integer> dm5 = new Pair<>(nFilas - 2, nColumnas);
        Pair<Integer, Integer> dm6 = new Pair<>(nFilas + 1, nColumnas);
        Pair<Integer, Integer> dm7 = new Pair<>(nFilas + 2, nColumnas);
        Pair<Integer, Integer> dm8 = new Pair<>(nFilas - 1, nColumnas - 1);
        Pair<Integer, Integer> dm9 = new Pair<>(nFilas, nColumnas - 1);
        Pair<Integer, Integer> dm10 = new Pair<>(nFilas, nColumnas - 2);
        Pair<Integer, Integer> dm11 = new Pair<>(nFilas, nColumnas);
        dimensiones.addLast(dm);
        dimensiones.addLast(dm2);
        dimensiones.addLast(dm3);
        dimensiones.addLast(dm4);
        dimensiones.addLast(dm5);
        dimensiones.addLast(dm6);
        dimensiones.addLast(dm7);
        dimensiones.addLast(dm8);
        dimensiones.addLast(dm9);
        dimensiones.addLast(dm10);
        dimensiones.addLast(dm11);
        int numAl = (int) (Math.random() * dimensiones.size());
        nFilasSolucion = dimensiones.get(numAl).getKey();
        nColumnasSolucion = dimensiones.get(numAl).getValue();
    }
    // a partir de la dimension de la solucion este metodo crea una sopa ya insertando 
    // las palabras aleatoriamente, luego las filas y columnas de esta sopa seran desplazadas
    // añadidas o eliminadas por el metodo creacionSopaToShow
    //NOTA: la sopa solucion es una sopa la cual no se ha añadido, quitado ni desplazdo sus filas y columnas
    private void creacionSopaSolucion() {
        //creando las filas y columnas
        for (int i = 0; i < nFilasSolucion; i++) {
            filas.addLast(new CircularLinkedList<>());
        }
        for (int i = 0; i < nColumnasSolucion; i++) {
            columnas.addLast(new CircularLinkedList<>());
        }
        //creando diagonales
        int nDiagonalesSolucion;
        if(nFilasSolucion==nColumnasSolucion){
            //nuemero de diagonales cuando la sopa es cuadrada
            nDiagonalesSolucion=nColumnasSolucion*2-1;
        } else{
            //numero de diagonales cuando la sopa es rectangular
            nDiagonalesSolucion=(Math.max(nFilasSolucion, nColumnasSolucion)*2)-
                    ((Math.max(nFilasSolucion, nColumnasSolucion))-(Math.min(nFilasSolucion, nColumnasSolucion))+1);
        }
        System.out.println("filas solucion: "+nFilasSolucion+"  columnas solucion: "+nColumnasSolucion);
        System.out.println("numero de diagonales: "+nDiagonalesSolucion);
        for (int i=0; i<nDiagonalesSolucion; i++){
            diagonal1.addLast(new ArrayList<>());
            diagonal2.addLast(new ArrayList<>());
        }
        //llenando las filas, columnas y diagonales
        for (int i = 0; i < nFilasSolucion; i++) {
            for (int i2 = 0; i2 < nColumnasSolucion; i2++) {
                filas.get(i).addLast(String.valueOf(i)+String.valueOf(i2));
                columnas.get(i2).addLast(String.valueOf(i)+String.valueOf(i2));
                diagonal1.get(i+i2).addFirst(String.valueOf(i)+String.valueOf(i2));
                //falta añadir diagonal2
                //diagonal2.get(i).addLast(String.valueOf(i)+String.valueOf(i2));
            }
        }
        for (int i = 0; i < diagonal1.size(); i++) {
            System.out.println(diagonal1.get(i));
        }
        //2)cuando el jugadro marca la casilla inicial y la final, crear un mecanismo para 
        //saber si ese patron de marcacion corrresponde a una vertical, hori o diagonal
        //3)luego sabiendo eso, buscar en el arraylist respectivo , con los indices seleccionados, si se 
        //encuentra la palabra
        //4) cuando una fila o columna sea añadida o desplazada comunicar a las estructuras para que estas
        //se actualizen 

    }
    
    private void creacionSopaToShow(){
        //crear dos estructuras de sopaInicial y sopaFinal
        //en este metodo se quitan, añaden y desplzan las filas y columnas
        //resultando en una sopa final que se mostrara en pantalla
        // el desplazamiento debe ser minimo
        //se podrian crear niveles de dificultad que serian en base a cuantos desplazamientos se realizan
        //se deberia añadir un boton de ayuda para indicar los pasos a seguir para llegar a la
        //sopa solucion
    }

    //getters para obtener en ventana sopa de letras para que sean ubicados
    //como una sopa de letras
    public ArrayList<CircularLinkedList<String>> getFilas() {
        return filas;
    }

    public ArrayList<CircularLinkedList<String>> getColumnas() {
        return columnas;
    }

    public ArrayList<ArrayList<String>> getDiagonal1() {
        return diagonal1;
    }

    public ArrayList<ArrayList<String>> getDiagonal2() {
        return diagonal2;
    }

}
