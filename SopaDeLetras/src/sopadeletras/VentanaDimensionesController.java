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
import java.util.Random;
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
public class VentanaDimensionesController {

    private Stage stage;
    static boolean valorSopa = true;
    static int nFilas = 0;
    static int nColumnas = 0;
    static int nFilasSolucion;
    static int nColumnasSolucion;
    static ArrayList<CircularLinkedList<String>> filas = new ArrayList<>();
    static ArrayList<CircularLinkedList<String>> columnas = new ArrayList<>();
    static ArrayList<String> banco = new ArrayList<>();
    static ArrayList<String> palabrasBuscar = new ArrayList<>();
    //listas paralelas
    static ArrayList<String> posiciones = new ArrayList<>();
    static ArrayList<String> letras = new ArrayList<>();

    static ArrayList<String> posicionesFinal = new ArrayList<>();
    static ArrayList<String> palabrasNoUbicadas = new ArrayList<>();
    @FXML
    private ChoiceBox<String> choiceBoxDimensiones;

    private String[] dimensiones = {"7x7", "8x8", "9x9", "10x10", "11x11", "12x12",
        "13x13", "14x14", "15x15", "16x16", "17x17", "18x18", "19x19", "20x20"};
    @FXML
    private Label labelTamaño;

    @FXML
    private Button btnEmpezar;

    public void initialize() {
        choiceBoxDimensiones.getItems().addAll(dimensiones);
        choiceBoxDimensiones.setOnAction(this::construccionSopa);// se construye la sopa inicial cuando se elige la dimension
    }

    public void construccionSopa(ActionEvent event) {
        // se obtiene las dimensiones elegidas en el choiceBox
        palabrasBuscar.clear();
        palabrasNoUbicadas.clear();
        banco.clear();
        posiciones.clear();
        letras.clear();
        if (valorSopa) {
            String[] dim = choiceBoxDimensiones.getValue().split("x");
            nFilas = Integer.valueOf(dim[0]);
            nColumnas = Integer.valueOf(dim[1]);
        }

        if (!filas.isEmpty()) {
            //si se elige denuevo otra dimension, hay que limpiar las estructuras
            filas.clear();
            columnas.clear();
        }
        dimensionDeSolucion();
        creacionSopaBase();
        palabrasAleatoria();

        generarPosicionPalabra();
        creacionSopaToShow();
        System.out.println(palabrasBuscar);
        eliminarNoInsertados();
        System.out.println(palabrasNoUbicadas);
        System.out.println(palabrasBuscar);

    }

    @FXML
    private void switchToSopaLetras(ActionEvent event) throws IOException {
        //CAMBIANDO DE ESCENA AL JUEGO
        VentanaSopaDeLetras v = new VentanaSopaDeLetras();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(v.sceneSopa);
        stage.show();
        valorSopa = false;
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

    // a partir de la dimension de la solucion este metodo crea una sopa base la cual consiste
    // en una matriz con sus (numerofila-numerocolumna) en cada casilla, con ayuda de esta matriz
    // se insertaran las palabra aleatoria en la posicion aleatoria correspondiente
    private void creacionSopaBase() {
        //creando las filas y columnas
        for (int i = 0; i < nFilasSolucion; i++) {
            filas.addLast(new CircularLinkedList<>());
        }
        for (int i = 0; i < nColumnasSolucion; i++) {
            columnas.addLast(new CircularLinkedList<>());
        }

        //llenando las filas, columnas y diagonales
        for (int i = 0; i < nFilasSolucion; i++) {
            for (int i2 = 0; i2 < nColumnasSolucion; i2++) {
                filas.get(i).addLast(String.valueOf(i) + "-" + String.valueOf(i2));
                columnas.get(i2).addLast(String.valueOf(i) + "-" + String.valueOf(i2));
            }
        }
        //codigo para añadir numeros a lado de las posiciones que representa en que
        //lugar puede o no entrar una palabra. Si la palabra tiene n letras y el
        //numero asociado es menor que n, no hay manera de encajar la palabra.
        //si es mayor o igual si es posible encajar la palabra en al menos una posicion (vertical, horizontal o diagonal)
        int cont = 0;
        int i2 = 0;
        for (int a = 0; a < Math.min(nFilasSolucion, nColumnasSolucion); a++) {
            int i = Math.max(nFilasSolucion, nColumnasSolucion);
            int iOpuesto = i;
            //(int)(Math.max(nFilasSolucion, nColumnasSolucion)/2)+1 : formula que determina el numero de letras
            //maximo que debe tener una palabra para entrar en cualquier casilla
            if (Math.min(nFilasSolucion, nColumnasSolucion) - a < (int) (Math.max(nFilasSolucion, nColumnasSolucion) / 2) + 1) {
                i2 = (int) (Math.max(nFilasSolucion, nColumnasSolucion) / 2) + 1;
            } else {
                i2 = Math.min(nFilasSolucion, nColumnasSolucion) - a;
            }
            if (a >= ((int) (Math.max(nFilasSolucion, nColumnasSolucion) / 2) + 1)) {
                cont++;
                i2 = i2 + cont;
            }

            int e = 100;
            for (int b = 0; b < Math.max(nFilasSolucion, nColumnasSolucion); b++) {
                if (nFilasSolucion > nColumnasSolucion) {
                    if (i > i2) {
                        String elemento = columnas.get(a).get(b);
                        columnas.get(a).set(b, elemento.concat("-" + Integer.toString(i)));
                        i--;
                        iOpuesto--;
                    } else if (i == i2 && e > 0) {
                        if (e == 100) {
                            e = nFilasSolucion - ((nFilasSolucion - i) * 2);
                        }
                        String elemento = columnas.get(a).get(b);
                        columnas.get(a).set(b, elemento.concat("-" + Integer.toString(i)));
                        e--;
                    } else {
                        iOpuesto++;
                        String elemento = columnas.get(a).get(b);
                        columnas.get(a).set(b, elemento.concat("-" + Integer.toString(iOpuesto)));
                    }
                } else {//se añade a fila

                    if (i > i2) {
                        String elemento = filas.get(a).get(b);
                        filas.get(a).set(b, elemento.concat("-" + Integer.toString(i)));
                        i--;
                        iOpuesto--;
                    } else if (i == i2 && e > 0) {
                        if (e == 100) {
                            e = nColumnasSolucion - ((nColumnasSolucion - i) * 2);
                        }
                        String elemento = filas.get(a).get(b);
                        filas.get(a).set(b, elemento.concat("-" + Integer.toString(i)));
                        e--;
                    } else {
                        iOpuesto++;
                        String elemento = filas.get(a).get(b);
                        filas.get(a).set(b, elemento.concat("-" + Integer.toString(iOpuesto)));
                    }
                }
            }

        }
        // posicionFila-posicionyColumna-numeroLetrasMaximo
        if (nFilasSolucion > nColumnasSolucion) {
            for (int ix = 0; ix < columnas.size(); ix++) {
                System.out.println(columnas.get(ix));
            }
        } else {
            for (int ix = 0; ix < filas.size(); ix++) {
                System.out.println(filas.get(ix));
            }
        }

        //2)cuando el jugadro marca la casilla inicial y la final, crear un mecanismo para 
        //saber si ese patron de marcacion corrresponde a una vertical, hori o diagonal
        //3)luego sabiendo eso, buscar en el arraylist respectivo , con los indices seleccionados, si se 
        //encuentra la palabra
        //4) cuando una fila o columna sea añadida o desplazada comunicar a las estructuras para que estas
        //se actualizen 
    }

    private ArrayList cargarBanco() {

        //banco de capitales del mundo por ejemplo
        //de un archivo cargar las palabras en el arraylist banco
        return banco;
    }

    private void palabrasAleatoria() {
        // aqui va el arraylist del banco
        // en el metodo cargar banco hacer la validacion del numero de letras maximo
        String[] bancoEjemplo = {"UNO", "DOS", "TRES", "FOUR", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ"};
        for (int i = 0; i < bancoEjemplo.length; i++) {
            banco.addLast(bancoEjemplo[i]);
        }
        int numAl;
        for (int i = 0; i < 5; i++) {
            numAl = (int) (Math.random() * banco.size());//numero aleatorio para elegir la palabra del banco
            palabrasBuscar.addLast(banco.get(numAl));
            banco.remove(numAl);

        }

    }

    private void generarPosicionPalabra() {
        //se van a generar 5 palabras por defecto. en la sopa con menor dimension puede que sean 4 
        //dependiendo si se pudo acomodar
        ArrayList<ArrayList<String>> posiblesPosiciones = new ArrayList<>();
        for (int i = 0; i < palabrasBuscar.size(); i++) {
            String pal = palabrasBuscar.get(i);
            int tamaño = pal.length();
            ArrayList<String> posiblePosicion = new ArrayList<>();
            for (int i2 = 0; i2 < nFilasSolucion; i2++) {//bucle q determina la posicion de la letra inicial
                for (int i3 = 0; i3 < nColumnasSolucion; i3++) {
                    // condicion para saber si los numeros asociados se llenaron en las filas o columnas
                    String[] elementos = filas.get(i2).get(i3).split("-");
                    String[] elementos2 = columnas.get(i3).get(i2).split("-");
                    if (elementos.length == 3) {//filas
                        //extraer las posiciones que tengan el numero asociado >= al numero de letras
                        if (tamaño <= Integer.valueOf(elementos[2])) {
                            posiblePosicion.addLast(elementos[0] + "-" + elementos[1]);
                        }
                    } else {//columnas
                        if (tamaño <= Integer.valueOf(elementos2[2])) {
                            posiblePosicion.addLast(elementos2[0] + "-" + elementos2[1]);
                        }
                    }
                }
            }
            posiblesPosiciones.addLast(posiblePosicion);

        }

        for (int i0 = 0; i0 < palabrasBuscar.size(); i0++) {
            boolean flagFila = false;
            boolean flagFilaAnti = false;
            boolean flagColumna = false;
            boolean flagColumnaAnti = false;
            boolean flagDiag1 = false;
            boolean flagDiag1Anti = false;
            boolean flagDiag2 = false;
            boolean flagDiag2Anti = false;
            String pal = "";
            for (int i = 0; i < palabrasBuscar.get(i0).length(); i++) {
                pal = palabrasBuscar.get(i0);
                if (i == 0) {
                    System.out.println(pal);
                    int numAl = (int) (Math.random() * posiblesPosiciones.get(i0).size());
                    String pos = posiblesPosiciones.get(i0).get(numAl);
                    if (posiciones.contains(pos)) {
                        int indice = posiciones.getIndex(pos);
                        String letra = letras.get(indice);
                        if (String.valueOf(pal.charAt(i)).equals(letra)) {
                            posiciones.addLast(pos);
                            letras.addLast(String.valueOf(pal.charAt(0)));
                        } else {
                            for (int e = 0; e < posiblesPosiciones.get(i0).size(); e++) {
                                String pos2 = posiblesPosiciones.get(i0).get(e);
                                if (!posiciones.contains(pos2)) {
                                    posiciones.addLast(pos2);
                                    System.out.println("");
                                    letras.addLast(String.valueOf(pal.charAt(0)));
                                    break;
                                }
                            }
                        }
                    } else {
                        posiciones.addLast(pos);
                        letras.addLast(String.valueOf(pal.charAt(0)));
                    }
                } else {
                    System.out.println("posiciones: " + posiciones);
                    System.out.println("letras: " + letras);

                    //0: se va a ubicar en fila.  1: columna.  2: diagonal1.  3:diagonal2
                    if (i == 1) {
                        ArrayList<Integer> posi = new ArrayList<>();
                        posi.addLast(0);
                        posi.addLast(1);
                        posi.addLast(2);
                        posi.addLast(3);
                        posi.addLast(4);
                        posi.addLast(5);
                        posi.addLast(6);
                        posi.addLast(7);
                        for (int p = 0; p <= 7; p++) {
                            int ind = (int) (Math.random() * posi.size());
                            int numAl = posi.get(ind);
                            if (numAl == 0) {
                                String ultiPos = posiciones.get(posiciones.size() - 1);
                                String[] ultiSplit = ultiPos.split("-");
                                int fil = 0;
                                for (int p2 = 1; p2 < pal.length(); p2++) {
                                    String posic = ultiSplit[0].concat("-" + String.valueOf(Integer.valueOf(ultiSplit[1]) + p2));
                                    if ((Integer.valueOf(ultiSplit[1]) + p2) < columnas.size()) {
                                        if (posiciones.contains(posic)) {
                                            int indice = posiciones.getIndex(posic);
                                            String letra = letras.get(indice);
                                            if (String.valueOf(pal.charAt(p2)).equals(letra)) {
                                                fil++;
                                            }
                                        } else {
                                            fil++;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                if (fil == pal.length() - 1) {
                                    flagFila = true;
                                    break;
                                }
                            } else if (numAl == 1) {
                                String ultiPos = posiciones.get(posiciones.size() - 1);
                                String[] ultiSplit = ultiPos.split("-");
                                int filAnti = 0;
                                for (int p2 = 1; p2 < pal.length(); p2++) {
                                    String posic = ultiSplit[0].concat("-" + String.valueOf(Integer.valueOf(ultiSplit[1]) - p2));
                                    if ((Integer.valueOf(ultiSplit[1]) - p2) >= 0) {
                                        if (posiciones.contains(posic)) {
                                            int indice = posiciones.getIndex(posic);
                                            String letra = letras.get(indice);
                                            if (String.valueOf(pal.charAt(p2)).equals(letra)) {
                                                filAnti++;
                                            }
                                        } else {
                                            filAnti++;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                if (filAnti == pal.length() - 1) {
                                    flagFilaAnti = true;
                                    break;
                                }

                            } else if (numAl == 2) {
                                String ultiPos = posiciones.get(posiciones.size() - 1);
                                String[] ultiSplit = ultiPos.split("-");
                                int col = 0;
                                for (int p2 = 1; p2 < pal.length(); p2++) {
                                    String posic = String.valueOf(Integer.valueOf(ultiSplit[0]) + p2).concat("-" + ultiSplit[1]);
                                    if ((Integer.valueOf(ultiSplit[0]) + p2) < filas.size()) {
                                        if (posiciones.contains(posic)) {
                                            int indice = posiciones.getIndex(posic);
                                            String letra = letras.get(indice);
                                            if (String.valueOf(pal.charAt(p2)).equals(letra)) {
                                                col++;
                                            }
                                        } else {
                                            col++;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                if (col == pal.length() - 1) {
                                    flagColumna = true;
                                    break;
                                }

                            } else if (numAl == 3) {
                                String ultiPos = posiciones.get(posiciones.size() - 1);
                                String[] ultiSplit = ultiPos.split("-");
                                int colAnti = 0;
                                for (int p2 = 1; p2 < pal.length(); p2++) {
                                    String posic = String.valueOf(Integer.valueOf(ultiSplit[0]) - p2).concat("-" + ultiSplit[1]);
                                    if ((Integer.valueOf(ultiSplit[0]) - p2) >= 0) {
                                        if (posiciones.contains(posic)) {
                                            int indice = posiciones.getIndex(posic);
                                            String letra = letras.get(indice);
                                            if (String.valueOf(pal.charAt(p2)).equals(letra)) {
                                                colAnti++;
                                            }
                                        } else {
                                            colAnti++;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                if (colAnti == pal.length() - 1) {
                                    flagColumnaAnti = true;
                                    break;
                                }

                            } else if (numAl == 4) {
                                String ultiPos = posiciones.get(posiciones.size() - 1);
                                String[] ultiSplit = ultiPos.split("-");
                                int diag1 = 0;
                                for (int p2 = 1; p2 < pal.length(); p2++) {
                                    String posic = String.valueOf(Integer.valueOf(ultiSplit[0]) + p2).concat("-" + String.valueOf(Integer.valueOf(ultiSplit[1]) + p2));
                                    if ((Integer.valueOf(ultiSplit[0]) + p2) < filas.size() && (Integer.valueOf(ultiSplit[1]) + p2) < columnas.size()) {
                                        if (posiciones.contains(posic)) {
                                            int indice = posiciones.getIndex(posic);
                                            String letra = letras.get(indice);
                                            if (String.valueOf(pal.charAt(p2)).equals(letra)) {
                                                diag1++;
                                            }
                                        } else {
                                            diag1++;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                if (diag1 == pal.length() - 1) {
                                    flagDiag1 = true;
                                    break;
                                }

                            } else if (numAl == 5) {
                                String ultiPos = posiciones.get(posiciones.size() - 1);
                                String[] ultiSplit = ultiPos.split("-");
                                int diag1Anti = 0;
                                for (int p2 = 1; p2 < pal.length(); p2++) {
                                    String posic = String.valueOf(Integer.valueOf(ultiSplit[0]) - p2).concat("-" + String.valueOf(Integer.valueOf(ultiSplit[1]) - p2));
                                    if ((Integer.valueOf(ultiSplit[0]) - p2) >= 0 && (Integer.valueOf(ultiSplit[1]) - p2) >= 0) {
                                        if (posiciones.contains(posic)) {
                                            int indice = posiciones.getIndex(posic);
                                            String letra = letras.get(indice);
                                            if (String.valueOf(pal.charAt(p2)).equals(letra)) {
                                                diag1Anti++;
                                            }
                                        } else {
                                            diag1Anti++;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                if (diag1Anti == pal.length() - 1) {
                                    flagDiag1Anti = true;
                                    break;
                                }

                            } else if (numAl == 6) {
                                String ultiPos = posiciones.get(posiciones.size() - 1);
                                String[] ultiSplit = ultiPos.split("-");
                                int diag2 = 0;
                                for (int p2 = 1; p2 < pal.length(); p2++) {
                                    String posic = String.valueOf(Integer.valueOf(ultiSplit[0]) - p2).concat("-" + String.valueOf(Integer.valueOf(ultiSplit[1]) + p2));
                                    if ((Integer.valueOf(ultiSplit[0]) - p2) >= 0 && (Integer.valueOf(ultiSplit[1]) + p2) < columnas.size()) {
                                        if (posiciones.contains(posic)) {
                                            int indice = posiciones.getIndex(posic);
                                            String letra = letras.get(indice);
                                            if (String.valueOf(pal.charAt(p2)).equals(letra)) {
                                                diag2++;
                                            }
                                        } else {
                                            diag2++;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                if (diag2 == pal.length() - 1) {
                                    flagDiag2 = true;
                                    break;
                                }

                            } else {//numal = 7
                                String ultiPos = posiciones.get(posiciones.size() - 1);
                                String[] ultiSplit = ultiPos.split("-");
                                int diag2Anti = 0;
                                for (int p2 = 1; p2 < pal.length(); p2++) {
                                    String posic = String.valueOf(Integer.valueOf(ultiSplit[0]) + p2).concat("-" + String.valueOf(Integer.valueOf(ultiSplit[1]) - p2));
                                    if ((Integer.valueOf(ultiSplit[0]) + p2) < filas.size() && (Integer.valueOf(ultiSplit[1]) - p2) >= 0) {
                                        if (posiciones.contains(posic)) {
                                            int indice = posiciones.getIndex(posic);
                                            String letra = letras.get(indice);
                                            if (String.valueOf(pal.charAt(p2)).equals(letra)) {
                                                diag2Anti++;
                                            }
                                        } else {
                                            diag2Anti++;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                if (diag2Anti == pal.length() - 1) {
                                    flagDiag2Anti = true;
                                    break;
                                }

                            }
                            posi.remove(numAl);

                        }
                    }
                    if (flagFila) {
                        System.out.println("flagFila");
                        String ultiPos = posiciones.get(posiciones.size() - 1);
                        String[] ultiSplit = ultiPos.split("-");
                        String posic = ultiSplit[0].concat("-" + String.valueOf(Integer.valueOf(ultiSplit[1]) + 1));
                        posiciones.addLast(posic);
                        letras.addLast(String.valueOf(pal.charAt(i)));

                    } else if (flagFilaAnti) {
                        System.out.println("flagFilaAnti");
                        String ultiPos = posiciones.get(posiciones.size() - 1);
                        String[] ultiSplit = ultiPos.split("-");
                        String posic = ultiSplit[0].concat("-" + String.valueOf(Integer.valueOf(ultiSplit[1]) - 1));
                        posiciones.addLast(posic);
                        letras.addLast(String.valueOf(pal.charAt(i)));

                    } else if (flagColumna) {
                        System.out.println("flagColumna");
                        String ultiPos = posiciones.get(posiciones.size() - 1);
                        String[] ultiSplit = ultiPos.split("-");
                        String posic = String.valueOf(Integer.valueOf(ultiSplit[0]) + 1).concat("-" + ultiSplit[1]);
                        posiciones.addLast(posic);
                        letras.addLast(String.valueOf(pal.charAt(i)));

                    } else if (flagColumnaAnti) {
                        System.out.println("flagColumnaAnti");
                        String ultiPos = posiciones.get(posiciones.size() - 1);
                        String[] ultiSplit = ultiPos.split("-");
                        String posic = String.valueOf(Integer.valueOf(ultiSplit[0]) - 1).concat("-" + ultiSplit[1]);
                        posiciones.addLast(posic);
                        letras.addLast(String.valueOf(pal.charAt(i)));

                    } else if (flagDiag1) {
                        System.out.println("flagDiag1");
                        String ultiPos = posiciones.get(posiciones.size() - 1);
                        String[] ultiSplit = ultiPos.split("-");
                        String posic = String.valueOf(Integer.parseInt(ultiSplit[0]) + 1).concat("-" + String.valueOf(Integer.parseInt(ultiSplit[1]) + 1));
                        posiciones.addLast(posic);
                        letras.addLast(String.valueOf(pal.charAt(i)));

                    } else if (flagDiag1Anti) {
                        System.out.println("flagDiag1anti");
                        String ultiPos = posiciones.get(posiciones.size() - 1);
                        String[] ultiSplit = ultiPos.split("-");
                        String posic = String.valueOf(Integer.parseInt(ultiSplit[0]) - 1).concat("-" + String.valueOf(Integer.parseInt(ultiSplit[1]) - 1));
                        posiciones.addLast(posic);
                        letras.addLast(String.valueOf(pal.charAt(i)));

                    } else if (flagDiag2) {
                        System.out.println("flagDiag2");
                        String ultiPos = posiciones.get(posiciones.size() - 1);
                        String[] ultiSplit = ultiPos.split("-");
                        String posic = String.valueOf(Integer.parseInt(ultiSplit[0]) - 1).concat("-" + String.valueOf(Integer.parseInt(ultiSplit[1]) + 1));
                        posiciones.addLast(posic);
                        letras.addLast(String.valueOf(pal.charAt(i)));

                    } else if (flagDiag2Anti) {
                        System.out.println("flagDiag2Anti");
                        String ultiPos = posiciones.get(posiciones.size() - 1);
                        String[] ultiSplit = ultiPos.split("-");
                        System.out.println(ultiSplit[0] + "-" + ultiSplit[1]);
                        String posic = String.valueOf(Integer.parseInt(ultiSplit[0]) + 1).concat("-" + String.valueOf(Integer.parseInt(ultiSplit[1]) - 1));
                        posiciones.addLast(posic);
                        letras.addLast(String.valueOf(pal.charAt(i)));

                    }

                }
            }
            if (!flagFila && !flagFilaAnti && !flagColumna && !flagColumnaAnti && !flagDiag1 && !flagDiag1Anti && !flagDiag2 && !flagDiag2Anti) {
                posiciones.remove(posiciones.size() - 1);
                letras.remove(letras.size() - 1);
                palabrasNoUbicadas.addLast(pal);
            }
        }
        System.out.println(posiciones);
        System.out.println(letras);
        for (int i = 0; i < posiciones.size(); i++) {
            posicionesFinal.addLast(posiciones.get(i).concat("-" + letras.get(i)));
        }

    }

    private void creacionSopaToShow() {

        for (int i = 0; i < nFilasSolucion; i++) {
            for (int i2 = 0; i2 < nColumnasSolucion; i2++) {
                String pos = String.valueOf(i).concat("-" + String.valueOf(i2));
                if (posiciones.contains(pos)) {
                    int index = posiciones.getIndex(pos);
                    String letra = letras.get(index).concat("-s");
                    filas.get(i).set(i2, letra);
                    columnas.get(i2).set(i, letra);

                } else {
                    Random random = new Random();
                    char randomizedCharacter = (char) (random.nextInt(26) + 'a');
                    String letra = String.valueOf(randomizedCharacter).toUpperCase().concat("-n"); //letra elegida aleatoriamente del abecedario
                    filas.get(i).set(i2, letra);
                    columnas.get(i2).set(i, letra);
                }

            }
        }
        System.out.println("FILAS");
        for (int ix = 0; ix < filas.size(); ix++) {
            System.out.println(filas.get(ix));
        }
        
       /* while(nFilas!=nFilasSolucion){
            if(nFilas>nFilasSolucion){//agregas
                int numAl = (int) (Math.random() * filas.size());//indice donde se agregar la fila
                //agregarFila();
            }else{//quitas
                int numAl = (int) (Math.random() * filas.size());//indice donde se quitara la fila
                //quitarFila();
            }
        }*/

        //segun sea necesario para llegar a las dimensiones proporcionadas por el usuario
        //elminarColumnas(); agregarColumnas(); 
        //eliminarFilas(); agregarFilas(); 
    }

    private void eliminarNoInsertados() {
        for (String s : palabrasNoUbicadas) {
            palabrasBuscar.remove(palabrasBuscar.getIndex(s));
        }
    }

}
