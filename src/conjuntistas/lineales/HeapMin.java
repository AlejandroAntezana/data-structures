package conjuntistas.lineales;

public class HeapMin {
    private Comparable[] heap;
    private int ultimo;
    private int TAMANIO = 20;

    public HeapMin() {
        this.heap = new Comparable[TAMANIO];
        this.ultimo = 0; //La posición 0 no se usa
    }

    public boolean insertar(Comparable elem) {
        boolean exito = false;
        if (this.ultimo + 1 < this.TAMANIO) {
            this.ultimo++;
            this.heap[this.ultimo] = elem;
            hacerSubir(this.ultimo);
            exito = true;
        }
        return exito;
    }

    private void hacerSubir(int pos) {
        Comparable temp = this.heap[pos];
        while (pos > 1 && temp.compareTo(this.heap[pos / 2]) < 0) {
            this.heap[pos] = this.heap[pos / 2];
            pos = pos / 2;
        }
        this.heap[pos] = temp;
    }

    public boolean eliminarCima() {
        boolean exito = false;
        if (this.ultimo > 0) {
            this.heap[1] = this.heap[this.ultimo];
            this.ultimo--;
            hacerBajar(1);
            exito = true;
        }
        return exito;
    }

    private void hacerBajar(int pos) {
        Comparable temp = this.heap[pos];
        int hijo = pos * 2;
        boolean salir = false;

        while (hijo <= this.ultimo && !salir) {
            if (hijo < this.ultimo && this.heap[hijo + 1].compareTo(this.heap[hijo]) < 0) {
                hijo++;
            }
            if (this.heap[hijo].compareTo(temp) < 0) {
                this.heap[pos] = this.heap[hijo];
                pos = hijo;
                hijo = pos * 2;
            } else {
                salir = true;
            }
        }
        this.heap[pos] = temp;
    }

    public Comparable recuperarCima() {
        return this.heap[1];
    }

    public boolean esVacio() {
        return this.ultimo == 0;
    }

    public void vaciar() {
        this.heap = new Comparable[TAMANIO];
        this.ultimo = 0;
    }

    public HeapMin clone() {
        HeapMin clon = new HeapMin();
        clon.ultimo = this.ultimo;
        clon.heap = this.heap.clone(); //Utilizamos el metodo clone de la clase Object
        return clon;
    }

    public String toString() {
        String s = "";
        for (int i = 1; i <= this.ultimo; i++) {
            s += this.heap[i] + " ";
        }
        return s;
    }

}
