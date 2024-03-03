package lineales.dinamicas;

/*
 *@author Antezana de la Rivera, Alejandro. FAI-2945.
 */
public class Pila {
    private Nodo tope;

    public Pila() {
        this.tope = null;
    }

    public boolean apilar(Object nuevoElem) {
        Nodo nuevo = new Nodo(nuevoElem, this.tope);
        this.tope = nuevo;
        return true;
    }

    public boolean desapilar() {
        boolean exito;
        if (this.tope == null) {
            //error pila vacia
            exito = false;
        } else {
            //le asigno a tope el enlace del siguiente Nodo.
            this.tope = this.tope.getEnlace();
            exito = true;
        }
        return exito;
    }

    public Object obtenerTope() {
        Object elem;
        if (this.tope != null) {
            elem = this.tope.getElem();
        } else {
            elem = null;
        }
        return elem;
    }

    public boolean esVacia() {
        return (this.tope == null);
    }

    public void vaciar() {
        this.tope = null;
    }

    public Pila clone() {
        Pila pilaClon = new Pila();
        clonar(pilaClon, this.tope);
        return pilaClon;
    }

    //Modulo recursivo que permite clonar una pila
    private void clonar(Pila unaPila, Nodo topePila) {
        if (topePila != null) {
            clonar(unaPila, topePila.getEnlace());
            Nodo nuevo = new Nodo(topePila.getElem(), unaPila.tope);
            unaPila.tope = nuevo;
        }
    }


    @Override
    public String toString() {
        String s = "";

        if (this.tope == null) {
            s = "Pila vacia.";
        } else {
            //se ubica para recorrer la pila.
            Nodo aux = this.tope;
            s = "[";

            while (aux != null) {
                //agrega el texto del elem y avanza
                s += aux.getElem().toString();
                aux = aux.getEnlace();
                if (aux != null) {
                    s += ",";
                }
            }
            s += "]";
        }
        return s;
    }
}
