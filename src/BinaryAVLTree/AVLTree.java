package BinaryAVLTree;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author jaime
 * @param <T>
 */
public class AVLTree<T extends Comparable<T>> implements AVLTreeADT<T>{
    private AVLNode<T> root;
    private int count;
    
    public AVLTree(){
        root = null;
        count = 0;
    }
    
    public int height(AVLNode<T> N){
        if (N == null)
            return 0;
        
        return N.getHeight();
    }
 
    private int max(int a, int b){
        return (a > b) ? a : b;
    }
 
    private AVLNode<T> rightRotate(AVLNode<T> y){
        AVLNode<T> x = y.getLeft();
        AVLNode<T> T2 = x.getRight();
 
        x.setRight(y);
        y.setLeft(T2);
 
        y.setHeight(max(height(y.getLeft()), height(y.getRight())) + 1);
        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1);
 
        return x;
    }
 
    private AVLNode<T> leftRotate(AVLNode<T> x){
        AVLNode<T> y = x.getRight();
        AVLNode<T> T2 = y.getLeft();
 
        y.setLeft(x);
        x.setRight(T2);
 
        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1);
        y.setHeight(max(height(y.getLeft()), height(y.getRight())) + 1);
 
        return y;
    }
 
    private int getBalance(AVLNode<T> N){
        if (N == null)
            return 0;
        return height(N.getLeft()) - height(N.getRight());
    }
    
    @Override
    public void insert(T element){
        AVLNode<T> node;
        AVLNode<T> parent = new AVLNode<>();
        AVLNode<T> nuevo = new AVLNode<>(element);
        
        if(root == null){
            root = nuevo;
            count++;
            return;
        }
        
        node = root;
        while(node != null){
            parent = node;
            if(element.compareTo(node.getElement()) <= 0 )
                node = node.getLeft();
            else
                node = node.getRight();
        }
        parent.hang(nuevo);
        count++;
        
        parent.setHeight(1 + max(height(parent.getLeft()), height(parent.getRight())));
 
        int balance = getBalance(parent);
 
        if (balance > 1 && element.compareTo(parent.getLeft().getElement()) < 0)
            rightRotate(node);
 
        if (balance < -1 && element.compareTo(parent.getRight().getElement()) > 0)
            leftRotate(node);
 
        if (balance > 1 && element.compareTo(parent.getLeft().getElement()) > 0){
            parent.setLeft(leftRotate(parent.getLeft()));
            rightRotate(node);
        }
 
        if (balance < -1 && element.compareTo(parent.getRight().getElement()) < 0){
            parent.setRight(rightRotate(parent.getRight()));
            leftRotate(node);
        }
    }
 
    private AVLNode<T> minValueNode(AVLNode<T> node){
        AVLNode<T> current = node;
 
        while (current.getLeft() != null)
            current = current.getLeft();
 
        return current;
    }
    
    @Override
    public T remove(T element){
        if(element == null)
            throw new RuntimeException("Element is null");
        
        return remove(root, element).getElement();
    }
 
    private AVLNode<T> remove(AVLNode<T> node, T key){
        if (node == null)
            return node;

        if (key.compareTo(node.getElement()) < 0)
            node.setLeft(remove(node.getLeft(), key));
 
        else if (key.compareTo(node.getElement()) > 0)
            node.setRight(remove(node.getRight(), key));
 
        else{
            if ((node.getLeft() == null) || (node.getRight() == null))
            {
                AVLNode<T> temp = null;
                if (temp == node.getLeft())
                    temp = node.getRight();
                else
                    temp = node.getLeft();
                
                if (temp == null){
                    temp = node;
                    node = null;
                }
                else
                    node = temp;
            }
            else{
                AVLNode<T> temp = minValueNode(node.getRight());
                node.setElement(temp.getElement());
                node.setRight(remove(node.getRight(), temp.getElement()));
            }
        }
 
        if (node == null)
            return node;
 
        node.setHeight(max(height(node.getLeft()), height(node.getRight())) + 1);
 
        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.getLeft()) >= 0)
            return rightRotate(node);
        
        if (balance < -1 && getBalance(node.getRight()) <= 0)
            return leftRotate(node);
        
        if (balance > 1 && getBalance(node.getLeft()) < 0){
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }
        
        if (balance < -1 && getBalance(node.getRight()) > 0){
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }
 
        return node;
    }
    
    public void preOrder(AVLNode<T> node){
        if (node != null)
        {
            System.out.print(node.getElement() + " ");
            preOrder(node.getLeft());
            preOrder(node.getRight());
        }
    }

    public int size() {
        return count;
    }
    
    public boolean contains(T element) {
        if(element == null)
            throw new RuntimeException("Element is null");
         
        return contains(element, root);
    } 
    
    public int height(){
        if(root == null)
            return 0;
        
        int sizeIzq = 0, sizeDer = 0;
        return height(root, sizeIzq,sizeDer);
    }
    
    private int height(AVLNode<T> actual, int size1, int size2){
        
        if(actual.getLeft() != null)
            size1 = height(actual.getLeft(),size1+1,size2);
        
        if(actual.getRight() != null)
                size2 = height(actual.getRight(),size1, size2+1);
                
        if(size1 > size2)
            return size1;
        else
            return size2;
    }
    
    private boolean contains(T element, AVLNode<T> actual){
        if(actual == null) 
            throw new RuntimeException("Element is not in AVLTree");
        if(element.equals(actual.getElement()))
            return true;
            
        contains(element, actual.getLeft());
        contains(element, actual.getRight());
        
        
        return false;
    }
    
    public Iterator<T> preOrderIterator() {
        ArrayList<T> lista = new ArrayList<>();
        
        preOrder(root,lista);
        return lista.iterator();
    }

    public Iterator<T> postOrderIterator() {
        ArrayList<T> lista = new ArrayList<>();
        
        postOrder(root,lista);
        return lista.iterator();
    }

    public Iterator<T> inOrderIterator() {
        ArrayList<T> lista = new ArrayList<>();
        
        inOrder(root,lista);
        return lista.iterator();
    }
    
    private void preOrder(AVLNode<T> actual, ArrayList<T> list){
        if(actual == null)
            return;
        
        list.add(actual.getElement());
        preOrder(actual.getLeft(),list);
        preOrder(actual.getRight(),list);
    }
    
    private void postOrder(AVLNode<T> actual, ArrayList<T> list){
        if(actual == null)
            return;
        
        postOrder(actual.getLeft(),list);
        postOrder(actual.getRight(),list);
        list.add(actual.getElement());
        
    }
    
    private void inOrder(AVLNode<T> actual, ArrayList<T> list){
        if(actual == null)
            return;
        
        inOrder(actual.getLeft(),list);
        list.add(actual.getElement());
        inOrder(actual.getRight(),list);
    }

    public AVLNode<T> getRoot(){
        return root;
    }
}
