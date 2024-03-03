package jerarquicas;

import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;

public class ArbolBin {

    private NodoArbol raiz;

    public ArbolBin() {
        this.raiz = null;
    }

    public boolean insertar(Object elemNuevo, Object elemPadre, char lugar) {
        boolean exito = true;

        if (this.raiz == null) {
            //si el arbol esta vacio, pone elem Nuevo en la raiz
            this.raiz = new NodoArbol(elemNuevo, null, null);
        } else {
            //si arbol no esta vacio, busca el padre
            NodoArbol nPadre = obtenerNodo(this.raiz, elemPadre);

            //si padre existe y lugar no esta ocupado lo pone, sino da error
            if (nPadre != null) {
                if (lugar == 'I' && nPadre.getIzquierdo() == null) {
                    nPadre.setIzquierdo(new NodoArbol(elemNuevo, null, null));
                } else if (lugar == 'D' && nPadre.getDerecho() == null) {
                    nPadre.setDerecho(new NodoArbol(elemNuevo, null, null));
                } else {
                    exito = false;
                }
            } else {
                exito = false;
            }
        }
        return exito;
    }

    private NodoArbol obtenerNodo(NodoArbol n, Object buscado) {
        /*metodo PRIVADO que busca un elemento y devuelve el nodo que
        lo contiene. Si no se encuentra devuelve null*/

        NodoArbol resultado = null;
        if (n != null) {
            if (n.getElem().equals(buscado)) {
                //si el buscado es n lo devuelve
                resultado = n;
            } else {
                //no es el buscado: busca primero en el HI
                resultado = obtenerNodo(n.getIzquierdo(), buscado);
                //Si no lo encontro en el izquierdo, lo busca en el HD
                if (resultado == null) {
                    resultado = obtenerNodo(n.getDerecho(), buscado);
                }
            }
        }
        return resultado;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public int altura() {
        int altura;
        if (this.raiz == null) {
            altura = -1;
        } else {
            altura = alturaAux(this.raiz);
        }
        return altura;
    }

    private int alturaAux(NodoArbol nodo) {
        NodoArbol izq, der;
        int altI, altD, alturaMax = 0;

        if (nodo != null) {
            izq = nodo.getIzquierdo();
            der = nodo.getDerecho();
            altI = 0;
            altD = 0;
            if (izq != null) {
                altI += 1;
            }
            if (der != null) {
                altD += 1;
            }
            altI += alturaAux(izq);
            altD += alturaAux(der);
            if (altI >= altD) {
                alturaMax = altI;
            } else {
                alturaMax = altD;
            }
        }
        return alturaMax;
    }

    public int nivel(Object elemento) {
        return nivelAux(this.raiz, elemento, -1);
    }

    private int nivelAux(NodoArbol nodo, Object elemento, int nivel) {
        int nivelAux = -1;
        if (nodo != null) {
            if (nodo.getElem() == elemento) {
                nivelAux = nivel + 1;
            } else {
                nivelAux = nivelAux(nodo.getIzquierdo(), elemento, nivel + 1);
                if (nivelAux == -1) {
                    nivelAux = nivelAux(nodo.getDerecho(), elemento, nivel + 1);
                }
            }
        }
        return nivelAux;
    }

    public Object padre(Object elemento) {
        Object padre = null;
        if (this.raiz != null) {
            if (this.raiz.getElem() == elemento) {
                padre = null;
            } else {
                padre = padreAux(this.raiz, elemento);
            }
        }
        return padre;
    }
    private Object padreAux(NodoArbol nodo, Object elementoBuscado) {
        Object padre = null;
        if (nodo != null) {
            if (nodo.getIzquierdo() != null && nodo.getIzquierdo().getElem() == elementoBuscado) {
                padre = nodo.getElem();
            } else if (nodo.getDerecho() != null && nodo.getDerecho().getElem() == elementoBuscado) {
                padre = nodo.getElem();
            } else {
                padre = padreAux(nodo.getIzquierdo(), elementoBuscado);
                if (padre == null) {
                    padre = padreAux(nodo.getDerecho(), elementoBuscado);
                }
            }
        }
        return padre;
    }

    public Lista listarPreorden() {
        //retorna una lista con los elementos del arbol en PREORDEN
        Lista lis = new Lista();
        listarPreordenAux(this.raiz, lis);
        return lis;
    }

    private void listarPreordenAux(NodoArbol nodo, Lista lis) {
        //Metodo recursivo PRIVADO porque su parametro es de tipo nodoArbol

        if (nodo != null) {
            //visita el elemento en el nodo
            lis.insertar(nodo.getElem(), lis.longitud() + 1); //(1)

            //recorre a sus hijos en preorden
            listarPreordenAux(nodo.getIzquierdo(), lis); //(2)
            listarPreordenAux(nodo.getDerecho(), lis); //(3)
        }
    }

    public Lista listarInorden() {
        //retorna una lista con los elementos del arbol en INORDEN
        Lista lis = new Lista();
        listarInordenAux(this.raiz, lis);
        return lis;
    }

    private void listarInordenAux(NodoArbol nodo, Lista lis) {
        //Metodo recursivo PRIVADO porque su parametro es de tipo nodoArbol

        if (nodo != null) {
            //recorre a sus hijos en preorden
            listarInordenAux(nodo.getIzquierdo(), lis); //(1)

            //visita el elemento en el nodo
            lis.insertar(nodo.getElem(), lis.longitud() + 1); //(2)

            listarInordenAux(nodo.getDerecho(), lis); //(3)
        }
    }

    public Lista listarPosorden() {
        //retorna una lista con los elementos del arbol en POSORDEN
        Lista lis = new Lista();
        listarPosordenAux(this.raiz, lis);
        return lis;
    }

    private void listarPosordenAux(NodoArbol nodo, Lista lis) {
        //Metodo recursivo PRIVADO porque su parametro es de tipo nodoArbol

        if (nodo != null) {
            //recorre a sus hijos en preorden
            listarPosordenAux(nodo.getIzquierdo(), lis); //(1)
            listarPosordenAux(nodo.getDerecho(), lis); //(2)

            //visita el elemento en el nodo
            lis.insertar(nodo.getElem(), lis.longitud() + 1); //(3)
        }
    }

    public Lista listarPorNiveles() {
        //retorna una lista con los elementos del arbol en NIVELES
        Lista lis = new Lista();
        if (this.raiz != null) {
            //si el arbol no esta vacio, llama a metodo auxiliar recursivo
            listarNivelesAux(this.raiz, lis);
        }
        return lis;
    }

    private void listarNivelesAux(NodoArbol nodo, Lista lis) {
        //Metodo recursivo PRIVADO porque su parametro es de tipo nodoArbol
        //recorre el arbol en profundidad, visitando cada nodo una vez

        NodoArbol nodoAux;
        Cola colaAux = new Cola();
        colaAux.poner(nodo);
        while (!colaAux.esVacia()) {
            nodoAux = (NodoArbol) colaAux.obtenerFrente();
            colaAux.sacar();
            lis.insertar(nodoAux.getElem(), lis.longitud() + 1);
            if (nodoAux.getIzquierdo() != null) {
                colaAux.poner(nodoAux.getIzquierdo());
            }
            if (nodoAux.getDerecho() != null) {
                colaAux.poner(nodoAux.getDerecho());
            }
        }
    }

    public ArbolBin clone() {
        ArbolBin clon = new ArbolBin();
        if (this.raiz != null) {
            clon.raiz = new NodoArbol(this.raiz.getElem(), null, null);
            cloneAux(this.raiz, clon.raiz);
        }
        return clon;
    }

    private void cloneAux(NodoArbol nodo, NodoArbol nodoClon) {
        if (nodo != null) {
            if (nodo.getIzquierdo() != null) {
                nodoClon.setIzquierdo(new NodoArbol(nodo.getIzquierdo().getElem(), null, null));
                cloneAux(nodo.getIzquierdo(), nodoClon.getIzquierdo());
            }
            if (nodo.getDerecho() != null) {
                nodoClon.setDerecho(new NodoArbol(nodo.getDerecho().getElem(), null, null));
                cloneAux(nodo.getDerecho(), nodoClon.getDerecho());
            }
        }
    }


    public void vaciar() {
        this.raiz = null;
    }

    public String toString() {
        String s = "";
        if (this.raiz != null) {
            s = toStringAux(this.raiz);
        } else {
            s = "Arbol vacio";
        }
        return s;
    }
    private String toStringAux(NodoArbol nodo) {
        String s = "";
        if (nodo != null) {
            s += "Nodo: " + nodo.getElem() + " - ";
            if (nodo.getIzquierdo() != null) {
                s += "HI: " + nodo.getIzquierdo().getElem() + " - ";
            } else {
                s += "HI: null - ";
            }
            if (nodo.getDerecho() != null) {
                s += "HD: " + nodo.getDerecho().getElem() + "\n";
            } else {
                s += "HD: null\n";
            }
            s += toStringAux(nodo.getIzquierdo());
            s += toStringAux(nodo.getDerecho());
        }
        return s;
    }

    public Lista frontera() {
        //metodo que retorna una lista con los elementos de la frontera
        Lista lis = new Lista();
        fronteraAux(this.raiz, lis);
        return lis;
    }

    private void fronteraAux(NodoArbol nodo, Lista lis) {
        if (nodo != null) {
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                lis.insertar(nodo.getElem(), lis.longitud() + 1);
            } else {
                fronteraAux(nodo.getIzquierdo(), lis);
                fronteraAux(nodo.getDerecho(), lis);
            }
        }
    }


}
