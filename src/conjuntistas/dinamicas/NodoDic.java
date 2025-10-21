package conjuntistas.dinamicas;

/**
 * Representa un nodo en un árbol AVL utilizado para implementar un Diccionario.
 * Cada nodo contiene una clave (que debe ser Comparable), un dato asociado,
 * la altura del subárbol que lo tiene como raíz y referencias a sus hijos
 * izquierdo y derecho.
 */
public class NodoDic {
    private Comparable clave;
    private Object dato;
    private int altura;
    private NodoDic izquierdo;
    private NodoDic derecho;

    /**
     * Constructor de la clase NodoDic.
     * @param clave la clave para ordenar el nodo en el árbol.
     * @param dato el dato asociado a la clave.
     */
    public NodoDic(Comparable clave, Object dato) {
        this.clave = clave;
        this.dato = dato;
        this.altura = 0;
        this.izquierdo = null;
        this.derecho = null;
    }

    // --- Observadores ---
    public Comparable getClave() {
        return this.clave;
    }

    public Object getDato() {
        return this.dato;
    }

    public int getAltura() {
        return this.altura;
    }

    public NodoDic getIzquierdo() {
        return this.izquierdo;
    }

    public NodoDic getDerecho() {
        return this.derecho;
    }

    // --- Modificadores ---
    public void setClave(Comparable clave) {
        this.clave = clave;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public void setIzquierdo(NodoDic izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void setDerecho(NodoDic derecho) {
        this.derecho = derecho;
    }

    /**
     * Recalcula la altura del nodo basándose en la altura de sus hijos.
     * La altura de un nodo es max(altura(hijoIzq), altura(hijoDer)) + 1.
     */
    public void recalcularAltura() {
        int altIzq = -1;
        int altDer = -1;
        if (this.izquierdo != null) {
            altIzq = this.izquierdo.getAltura();
        }
        if (this.derecho != null) {
            altDer = this.derecho.getAltura();
        }
        this.altura = Math.max(altIzq, altDer) + 1;
    }
}