/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sopadeletras;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 *
 * @author leonardo
 */
public class BotonControl extends Button {
    String contenido;
    boolean bloqueado;
    int numero;
    String tipo;
    boolean presionado = this.isPressed();

    public BotonControl(String contenido, int numero, String tipo) {
        super(contenido);
        this.numero=numero;
        this.bloqueado=false;
        this.tipo=tipo;
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
