package lineales.dinamicas;

/**
 * Clase auxiliar para la Cola de Prioridad.
 * Almacena el elemento, su prioridad y un orden de llegada para
 * manejar desempates (FIFO para prioridades iguales).
 * Implementa Comparable para ser usada en el Heap.
 */
public class CeldaCP implements Comparable {

    private Object elemento;
    private double prioridad;
    private int ordenLlegada;

    /**
     * Constructor.
     * @param elemento El dato a almacenar (ej. un NodoVert).
     * @param prioridad El costo o prioridad (menor es más prioritario).
     * @param ordenLlegada Un contador para desempate.
     */
    public CeldaCP(Object elemento, double prioridad, int ordenLlegada) {
        this.elemento = elemento;
        this.prioridad = prioridad;
        this.ordenLlegada = ordenLlegada;
    }

    // --- Observadores ---
    public Object getElemento() {
        return this.elemento;
    }

    public double getPrioridad() {
        return this.prioridad;
    }

    public int getOrdenLlegada() {
        return this.ordenLlegada;
    }

    /**
     * Compara esta celda con otra.
     * Define un "Min-Heap" (menor es más prioritario).
     * 1. Compara por prioridad.
     * 2. Si las prioridades son iguales, compara por orden de llegada.
     * @param otro El objeto a comparar (se asume CeldaCP).
     * @return -1 si this es menor (más prioritario), 1 si es mayor, 0 si son idénticos.
     */
    @Override
    public int compareTo(Object otro) {
        CeldaCP otraCelda = (CeldaCP) otro;
        int resultado;

        // Comparamos por prioridad (menor prioridad = más urgente)
        if (this.prioridad < otraCelda.getPrioridad()) {
            resultado = -1;
        } else if (this.prioridad > otraCelda.getPrioridad()) {
            resultado = 1;
        } else {
            // Prioridades iguales, desempatamos por orden de llegada
            // (menor orden de llegada = llegó antes)
            if (this.ordenLlegada < otraCelda.getOrdenLlegada()) {
                resultado = -1;
            } else if (this.ordenLlegada > otraCelda.getOrdenLlegada()) {
                resultado = 1;
            } else {
                resultado = 0;
            }
        }
        return resultado;
    }
}