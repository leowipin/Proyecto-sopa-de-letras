/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import TDA.List;
import java.util.Iterator;

/**
 *
 * @author Pincay
 * @param <E>
 */
public class CircularLinkedList<E> implements List<E> {

    public NodeCircularList<E> last;

    public CircularLinkedList() {
        last = null;
    }

    @Override
    public boolean addFirst(E e) {
        NodeCircularList<E> newNode = new NodeCircularList<>(e);
        if (isEmpty()) {
            last = newNode;

        } else {
            newNode.setNext(last.getNext());
            newNode.setPrevious(last);
            last.getNext().setPrevious(newNode);
            last.setNext(newNode);
        }
        return true;
    }

    @Override
    public boolean addLast(E e) {
        NodeCircularList<E> newNode = new NodeCircularList<>(e);
        if (isEmpty()) {
            addFirst(e);
        } else {
            newNode.setNext(last.getNext());
            newNode.setPrevious(last);
            last.getNext().setPrevious(newNode);
            last.setNext(newNode);
            last = newNode;
        }
        return true;
    }

    @Override
    public boolean add(int index, E element) {
        if (!this.isEmpty()) {
            if (this.size() - 1 != index) {
                NodeCircularList<E> v = last;
                for (int i = 0; i <= index; i++) {
                    v = v.getNext();
                }
                NodeCircularList<E> nuevo = new NodeCircularList(element, v.getPrevious(), v);
                v.getPrevious().setNext(nuevo);
                v.setPrevious(nuevo);
            } else if(index==0){
                addFirst(element);
            }
            else{
                addLast(element);
            }
        } else {
            addFirst(element);
        }

        return true;

    }

    @Override
    public E remove(int index) {
        NodeCircularList<E> removed;
        if (!this.isEmpty()) {
            if ((this.size() - 1) != index) {
                NodeCircularList<E> v = last;
                for (int i = 0; i <= index; i++) {
                    v = v.getNext();
                }
                removed = v;
                v.getPrevious().setNext(v.getNext());
                v.getNext().setPrevious(v.getPrevious());
                v.setNext(null);
                v.setPrevious(null);
                return removed.getContent();
            }  else {
                removed = last;
                removed.getPrevious().setNext(removed.getNext());
                removed.getNext().setPrevious(removed.getPrevious());
                NodeCircularList<E> newLast = last.getPrevious();
                last.setNext(null);
                last.setPrevious(null);
                last = newLast;
                return removed.getContent();
            }

        } else {
            return null;
        }

    }

    private NodeCircularList<E> getNode(E content) {
        if (!isEmpty()) {
            NodeCircularList<E> v;
            int i = 0;
            for (v = last.getNext(); !v.getContent().equals(content); v = v.getNext()) {
                i++;
                if (i == this.size()) {// terminar el bucle cuando sobrepase el tamaño
                    return null;
                }
            }
            return v;
        }
        return null;
    }

    @Override
    public E getNext(E content) {
        NodeCircularList<E> ncl = getNode(content);
        return ncl.getNext().getContent();
    }

    @Override
    public E getPrevious(E content) {
        NodeCircularList<E> ncl = getNode(content);
        return ncl.getPrevious().getContent();
    }

    @Override
    public E get(int index) {
        NodeCircularList<E> v;
        int i = 0;
        if (index > this.size() - 1 || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        for (v = last.getNext(); i < index; v = v.getNext()) {
            i++;
        }
        return v.getContent();
    }

    @Override
    public E set(int index, E element) {
        NodeCircularList<E> v;
        int i = 0;
        if (index > this.size() - 1 || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        for (v = last.getNext(); i < index; v = v.getNext()) {
            i++;
        }
        NodeCircularList<E> seteado = v;
        v.setContent(element);
        return seteado.getContent();
    }

    @Override
    public int size() {
        int cont = 0;
        if (!isEmpty()) {
            for (NodeCircularList<E> p = last.getNext(); p != last; p = p.getNext()) {
                cont++;
            }
            cont++;
        }
        return cont;

    }

    @Override
    public boolean isEmpty() {
        return last == null;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder();
            NodeCircularList v = last.getNext();
            sb.append("[");
            for (int i = 0; i < this.size(); i++) {
                sb.append(v.getContent()).append(",");
                v = v.getNext();
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]");
            return sb.toString();
        }

    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator() {
            NodeCircularList<E> v = last.getNext();
            int i = 0;
            
            @Override
            public boolean hasNext() {
                return i<size();
            }

            @Override
            public E next() {
                E x = v.getContent();
                v = v.getNext();
                i++;
                return x;
            }
        };
        return it;
    }

    @Override
    public int getIndex(E element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(E element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void desplazarIzquierda() {
        last = last.getNext();
    }

    @Override
    public void desplazarDerecha() {
        last = last.getPrevious();
    }

}
