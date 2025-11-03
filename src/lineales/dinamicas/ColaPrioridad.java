package lineales.dinamicas;

/**
 * Implementación del TDA Cola de Prioridad usando un Min-Heap (árbol Heap).
 * Esta estructura permite insertar en O(log n) y obtener/eliminar el frente
 * (elemento de mayor prioridad) en O(log n) u O(1).
 * Utiliza un contador 'ordenLlegada' para manejar FIFO en prioridades iguales.
 */
public class ColaPrioridad {

    private static final int TAM_INICIAL = 20;
    private CeldaCP[] heap;
    private int ultimo; // Cantidad de elementos
    private int ordenLlegada; // Contador para desempate

    /**
     * Constructor. Crea una cola de prioridad vacía.
     */
    public ColaPrioridad() {
        // Usamos un arreglo 1-based para facilitar los cálculos de hijos/padre
        this.heap = new CeldaCP[TAM_INICIAL];
        this.ultimo = 0;
        this.ordenLlegada = 0;
    }

    /**
     * Inserta un elemento con una prioridad dada.
     * El elemento se acomoda en el heap según su prioridad.
     * @param elemento El dato a almacenar.
     * @param prioridad La prioridad (costo). Menor valor es más prioritario.
     * @return verdadero (nunca falla por espacio en esta implementación).
     */
    public boolean insertar(Object elemento, double prioridad) {
        // Verificamos si necesitamos agrandar el arreglo
        if (this.ultimo + 1 >= this.heap.length) {
            redimensionar();
        }

        // Incrementamos el orden de llegada para el desempate
        this.ordenLlegada++;

        // Creamos la celda
        CeldaCP nuevaCelda = new CeldaCP(elemento, prioridad, this.ordenLlegada);

        // Agregamos el elemento al final y lo hacemos "subir"
        this.ultimo++;
        this.heap[this.ultimo] = nuevaCelda;
        hacerSubir(this.ultimo);

        return true;
    }

    /**
     * Elimina el elemento de mayor prioridad (la raíz del Min-Heap).
     * @return verdadero si pudo eliminar, falso si la cola estaba vacía.
     */
    public boolean eliminarFrente() {
        boolean exito = false;
        if (!this.esVacia()) {
            // Reemplazamos la raíz (heap[1]) por el último elemento
            this.heap[1] = this.heap[this.ultimo];
            this.heap[this.ultimo] = null; // Limpiar la última posición
            this.ultimo--;

            // Hacemos "bajar" el nuevo elemento raíz para reordenar
            if (this.ultimo > 0) {
                hacerBajar(1);
            }
            exito = true;
        }
        return exito;
    }

    /**
     * Obtiene el elemento de mayor prioridad (la raíz) sin eliminarlo.
     * @return El elemento más prioritario, o null si la cola está vacía.
     */
    public Object obtenerFrente() {
        Object frente = null;
        if (!this.esVacia()) {
            frente = this.heap[1].getElemento();
        }
        return frente;
    }

    /**
     * Obtiene la celda completa (elemento + prioridad) del frente
     * sin eliminarla.
     * @return La CeldaCP más prioritaria, o null si la cola está vacía.
     */
    public CeldaCP obtenerFrenteCelda() {
        return this.esVacia() ? null : this.heap[1];
    }

    /**
     * Verifica si la cola no tiene elementos.
     * @return verdadero si está vacía, falso en caso contrario.
     */
    public boolean esVacia() {
        return this.ultimo == 0;
    }

    // --- Métodos privados del Heap ---

    /**
     * Reordena el heap hacia arriba (percolate up).
     * Compara el nodo en posHijo con su padre y los intercambia
     * si el hijo es más prioritario que el padre.
     */
    private void hacerSubir(int posHijo) {
        int posPadre = posHijo / 2;
        boolean seguir = (posPadre > 0);

        while (seguir) {
            // Comparamos hijo vs padre (usamos el compareTo de CeldaCP)
            if (this.heap[posHijo].compareTo(this.heap[posPadre]) < 0) {
                // El hijo es menor (más prioritario), los intercambiamos
                intercambiar(posHijo, posPadre);
                // Continuamos subiendo
                posHijo = posPadre;
                posPadre = posHijo / 2;
                seguir = (posPadre > 0);
            } else {
                // El padre es menor o igual, el heap está ordenado
                seguir = false;
            }
        }
    }

    /**
     * Reordena el heap hacia abajo (percolate down).
     * Compara el nodo en posPadre con sus hijos y lo intercambia
     * con el hijo más prioritario si es necesario.
     */
    private void hacerBajar(int posPadre) {
        int posHijoIzq = posPadre * 2;
        int posHijoMenor;
        boolean seguir = true;

        while (seguir && posHijoIzq <= this.ultimo) {
            // 1. Encontrar al hijo más prioritario (menor)
            posHijoMenor = posHijoIzq;
            int posHijoDer = posHijoIzq + 1;

            if (posHijoDer <= this.ultimo) {
                // Hay dos hijos, compararlos
                if (this.heap[posHijoDer].compareTo(this.heap[posHijoIzq]) < 0) {
                    posHijoMenor = posHijoDer;
                }
            }

            // 2. Comparar al padre con su hijo menor
            if (this.heap[posPadre].compareTo(this.heap[posHijoMenor]) > 0) {
                // El padre es mayor (menos prioritario), intercambiar
                intercambiar(posPadre, posHijoMenor);
                // Continuamos bajando
                posPadre = posHijoMenor;
                posHijoIzq = posPadre * 2;
            } else {
                // El padre es menor o igual, el heap está ordenado
                seguir = false;
            }
        }
    }

    /**
     * Intercambia dos elementos en el arreglo del heap.
     */
    private void intercambiar(int pos1, int pos2) {
        CeldaCP temp = this.heap[pos1];
        this.heap[pos1] = this.heap[pos2];
        this.heap[pos2] = temp;
    }

    /**
     * Duplica el tamaño del arreglo si se llena.
     */
    private void redimensionar() {
        int nuevoTam = this.heap.length * 2;
        CeldaCP[] nuevoHeap = new CeldaCP[nuevoTam];
        System.arraycopy(this.heap, 1, nuevoHeap, 1, this.ultimo);
        this.heap = nuevoHeap;
    }

    /**
     * Vacía la cola de prioridad.
     */
    public void vaciar() {
        this.ultimo = 0;
        // Opcional: limpiar el arreglo
        this.heap = new CeldaCP[TAM_INICIAL];
    }
}