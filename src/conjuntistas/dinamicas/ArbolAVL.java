package conjuntistas.dinamicas;

import lineales.dinamicas.Lista;

public class ArbolAVL {
    private NodoAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    public boolean pertenece(Comparable elem) {
        return perteneceAux(this.raiz, elem);
    }

    private boolean perteneceAux(NodoAVL nodo, Comparable elem) {
        boolean res = false;
        if (nodo != null) {
            if (elem.compareTo(nodo.getElemento()) == 0) {
                res = true;
            } else {
                if (elem.compareTo(nodo.getElemento()) < 0) {
                    res = perteneceAux((NodoAVL) nodo.getIzquierdo(), elem);
                } else {
                    res = perteneceAux((NodoAVL) nodo.getDerecho(), elem);
                }
            }
        }
        return res;
    }

    public boolean insertar(Comparable elem) {
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoAVL(elem, null, null);
        } else {
            exito = insertarAux(this.raiz, elem);
        }
        return exito;
    }

    private boolean insertarAux(NodoAVL nodo, Comparable elem) {
        boolean exito = true;
        if (elem.compareTo(nodo.getElemento()) == 0) {
            exito = false;
        } else {
            if (elem.compareTo(nodo.getElemento()) < 0) {
                if (nodo.getIzquierdo() != null) {
                    exito = insertarAux((NodoAVL) nodo.getIzquierdo(), elem);
                } else {
                    nodo.setIzquierdo(new NodoAVL(elem, null, null));
                }
            } else {
                if (nodo.getDerecho() != null) {
                    exito = insertarAux((NodoAVL) nodo.getDerecho(), elem);
                } else {
                    nodo.setDerecho(new NodoAVL(elem, null, null));
                }
            }
        }
        return exito;
    }

    private void verificarBalanceNodo(NodoAVL nodo) {
        int balance = obtenerBalance(nodo);
        if (balance > 1) {
            if (obtenerBalance((NodoAVL) nodo.getIzquierdo()) > 0) {
                rotacionDerecha(nodo);
            } else {
                rotacionIzquierda((NodoAVL) nodo.getIzquierdo());
                rotacionDerecha(nodo);
            }
        } else if (balance < -1) {
            if (obtenerBalance((NodoAVL) nodo.getDerecho()) < 0) {
                rotacionIzquierda(nodo);
            } else {
                rotacionDerecha((NodoAVL) nodo.getDerecho());
                rotacionIzquierda(nodo);
            }
        }
    }

    private int obtenerBalance(NodoAVL nodo) {
        int altIzq = -1;
        int altDer = -1;
        if (nodo.getIzquierdo() != null) {
            altIzq = NodoAVL.obtenerAltura((NodoAVL) nodo.getIzquierdo());
        }
        if (nodo.getDerecho() != null) {
            altDer = NodoAVL.obtenerAltura((NodoAVL) nodo.getDerecho());
        }
        return altIzq - altDer;
    }

    private void rotacionDerecha(NodoAVL nodo) {
        NodoAVL aux = (NodoAVL) nodo.getIzquierdo();
        nodo.setIzquierdo(aux.getDerecho());
        aux.setDerecho(nodo);
        NodoAVL.recalcularAltura(nodo);
        NodoAVL.recalcularAltura(aux);
    }

    private void rotacionIzquierda(NodoAVL nodo) {
        NodoAVL aux = (NodoAVL) nodo.getDerecho();
        nodo.setDerecho(aux.getIzquierdo());
        aux.setIzquierdo(nodo);
        NodoAVL.recalcularAltura(nodo);
        NodoAVL.recalcularAltura(aux);
    }

    private void rotacionDobleDerecha(NodoAVL nodo) {
        rotacionIzquierda((NodoAVL) nodo.getIzquierdo());
        rotacionDerecha(nodo);
    }

    private void rotacionDobleIzquierda(NodoAVL nodo) {
        rotacionDerecha((NodoAVL) nodo.getDerecho());
        rotacionIzquierda(nodo);
    }

    public boolean eliminar(Comparable elem) {
        return eliminarAux(this.raiz, elem);
    }

    //metodo eliminarAux: elimina el elemento y reacomoda el arbol
    private boolean eliminarAux(NodoAVL nodo, Comparable elem) {
        boolean exito = false;
        if (nodo != null) {
            if (elem.compareTo(nodo.getElemento()) == 0) {
                if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                    if (nodo == this.raiz) {
                        this.raiz = null;
                    } else {
                        NodoAVL padre = obtenerPadre(this.raiz, nodo.getElemento());
                        if (padre.getIzquierdo() == nodo) {
                            padre.setIzquierdo(null);
                        } else {
                            padre.setDerecho(null);
                        }
                    }
                } else if (nodo.getIzquierdo() != null && nodo.getDerecho() != null) {
                    Comparable sucesor = obtenerSucesor((NodoAVL) nodo.getDerecho());
                    nodo.setElemento(sucesor);
                    eliminarAux((NodoAVL) nodo.getDerecho(), sucesor);
                } else {
                    NodoAVL hijo = (NodoAVL) (nodo.getIzquierdo() != null ? nodo.getIzquierdo() : nodo.getDerecho());
                    if (nodo == this.raiz) {
                        this.raiz = hijo;
                    } else {
                        NodoAVL padre = obtenerPadre(this.raiz, nodo.getElemento());
                        if (padre.getIzquierdo() == nodo) {
                            padre.setIzquierdo(hijo);
                        } else {
                            padre.setDerecho(hijo);
                        }
                    }
                }
                exito = true;
            } else {
                if (elem.compareTo(nodo.getElemento()) < 0) {
                    exito = eliminarAux((NodoAVL) nodo.getIzquierdo(), elem);
                } else {
                    exito = eliminarAux((NodoAVL) nodo.getDerecho(), elem);
                }
            }
            if (exito) {
                verificarBalanceNodo(nodo);
            }
        }
        return exito;
    }

    //metodo obtenerPadre: devuelve el nodo padre del nodo que contiene al elem
    private NodoAVL obtenerPadre(NodoAVL nodo, Comparable elem) {
        NodoAVL res = null;
        if (nodo != null) {
            if ((nodo.getIzquierdo() != null && nodo.getIzquierdo().getElemento().compareTo(elem) == 0)
                    || (nodo.getDerecho() != null && nodo.getDerecho().getElemento().compareTo(elem) == 0)) {
                res = nodo;
            } else {
                if (elem.compareTo(nodo.getElemento()) < 0) {
                    res = obtenerPadre((NodoAVL) nodo.getIzquierdo(), elem);
                } else {
                    res = obtenerPadre((NodoAVL) nodo.getDerecho(), elem);
                }
            }
        }
        return res;
    }

    //metodo obtenerSucesor: devuelve el sucesor inorden del nodo
    private Comparable obtenerSucesor(NodoAVL nodo) {
        Comparable res = null;
        if (nodo.getIzquierdo() == null) {
            res = nodo.getElemento();
        } else {
            res = obtenerSucesor((NodoAVL) nodo.getIzquierdo());
        }
        return res;
    }

    public Lista listar() {
        Lista lista = new Lista();
        listarAux(this.raiz, lista);
        return lista;
    }

    private void listarAux(NodoAVL nodo, Lista lista) {
        if (nodo != null) {
            listarAux((NodoAVL) nodo.getIzquierdo(), lista);
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
            listarAux((NodoAVL) nodo.getDerecho(), lista);
        }
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public void vaciar() {
        this.raiz = null;
    }

    public ArbolAVL clone() {
        ArbolAVL clon = new ArbolAVL();
        clon.raiz = cloneAux(this.raiz);
        return clon;
    }

    private NodoAVL cloneAux(NodoAVL nodo) {
        NodoAVL clon = null;
        if (nodo != null) {
            clon = new NodoAVL(nodo.getElemento(), cloneAux((NodoAVL) nodo.getIzquierdo()),
                    cloneAux((NodoAVL) nodo.getDerecho()));
        }
        return clon;
    }

    //metodo toString redefinido para arbol AVL donde muestra la altura de cada nodo
    public String toString() {
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoAVL nodo) {
        String res = "";
        if (nodo != null) {
            res += "Nodo: " + nodo.getElemento() + " Altura: " + NodoAVL.obtenerAltura(nodo) + "\n";
            res += "Izquierdo: " + toStringAux((NodoAVL) nodo.getIzquierdo());
            res += "Derecho: " + toStringAux((NodoAVL) nodo.getDerecho());
        }
        return res;
    }

}