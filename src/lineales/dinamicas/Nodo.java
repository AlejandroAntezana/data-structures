package lineales.dinamicas;
/*
 *@author Antezana de la Rivera, Alejandro. FAI-2945.
 */

public class Nodo {
    private Object elem; // elemento almacenado en el nodo.
    private Nodo enlace; //Valor que almacena el siguiente nodo en la pila.

    //Constructor
    public Nodo(Object elem, Nodo enlace) {
        this.elem = elem;
        this.enlace = enlace;
    }

    //modificadoras
    public void setElem(Object elem) {
        this.elem = elem;
    }

    public void setEnlace(Nodo enlace) {
        this.enlace = enlace;
    }

    //Observadoras
    public Object getElem() {
        return elem;
    }

    public Nodo getEnlace() {
        return enlace;
    }
}
