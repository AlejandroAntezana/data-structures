package lineales.estaticas;

public class Cola {

    private Object[] arreglo;
    private int frente;
    private int fin;
    private static final int TAMANIO = 10;

    public Cola() {
        this.arreglo = new Object[this.TAMANIO];
        this.frente = 0;
        this.fin = 0;
    }

    public boolean poner(Object elem) {
        boolean exito;
        if ((this.fin + 1) % TAMANIO == this.frente) {
            //Error de cola llena
            exito = false;
        } else {
            //se agrega el elemento y se actualiza el final
            this.arreglo[this.fin] = elem;
            this.fin = (this.fin + 1) % this.TAMANIO;
            exito = true;
        }
        return exito;
    }


    public boolean sacar() {
        boolean exito = true;
        if (this.frente == this.fin) {
            exito = false;
        } else {
            this.arreglo[this.frente] = null;
            this.frente = (this.frente + 1) % this.TAMANIO;
        }
        return exito;
    }

    public Object obtenerFrente() {
        Object element;
        if (this.fin == this.frente) {
            //la cola esta vacia
            element = null;
        } else {
            element = this.arreglo[this.frente];
        }
        return element;
    }

    public boolean esVacia() {
        return (this.frente == this.fin);
    }

    public void vaciar() {
        this.arreglo = new Object[this.TAMANIO];
        this.frente = 0;
        this.fin = 0;
    }

    public Cola clone() {
        //crea una cola nueva con los mismos elementos de la cola original
        Cola clon = new Cola();
        clon.frente = this.frente;
        clon.fin = this.fin;
        for (int i = 0; i < this.TAMANIO; i++) {
            clon.arreglo[i] = this.arreglo[i];
        }
        return clon;
    }

    @Override
    public String toString(){
        String out = "";
        if(this.frente == this.fin){
            out = "[]";
        }else{
            int i = this.frente;
            out = "[ ";
            while(i != this.fin){
                out += arreglo[i];
                if((i+1)%TAMANIO != this.fin){
                    out += " , ";
                }
                i = (i+1)%TAMANIO;
            }
            out += " ]";
        }
        return out;
    }
}
