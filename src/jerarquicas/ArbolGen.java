package jerarquicas;
import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Nodo;

public class ArbolGen {

    private NodoGen raiz;

    public ArbolGen() {
        this.raiz = null;
    }

    public boolean insertar( Object elemNuevo, Object elemPadre) {
        //inserta el elemento elemNuevo como hijo del primer nodo encontrado en preorden
        //cuyo elemento sea elemPadre
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoGen(elemNuevo, null, null);
        } else {
            NodoGen nodoPadre = obtenerNodo(this.raiz, elemPadre);
            if (nodoPadre != null) {
                NodoGen nodoNuevo = new NodoGen(elemNuevo, null, null);
                if (nodoPadre.getHijoIzquierdo() == null) {
                    nodoPadre.setHijoIzquierdo(nodoNuevo);
                } else {
                    NodoGen nodoHermano = nodoPadre.getHijoIzquierdo();
                    while (nodoHermano.getHermanoDerecho() != null) {
                        nodoHermano = nodoHermano.getHermanoDerecho();
                    }
                    nodoHermano.setHermanoDerecho(nodoNuevo);
                }
            } else {
                exito = false;
            }
        }
        return exito;
    }

    private NodoGen obtenerNodo(NodoGen nodo, Object buscado) {
        NodoGen resultado = null;
        if (nodo != null) {
            if (nodo.getElemento().equals(buscado)) {
                resultado = nodo;
            } else {
                resultado = obtenerNodo(nodo.getHijoIzquierdo(), buscado);
                if (resultado == null) {
                    resultado = obtenerNodo(nodo.getHermanoDerecho(), buscado);
                }
            }
        }
        return resultado;
    }

    public boolean pertenece(Object elemento) {
        boolean exito = false;
        NodoGen aux = obtenerNodo(this.raiz, elemento);
        if (aux != null) {
            exito = true;
        }
        return exito;
    }

    public Lista ancestros(Object elemento){
        //devuelve una lista con los ancestros del elemento ingresado
        Lista salida = new Lista();
        ancestrosAux(this.raiz, elemento, salida);
        return salida;
    }

  private void ancestrosAux(NodoGen nodo, Object elemento, Lista salida) {
        if (nodo != null) {
            if (nodo.getElemento().equals(elemento)) {
                salida.insertar(nodo.getElemento(), 1);
            } else {
                ancestrosAux(nodo.getHijoIzquierdo(), elemento, salida);
                if (!salida.esVacia()) {
                    salida.insertar(nodo.getElemento(), 1);
                } else {
                    ancestrosAux(nodo.getHermanoDerecho(), elemento, salida);
                    if (!salida.esVacia()) {
                        salida.insertar(nodo.getElemento(), 1);
                    }
                }
            }
        }
    }

    public boolean eliminar(Object elemento) {
        //elimina el elemento ingresado y su subarbol
        boolean exito = false;
        if (this.raiz != null) {
            if (this.raiz.getElemento().equals(elemento)) {
                this.raiz = null;
                exito = true;
            } else {
                exito = eliminarAux(this.raiz, elemento);
            }
        }
        return exito;
    }

    private boolean eliminarAux(NodoGen nodo, Object elemento) {
        boolean exito = false;
        if (nodo != null) {
            if (nodo.getHijoIzquierdo() != null) {
                if (nodo.getHijoIzquierdo().getElemento().equals(elemento)) {
                    nodo.setHijoIzquierdo(null);
                    exito = true;
                } else {
                    exito = eliminarAux(nodo.getHijoIzquierdo(), elemento);
                }
            }
            if (!exito && nodo.getHermanoDerecho() != null) {
                if (nodo.getHermanoDerecho().getElemento().equals(elemento)) {
                    nodo.setHermanoDerecho(null);
                    exito = true;
                } else {
                    exito = eliminarAux(nodo.getHermanoDerecho(), elemento);
                }
            }
        }
        return exito;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public int altura() {
        //devuelve la altura del arbol
        int altura = -1;
        if (this.raiz != null) {
            altura = alturaAux(this.raiz);
        }
        return altura;
    }

    private int alturaAux(NodoGen nodo) {
        int alturaMax = -1;
        NodoGen hijo = nodo.getHijoIzquierdo();
        while (hijo != null) {
            int alturaHijo = alturaAux(hijo);
            if (alturaHijo > alturaMax) {
                alturaMax = alturaHijo;
            }
            hijo = hijo.getHermanoDerecho();
        }
        return alturaMax + 1;
    }

    public int nivel(Object elemento) {
        //devuelve el nivel del elemento ingresado
        int nivel = -1;
        if (this.raiz != null) {
            nivel = nivelAux(this.raiz, elemento, 0);
        }
        return nivel;
    }

    private int nivelAux(NodoGen nodo, Object elemento, int nivel) {
        int nivelBuscado = -1;
        if (nodo != null) {
            if (nodo.getElemento().equals(elemento)) {
                nivelBuscado = nivel;
            } else {
                nivelBuscado = nivelAux(nodo.getHijoIzquierdo(), elemento, nivel + 1);
                if (nivelBuscado == -1) {
                    nivelBuscado = nivelAux(nodo.getHermanoDerecho(), elemento, nivel);
                }
            }
        }
        return nivelBuscado;
    }

    public Object padre(Object elemento) {
        //devuelve el elemeto del nodo padre del elemento ingresado
        Object elementoBuscado;
        elementoBuscado = padreAux(elemento, this.raiz);
        return elementoBuscado;
    }

    private Object padreAux(Object elem, NodoGen nodoActual) {
        Object elementoPadre = null;
        if (nodoActual != null) {
            NodoGen nodoSiguiente = nodoActual.getHijoIzquierdo();
            while (nodoSiguiente != null && !nodoSiguiente.getElemento().equals(elem)) {
                nodoSiguiente = nodoSiguiente.getHermanoDerecho();
            }
            if (nodoSiguiente != null) {
                elementoPadre = nodoActual.getElemento();
            } else {
                elementoPadre = padreAux(elem, nodoActual.getHijoIzquierdo());
                if (elementoPadre == null) {
                    elementoPadre = padreAux(elem, nodoActual.getHermanoDerecho());
                }
            }
        }
        return elementoPadre;
    }

    public void vaciar() {
        this.raiz = null;
    }

    public Lista listarPreorden() {
        Lista salida = new Lista();
        listarPreordenAux(this.raiz, salida);
        return salida;
    }

    private void listarPreordenAux(NodoGen nodo, Lista lista) {
        if (nodo != null) {
            //visita del nodo
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
            //llamados recursivos con los otros hijos de n
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                listarPreordenAux(hijo, lista);
                hijo = hijo.getHermanoDerecho();
            }
        }
    }

    public Lista listarInorden() {
        Lista salida = new Lista();
        listarInordenAux(this.raiz, salida);
        return salida;
    }

    private void listarInordenAux(NodoGen n, Lista ls) {
        if (n != null) {
            //llamado recursivo con el primer hijo de n.
            if (n.getHijoIzquierdo() != null) {
                listarInordenAux(n.getHijoIzquierdo(), ls);
            }
            //visita del nodo n
            ls.insertar(n.getElemento(), ls.longitud() + 1);
            //llamados recursivos con los otros hijos de n
            if (n.getHijoIzquierdo() != null) {
                NodoGen hijo = n.getHijoIzquierdo().getHermanoDerecho();
                while (hijo != null) {
                    listarInordenAux(hijo, ls);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
    }

    public Lista listarPosorden() {
        Lista salida = new Lista();
        listarPosordenAux(this.raiz, salida);
        return salida;
    }

    private void listarPosordenAux(NodoGen n, Lista ls) {
        if (n != null) {
            //llamados recursivos con los hijos de n
            NodoGen hijo = n.getHijoIzquierdo();
            while (hijo != null) {
                listarPosordenAux(hijo, ls);
                hijo = hijo.getHermanoDerecho();
            }
            //visita del nodo n
            ls.insertar(n.getElemento(), ls.longitud() + 1);
        }
    }

    public Lista listarPorNiveles(){
        Lista salida = new Lista();
        if(this.raiz != null){
            Cola q = new Cola();
            q.poner(this.raiz);
            while(!q.esVacia()){
                NodoGen nodo = (NodoGen) q.obtenerFrente();
                q.sacar();
                salida.insertar(nodo.getElemento(), salida.longitud()+1);
                NodoGen hijo = nodo.getHijoIzquierdo();
                while(hijo != null){
                    q.poner(hijo);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return salida;
    }

    public ArbolGen clone() {
        ArbolGen clon = new ArbolGen();
        if (this.raiz != null) {
            clon.raiz = cloneAux(this.raiz);
        }
        return clon;
    }

    private NodoGen cloneAux(NodoGen nodo) {
        NodoGen nuevo = new NodoGen(nodo.getElemento(), null, null);
        if (nodo.getHijoIzquierdo() != null) {
            nuevo.setHijoIzquierdo(cloneAux(nodo.getHijoIzquierdo()));
        }
        if (nodo.getHermanoDerecho() != null) {
            nuevo.setHermanoDerecho(cloneAux(nodo.getHermanoDerecho()));
        }
        return nuevo;
    }

    public String toString() {
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoGen n) {
        String s = "";
        if (n != null) {
            //visita del nodo n
            s += n.getElemento().toString() + "-> ";
            NodoGen hijo = n.getHijoIzquierdo();
            while (hijo != null) {
                s += hijo.getElemento().toString() + " | ";
                hijo = hijo.getHermanoDerecho();
            }
            //
            //
            hijo = n.getHijoIzquierdo();
            while (hijo != null) {
                s += "\n" + toStringAux(hijo);
                hijo = hijo.getHermanoDerecho();
            }
        }
        return s;
    }

    //Otras operaciones del TDA Arbol Generico
    public boolean sonFrontera(Lista unaLista) {
        boolean exito;
        if (unaLista.esVacia() || this.esVacio()) {
            exito = false;
        } else {
            exito = sonFronteraAux(this.raiz, unaLista);
        }
        return exito;
    }

    private boolean sonFronteraAux(NodoGen nodo, Lista unaLista) {
        boolean exito = false;
        if (!unaLista.esVacia()) {
            if (nodo != null) {
                if (nodo.getHijoIzquierdo() != null) {
                    NodoGen siguiente = nodo.getHijoIzquierdo();
                    while (siguiente != null) {
                        sonFronteraAux(siguiente, unaLista);
                        siguiente = siguiente.getHermanoDerecho();
                    }
                } else {
                    int pos = unaLista.localizar(nodo.getElemento());
                    if (pos != -1) {
                        unaLista.eliminar(pos);
                    }
                }
            }
        }
        if (unaLista.esVacia()) {
            exito = true;
        }
        return exito;
    }

    public int grado() {
        int grado = 0;
        if (this.raiz != null) {
            grado = gradoAux(this.raiz);
        }
        return grado;
    }

    private int gradoAux(NodoGen nodo) {
        int gradoMax = 0;
        NodoGen hijo = nodo.getHijoIzquierdo();
        while (hijo != null) {
            int gradoHijo = gradoAux(hijo);
            if (gradoHijo > gradoMax) {
                gradoMax = gradoHijo;
            }
            hijo = hijo.getHermanoDerecho();
        }
        return gradoMax + 1;
    }

    public int gradoSubarbol(Object elemento){
        int grado = 0;
        NodoGen nodo = obtenerNodo(this.raiz, elemento);
        if(nodo != null){
            grado = gradoAux(nodo);
        }
        return grado;
    }


}
