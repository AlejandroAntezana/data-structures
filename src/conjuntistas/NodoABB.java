package conjuntistas;

public class NodoABB {
    private Comparable elemento;
    private NodoABB izquierdo;
    private NodoABB derecho;

    public NodoABB(Comparable elemento, NodoABB izquierdo, NodoABB derecho) {
        this.elemento = elemento;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    public Comparable getElemento() {
        return elemento;
    }

    public void setElemento(Comparable elemento) {
        this.elemento = elemento;
    }

    public NodoABB getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoABB izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoABB getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoABB derecho) {
        this.derecho = derecho;
    }
}
