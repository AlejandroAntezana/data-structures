package conjuntistas.dinamicas;

public class ArbolBB_V2 {
    private NodoABB raiz;

    public ArbolBB_V2() {
        this.raiz = null;
    }

    //Algoritmo Pertenece implementado de forma iterativa
    public boolean pertenece(Comparable elem) {
        /*Este algoritmo puede plantearse de forma iterativa dado que solo se recorre
         * una rama del Arbol BB.*/
        boolean encontrado = false;
        NodoABB aux = this.raiz;

        while (!encontrado && aux != null) {
            if (elem.compareTo(aux.getElemento()) == 0) {
                encontrado = true;
            } else {
                if (elem.compareTo(aux.getElemento()) < 0) {
                    aux = aux.getIzquierdo();
                } else {
                    aux = aux.getDerecho();
                }
            }
        }
        return encontrado;
    }

    //Algoritmo pertenece implementado de forma recursiva
    /*public boolean perteneceRecursivo(Comparable elem) {
        return perteneceRecursivoAux(this.raiz, elem);
    }
    private boolean perteneceRecursivoAux(NodoABB nodo, Comparable elem) {
        boolean encontrado = false;
        if (nodo != null) {
            if (elem.compareTo(nodo.getElemento()) == 0) {
                encontrado = true;
            } else {
                if (elem.compareTo(nodo.getElemento()) < 0) {
                    encontrado = perteneceRecursivoAux(nodo.getIzquierdo(), elem);
                } else {
                    encontrado = perteneceRecursivoAux(nodo.getDerecho(), elem);
                }
            }
        }
        return encontrado;
    } */
    public boolean insertar(Comparable elemento) {
        /*Recibe un elemento y lo agrega en el árbol de manera ordenada. Si el elemento ya se encuentra
        en el árbol no se realiza la inserción. Devuelve verdadero si el elemento se agrega a la estructura y
        falso en caso contrario.*/
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoABB(elemento, null, null);
        } else {
            exito = insertarAux(this.raiz, elemento);
        }
        return exito;
    }

    //Algoritmo insertar implementado de manera iterativa
    public boolean insertarAux(NodoABB nodo, Comparable elemento) {
        boolean succes = true;
        boolean insertado = false;
        while(!insertado) {
            if (elemento.compareTo(nodo.getElemento()) == 0) {
                //error de elemento repetido
                succes = false;
                insertado = true;
            } else if (elemento.compareTo(nodo.getElemento()) < 0) { //me voy a la izquierda
                if (nodo.getIzquierdo() == null) {
                    NodoABB nuevoHI = new NodoABB(elemento, null, null);
                    nodo.setIzquierdo(nuevoHI);
                    insertado = true;
                } else {
                    nodo = nodo.getIzquierdo();
                }
            } else {
                if (nodo.getDerecho() == null) {
                    NodoABB nuevoHD = new NodoABB(elemento, null, null);
                    nodo.setDerecho(nuevoHD);
                    insertado = true;
                } else {
                    nodo = nodo.getDerecho();
                }
            }
        }
        return succes;
    }
    //Algoritmo insertar implementado de manera recursiva
    /*private boolean insertarAux(NodoABB nodo, Comparable elemento) {
        // precondicion: n no es nulo
        boolean exito = true;

        if ((elemento.compareTo(nodo.getElemento()) == 0)) {
            //Reporta error: Elemento repetido
            exito = false;
        } else if ((elemento.compareTo(nodo.getElemento()) < 0)) {
            // elemento es menor que n.getElem()
            // si tiene HI baja a la izquierda, sino agrega elemento
            if (nodo.getIzquierdo() != null) {
                exito = insertarAux(nodo.getIzquierdo(), elemento);
            } else {
                nodo.setIzquierdo(new NodoABB(elemento, null, null));
            }
        } else { // elemento es mayor que n.getElem()
            // si tiene HD baja a la derecha, sino agrega elemento
            if (nodo.getDerecho() != null) {
                exito = insertarAux(nodo.getDerecho(), elemento);
            } else {
                nodo.setDerecho(new NodoABB(elemento, null, null));
            }
        }
        return exito;
    } */

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
