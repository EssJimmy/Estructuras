package BinarySearchTree;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author jaime
 * @param <T>
 */
public class BinarySearchTree<T extends Comparable<T>> implements BinarySearchTreeADT<T>{
    private BSTNode<T> root;
    private int count;
    
    public BinarySearchTree(){
        root = null;
        count = 0;
    }
    
    //Método de fuerza bruta
    @Override
    public BSTNode<T> searchRec(T element){
        if(element == null) throw
                new RuntimeException("Element is null");
        
        return search(root, element);
    }
    
    private BSTNode<T> search(BSTNode<T> actual, T element){
        if(actual == null)
            return null;
        if(actual.getElement().equals(element))
            return actual;
        
        BSTNode<T> res = search( actual.getLeft(), element);
            if(res == null)
                res = search( actual.getRight(), element);
        return res;
    }
    
    @Override
    public void add(T element){
        BSTNode<T> actual;
        BSTNode<T> parent = new BSTNode<>();
        BSTNode<T> nuevo = new BSTNode<>(element);
        
        if(root == null){
            root = nuevo;
            count++;
            return;
        }
        
        actual = root;
        while(actual != null){
            parent = actual;
            if(element.compareTo(actual.getElement()) <= 0 )
                actual = actual.getLeft();
            else
                actual = actual.getRight();
        }
        parent.hang(nuevo);
        count++;
    }
    
    @Override
    public T remove(T element){
        if(element == null) throw
                new RuntimeException("Element is null");
        
        BSTNode<T> actual = search(element);
        BSTNode<T> parent;
        BSTNode<T> child;
        T aux = null;
        
        if(actual == null)
            throw new RuntimeException("No such element in BST");
        
        count--;
        
        //Caso 1: 0 hijos, hoja sola
        if(actual.getLeft() == null && actual.getRight() == null){
            //Caso 1a
            if(actual.equals(root)){
                aux = root.getElement();
                root = null;
            }
            //Caso 1b
            else{
                parent = actual.getParent();

                if(parent.getElement().compareTo(element) < 0){
                    aux = actual.getElement();
                    parent.setLeft(null);

                }
                else{
                    aux = actual.getElement();
                    parent.setRight(null);

                }    
            }
        }
        else{
            //Caso 2: 2 hijos
            if(actual.getLeft() != null && actual.getRight() != null){
                aux = actual.getElement();
                parent = actual;
                actual = actual.getRight();

                while(actual.getLeft() != null)
                    actual = actual.getLeft();

                if(actual.getRight() != null)
                    actual.getParent().setRight(actual.getRight());

                parent.setElement(actual.getElement());
                actual.getParent().setLeft(null);
            }
            //Caso 3: 1 hijo
            else{
                if(actual.getLeft() != null || actual.getRight() != null){
                    if(actual.getLeft() != null)
                        child = actual.getLeft();
                    else
                        child = actual.getRight();

                    if(actual.equals(root)){
                        aux = actual.getElement();
                        root = child;
                    }
                    else{
                        aux = actual.getElement();
                        parent = actual.getParent();
                        
                        if(actual.getElement().compareTo(parent.getElement())>0)
                            parent.setRight(null);
                        else
                            parent.setLeft(null);
                        
                        parent.hang(child);
                    }
                }
            }
        }
        
        return aux;
    }
    
    private BSTNode<T> search(T element){
        if(element == null)
            throw new RuntimeException("Element is null");
        
        BSTNode<T> actual = root;
        
        while(actual != null && !actual.getElement().equals(element)){
            if(element.compareTo(actual.getElement()) < 0)
                actual = actual.getLeft();
            else
                actual = actual.getRight();
        }
            
        return actual;
    }
   
    @Override
    public T findMin(){
        BSTNode<T> actual;
        
        if(root == null) throw 
                new RuntimeException("Binary Tree is empty");
        
        actual = root;
        while(actual.getLeft() != null)
            actual = actual.getLeft();
        
        
        return actual.getElement();
    }
    
    @Override
    public T findMax(){
        BSTNode<T> actual;
        
        if(root == null) throw 
            new RuntimeException("Binary Tree is empty");
        
        actual = root;
        while(actual.getRight() != null)
            actual = actual.getRight();
                
        return actual.getElement();
    }
    
    @Override
    public boolean isEmpty(){
        return size() == 0;
    }

    @Override
    public int size() {
        return count;
    }
    
    @Override
    public boolean contains(T element) {
        if(element == null)
            throw new RuntimeException("Element is null");
         
        return contains(element, root);
    }  
    
    //Métodos iterativos
    /*@Override
    public Iterator<T> preOrderIterator(){
        Stack<BinaryNode<T>> stackAux = new Stack<>();
        ArrayList<T> list = new ArrayList<>();
        BSTNode<T> actual;
        stackAux.push(root);
        
        while(!isEmpty()){
            actual = stackAux.pop();
            list.add(actual.getElement());
            if(actual.getRight() != null)
                stackAux.push(actual.getRight());
            if(actual.getLeft() != null)
                stackAux.push(actual.getLeft());
        }
       
        return list.iterator();
    }*/
    
    /*@Override
    public Iterator<T> postOrderIterator(){
        Queue<BinaryNode<T>> queueAux = new ArrayDeque<>();
        ArrayList<T> list = new ArrayList<>();
        BSTNode<T> actual;
        queueAux.add(root);
        
        while(!queueAux.isEmpty()){
            actual = queueAux.remove();
            list.add(actual.getElement());
            if(actual.getLeft() != null)
                queueAux.add(actual.getLeft());
            if(actual.getRight() != null)
                queueAux.add(actual.getRight());
        }
        
        return list.iterator();
    }*/

    @Override
    public int height(){
        if(root == null)
            return 0;
        
        int sizeIzq = 0, sizeDer = 0;
        return height(root, sizeIzq,sizeDer);
    }
    
    private int height(BSTNode<T> actual, int size1, int size2){
        
        if(actual.getLeft() != null)
            size1 = height(actual.getLeft(),size1+1,size2);
        
        if(actual.getRight() != null)
                size2 = height(actual.getRight(),size1, size2+1);
                
        if(size1 > size2)
            return size1;
        else
            return size2;
    }
    
    private boolean contains(T element, BSTNode<T> actual){
        if(actual == null) 
            throw new RuntimeException("Element is not in BT");
        if(element.equals(actual.getElement()))
            return true;
            
        contains(element, actual.getLeft());
        contains(element, actual.getRight());
        
        
        return false;
    }
    
    @Override
    public Iterator<T> preOrderIterator() {
        ArrayList<T> lista = new ArrayList<>();
        
        preOrder(root,lista);
        return lista.iterator();
    }

    @Override
    public Iterator<T> postOrderIterator() {
        ArrayList<T> lista = new ArrayList<>();
        
        postOrder(root,lista);
        return lista.iterator();
    }

    @Override
    public Iterator<T> inOrderIterator() {
        ArrayList<T> lista = new ArrayList<>();
        
        inOrder(root,lista);
        return lista.iterator();
    }
    
    private void preOrder(BSTNode<T> actual, ArrayList<T> list){
        if(actual == null)
            return;
        
        list.add(actual.getElement());
        preOrder(actual.getLeft(),list);
        preOrder(actual.getRight(),list);
    }
    
    private void postOrder(BSTNode<T> actual, ArrayList<T> list){
        if(actual == null)
            return;
        
        postOrder(actual.getLeft(),list);
        postOrder(actual.getRight(),list);
        list.add(actual.getElement());
        
    }
    
    private void inOrder(BSTNode<T> actual, ArrayList<T> list){
        if(actual == null)
            return;
        
        inOrder(actual.getLeft(),list);
        list.add(actual.getElement());
        inOrder(actual.getRight(),list);
    }
    
    @Override
    public BSTNode getRoot(){
        return root;
    }
    
}
