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

    /**
     * Busca un elemento en la lista.
     * @param elem el elemento a buscar.
     * @return la posición (1-based) donde se encuentra, o -1 si no se encuentra.
     */
    public int localizar(Object elem) {
        int locacion = -1;
        boolean exito = false;
        if (!esVacia()) {
            int varIter = 1;
            Nodo indice = this.cabecera;

            // Recorremos la lista mientras no encontremos el elemento
            while (indice != null && !exito) {
                if (elem.equals(indice.getElem())) {
                    exito = true;
                    locacion = varIter;
                }
                indice = indice.getEnlace();
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
        if (esVacia()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Nodo indice = this.cabecera;

        while (indice != null) {
            sb.append(indice.getElem().toString());
            indice = indice.getEnlace();
            if (indice != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
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

    /**
     * Elimina todas las apariciones de un elemento 'x' en la lista.
     * @param x el elemento a eliminar.
     */
    public void eliminarApariciones(Object x) {
        // Usamos dos punteros, uno 'previo' y uno 'actual'
        Nodo prev = null;
        Nodo actual = this.cabecera;

        while (actual != null) {
            if (actual.getElem().equals(x)) {
                // Se encontró el elemento 'x', hay que eliminarlo
                if (prev == null) {
                    // El elemento a eliminar es la cabecera
                    this.cabecera = actual.getEnlace();
                } else {
                    // El elemento está en el medio o al final
                    prev.setEnlace(actual.getEnlace());
                }
                // Avanzamos 'actual' sin mover 'prev', por si hay elementos repetidos
                actual = actual.getEnlace();
            } else {
                // El elemento no es 'x', avanzamos ambos punteros
                prev = actual;
                actual = actual.getEnlace();
            }
        }
    }

}


