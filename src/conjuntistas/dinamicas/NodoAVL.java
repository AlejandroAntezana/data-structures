package conjuntistas.dinamicas;

public class NodoAVL extends NodoABB{
    //Agregamos el atributo de altura para el nodo
    private int altura;

    //Constructor redefinido para avl
    public NodoAVL(Comparable elem, NodoAVL izq, NodoAVL der){
        super(elem, izq, der);
        this.altura = 0;
    }

    //Las operaciones de acceso y modificacion de la altura
    public static int obtenerAltura(NodoAVL nodo){
        return nodo.altura;
    }

    public static void recalcularAltura(NodoAVL nodo){
        int altIzq = -1;
        int altDer = -1;
        if(nodo.getIzquierdo() != null){
            //En este caso realizamos un casteo de la clase padre a la clase hija.
            altIzq = obtenerAltura((NodoAVL)nodo.getIzquierdo());
        }
        if(nodo.getDerecho() != null){
            altDer = obtenerAltura((NodoAVL)nodo.getDerecho());
        }
        nodo.altura = Math.max(altIzq, altDer) + 1;
    }


}
