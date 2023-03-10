package BinarySearchTree;
import java.util.Iterator;


public interface BinarySearchTreeADT<T extends Comparable<T>>{
    public BSTNode<T> searchRec(T element);
    
    public void add(T element);
    
    public T remove(T element);
    
    public T findMin();
    
    public T findMax();
    
    public boolean isEmpty();
    
    public int size();
    
    public boolean contains(T element);
    
    public BSTNode getRoot();
    
    public Iterator<T> preOrderIterator();
    
    public Iterator<T> postOrderIterator();
    
    public Iterator<T> inOrderIterator();
    
    public int height();
}
