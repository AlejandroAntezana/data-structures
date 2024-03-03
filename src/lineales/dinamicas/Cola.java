package lineales.dinamicas;

public class Cola {

    private Nodo frente;
    private Nodo fin;

    public Cola() {
        this.frente = null;
        this.fin = null;
    }

    public boolean poner(Object elem) {
        if (this.frente == null) {
            //Si la lista esta vacia, frente y fin quedan apuntando al nuevo nodo.
            Nodo nuevoNodo = new Nodo(elem, null);
            this.fin = nuevoNodo;
            this.frente = nuevoNodo;
        } else {
            Nodo nuevoNodo = new Nodo(elem, null);
            this.fin.setEnlace(nuevoNodo);
            this.fin = this.fin.getEnlace();
        }
        return true; //nunca hay error de cola llena
    }


    public boolean sacar() {
        boolean exito = true;

        if (this.frente == null) {
            //Error de cola vacia
            exito = false;
        } else {
            //al menos hay 1 elemento
            //quita el primer elemento y actualiza frente (y fin si es que queda vacia)
            this.frente = this.frente.getEnlace();
            if (this.frente == null) {
                this.fin = null;
            }
        }
        return exito;
    }

    public Object obtenerFrente() {
        Object elemento;
        if (this.frente == null) {
            //si la cola esta vacia devuelve null
            elemento = null;
        } else {
            //sino devuelve el elemento del frente
            elemento = this.frente.getElem();
        }
        return elemento;
    }

    public boolean esVacia() {
        return this.frente == null;
    }

    public void vaciar() {
        this.frente = null;
        this.fin = null;
    }

    public Cola clone() {
        Cola clon = new Cola();
        clonar(clon, this.frente);
        return clon;
    }

    private void clonar(Cola unaCola, Nodo elfrente) {
        if (elfrente != null) {
            clonar(unaCola, elfrente.getEnlace());
            Nodo nuevo = new Nodo(elfrente.getElem(), unaCola.frente);
            if (nuevo.getEnlace() == null) {
                unaCola.fin = nuevo;
            }
            unaCola.frente = nuevo;
        }
    }

    public String toString() {
        String elementos = "";

        if (this.frente == null) {
            elementos = "[]";
        } else {
            Nodo indice = this.frente;
            elementos += "[ ";

            while (indice != null) {
                elementos += indice.getElem().toString() + " ";
                indice = indice.getEnlace();
            }
            elementos += "]";
        }
        return elementos;
    }


}
