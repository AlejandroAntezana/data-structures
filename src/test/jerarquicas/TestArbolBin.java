package test.jerarquicas;

import jerarquicas.ArbolBin;

public class TestArbolBin {
    public static void main(String[] args) {
        ArbolBin arbol1 = new ArbolBin();

        //inserto la raiz
arbol1.insertar(1, 1, 'R');
        //inserto los hijos
        arbol1.insertar(2, 1, 'I');
        arbol1.insertar(3, 1, 'D');
        //inserto los nietos
        arbol1.insertar(4, 2, 'I');
        arbol1.insertar(5, 2, 'D');
        arbol1.insertar(6, 3, 'I');
        arbol1.insertar(7, 3, 'D');

        System.out.println(arbol1.toString());
        System.out.println("Altura: " + arbol1.altura());
        System.out.println(arbol1.padre(2));
        System.out.println(arbol1.insertar(8, 9, 'I'));
        System.out.println(arbol1.nivel(1000));

    }
}
