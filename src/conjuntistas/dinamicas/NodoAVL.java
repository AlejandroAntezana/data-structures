package conjuntistas.dinamicas;

public class NodoAVL{
    //Agregamos el atributo de altura para el nodo
    Comparable elemento;
    private NodoAVL izquierdo;
    private NodoAVL derecho;
    private int altura;

    //constructor
    public NodoAVL(Comparable elem){
        this.elemento = elem;
        this.izquierdo = null;
        this.derecho = null;
        this.altura = 0; //seria la altura del subarbol que tiene a este nodo como raiz
    }

    //observadores
    public Comparable getElemento(){
        return this.elemento;
    }
    public void setElemento(Comparable elem){
        this.elemento = elem;
    }
    public NodoAVL getIzquierdo(){
        return this.izquierdo;
    }
    public void setIzquierdo(NodoAVL izq){
        this.izquierdo = izq;
    }
    public NodoAVL getDerecho(){
        return this.derecho;
    }
    public void setDerecho(NodoAVL der){
        this.derecho = der;
    }
    public int getAltura(){
        return this.altura;
    }
    public void setAltura(int alt){
        this.altura = alt;
    }

    //Las operaciones de acceso y modificacion de la altura
    public void recalcularAltura(){
        int altIzq = -1;
        int altDer = -1;
        if(this.izquierdo != null){
            altIzq = this.izquierdo.getAltura();
        }
        if(this.derecho != null){
            altDer = this.derecho.getAltura();
        }
        this.altura = Math.max(altIzq, altDer) + 1;
    }

}
