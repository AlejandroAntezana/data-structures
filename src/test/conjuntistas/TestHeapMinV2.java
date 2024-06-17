package test.conjuntistas;
import conjuntistas.lineales.HeapMin;

public class TestHeapMinV2 {
    public static void main(String[] args) {
        pruebaInsertarElemento();
        System.out.println("-------------------------------------------------");
        pruebaEliminarCima();
        System.out.println("-------------------------------------------------");
        pruebaRecuperarCima();
        System.out.println("-------------------------------------------------");
        pruebaEsVacioYVaciar();
        System.out.println("Finalizaron las pruebas");


    }

    //metodo que prueba insertar 10 elementos aleatorios en un heap
    public static void pruebaInsertarElemento() {
        HeapMin heap = new HeapMin();
        System.out.println("Se creo un heap vacio: " + heap.toString());
        System.out.println("Insertando elementos...");
        for (int i = 0; i < 10; i++) {
            int num = (int) (Math.random() * 100);
            heap.insertar(num);
            System.out.println("Se inserto el elemento " + num + " en el heap: " + heap.toString());
        }
    }
    //Metodo que prueba eliminar la cima de un heap
    public static void pruebaEliminarCima() {
        HeapMin heap = new HeapMin();
        System.out.println("Se creo un heap vacio: " + heap.toString());
        System.out.println("Eliminando cima...");
        heap.eliminarCima();
        System.out.println("Se elimino la cima del heap: " + heap.toString());
        System.out.println("Insertando elementos...");
        for (int i = 0; i < 10; i++) {
            int num = (int) (Math.random() * 100);
            heap.insertar(num);
            System.out.println("Se inserto el elemento " + num + " en el heap: " + heap.toString());
        }
        System.out.println("Eliminando cima...");
        heap.eliminarCima();
        System.out.println("Se elimino la cima del heap: " + heap.toString());
    }

    public static void pruebaRecuperarCima() {
        HeapMin heap = new HeapMin();
        System.out.println("Se creo un heap vacio: " + heap.toString());
        System.out.println("Recuperando cima...");
        System.out.println("La cima del heap es: " + heap.recuperarCima());
        System.out.println("Insertando elementos...");
        for (int i = 0; i < 10; i++) {
            int num = (int) (Math.random() * 100);
            heap.insertar(num);
            System.out.println("Se inserto el elemento " + num + " en el heap: " + heap.toString());
        }
        System.out.println("Recuperando cima...");
        System.out.println("La cima del heap es: " + heap.recuperarCima());
        System.out.println("Eliminando cima...");
        heap.eliminarCima();
        System.out.println("Se elimino la cima del heap: " + heap.toString());
        System.out.println("Recuperando cima...");
        System.out.println("La cima del heap es: " + heap.recuperarCima());
    }

    public static void pruebaClonar() {
        HeapMin heap = new HeapMin();
        System.out.println("Se creo un heap vacio: " + heap.toString());
        System.out.println("Insertando elementos...");
        for (int i = 0; i < 10; i++) {
            int num = (int) (Math.random() * 100);
            heap.insertar(num);
            System.out.println("Se inserto el elemento " + num + " en el heap: " + heap.toString());
        }
        System.out.println("Clonando heap...");
        HeapMin clon = heap.clone();
        System.out.println("El clon del heap es: " + clon.toString());
    }

    public static void pruebaEsVacioYVaciar() {
        HeapMin heap = new HeapMin();
        System.out.println("Se creo un heap vacio: " + heap.toString());
        System.out.println("El heap esta vacio? " + heap.esVacio());
        System.out.println("Insertando elementos...");
        for (int i = 0; i < 10; i++) {
            int num = (int) (Math.random() * 100);
            heap.insertar(num);
            System.out.println("Se inserto el elemento " + num + " en el heap: " + heap.toString());
        }
        System.out.println("El heap esta vacio? " + heap.esVacio());
        System.out.println("Vaciando heap...");
        heap.vaciar();
        System.out.println("El heap esta vacio? " + heap.esVacio());
    }





}
