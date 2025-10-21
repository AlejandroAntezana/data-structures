package conjuntistas.dinamicas;

/**
 * Representa un vértice en la estructura de listas de adyacencia de un grafo.
 * Almacena el elemento del vértice, una referencia al siguiente vértice
 * en la lista principal de vértices, y una referencia al primer nodo
 * de su lista de adyacencia (sus arcos salientes).
 * Basado en la Figura 5.13 del apunte.
 */
public class NodoVert {
    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;

    /**
     * Constructor.
     * @param elem el elemento (ej. Estacion) a almacenar en el vértice.
     * @param sigVertice el siguiente NodoVert en la lista de vértices.
     */
    public NodoVert(Object elem, NodoVert sigVertice) {
        this.elem = elem;
        this.sigVertice = sigVertice;
        this.primerAdy = null;
    }

    // --- Observadores ---
    public Object getElem() {
        return this.elem;
    }

    public NodoVert getSigVertice() {
        return this.sigVertice;
    }

    public NodoAdy getPrimerAdy() {
        return this.primerAdy;
    }

    // --- Modificadores ---
    public void setElem(Object elem) {
        this.elem = elem;
    }

    public void setSigVertice(NodoVert sigVertice) {
        this.sigVertice = sigVertice;
    }

    public void setPrimerAdy(NodoAdy primerAdy) {
        this.primerAdy = primerAdy;
    }
}