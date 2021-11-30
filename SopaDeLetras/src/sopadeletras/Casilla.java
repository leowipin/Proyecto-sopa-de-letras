/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sopadeletras;

import javafx.scene.control.Button;

/**
 *
 * @author leonardo
 */
public class Casilla extends Button {
    
    boolean pintado;
    boolean esSolucion;
    int posicionx;
    int posiciony;
    String contenido;
    
    
    public Casilla(String contenido, int posicionx, int posiciony) {
        super(contenido);
        this.contenido=contenido;
        this.pintado=false;
        this.posicionx=posicionx;
        this.posiciony=posiciony;
        this.esSolucion=false;
    }
    //getter setters

    public boolean isPintado() {
        return pintado;
    }

    public boolean isEsSolucion() {
        return esSolucion;
    }

    public int getPosicionx() {
        return posicionx;
    }

    public int getPosiciony() {
        return posiciony;
    }

    public String getContenido() {
        return contenido;
    }

    public void setPintado(boolean pintado) {
        this.pintado = pintado;
    }

    public void setEsSolucion(boolean esSolucion) {
        this.esSolucion = esSolucion;
    }

    public void setPosicionx(int posicionx) {
        this.posicionx = posicionx;
    }

    public void setPosiciony(int posiciony) {
        this.posiciony = posiciony;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    
    
    
}
