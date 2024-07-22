package test.conjuntistas;

//import conjuntistas.dinamicas.ArbolBB;
import conjuntistas.dinamicas.ArbolBB;


public class TestABB {
    public static void main(String[] args) {
        //generar un test para probar todas las operaciones del arbol binario de busqueda
        ArbolBB arbol = new ArbolBB();
        System.out.println("-----PRUEBA METODO toString(), esVacio() e insertar()-----");
        System.out.println("Arbol vacio: " + arbol.toString());
        System.out.println("Es vacio: " + arbol.esVacio());
        System.out.println("Insertar 10: " + arbol.insertar(10));
        System.out.println("Insertar 7: " + arbol.insertar(7));
        System.out.println("Insertar 4: " + arbol.insertar(4));
        System.out.println("Insertar 9: " + arbol.insertar(9));
        System.out.println("Insertar 23: " + arbol.insertar(23));
        System.out.println("Insertar 15: " + arbol.insertar(15));
        System.out.println("Insertar 12: " + arbol.insertar(12));
        System.out.println("Insertar 25: " + arbol.insertar(25));
        System.out.println("Insertar 24: " + arbol.insertar(24));
        System.out.println("Insertar 30: " + arbol.insertar(30));
        System.out.println("Insertar 27: " + arbol.insertar(27));
        System.out.println("Insertar elemento Repetido 27. Devuelve False: " + arbol.insertar(27));
        System.out.println("Insertar elemento Repetido 22. Devuelve False: " + arbol.insertar(23));
        System.out.println("");
        System.out.println("-----COMPRUEBO EL ARBOL ACTUAL Y PROBAMOS METODO PERTENECE-----");
        System.out.println("Arbol: " + arbol.toString());
        System.out.println("Es vacio: " + arbol.esVacio());
        System.out.println("Pertenece 10. Devuelve true: " + arbol.pertenece(10));
        System.out.println("Pertenece 9. Devuelve true: " + arbol.pertenece(9));
        System.out.println("Pertenece 30. Devuelve true: " + arbol.pertenece(30));
        System.out.println("Pertenece 22. Devuelve false: " + arbol.pertenece(22));
        System.out.println("Pertenece 1. Devuelve false: " + arbol.pertenece(1));
        System.out.println("");
        System.out.println("-----PROBAMOS EL METODO ELIMINAR-----");
        System.out.println("+++++PRUEBA ELEMENTO QUE NO EXISTE EN EL ARBOL+++++");
        System.out.println("Eliminar 1. Devuelve false: " + arbol.eliminar(1));
        System.out.println("Eliminar 66. Devuelve false: " + arbol.eliminar(66));
        System.out.println("");
        System.out.println("+++++PRUEBA ELIMINAR CASO 1+++++");
        System.out.println("Eliminar 9. Devuelve true: " + arbol.eliminar(9));
        System.out.println("Eliminar 24. Devuelve true: " + arbol.eliminar(24));
        System.out.println("");
        System.out.println("+++++PRUEBA ELIMINAR CASO 2+++++");
        System.out.println("Eliminar 7. Devuelve true: " + arbol.eliminar(7));
        System.out.println("Eliminar 30. Devuelve true: " + arbol.eliminar(30));
        System.out.println("");
        System.out.println("+++++PRUEBA ELIMINAR CASO 3+++++");
        System.out.println("Eliminar 23. Devuelve true: " + arbol.eliminar(23));
        System.out.println("");
        System.out.println("-----IMPRIMO ARBOL RESULTANTE-----");
        System.out.println(arbol.toString());


    }
}
