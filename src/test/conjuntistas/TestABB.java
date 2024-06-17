package test.conjuntistas;

import conjuntistas.dinamicas.ArbolBB;



public class TestABB {
    public static void main(String[] args) {
        ArbolBB arbol = new ArbolBB();

        arbol.insertar(4);
        arbol.insertar(3);
        arbol.insertar(8);
        arbol.insertar(12);
        arbol.insertar(6);
        arbol.insertar(7);
        arbol.insertar(1);



        System.out.println(arbol.toString());
        //System.out.println(arbol.pertenece(4));
    }
}
