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
public class ArrayList<E> implements List<E> {

    private E[] elements;
    private int effectiveSize = 0;
    private int capacity = 100;

    public ArrayList() {
        this.elements = (E[]) new Object[this.capacity]; //JAVA no permite crear objetos genericos por lo cual
        // lo creo con object y lo casteo al generico.
    }

    @Override
    public boolean addFirst(E e) {
        if (isFull()) {
            addCapacity();
        }
        for (int i = effectiveSize - 1; i >= 0; i--) {
            elements[i + 1] = elements[i];
        }
        elements[0] = e;
        effectiveSize++;
        return true;

    }

    @Override
    public boolean addLast(E e) {
        if (e != null) {
            if (isFull()) {
                addCapacity();
            }
            elements[effectiveSize] = e;
            effectiveSize++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public E get(int index) {
        return elements[index]; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public E set(int index, E element) {
        E seted = elements[index];
        elements[index] = element;
        return seted;
    }

    @Override
    public int size() {
        return effectiveSize;
    }

    @Override
    public boolean isEmpty() {
        return effectiveSize == 0;
    }

    @Override
    public void clear() {
        effectiveSize = 0;
        capacity = 100;
        elements = (E[]) new Object[capacity];
    }

    private boolean isFull() {
        return effectiveSize == capacity;
    }

    private void addCapacity() {
        E[] tmp = (E[]) new Object[capacity * 2];
        for (int i = 0; i < effectiveSize; i++) {
            tmp[i] = elements[i];
        }
        elements = tmp;
        capacity = capacity * 2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < effectiveSize; i++) {
            sb.append(elements[i]).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public E getPrevious(E content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<E> iterator() {
        Iterator it = new Iterator() {
            int cont = 0;

            @Override
            public boolean hasNext() {
                return cont < effectiveSize;
            }

            @Override
            public E next() {
                E currentValue = elements[cont++];
                return currentValue;
            }
        };
        return it;
    }

    @Override
    public boolean add(int index, E element) {
        if (isFull()) {
            addCapacity();
        }
        for (int i = effectiveSize - 1; i >= index; i--) {
            elements[i + 1] = elements[i];
        }
        elements[index] = element;
        effectiveSize++;
        return true;
    }

    @Override
    public E remove(int index) {
        E removed = elements[index];
        for (int i = index; i < effectiveSize; i++) {
            elements[i] = elements[i + 1];
        }
        elements[effectiveSize - 1] = null;
        effectiveSize--;
        return removed;
    }

    @Override
    public E getNext(E content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
