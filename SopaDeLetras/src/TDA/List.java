/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDA;

import java.util.Iterator;

/**
 *
 * @author Pincay
 * @param <E>
 */
public interface List<E> extends Iterable<E> {
    
    public boolean addFirst(E e);
    public boolean addLast(E e);
    public boolean add(int index, E element);
    public E remove(int index);
    public E get(int index);
    public E getNext(E content);
    public E getPrevious(E content);
    public E set(int index, E element);
    public int size();
    public boolean isEmpty();
    public void clear();
    @Override
    public String toString();
    @Override
    public Iterator<E> iterator ();
        
    
    
   
}
