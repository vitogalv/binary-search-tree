package BinarySearchTree;

public interface SearchTree<E extends Comparable> {
    boolean add(E elem);
    boolean remove(E elem);
    Entry<E> getEntry(E elem);
    int size();
    int compare(E elem, Entry<E> ent);
    Entry<E> getSuccessor(Entry<E> node);
    Entry<E> getPredecessor(Entry<E> node);
    Entry<E> getRoot();
}
