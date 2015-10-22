/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.yandex.shad.collections;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Romam Usik
 * @param <E>
 */
public class MyArrayList<E> implements Iterable<E> {
    
    /**
     * Default capacity of array
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Const empty array represents empty String array
     */
    private static final Object[] EMPTY_DATA = {};

    /**
     * Array of elements
     */
    private E[] elementData;

    /**
     * The <tt>Integer</tt> value represents number of elements in array
     */
    private int size;

    /**
     * Constructs an empty array with an initial capacity of ten.
     */
    public MyArrayList() {
        elementData = (E[])EMPTY_DATA;
        size = 0;
    }
    
    /**
     * Represents an optimized version of Iterator<E>
     */
    private class Itr implements Iterator<E> {

        private int curr;

        private Itr() {
            curr = -1;
        }

        @Override
        public boolean hasNext() {
            return curr + 1 != size;
        }

        @Override
        public E next() {
            return elementData[++curr];
        }
        
    }

    /**
     * This method returns array iterator
     * @return - iterator Itr
     */
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }
    
    /**
     * Add given object to the end of array
     *
     * If array is full, creates new array with capacity of 3/2 of old array
     * and copy all elements to new array
     *
     * @param o
     * @return true if the element added successfully
     */

    public boolean add(E o) {
        if(elementData == EMPTY_DATA) {
            elementData = (E[])new Object[DEFAULT_CAPACITY];
        }
        else{
            if(elementData.length - size < 1) {
                elementData = Arrays.copyOf(elementData, elementData.length + (elementData.length >> 1));
            }
        }
        elementData[size++] = o;
        return true;
    }
    
    public int size() {
        return this.size;
    }
    
}
