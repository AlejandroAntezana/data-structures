package jerarquicas;

public class NodoArbol {
    private Object elem;
    private NodoArbol izquierdo;
    private NodoArbol derecho;

    public NodoArbol(Object elemento, NodoArbol izquierdo, NodoArbol derecho) {
        this.elem = elemento;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    public Object getElem() {
        return elem;
    }

    public NodoArbol getIzquierdo() {
        return izquierdo;
    }

    public NodoArbol getDerecho() {
        return derecho;
    }

    public void setElem(Object elem) {
        this.elem = elem;
    }

    public void setIzquierdo(NodoArbol izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void setDerecho(NodoArbol derecho) {
        this.derecho = derecho;
    }
}
