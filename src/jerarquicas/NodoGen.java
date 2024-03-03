package jerarquicas;

public class NodoGen {
    private Object elemento;
    private NodoGen hijoIzquierdo;
    private NodoGen hermanoDerecho;

    public NodoGen(Object elemento, NodoGen hijoIzquierdo, NodoGen hermanoDerecho){
        this.elemento = elemento;
        this.hijoIzquierdo = hijoIzquierdo;
        this.hermanoDerecho = hermanoDerecho;
    }

    public Object getElemento() {
        return elemento;
    }

    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }

    public NodoGen getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public void setHijoIzquierdo(NodoGen hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public NodoGen getHermanoDerecho() {
        return hermanoDerecho;
    }

    public void setHermanoDerecho(NodoGen hermanoDerecho) {
        this.hermanoDerecho = hermanoDerecho;
    }
}
