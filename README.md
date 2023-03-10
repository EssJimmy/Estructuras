# Árboles AVL
## Métodos auxiliares para rotación, inserción y eliminación.
### height(AVLNode Node)
Regresa la altura de un nodo que es el factor de equilibrio, es para evitar escribir algo así como `nodo.getLeft().getRight()` y se vea un poco más legible el código, es un método privado y no afecta la función del código sin él.
```java
    public int height(AVLNode<T> N){
        if (N == null)
            return 0;
        
        return N.getHeight();
    }
```

### max(int a, int b)
Regresa el número máximo entre dos enteros, está pensado para regresar el factor de equilibrio máximo entre dos nodos, esto se vera más en la parte de rotaciones.
```java
    private int max(int a, int b){
        return (a > b) ? a : b;
    }
```

### getBalance(AVLNode n)
Regresa el factor de equilibrio de un nodo n, si es nulo el nodo regresa 0.
```java
    private int getBalance(AVLNode<T> N){
        if (N == null)
            return 0;
        return height(N.getLeft()) - height(N.getRight());
    }
```

### minValueNode(AVLNode node)
Regresa el nodo con el menor valor en el árbol, esto es, regresa el nodo que esté más hasta la izquierda.
```java
    private AVLNode<T> minValueNode(AVLNode<T> node){
        AVLNode<T> current = node;
        
        while (current.getLeft() != null)
            current = current.getLeft();
 
        return current;
    }
```

## Rotaciones.
Tenemos cuatro principales rotaciones:
  * Izquierda-izquierda.
  * Derecha-derecha.
  * Izquierda-derecha.
  * Derecha-izquierda.

Programé métodos para las dos primeras rotaciones, los demás están incluidos en el código de inserción y eliminación.

### leftRotate(AVLNode x)
Empezamos declarando dos nodos auxiliares para la rotación. El primer nodo (llamemoslo `y`) auxiliar tomará el valor del hijo derecho de `x`. El segundo nodo auxiliar (llamemoslo `t`) tomará el valor del hijo izquierdo de `y`.

Ahora viene la parte buena:
  1) Como `x` es menor que `y` hacemos que el hijo izquierdo de `y` sea `x`.
  2) Hacemos que `x` deje de apuntar a `y` y ahora apunta hacia `t`.
  3) Actualizamos la altura de los nodos, excepto de `t`.

Esta función regresa un nodo AVL por que lo utilizaremos para las últimas dos rotaciones.

```java
    private AVLNode<T> leftRotate(AVLNode<T> x){
        AVLNode<T> y = x.getRight();
        AVLNode<T> T2 = y.getLeft();
 
        y.setLeft(x);
        x.setRight(T2);
 
        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1);
        y.setHeight(max(height(y.getLeft()), height(y.getRight())) + 1);
 
        return y;
    
```

### rightRotate(AVLNode x)
Este método hace exactamente lo mismo que el anterior, simplemente hacia el otro sentido, por lo que (manteniendo los mismos nombres de nodos que había dicho) `y` toma el valor del hijo izquierdo de `x` y `t` toma el valor del hijo derecho de `y`

Ahora viene la parte buena:
  1) Como `x` es mayor que `y` hacemos que el hijo derecho de `y` sea `x`.
  2) Hacemos que `x` deje de apuntar a `y` y ahora apunta hacia `t`.
  3) Actualizamos la altura de los nodos, excepto de `t`.

Esta función también regresa un nodo AVL para los dos últimos casos de rotaciones.

```java
    private AVLNode<T> rightRotate(AVLNode<T> y){
        AVLNode<T> x = y.getLeft();
        AVLNode<T> T2 = x.getRight();
 
        x.setRight(y);
        y.setLeft(T2);
 
        y.setHeight(max(height(y.getLeft()), height(y.getRight())) + 1);
        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1);
 
        return x;
    }
```

**Nota: los métodos hacen alución hacía el lado al que giran, no al caso que cumplen**

***Nota: `t` existe en el dado caso de que `y` fuera a tener un hijo, si no lo tiene lo único que se le está colgando a `x` es un null.***

## Inserción.
La inserción en mi código de AVL tiene dos partes claras: la inserción y la rotación.

La inserción se hace exactamente igual que la de un árbol de búsqueda binario como el siguiente código.
```java
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
```
Se revisa primero si la raíz del árbol es nula, si lo es, entonces se inserta el nuevo nodo como raíz del árbol y acaba el método.

Si la raíz no es nula entonces procedemos a recorrer el árbol hasta encontrar el lugar donde vamos a insertar el nodo, yendonos a la parte de rotaciones. 

Empezamos actualizando la altura del padre del nodo insertado, esto es para revisar si se desbalanceó y tenemos que hacer una rotación.

`parent.setHeight(1 + max(height(parent.getLeft()), height(parent.getRight())));`

La altura de un nodo está en valor absoluto, por eso podemos usar la función max sin que el número mayor siempre sea el del nodo derecho, y le sumamos 1 por que estamos contando la altura del propio nodo padre también.

Después checamos el balance del nodo, que es aquí donde determinaremos la parte de las rotaciones, esto lo haremos con la funcion `getBalance(AVLNode n)` que ya habíamos comentado.

Después pasamos a checar si hay un desbalanceo en el nodo padre del que acaba de ser insertado con ifs, y hacemos el caso que necesitemos.
Casos Normales:
* Caso Izquierda-izquierda.
```java
    if (balance > 1 && element.compareTo(parent.getLeft().getElement()) < 0)
      rightRotate(node);
```
* Caso Derecha-derecha.
```java
    if (balance < -1 && element.compareTo(parent.getRight().getElement()) > 0)
      leftRotate(node);
```
Casos dobles: en el caso de los casos izquierda-derecha y derecha-izquierda tenemos que hacer una doble rotación, por lo que aquí es donde nos sirve que los métodos `leftRotate` y `rightRotate` nos regresen un nodo AVL

* Caso Izquierda-derecha.
```java
    if (balance > 1 && element.compareTo(parent.getLeft().getElement()) > 0){
      parent.setLeft(leftRotate(parent.getLeft()));
      rightRotate(node);
  }
```
* Caso Derecha-izquierda.
```java
    if (balance < -1 && element.compareTo(parent.getRight().getElement()) < 0){
      parent.setRight(rightRotate(parent.getRight()));
      leftRotate(node);
  }
```

## Eliminación.
Como en inserción, el primer paso para eliminar en árboles AVL es usar el algoritmo de árboles binarios de búsqueda para borrar, de ahí, haremos rotaciones justo como en el caso de inserción. En mi caso para facilitarme las cosas dividí el método en dos partes para hacer recursión indirecta.

Parte pública:
```java
    @Override
    public T remove(T element){
        if(element == null)
            throw new RuntimeException("Element is null");
        
        return remove(root, element).getElement();
    }
```
Parte Privada:
### 1) Eliminación en BST (método recursivo).
Los primeros ifs se encargan de recorrer el arreglo para encontrar el elemento que queremos eliminar (es hacer búsqueda binaria básicamente). En el else grandote nos encargamos de borrar por casos:
* Caso 1: Nodo hoja.
* Caso 2: nodo con 1 hijo.
* Caso 3: nodo con 2 hijos.

**Nota: estos casos consideran si hablamos de la raíz o no.**

```java
        if (node == null)
            return node;
 
        if (key.compareTo(node.getElement()) < 0)
            node.setLeft(remove(node.getLeft(), key));
 
        else if (key.compareTo(node.getElement()) > 0)
            node.setRight(remove(node.getRight(), key))
          
        else
        {
 
            if ((node.getLeft() == null) || (node.getRight() == null)){
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
            else
            {
                AVLNode<T> temp = minValueNode(node.getRight());
                node.setElement(temp.getElement());
                node.setRight(remove(node.getRight(), temp.getElement()));
            }
        }
 
        if (node == null)
            return node;
```
### 2) Rotaciones (es exactamente lo mismo que inserción).
Empezamos actualizando la altura del nodo padre y sumándole 1, (como usamos la función max, nos regresa la máxima altura del lado izquierdo y derecho del nodo, le debemos sumar 1 porque estamos considerando la altura del propio nodo con el que llamamos la función.
Obtenemos el balance del nodo actual, y procedemos a checar los casos para ver si está desbalanceado, la misma historia que con inserción.

```java
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
```
