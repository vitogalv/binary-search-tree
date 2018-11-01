package BinarySearchTree;

import java.io.Serializable;

public class Entry<E extends Comparable> implements Serializable {

    /**
     * The object that this is to hold
     */
    private E _element;

    /**
     * The linked entries
     */
    private Entry<E> _parent, _right, _left;

    public Entry(E elem){
        setElement(elem);
    }

    //setters
    public void setParent(Entry<E> ent){
        _parent = ent;
    }
    public void setRight(Entry<E> ent){
        _right = ent;
    }
    public void setLeft(Entry<E> ent) {
        _left = ent;
    }
    public void setElement(E elem){
        if(elem == null){
            throw new NullPointerException();
        }
        _element = elem;
    }

    /**
     * @return true if this entry is a leaf
     */
    public boolean isLeaf(){
       return _left == null && _right == null;
    }

    //getters
    public Entry<E> getParent(){
        return _parent;
    }

    public Entry<E> getRight(){
        return _right;
    }

    public Entry<E> getLeft(){
        return _left;
    }

    public E getElement(){
        return _element;
    }

}
