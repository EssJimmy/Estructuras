package BinaryAVLTree;

import BinarySearchTree.BSTNode;
import java.util.Objects;

/**
 *
 * @author jaime
 * @param <T>
 */
public class AVLNode<T extends Comparable<T>>{
    private T element;
    private AVLNode<T> right, left, parent;
    private int height;
 
    public AVLNode(){
        element = null;
        right = null;
        left = null;
        parent = null;
        height = 1;
    }
    
    public AVLNode(T element) {
        this.element = element;
        right = null;
        left = null;
        parent = null;
        height = 1;     
    }
    
    public void setHeight(int newHeight){
        height = newHeight;
    }
    
    public int getHeight(){
        return height;
    }
    
    public void setLeft(AVLNode<T> left){
        this.left = left;
        left.setParent(this);
    }
    
    public void setRight(AVLNode<T> right){
        this.right = right;
        right.setParent(this);
    }
    
    public void setParent(AVLNode<T> parent){
        this.parent = parent;
    }
    
    public void setElement(T element){
        this.element = element;
    }
    
    public void hang(AVLNode<T> child){
        if(child == null)
            throw new RuntimeException("Child is null");
        
        if(child.getElement().compareTo(element) < 0)
            setLeft(child);
        else
            setRight(child);
        child.setParent(this);
    }  
    
    public AVLNode <T> getParent(){
        return parent;
    }
    
    public AVLNode<T> getLeft(){
        return left;
    }
    
    public AVLNode<T> getRight(){
        return right;
    }
    
    public T getElement(){
        return element;
    }

    @Override
    public boolean equals(Object other){
        if(other == null)
            throw new RuntimeException("Other is null");
        
        if(other instanceof BSTNode bn)
            if(element != null && bn.getElement() != null)
                return element.equals(bn.getElement());
            else
                return false;
        else
            return false;
        
    }
    
    public int numDesc(){
        int cont = 0;
        
        if(left != null)
            cont = left.numDesc() + 1;
        
        if(right != null)
            cont += right.numDesc() + 1;
        
        return cont;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.element);
        return hash;
    }
}
