package test.jerarquicas;
import jerarquicas.ArbolGen;
import lineales.dinamicas.Lista;


public class TestArbolGen {
    public static void main(String[] args) {
        ArbolGen a1 = new ArbolGen();
        Lista lista = new Lista();

        a1.insertar('A', null);
        a1.insertar('B', 'A');
        a1.insertar('C', 'A');
        a1.insertar('D', 'B');
        a1.insertar('G', 'D');

        lista.insertar('G', 1);
        lista.insertar('A', 2);
        lista.insertar('B', 3);



        System.out.println(a1.sonFrontera(lista));
        System.out.println(a1.listarPreorden().toString());
    }


}
