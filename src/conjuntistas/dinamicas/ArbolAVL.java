package conjuntistas.dinamicas;

import lineales.dinamicas.Lista;

public class ArbolAVL {
    private NodoAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    //Primero implementamos los metodos privados que nos ayudaran a realizar las operaciones
    //de insercion y eliminacion de elementos

    //metodo calcularBalanceo
    private int calcularBalanceo(NodoAVL nodo){
        //Calcula el balanceo del nodo pasado por parametro
        //Si el balanceo es positivo, el subarbol izquierdo es mas pesado
        //Si el balanceo es negativo, el subarbol derecho es mas pesado
        //Si el balanceo es 0, los subarboles tienen la misma altura

        int altIzq = -1; //Se inicializan en -1 por si el nodo no tiene hijo izquierdo o derecho.
        int altDer = -1;
        if(nodo.getIzquierdo() != null){
            altIzq = nodo.getIzquierdo().getAltura();
        }
        if(nodo.getDerecho() != null){
            altDer = nodo.getDerecho().getAltura();
        }
        return altIzq - altDer;
    }

    /*---Rotaciones del AVL---*/

    private void rotacionSimpleIzquierda(NodoAVL nodo){
        NodoAVL aux = nodo.getIzquierdo();
        nodo.setIzquierdo(aux.getDerecho());
        aux.setDerecho(nodo);
    }
    private void rotacionSimpleDerecha(NodoAVL nodo){
        NodoAVL aux = nodo.getDerecho();
        nodo.setDerecho(aux.getIzquierdo());
        aux.setIzquierdo(nodo);
    }

    private void rotacionIzquierdaDerecha(NodoAVL nodo){

    }

    private void rotacionDerechaIzquierda(NodoAVL nodo){

    }
}