/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

/**
 *
 * @author leonardo
 */
class NodeCircularList<E> {

    private E content;
    private NodeCircularList<E> previous;
    private NodeCircularList<E> next;
    public NodeCircularList(E content) {
        this.content = content;
        this.previous = this;
        this.next = this;
    }

    public NodeCircularList(E content, NodeCircularList<E> previous, NodeCircularList<E> next) {
        this.content = content;
        this.previous=previous;
        this.next = next;
    }
    //getters
    public E getContent() {
        return content;
    }

    public NodeCircularList<E> getNext() {
        return next;
    }

    public NodeCircularList<E> getPrevious() {
        return previous;
    }
    
    //setters
    public void setContent(E content) {
        this.content = content;
    }

    public void setNext(NodeCircularList<E> next) {
        this.next = next;
    }

    public void setPrevious(NodeCircularList<E> previous) {
        this.previous = previous;
    }
    
}
