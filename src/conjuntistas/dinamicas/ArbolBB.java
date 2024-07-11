package conjuntistas.dinamicas;

import lineales.dinamicas.Lista;

public class ArbolBB {
    private NodoABB raiz;

    public ArbolBB() {
        this.raiz = null;
    }


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

    private boolean insertarAux(NodoABB nodo, Comparable elemento) {
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
    }

    public boolean eliminar(Comparable elemento) {
        return eliminarAux(this.raiz, null, elemento);
    }

    private boolean eliminarAux(NodoABB nodo, NodoABB padre, Comparable elemento) {
        boolean exito = false;
        if (nodo != null) {
            if (nodo.getElemento().compareTo(elemento) == 0) {
                if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                    //el nodo es una hoja / Caso 1
                    if (elemento.compareTo(padre.getElemento()) < 0) {
                        padre.setIzquierdo(null);
                    } else {
                        padre.setDerecho(null);
                    }
                } else if (nodo.getIzquierdo() != null && nodo.getDerecho() != null) {
                    //llamo caso 3
                    nodo.setElemento(nodo.getIzquierdo().getElemento());

                } else {
                    //llamo caso 2
                    if (elemento.compareTo(padre.getElemento()) < 0) {
                        if (nodo.getIzquierdo() != null) {
                            padre.setIzquierdo(nodo.getIzquierdo());
                        } else {
                            padre.setIzquierdo(nodo.getDerecho());
                        }
                    } else {
                        if (nodo.getIzquierdo() != null) {
                            padre.setDerecho(nodo.getIzquierdo());
                        } else {
                            padre.setDerecho(nodo.getDerecho());
                        }
                    }
                }
                exito = true;
            } else {
                if (nodo.getElemento().compareTo(elemento) > 0) {
                    exito = eliminarAux(nodo.getIzquierdo(), nodo, elemento);
                } else {
                    exito = eliminarAux(nodo.getDerecho(), nodo, elemento);
                }
            }

        }
        return exito;
    }

    public boolean pertenece(Comparable elemento) {
        return perteneceAux(this.raiz, elemento);
    }

    private boolean perteneceAux(NodoABB nodo, Comparable elementoBuscado) {
        boolean exito = false;
        if (nodo != null) {
            if (nodo.getElemento().compareTo(elementoBuscado) == 0) {
                exito = true;
            } else {
                if (nodo.getElemento().compareTo(elementoBuscado) > 0) {
                    if (nodo.getIzquierdo() != null) {
                        exito = perteneceAux(nodo.getIzquierdo(), elementoBuscado);
                    }
                } else {
                    if (nodo.getDerecho() != null) {
                        exito = perteneceAux(nodo.getDerecho(), elementoBuscado);
                    }
                }
            }
        }
        return exito;
    }

    public boolean esVacio() {
        // Devuelve falso si hay al menos un elemento en el árbol y verdadero en caso contrario.
        return this.raiz == null;
    }

    public Lista listar() {
        /*Recorre el árbol completo y devuelve una lista ordenada con los elementos que se encuentran
        almacenados en él.*/
        Lista lis = new Lista();
        if (this.raiz != null) {
            listarAux(this.raiz, lis);
        }
        return lis;
    }

    private void listarAux(NodoABB n, Lista lis) {
        if (n.getIzquierdo() != null) {
            listarAux(n.getIzquierdo(), lis);
        }

        lis.insertar(n.getElemento(), lis.longitud() + 1);

        if (n.getDerecho() != null) {
            listarAux(n.getDerecho(), lis);
        }
    }

    public Lista listarRango(Comparable elemMin, Comparable elemMax) {
        /*Recorre parte del árbol (sólo lo necesario) y devuelve una lista ordenada con los elementos que
        se encuentran en el intervalo [elemMinimo, elemMaximo].*/
        Lista lis = new Lista();
        if (this.raiz != null) {
            listarRangoAux(this.raiz, lis, elemMin, elemMax);
        }
        return lis;
    }

    private void listarRangoAux(NodoABB n, Lista lis, Comparable elemMin, Comparable elemMax) {
        if (n.getIzquierdo() != null && (elemMin.compareTo(n.getElemento()) < 0)) {
            listarRangoAux(n.getIzquierdo(), lis, elemMin, elemMax);
        }
        if ((elemMin.compareTo(n.getElemento()) <= 0) && (elemMax.compareTo(n.getElemento()) >= 0)) {
            lis.insertar(n.getElemento(), lis.longitud() + 1);
        }

        if (n.getDerecho() != null && (elemMax.compareTo(n.getElemento()) > 0)) {
            listarRangoAux(n.getDerecho(), lis, elemMin, elemMax);
        }
    }

    public Object minimoElem() {
        // recorre la rama correspondiente y devuelve el elemento más pequeño almacenado en el árbol.
        Object elem = null;
        if (this.raiz != null) {
            elem = minimoElemAux(this.raiz);
        }
        return elem;
    }

    private Object minimoElemAux(NodoABB n) {
        //Modulo recursivo
        Object elem = n.getElemento();
        if (n.getIzquierdo() != null) {
            elem = minimoElemAux(n.getIzquierdo());
        }
        return elem;
    }

    public Object maximoElem() {
        // recorre la rama correspondiente y devuelve el elemento más grande almacenado en el árbol.
        Object elem = null;
        if (this.raiz != null) {
            elem = maximoElemAux(this.raiz);
        }
        return elem;
    }

    private Object maximoElemAux(NodoABB n) {
        //Modulo recursivo
        Object elem = n.getElemento();
        if (n.getDerecho() != null) {
            elem = maximoElemAux(n.getDerecho());
        }
        return elem;
    }

    public ArbolBB clone() {
        ArbolBB clon = new ArbolBB();
        clon.raiz = cloneAux(this.raiz);
        return clon;
    }

    private NodoABB cloneAux(NodoABB n) {
        NodoABB nuevo = null;
        if (n != null) {
            nuevo = new NodoABB(n.getElemento(), cloneAux(n.getIzquierdo()), cloneAux(n.getDerecho()));
        }
        return nuevo;
    }

    public void vaciar() {
        this.raiz = null;
    }

    public String toString() {
        String cadena = "Arbol vacio";
        if (this.raiz != null) {
            cadena = stringAux(this.raiz);
        }
        return cadena;
    }

    private String stringAux(NodoABB nodo) {
        String stringAux = "";
        NodoABB izq, der;
        if (nodo != null) {
            izq = nodo.getIzquierdo();
            der = nodo.getDerecho();
            stringAux += nodo.getElemento();
            if (izq != null) {
                stringAux += " HI: " + izq.getElemento();
            } else {
                stringAux += " HI: - ";
            }
            if (der != null) {
                stringAux += " HD: " + der.getElemento();
            } else {
                stringAux += " HD: - ";
            }
            stringAux += "\n";
            stringAux += stringAux(izq);
            stringAux += stringAux(der);
        }
        return stringAux;
    }


}
