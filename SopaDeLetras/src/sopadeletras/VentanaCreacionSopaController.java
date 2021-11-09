/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sopadeletras;

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
public class VentanaCreacionSopaController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
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
    }

    @FXML
    private void crecionDeCuadriculas(ActionEvent event) throws IOException {
        
        // CREACION DE LA CUADRICULA
        
        //CAMBIANDO DE ESCENA AL JUEGO
        root = FXMLLoader.load(getClass().getResource("VentanaSopaDeLetras.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
