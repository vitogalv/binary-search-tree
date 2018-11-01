package BinarySearchTree;


import java.io.Serializable;
import java.util.Comparator;

/**
 * A vanilla binary search tree
 * @param <E>
 */
public class BinarySearchTree<E extends Comparable<E>> implements SearchTree<E>, Serializable {

    private Entry<E> _root;
    private Comparator<E> _comparator;
    private int _size;
    /**
     * Will create an empty tree ordered naturally
     */
    public BinarySearchTree(){ _size = 0; }

    /**
     * Will create a new tree ordered with defined comparator.
     * @param root the root entry
     * @param comparator the comparator to determine ordering, if null natural ordering used
     */
    public BinarySearchTree(Entry<E> root, Comparator<E> comparator){
        setRoot(root);
        _comparator = comparator;
        _size = 1;
    }

    public BinarySearchTree(Comparator<E> comparator){
        _size = 0;
        _comparator = comparator;
    }

    /**
     * @return the number of Entries in the tree
     */
    public int size(){
        return _size;
    }
    /**
     * @param root starting point
     * @throws NullPointerException if roots element is null
     */
    private void setRoot(Entry<E> root){
        if(root.getElement() == null){
            throw new NullPointerException();
        }
        if(_root == null){
            _size++;
        }
        _root = root;
    }

    /**
     * @return the root {@link Entry}
     */
    @Override
    public Entry<E> getRoot(){
        return _root;
    }

    /**
     * @param elem non-null comparable element of type E
     * @return true if the add was successful
     * @throws NullPointerException if {@param elem} is null
     */
    public boolean add(E elem){
        if(elem == null){
            throw new NullPointerException();
        }
        if(_root == null){
            _root = new Entry<>(elem);
            _size++;
            return true;
        }
        return add(elem, _root);
    }

    /**
     * This method adds elements using naturally ordering if there is no comparator
     * @param node is the cursor for the addition
     * @param elem the element to be added
     * @return true if the element was successfully added to the tree, false if element already exists in tree
     */
    private boolean add(E elem, Entry<E> node) {
        int comparison = compare(elem, node);

        if (comparison > 0) {
            Entry<E> right = node.getRight();
            if (right == null) {
                addToRight(node, elem);
                _size++;
                return true;
            }
            return add(elem, right);
        } else if(comparison < 0) {
            Entry<E> left = node.getLeft();
            if (left == null) {
                addToLeft(node, elem);
                _size++;
                return true;
            }
            return add(elem, node.getLeft());
        }
        return false;
    }

    /**
     * This method will create a new {@link Entry} with {@param elem} as it's element and establish proper links
     * @param node the parent node of the new {@link Entry}
     * @param elem the element of the new {@link Entry}
     */
    private void addToLeft(Entry<E> node, E elem){
        Entry<E> elemEnt = new Entry<>(elem);
        elemEnt.setParent(node);
        node.setLeft(elemEnt);
    }

    /**
     * This method does the same thing as addToLeft but adds the new Entry to the right
     * @param node the parent node of the new {@link Entry}
     * @param elem the element of the new {@link Entry}
     */
    private void addToRight(Entry<E> node, E elem){
        Entry<E> elemEnt = new Entry<>(elem);
        elemEnt.setParent(node);
        node.setRight(elemEnt);
    }

    /**
     * Method will remove the first instance equal to {@param elem}
     * @param elem is the element to be removed
     * @return false if the item could not be found
     * @throws NullPointerException when the root is null and tree is empty
     */
    public boolean remove(E elem){
        Entry<E> match = getEntry(elem);
        if(match.isLeaf()){
            removeLeaf(match);
        }else{
            removeInterior(match);
        }
        _size--;
        return true;
    }

    /**
     * Check if a node is a lead
     * @param node the Entry that may or may not be a leaf
     * @return true of {@param node} is a leaf
     */
    private boolean isLeaf(Entry<E> node){
        return node.getLeft() == null && node.getRight() == null;
    }

    /**
     * This method will remove {@param leaf}
     * @param leaf is the node to be removed
     */
    private void removeLeaf(Entry<E> leaf){
        if(!isLeaf(leaf)){
            throw new IllegalArgumentException();
        }
        Entry<E> parent = leaf.getParent();
        if(parent == null){
            _root = null;
            return;
        }
        if(parent.getLeft() == leaf){
            parent.setLeft(null);
        }else{
            parent.setRight(null);
        }
    }

    /**
     * @param node the node to be removed
     */
    private void removeInterior(Entry<E> node){
        Entry<E> replacement;
        if(node.getRight() != null){
            replacement = getSuccessor(node);
        }else{
            replacement = getPredecessor(node);
        }
        node.setElement(replacement.getElement());
        removeLeaf(replacement);
    }

    /**
     * @param elem the element being looked for
     * @return the entry that contains the given element
     */
    public Entry<E> getEntry(E elem){
        return getEntry(elem, _root);
    }
    private Entry<E> getEntry(E elem, Entry<E> node){
        int comparison = compare(elem, node);

        if(comparison == 0){
            return node;
        }else if(comparison > 0){
            return getEntry(elem, node.getRight());
        }else{
            return getEntry(elem, node.getLeft());
        }
    }

    /**
     * This method will compare using the comparator if present, and naturally if not
     * @param elem the element to be compared with the element in contained in {@param node}
     * @param node the {@link Entry} that contains the element being compared to {@param elem}
     * @return the same as Comparable and Comparator
     * @throws NullPointerException when {@param node} is null
     */
    @Override
    public int compare(E elem, Entry<E> node){
        if(node == null){
            throw new NullPointerException();
        }
        int comparison = elem.compareTo(node.getElement());
        if(_comparator != null){
            comparison = _comparator.compare(elem, node.getElement());
        }
        return comparison;
    }

    /**
     * @param node the node who's successor is to be returned
     * @return the successor of {@param node}
     * @throws NullPointerException when {@param node} is null
     */
    @Override
    public Entry<E> getSuccessor(Entry<E> node){
        if(node == null){
            throw new NullPointerException();
        }
        Entry<E> trav;
        if(node.getRight() != null){
            trav = node.getRight();
            while(trav.getLeft() != null){
                trav = trav.getLeft();
            }
            return trav;
        }else{
            Entry<E> parent = node.getParent();
            if(parent == null){
                return null;
            }
            int comp = compare(node.getElement(), parent);
            trav = parent;
            while(comp > 0 && trav != null){
                trav = trav.getParent();
                try{
                    comp = compare(node.getElement(), trav);
                }catch(NullPointerException e){
                    return trav;
                }
            }
            return trav;
        }
    }

    /**
     * @param node is the node who's predecessor is to be returned
     * @return the predecessor of the given node and null if there is no predecessor
     * @throws NullPointerException if {@param node} is null
     */
    @Override
    public Entry<E> getPredecessor(Entry<E> node){
        if(node == null){
            throw new NullPointerException();
        }
        Entry<E> retVal = null;
        if(node.getLeft() != null) {
            retVal = node.getLeft();
            while(retVal.getRight() != null){
                retVal = retVal.getRight();
            }
        }
        return retVal;
    }

}
