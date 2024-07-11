package test.conjuntistas;

import conjuntistas.dinamicas.ArbolBB;



public class TestABB {
    public static void main(String[] args) {
       //generar un test para probar todas las operaciones del arbol binario de busqueda
         ArbolBB arbol = new ArbolBB();
            System.out.println("Arbol vacio: " + arbol.toString());
            System.out.println("Es vacio: " + arbol.esVacio());
            System.out.println("Insertar 10: " + arbol.insertar(10));
            System.out.println("Insertar 5: " + arbol.insertar(5));
            System.out.println("Insertar 15: " + arbol.insertar(15));
            System.out.println("Insertar 3: " + arbol.insertar(3));
            System.out.println("Insertar 7: " + arbol.insertar(7));
            System.out.println("Insertar 12: " + arbol.insertar(12));
            System.out.println("Insertar 20: " + arbol.insertar(20));
            System.out.println("Insertar 1: " + arbol.insertar(1));
            System.out.println("Insertar 4: " + arbol.insertar(4));
            System.out.println("Insertar 6: " + arbol.insertar(6));
            System.out.println("Insertar 8: " + arbol.insertar(8));
            System.out.println("Insertar 11: " + arbol.insertar(11));
            System.out.println("Insertar 13: " + arbol.insertar(13));
            System.out.println("Insertar 17: " + arbol.insertar(17));
            System.out.println("Insertar 25: " + arbol.insertar(25));
            System.out.println("Insertar 2: " + arbol.insertar(2));
            System.out.println("Insertar 9: " + arbol.insertar(9));
            System.out.println("Insertar 14: " + arbol.insertar(14));
            System.out.println("Insertar 16: " + arbol.insertar(16));
            System.out.println("Insertar 18: " + arbol.insertar(18));
            System.out.println("Insertar 26: " + arbol.insertar(26));
            System.out.println("Insertar 19: " + arbol.insertar(19));
            System.out.println("Insertar 21: " + arbol.insertar(21));
            System.out.println("Insertar 22: " + arbol.insertar(22));
            System.out.println("Insertar 10: " + arbol.insertar(10));

            System.out.println("Arbol: " + arbol.toString());
            System.out.println("Es vacio: " + arbol.esVacio());
            System.out.println("Pertenece 10: " + arbol.pertenece(10));
            System.out.println("Pertenece 33: " + arbol.pertenece(5));
            System.out.println("Pertenece 26: " + arbol.pertenece(15));

            System.out.println("Eliminar 10: " + arbol.eliminar(10));
            System.out.println("Eliminar 5: " + arbol.eliminar(5));
            System.out.println("Eliminar 14: " + arbol.eliminar(15));
            System.out.println("Eliminar 3: " + arbol.eliminar(3));

            System.out.println("Arbol: " + arbol.toString());

            System.out.println("Clonar: " + arbol.clone());
            System.out.println("Vaciar: ");
            arbol.vaciar();
            System.out.println("Arbol vacio: " + arbol.toString());
            System.out.println("Es vacio: " + arbol.esVacio());
    }
}
