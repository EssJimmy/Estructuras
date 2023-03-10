package BinarySearchTree;

import java.util.Objects;

/**
 *
 * @author jaime
 * @param <T>
 */
public class BSTNode<T extends Comparable<T>> {
    private T element;
    BSTNode<T> left, right, parent;
    
    public BSTNode(){
        element = null;
        left = null;
        right = null;
        parent = null;
    }
    
    public BSTNode(T element){
        this.element = element;
        left = null;
        right = null;
        parent = null;
    }
    
    public void setLeft(BSTNode<T> left){
        this.left = left;
        left.setParent(this);
    }
    
    public void setRight(BSTNode<T> right){
        this.right = right;
        right.setParent(this);
    }
    
    public void setParent(BSTNode<T> parent){
        this.parent = parent;
    }
    
    public void setElement(T element){
        this.element = element;
    }
    
    public void hang(BSTNode<T> child){
        if(child == null)
            throw new RuntimeException("Child is null");
        
        if(child.getElement().compareTo(element) < 0)
            setLeft(child);
        else
            setRight(child);
        child.setParent(this);
    }  
    
    public BSTNode <T> getParent(){
        return parent;
    }
    
    public BSTNode<T> getLeft(){
        return left;
    }
    
    public BSTNode<T> getRight(){
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
