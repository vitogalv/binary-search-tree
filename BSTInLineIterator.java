package BinarySearchTree;

import java.util.Iterator;

/**
 * An object of this class will be used to iterate over a {@link BinarySearchTree} in an inline fashion
 */

public class BSTInLineIterator<T extends Comparable> implements Iterator<T> {

    /**
     * The entry that the iterator is ready to return
     */
    private Entry<T> _current;

    /**
     * the tree being iterated over
     */
    private SearchTree<T> _bst;

    public BSTInLineIterator(SearchTree<T> bst){
        _bst = bst;
        _current = getFirst();
    }


    @Override
    public boolean hasNext() {
        return _current != null;
    }

    @Override
    public T next() {
        T retVal = _current.getElement();
        _current = _bst.getSuccessor(_current);
        return retVal;
    }

    /**
     * @return the first {@link Entry} in the tree by order
     */
    private Entry<T> getFirst(){
        Entry<T> retVal = _bst.getRoot();
        if(retVal != null){
            while(retVal.getLeft() != null){
                retVal = retVal.getLeft();
            }
        }
        return retVal;
    }


}
