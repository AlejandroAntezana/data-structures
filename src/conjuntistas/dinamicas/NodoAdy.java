package conjuntistas.dinamicas;

/**
 * Representa un nodo en la lista de adyacencia, es decir, un arco.
 * Almacena la etiqueta (costo/distancia) del arco, una referencia
 * al vértice de destino, y una referencia al siguiente nodo de adyacencia
 * del mismo vértice origen.
 * Basado en la Figura 5.13 del apunte.
 */
public class NodoAdy {
    private NodoVert vertice; // Vértice destino del arco
    private NodoAdy sigAdyacente;
    private double etiqueta; // Etiqueta numérica (distancia en km)

    /**
     * Constructor.
     * @param vertice el NodoVert de destino.
     * @param sigAdyacente el siguiente NodoAdy en la lista.
     * @param etiqueta la etiqueta (costo) del arco.
     */
    public NodoAdy(NodoVert vertice, NodoAdy sigAdyacente, double etiqueta) {
        this.vertice = vertice;
        this.sigAdyacente = sigAdyacente;
        this.etiqueta = etiqueta;
    }

    // --- Observadores ---
    public NodoVert getVertice() {
        return this.vertice;
    }

    public NodoAdy getSigAdyacente() {
        return this.sigAdyacente;
    }

    public double getEtiqueta() {
        return this.etiqueta;
    }

    // --- Modificadores ---
    public void setVertice(NodoVert vertice) {
        this.vertice = vertice;
    }

    public void setSigAdyacente(NodoAdy sigAdyacente) {
        this.sigAdyacente = sigAdyacente;
    }

    public void setEtiqueta(double etiqueta) {
        this.etiqueta = etiqueta;
    }
}