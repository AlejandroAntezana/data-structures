package conjuntistas.dinamicas;

import lineales.dinamicas.Lista;

public class ArbolAVL {
    private NodoAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    /**
     * Inserta un elemento en el árbol. Si el elemento ya existe, la operación
     * no tiene efecto y retorna falso.
     *
     * @param elemento el elemento de tipo Comparable a insertar.
     * @return verdadero si el elemento fue insertado, falso en caso contrario.
     */
    public boolean insertar(Comparable elemento) {
        // Llama al método auxiliar recursivo y actualiza la raíz del árbol.
        // Se usa un arreglo para pasar un booleano por referencia.
        boolean[] exito = {true};
        this.raiz = insertarAux(this.raiz, elemento, exito);
        return exito[0];
    }

    private NodoAVL insertarAux(NodoAVL nodo, Comparable elem, boolean[] exito) {
        // Si el nodo es nulo, hemos encontrado la posición de inserción.
        if (nodo == null) {
            nodo = new NodoAVL(elem);
        } else {
            // Comparamos el elemento a insertar con el del nodo actual.
            int comparacion = elem.compareTo(nodo.getElemento());

            if (comparacion < 0) {
                // Si es menor, vamos al subárbol izquierdo.
                nodo.setIzquierdo(insertarAux(nodo.getIzquierdo(), elem, exito));
            } else if (comparacion > 0) {
                // Si es mayor, vamos al subárbol derecho.
                nodo.setDerecho(insertarAux(nodo.getDerecho(), elem, exito));
            } else {
                // Si es igual, el elemento ya existe.
                exito[0] = false;
            }
        }

        // Si la inserción tuvo éxito, recalculamos altura y balanceamos.
        if (exito[0]) {
            nodo.recalcularAltura();
            nodo = balancear(nodo);
        }

        return nodo;
    }
    /**
     * Elimina un elemento del árbol. Si el elemento no se encuentra,
     * la operación no tiene efecto y retorna falso.
     *
     * @param elemento el elemento a eliminar.
     * @return verdadero si el elemento fue eliminado, falso en caso contrario.
     */
    public boolean eliminar(Comparable elemento) {
        boolean[] exito = {false};
        this.raiz = eliminarAux(this.raiz, elemento, exito);
        return exito[0];
    }

    private NodoAVL eliminarAux(NodoAVL nodo, Comparable elem, boolean[] exito) {
        if (nodo != null) {
            int comparacion = elem.compareTo(nodo.getElemento());

            if (comparacion < 0) {
                // El elemento está en el subárbol izquierdo.
                nodo.setIzquierdo(eliminarAux(nodo.getIzquierdo(), elem, exito));
            } else if (comparacion > 0) {
                // El elemento está en el subárbol derecho.
                nodo.setDerecho(eliminarAux(nodo.getDerecho(), elem, exito));
            } else {
                // Encontramos el elemento a eliminar.
                exito[0] = true;
                if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                    // Caso 1: Es una hoja.
                    nodo = null;
                } else if (nodo.getIzquierdo() == null) {
                    // Caso 2: Tiene solo un hijo derecho.
                    nodo = nodo.getDerecho();
                } else if (nodo.getDerecho() == null) {
                    // Caso 2: Tiene solo un hijo izquierdo.
                    nodo = nodo.getIzquierdo();
                } else {
                    // Caso 3: Tiene dos hijos.
                    // Buscamos el mayor elemento del subárbol izquierdo (predecesor inmediato).
                    NodoAVL aux = buscarMayorDelSubarbolIzquierdo(nodo);
                    // Reemplazamos el elemento del nodo actual con el encontrado.
                    nodo.setElemento(aux.getElemento());
                    // Eliminamos el nodo que contenía el predecesor.
                    nodo.setIzquierdo(eliminarAux(nodo.getIzquierdo(), aux.getElemento(), exito));
                }
            }

            // Si se eliminó algo, actualizamos altura y balanceamos.
            if (nodo != null && exito[0]) {
                nodo.recalcularAltura();
                nodo = balancear(nodo);
            }
        }
        return nodo;
    }

    private NodoAVL buscarMayorDelSubarbolIzquierdo(NodoAVL nodo) {
        NodoAVL temp = nodo.getIzquierdo();
        while (temp.getDerecho() != null) {
            temp = temp.getDerecho();
        }
        return temp;
    }

    /**
     * Verifica si un elemento pertenece al árbol.
     *
     * @param elemento el elemento a buscar.
     * @return verdadero si el elemento está en el árbol, falso en caso contrario.
     */
    public boolean pertenece(Comparable elemento) {
        return perteneceAux(this.raiz, elemento);
    }

    private boolean perteneceAux(NodoAVL nodo, Comparable elem) {
        boolean encontrado = false;
        if (nodo != null) {
            int comparacion = elem.compareTo(nodo.getElemento());
            if (comparacion == 0) {
                encontrado = true;
            } else if (comparacion < 0) {
                encontrado = perteneceAux(nodo.getIzquierdo(), elem);
            } else {
                encontrado = perteneceAux(nodo.getDerecho(), elem);
            }
        }
        return encontrado;
    }

    /**
     * Verifica si el árbol está vacío.
     *
     * @return verdadero si el árbol no tiene elementos, falso en caso contrario.
     */
    public boolean vacio() {
        return this.raiz == null;
    }

    /**
     * Retorna el elemento más pequeño (menor) del árbol.
     *
     * @return el elemento mínimo, o null si el árbol está vacío.
     */
    public Comparable minimoElem() {
        return minimoElemAux(this.raiz);
    }

    private Comparable minimoElemAux(NodoAVL nodo) {
        Comparable minimo = null;
        if (nodo != null) {
            NodoAVL actual = nodo;
            while (actual.getIzquierdo() != null) {
                actual = actual.getIzquierdo();
            }
            minimo = actual.getElemento();
        }
        return minimo;
    }

    /**
     * Retorna el elemento más grande (mayor) del árbol.
     *
     * @return el elemento máximo, o null si el árbol está vacío.
     */
    public Comparable maximoElem() {
        return maximoElemAux(this.raiz);
    }

    private Comparable maximoElemAux(NodoAVL nodo) {
        Comparable maximo = null;
        if (nodo != null) {
            NodoAVL actual = nodo;
            while (actual.getDerecho() != null) {
                actual = actual.getDerecho();
            }
            maximo = actual.getElemento();
        }
        return maximo;
    }

    /**
     * Retorna una lista con los elementos del árbol ordenados de menor a mayor.
     *
     * @return una instancia de Lista con los elementos ordenados.
     */
    public Lista listar() {
        Lista lista = new Lista();
        listarAux(this.raiz, lista);
        return lista;
    }

    private void listarAux(NodoAVL nodo, Lista lista) {
        if (nodo != null) {
            // Recorrido Inorden: Izquierdo - Raíz - Derecho
            listarAux(nodo.getIzquierdo(), lista);
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
            listarAux(nodo.getDerecho(), lista);
        }
    }

    /**
     * Retorna una lista con los elementos del árbol cuyas claves se encuentran
     * en el rango [min, max], inclusive.
     *
     * @param min el valor mínimo del rango.
     * @param max el valor máximo del rango.
     * @return una lista con los elementos encontrados.
     */
    public Lista listarRango(Comparable min, Comparable max) {
        Lista lista = new Lista();
        listarRangoAux(this.raiz, min, max, lista);
        return lista;
    }

    private void listarRangoAux(NodoAVL nodo, Comparable min, Comparable max, Lista lista) {
        if (nodo != null) {
            Comparable claveActual = nodo.getElemento();
            // Si la clave actual es mayor que 'min', exploramos el subárbol izquierdo.
            if (claveActual.compareTo(min) > 0) {
                listarRangoAux(nodo.getIzquierdo(), min, max, lista);
            }
            // Si la clave actual está dentro del rango, la agregamos.
            if (claveActual.compareTo(min) >= 0 && claveActual.compareTo(max) <= 0) {
                lista.insertar(claveActual, lista.longitud() + 1);
            }
            // Si la clave actual es menor que 'max', exploramos el subárbol derecho.
            if (claveActual.compareTo(max) < 0) {
                listarRangoAux(nodo.getDerecho(), min, max, lista);
            }
        }
    }

    //--- MÉTODOS DE BALANCEO ---

    private int calcularBalance(NodoAVL nodo) {
        int altIzq = -1, altDer = -1;
        if (nodo.getIzquierdo() != null) {
            altIzq = nodo.getIzquierdo().getAltura();
        }
        if (nodo.getDerecho() != null) {
            altDer = nodo.getDerecho().getAltura();
        }
        return altIzq - altDer;
    }

    private NodoAVL balancear(NodoAVL nodo) {
        int balance = calcularBalance(nodo);

        if (balance > 1) { // Desbalanceado a la izquierda
            if (calcularBalance(nodo.getIzquierdo()) < 0) {
                // Rotación doble Izquierda-Derecha
                nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
            }
            // Rotación simple Derecha
            nodo = rotarDerecha(nodo);
        } else if (balance < -1) { // Desbalanceado a la derecha
            if (calcularBalance(nodo.getDerecho()) > 0) {
                // Rotación doble Derecha-Izquierda
                nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
            }
            // Rotación simple Izquierda
            nodo = rotarIzquierda(nodo);
        }
        return nodo;
    }

    private NodoAVL rotarIzquierda(NodoAVL r) {
        NodoAVL h = r.getDerecho();
        NodoAVL temp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);

        // Recalcular alturas, primero la del nodo que bajó (r)
        r.recalcularAltura();
        // y luego la del nuevo nodo raíz (h)
        h.recalcularAltura();

        return h;
    }

    private NodoAVL rotarDerecha(NodoAVL r) {
        NodoAVL h = r.getIzquierdo();
        NodoAVL temp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);

        // Recalcular alturas
        r.recalcularAltura();
        h.recalcularAltura();

        return h;
    }

    @Override
    public String toString() {
        // Para depuración, retorna los elementos en orden.
        return listar().toString();
    }
}