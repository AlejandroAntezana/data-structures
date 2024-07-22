package conjuntistas.dinamicas;

import lineales.dinamicas.Lista;

public class ArbolBB {
    private NodoABB raiz;

    //Constructor
    public ArbolBB() {
        this.raiz = null;
    }

    public boolean insertar(Comparable valor){
        boolean succes = true;

        if(this.raiz != null){
            //Si el arbol no está vacio llamo a un metodo auxiliar para realizar la insercion.
            succes = insertarAux(this.raiz, valor);
        }else {
            //si el arbol esta vacio le asigno el valor a la raiz.
            NodoABB raiz = new NodoABB(valor, null, null);
            this.raiz = raiz;
            succes = true;
        }
        return succes;
    }

    private boolean insertarAux(NodoABB nodo, Comparable valor){
        boolean succes = true;

        //Si el elemento guardado en el nodo es igual al valor que quiero insertar
        if(valor.compareTo(nodo.getElemento()) == 0){
            succes = false;
        } else if(valor.compareTo(nodo.getElemento()) < 1) {
            //si el valor es menor al elemento del nodo pregunto si tiene Hijo Izq.
            if(nodo.getIzquierdo() != null){
                //Si tiene HI vuelvo a llamar recursivamente con el hijo izq.
                succes = insertarAux(nodo.getIzquierdo(), valor);
            }else{
                //Si no tiene HI creo un nuevo nodo y le asigno el valor.
                NodoABB nuevoHijoIzq = new NodoABB(valor, null, null);
                //Enlazo al nuevo nodo como hijo izquierdo.
                nodo.setIzquierdo(nuevoHijoIzq);
            }
        }else{
            //Si el valor es mayor al elemento del nodo pregunto si tiene Hijo derecho.
            if(nodo.getDerecho() != null){
                //Si tiene vuelvo a llamar recursivamente con el Hijo derecho.
                succes = insertarAux(nodo.getDerecho(), valor);
            }else{
                //Si no tiene HD creo el nuevo nodo y le asigno valor.
                NodoABB nuevoHijoDer = new NodoABB(valor, null, null);
                //enlazo el nuevo nodo como hijo derecho.
                nodo.setDerecho(nuevoHijoDer);
            }
        }
        return succes;
    }

    public boolean pertenece(Comparable valor){
        //Devuelve True si el valor pasado por parametro se encuentra en el arbol. Devuelve False si no se encuentra.
        boolean pertenece = false;
        if(this.raiz != null){
            pertenece = perteneceAux(this.raiz, valor);
        }
        return pertenece;
    }

    private boolean perteneceAux(NodoABB nodo, Comparable valor){
        boolean encontrado = false;

        if(valor.compareTo(nodo.getElemento()) == 0){
          //si valor es igual al elemento del nodo
          encontrado = true;
        } else {
            if(valor.compareTo(nodo.getElemento()) < 0){
                //SI valor < elemento entonces busco en el subarbol izquierdo.
                if(nodo.getIzquierdo() != null) {
                    encontrado = perteneceAux(nodo.getIzquierdo(), valor);
                }
            }else {
                //Si valor > elemento entonces busco en el subarbol Derecho
                if(nodo.getDerecho() != null){
                encontrado = perteneceAux(nodo.getDerecho(), valor);
                }
            }
        }
        return encontrado;
    }

    public boolean eliminar(Comparable valor) {
        /* Recibe el elemento que se desea eliminar y se procede a removerlo del árbol. Si no se encuentra
        el elemento no se puede realizar la eliminación. Devuelve verdadero si el elemento se elimina de la
        estructura y falso en caso contrario.*/
        boolean succes = false;

        if(this.raiz != null){
            succes = eliminarAux(this.raiz, null, valor);
        }
        return succes;
    }

    private boolean eliminarAux(NodoABB nodo, NodoABB padre, Comparable valor){
        boolean eliminado = false;

        if(valor.compareTo(nodo.getElemento()) == 0){
            //Si el nodo contiene el elemento analizo que caso aplicar
            if(nodo.getIzquierdo() == null && nodo.getDerecho() == null){
                //Caso 1: El nodo es una hoja
                caso1(valor, padre);
            } else if(nodo.getIzquierdo() != null && nodo.getDerecho() != null) {
                //Caso 3: El nodo tiene ambos hijos
                caso3(nodo);
            }else {
                //Caso 2: El nodo tiene un solo hijo
                caso2(valor, nodo, padre);
            }
            eliminado = true;
        }else{
            //Si el nodo no contiene el valor buscado sigo buscando.
            int value = valor.compareTo(nodo.getElemento());
            if (value < 1){ //Voy al subarbol izquierdo
                if(nodo.getIzquierdo() != null) {
                    eliminado = eliminarAux(nodo.getIzquierdo(), nodo, valor);
                }
            }else{ //voy al subarbol derecho
                if(nodo.getDerecho() != null) {
                    eliminado = eliminarAux(nodo.getDerecho(), nodo, valor);
                }
            }

        }
        return eliminado;
    }

    private void caso1(Comparable valor, NodoABB padre){
        if (padre == null){
            //caso especial de que el elemento a eliminar sea la raiz del arbol
            this.raiz = null;
        }else {
            int aux = valor.compareTo(padre.getElemento());
            if(aux < 1){
                //si valor < padre entonces elimino el Hijo Izquierdo
                padre.setIzquierdo(null);
            }else {
                //caso contrario elimino el Hijo derecho
                padre.setDerecho(null);
            }
        }
    }

    private void caso2(Comparable valor, NodoABB nodo, NodoABB padre){
        NodoABB hijoIzquierdo = nodo.getIzquierdo();
        NodoABB hijoDerecho = nodo.getDerecho();

        if(padre == null){
            //caso especial que se elimine la raiz con un solo hijo.
            if (hijoIzquierdo != null){
                this.raiz = hijoIzquierdo;
            }else{
                this.raiz = hijoDerecho;
            }
        }else{
            int aux = valor.compareTo(padre.getElemento());
            if(aux < 1){ //si el nodo a eliminar esta a la izquierda del padre
                if(hijoIzquierdo != null){
                    padre.setIzquierdo(hijoIzquierdo);
                }else{
                    padre.setIzquierdo(hijoDerecho);
                }
            }else{
                if(hijoIzquierdo != null){
                    padre.setDerecho(hijoIzquierdo);
                }else{
                    padre.setDerecho(hijoDerecho);
                }
            }
        }
    }

    /*Usar el candidato B: el menor elemento del subArbol derecho de nodo. */
    private void caso3(NodoABB nodo){
        /*Las variables nodoActual y nodoSiguiente sirven para poder desvincular el nodo hoja, que corresponde
        * al candidato B.*/
        NodoABB nodoActual = nodo.getDerecho();
        NodoABB nodoSiguiente = nodo.getDerecho().getIzquierdo();

        if (nodoSiguiente == null){
            nodo.setElemento(nodoActual.getElemento());
            nodo.setDerecho(null);
        }else {
            //iniciamos la busqueda en la raiz del subarbol derecho de nodo
            while (nodoSiguiente != null) {
                if (nodoSiguiente.getIzquierdo() == null) {
                    //pregunto si encontre la hoja
                    nodo.setElemento(nodoSiguiente.getElemento()); //si la encuentro entonces le asigno su valor a nodo
                    nodoActual.setIzquierdo(null); //luego desvinculo la hoja del arbol
                }
                //actualizamos los valores del bucle
                nodoActual = nodoActual.getIzquierdo();
                nodoSiguiente = nodoSiguiente.getIzquierdo();
            }
        }
    }

    public boolean esVacio(){
        /*Devuelve falso si hay al menos un elemento en el árbol y verdadero en caso contrario.*/
        return this.raiz == null;
    }

    public void vaciar(){
        /*Vacia el arbol actual asignando null a la raiz.*/
       this.raiz = null;
    }

    public Lista listar(){
        /*recorre el árbol completo y devuelve una lista ordenada con los elementos
         que se encuentran almacenados en él.*/
        Lista lista = new Lista();

        if(this.raiz != null){ //Si el arbol no está vacio
           listarAux(lista, this.raiz);
        }
        return lista;
    }

    private void listarAux(Lista lista, NodoABB nodo){
        /*Metodo privado que realiza el recorrido del arbol con raiz <nodo> y guarda sus elementos en la <lista>.
        * El orden de los elementos es de menor a mayor.*/
        if(nodo.getIzquierdo() != null){
            listarAux(lista, nodo.getIzquierdo());
        }
        lista.insertar(nodo.getElemento(), lista.longitud() + 1);
        if(nodo.getDerecho() != null){
            listarAux(lista, nodo.getDerecho());
        }

    }

    public Lista listarRango(Comparable elemMin, Comparable elemMax){
        /*Recorre parte del árbol (sólo lo necesario) y devuelve una lista ordenada con los elementos que
        se encuentran en el rango comprendido entre elemMin y elemMax. */
        Lista lista = new Lista();
        if(this.raiz != null){
            listarRangoAux(this.raiz, lista, elemMin, elemMax);
        }
        return lista;
    }

    private void listarRangoAux(NodoABB nodo, Lista lista, Comparable elemMin, Comparable elemMax){
        /*Metodo privado que realiza el recorrido del arbol con raiz <nodo> y guarda sus elementos en la <lista>
        * que se encuentran en el rango comprendido entre elemMin y elemMax. */
        if(nodo.getIzquierdo() != null && (elemMin.compareTo(nodo.getElemento()) < 0)){
            listarRangoAux(nodo.getIzquierdo(), lista, elemMin, elemMax);
        }
        if((elemMin.compareTo(nodo.getElemento()) <= 0) && (elemMax.compareTo(nodo.getElemento()) >= 0)){
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
        }
        if(nodo.getDerecho() != null && (elemMax.compareTo(nodo.getElemento()) > 0)){
            listarRangoAux(nodo.getDerecho(), lista, elemMin, elemMax);
        }
    }

    public Object minimoElem(){
        /*Devuelve el elemento más pequeño almacenado en el árbol. Si el árbol está vacío devuelve null.*/
        Object minimo = null;
        if(this.raiz != null){
            NodoABB nodo = this.raiz;
            while (nodo.getIzquierdo() != null){
                nodo = nodo.getIzquierdo();
            }
            minimo = nodo.getElemento();
        }
        return minimo;
    }

    public Object maximoElem(){
        /*Devuelve el elemento más grande almacenado en el árbol. Si el árbol está vacío devuelve null.*/
        Object maximo = null;
        if(this.raiz != null){
            NodoABB nodo = this.raiz;
            while (nodo.getDerecho() != null){
                nodo = nodo.getDerecho();
            }
            maximo = nodo.getElemento();
        }
        return maximo;
    }

    public ArbolBB clone() {
        ArbolBB clon = new ArbolBB();
        clon.raiz = cloneAux(this.raiz);
        return clon;
    }

    private NodoABB cloneAux(NodoABB nodo) {
        NodoABB nuevo = null;
        if (nodo != null) {
            nuevo = new NodoABB(nodo.getElemento(), cloneAux(nodo.getIzquierdo()), cloneAux(nodo.getDerecho()));
        }
        return nuevo;
    }

   public String toString() {
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoABB nodo) {
        String cadena = "";
        if (nodo != null) {
            cadena += "Nodo: " + nodo.getElemento();
            if (nodo.getIzquierdo() != null) {
                cadena += " HI: " + nodo.getIzquierdo().getElemento();
            } else {
                cadena += " HI: -";
            }
            if (nodo.getDerecho() != null) {
                cadena += " HD: " + nodo.getDerecho().getElemento();
            } else {
                cadena += " HD: -";
            }
            cadena += "\n";
            cadena += toStringAux(nodo.getIzquierdo());
            cadena += toStringAux(nodo.getDerecho());
        }
        return cadena;
    }
}
