/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sopadeletras;

import Estructuras.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author leonardo
 */
public class BotonControl extends Button {

    String contenido;
    boolean bloqueado;
    boolean isVertical;
    boolean isHorizontal;
    int numero;
    String tipo;
    //boolean presionado = this.isPressed();
    static BorderPane pn = new BorderPane();
    Button btn = new Button("hola");

    public BotonControl(String contenido, int numero, String tipo, boolean isVertical, boolean isHorizontal) {
        
        super(contenido);
        this.numero = numero;
        this.bloqueado = false;
        this.tipo = tipo;
        this.isVertical=isVertical;
        this.isHorizontal=isHorizontal;

    }

    public void buildSopa() {
        
        Button retroceder = new Button("Retroceder");
        Button btnNuevo = new Button("Nuevo");
        Button mostrar = new Button("Mostrar");
        Button ayuda = new Button("Ayuda");
        Label info = new Label("Ejemplo: elimine la fila 2");
        Button btnVolver = new Button("Volver");
        ArrayList<Casilla> casillas = new ArrayList<>();
        ArrayList<BotonControl> botonesControl = new ArrayList<>();
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

       // agregandoCuadriculas(false);

        HBox opBotton = new HBox();
        btnVolver.setMinSize(75, 40);
        opBotton.getChildren().add(btnVolver);
        opBotton.setAlignment(Pos.CENTER);

        pn.setTop(puntuacion);
        pn.setRight(vBoxOp);

        pn.setBottom(opBotton);
        
    }

  

    public String getContenido() {
        return contenido;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public int getNumero() {
        return numero;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

}
