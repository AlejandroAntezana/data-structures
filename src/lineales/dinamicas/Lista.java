package lineales.dinamicas;

public class Lista {
    private Nodo cabecera;

    public Lista() {
        this.cabecera = null;
    }

    public boolean insertar(Object nuevoElem, int pos) {
        //inserta el elemento nuevo en la posicion pos.
        //detecta y reporta error posicion invalida.
        boolean exito = true;

        if (pos < 1 || pos > this.longitud() + 1) {
            exito = false;
        } else {
            if (pos == 1) {
                //crea un nuevo nodo y se enlaza en la cabecera
                this.cabecera = new Nodo(nuevoElem, this.cabecera);
            } else { //avanaza hasta el elemento en posicion pos-1
                Nodo indice = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    indice = indice.getEnlace();
                    i++;
                }
                //crea el nodo y lo enlaza
                Nodo nuevo = new Nodo(nuevoElem, indice.getEnlace());
                indice.setEnlace(nuevo);
            }
        }
        //nunca hay error de lista llena entonces devuelve true
        return exito;
    }

    public boolean eliminar(int pos) {
        boolean exito;
        int longitud = this.longitud();
        if (pos < 1 || pos > longitud) {
            exito = false;
        } else if (pos == 1 && !esVacia()) {
            this.cabecera = this.cabecera.getEnlace();
            exito = true;
        } else {
            Nodo indice = this.cabecera;
            int i = 1;
            while (i < pos - 1) {
                indice = indice.getEnlace();
                i++;
            }
            indice.setEnlace(indice.getEnlace().getEnlace());
            exito = true;
        }
        return exito;
    }

    public Object recuperar(int pos) {
        Object elem;
        Nodo indice = null;
        if (this.cabecera == null) {
            //error lista vacia
            elem = null;
        } else {
            if (pos >= 1 && pos < this.longitud() + 1) {
                int i = 1;
                indice = this.cabecera;
                while (i < pos) {
                    indice = indice.getEnlace();
                    i++;
                }
                elem = indice.getElem();
            } else {
                elem = null;
            }
        }
        return elem;
    }

    public int localizar(Object elem) {
        int locacion = -1;
        boolean exito = false;
        if (!esVacia()) {
            int varIter = 1;
            Nodo indice;
            for (indice = this.cabecera; indice != null && !exito; indice = indice.getEnlace()) {
                if (elem == indice.getElem()) {
                    exito = true;
                    locacion = varIter;
                }
                varIter++;
            }
        }
        return locacion;
    }
    public int longitud() {
        int longitud, varIter = 1;
        if (this.cabecera == null) {
            //la lista esta vacia
            longitud = 0;
        } else {
            Nodo indice = this.cabecera;
            longitud = 0;
            while (indice != null) {
                longitud++;
                indice = indice.getEnlace();
            }
        }
        return longitud;
    }

    public Lista clone() {
        Lista listaClon = new Lista();
        clonar(listaClon, this.cabecera);
        return listaClon;
    }

    private void clonar(Lista unaLista, Nodo cabecera) {
        if (cabecera != null) {
            clonar(unaLista, cabecera.getEnlace());
            Nodo nuevo = new Nodo(cabecera.getElem(), unaLista.cabecera);
            unaLista.cabecera = nuevo;
        }
    }
    public boolean esVacia() {
        return this.cabecera == null;
    }
    public void vaciar() {
        this.cabecera = null;
    }

    @Override
    public String toString() {
        String elementos = "";

        Nodo indice = this.cabecera;

        while (indice != null) {
            elementos += indice.getElem().toString();
            indice = indice.getEnlace();
        }
        return "Lista{" +
                elementos +
                '}';
    }

    public Lista obtenerMultiplos(int num) {
        Lista listaNueva = new Lista();
        Nodo indice = this.cabecera;
        Nodo indiceListaNueva = listaNueva.cabecera;
        int multiplo, i = 1, k = 1;
        if (num > 0) {
            //si num = 0 no devuelve lista vacia
            //si num = 1 devuelve toda la lista
            //si num >1 devuelve los multiplos de num
            while (indice != null) {
                multiplo = num * k;
                if (multiplo == i) {
                    if (listaNueva.cabecera == null) {
                        listaNueva.cabecera = new Nodo(indice.getElem(), listaNueva.cabecera);
                        indiceListaNueva = listaNueva.cabecera;
                        k++;
                    } else {
                        Nodo nuevo = new Nodo(indice.getElem(), null);
                        indiceListaNueva.setEnlace(nuevo);
                        indiceListaNueva = nuevo;
                        k++;
                    }
                }
                indice = indice.getEnlace();
                i++;
            }
        }
        return listaNueva;
    }

    public void eliminarApariciones(Object x) {
        if (!esVacia()) {
            Nodo indice = this.cabecera;
            while (indice != null) {
                if (indice.getElem().equals(x)) {
                    this.cabecera = this.cabecera.getEnlace();
                } else if (indice.getEnlace().getElem().equals(x)) {
                    indice.setEnlace(indice.getEnlace().getEnlace());
                }
                indice=indice.getEnlace();
            }
        }
    }

}


