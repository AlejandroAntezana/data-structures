package test.conjuntistas;
import conjuntistas.dinamicas.ArbolAVL;

public class TestArbolAVL {
    public static void main(String[] args) {
        //test exhaustivo de los metodos de la clase ArbolAVL
        ArbolAVL arbol = new ArbolAVL();
        System.out.println("Arbol vacio: " + arbol.toString());
        System.out.println("Arbol vacio es vacio: " + arbol.esVacio());
        System.out.println("Arbol vacio pertenece 1: " + arbol.pertenece(1));

        System.out.println("Insertar 10: " + arbol.insertar(10));
        System.out.println("Insertar 5: " + arbol.insertar(5));
        System.out.println("Insertar 15: " + arbol.insertar(15));
        System.out.println("Insertar 2: " + arbol.insertar(2));
        System.out.println("Insertar 7: " + arbol.insertar(7));
        System.out.println("Insertar 12: " + arbol.insertar(12));
        System.out.println("Insertar 20: " + arbol.insertar(20));
        System.out.println("Insertar 1: " + arbol.insertar(1));

        System.out.println("Arbol: " + arbol.toString());

        System.out.println("Pertenece 10: " + arbol.pertenece(10));
        System.out.println("Pertenece 5: " + arbol.pertenece(5));
        System.out.println("Pertenece 20: " + arbol.pertenece(20));
        System.out.println("Pertenece 2: " + arbol.pertenece(2));
        System.out.println("Pertenece 33: " + arbol.pertenece(33));
        System.out.println("Pertenece 77: " + arbol.pertenece(77));

        //prueba de eliminar
        System.out.println("Eliminar 2: " + arbol.eliminar(2));
        System.out.println("Eliminar 7: " + arbol.eliminar(7));
        System.out.println("Eliminar 15: " + arbol.eliminar(15));
        System.out.println("Eliminar 10: " + arbol.eliminar(10));

        System.out.println("Arbol: " + arbol.toString());


    }
}
