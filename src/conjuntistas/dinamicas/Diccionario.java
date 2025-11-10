package conjuntistas.dinamicas;

import lineales.dinamicas.Lista;

/**
 * Implementación del Tipo de Dato Abstracto Diccionario utilizando un Árbol AVL.
 * Un diccionario es una colección de pares (clave, dato) donde las claves son únicas.
 * Las operaciones de inserción, eliminación y búsqueda tienen un costo de O(log N).
 */
public class Diccionario {
    private NodoDic raiz;

    /**
     * Constructor. Crea un diccionario vacío.
     */
    public Diccionario() {
        this.raiz = null;
    }

    /**
     * Inserta un par (clave, dato) en el diccionario. Si la clave ya existe,
     * actualiza el dato asociado a esa clave y retorna falso (porque no se añadió
     * una nueva clave).
     *
     * @param clave la clave a insertar (debe ser única).
     * @param dato el dato a asociar con la clave.
     * @return verdadero si la clave no existía y se insertó, falso si la clave ya
     * existía y solo se actualizó el dato.
     */
    public boolean insertar(Comparable clave, Object dato) {
        boolean[] exito = {true};
        this.raiz = insertarAux(this.raiz, clave, dato, exito);
        return exito[0];
    }

    private NodoDic insertarAux(NodoDic nodo, Comparable clave, Object dato, boolean[] exito) {
        if (nodo == null) {
            // Posición encontrada, creamos el nuevo nodo.
            nodo = new NodoDic(clave, dato);
        } else {
            int comparacion = clave.compareTo(nodo.getClave());
            if (comparacion == 0) {
                // La clave ya existe, actualizamos el dato y marcamos como no exitoso.
                nodo.setDato(dato);
                exito[0] = false;
            } else if (comparacion < 0) {
                // La clave es menor, vamos al subárbol izquierdo.
                nodo.setIzquierdo(insertarAux(nodo.getIzquierdo(), clave, dato, exito));
            } else {
                // La clave es mayor, vamos al subárbol derecho.
                nodo.setDerecho(insertarAux(nodo.getDerecho(), clave, dato, exito));
            }
        }

        if (exito[0]) {
            // Si hubo inserción (no solo actualización), balanceamos.
            nodo.recalcularAltura();
            nodo = balancear(nodo);
        }

        return nodo;
    }

    /**
     * Elimina un elemento del diccionario dada su clave.
     * @param clave la clave del elemento a eliminar.
     * @return verdadero si se encontró y eliminó el elemento, falso en caso contrario.
     */
    public boolean eliminar(Comparable clave) {
        boolean[] exito = {false};
        this.raiz = eliminarAux(this.raiz, clave, exito);
        return exito[0];
    }

    private NodoDic eliminarAux(NodoDic nodo, Comparable clave, boolean[] exito) {
        if (nodo != null) {
            int comparacion = clave.compareTo(nodo.getClave());
            if (comparacion < 0) {
                nodo.setIzquierdo(eliminarAux(nodo.getIzquierdo(), clave, exito));
            } else if (comparacion > 0) {
                nodo.setDerecho(eliminarAux(nodo.getDerecho(), clave, exito));
            } else {
                // Encontramos el nodo a eliminar
                exito[0] = true;
                if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                    nodo = null;
                } else if (nodo.getIzquierdo() == null) {
                    nodo = nodo.getDerecho();
                } else if (nodo.getDerecho() == null) {
                    nodo = nodo.getIzquierdo();
                } else {
                    // Caso 3: tiene dos hijos
                    NodoDic aux = buscarMayorDelSubarbolIzquierdo(nodo);
                    nodo.setClave(aux.getClave());
                    nodo.setDato(aux.getDato());
                    nodo.setIzquierdo(eliminarAux(nodo.getIzquierdo(), aux.getClave(), exito));
                }
            }
            if (nodo != null && exito[0]) {
                nodo.recalcularAltura();
                nodo = balancear(nodo);
            }
        }
        return nodo;
    }

    private NodoDic buscarMayorDelSubarbolIzquierdo(NodoDic nodo) {
        NodoDic temp = nodo.getIzquierdo();
        while (temp.getDerecho() != null) {
            temp = temp.getDerecho();
        }
        return temp;
    }

    /**
     * Verifica si una clave existe en el diccionario.
     * @param clave la clave a buscar.
     * @return verdadero si la clave está en el diccionario, falso en caso contrario.
     */
    public boolean pertenece(Comparable clave) {
        return obtener(clave) != null;
    }

    /**
     * Obtiene el dato asociado a una clave.
     * @param clave la clave del dato a buscar.
     * @return el dato asociado a la clave, o null si la clave no existe.
     */
    public Object obtener(Comparable clave) {
        return obtenerAux(this.raiz, clave);
    }

    private Object obtenerAux(NodoDic nodo, Comparable clave) {
        Object resultado = null;
        if (nodo != null) {
            int comparacion = clave.compareTo(nodo.getClave());
            if (comparacion == 0) {
                resultado = nodo.getDato();
            } else if (comparacion < 0) {
                resultado = obtenerAux(nodo.getIzquierdo(), clave);
            } else {
                resultado = obtenerAux(nodo.getDerecho(), clave);
            }
        }
        return resultado;
    }

    /**
     * Retorna una lista con los datos almacenados en el diccionario, ordenados por clave.
     * @return una lista con los datos.
     */
    public Lista listar() {
        Lista lista = new Lista();
        listarAux(this.raiz, lista);
        return lista;
    }

    private void listarAux(NodoDic nodo, Lista lista) {
        if (nodo != null) {
            listarAux(nodo.getIzquierdo(), lista);
            lista.insertar(nodo.getDato(), lista.longitud() + 1);
            listarAux(nodo.getDerecho(), lista);
        }
    }

    /**
     * Retorna una lista con los datos cuyas claves se encuentran en el rango [min, max].
     * @param min clave mínima del rango.
     * @param max clave máxima del rango.
     * @return una lista con los datos correspondientes.
     */
    public Lista listarRango(Comparable min, Comparable max) {
        Lista lista = new Lista();
        listarRangoAux(this.raiz, min, max, lista);
        return lista;
    }

    private void listarRangoAux(NodoDic nodo, Comparable min, Comparable max, Lista lista) {
        if (nodo != null) {
            Comparable claveActual = nodo.getClave();
            if (claveActual.compareTo(min) > 0) {
                listarRangoAux(nodo.getIzquierdo(), min, max, lista);
            }
            if (claveActual.compareTo(min) >= 0 && claveActual.compareTo(max) <= 0) {
                lista.insertar(nodo.getDato(), lista.longitud() + 1);
            }
            if (claveActual.compareTo(max) < 0) {
                listarRangoAux(nodo.getDerecho(), min, max, lista);
            }
        }
    }

    // --- MÉTODOS DE BALANCEO (idénticos a ArbolAVL, pero con NodoDic) ---
    private NodoDic balancear(NodoDic nodo) {
        int balance = calcularBalance(nodo);
        if (balance > 1) {
            if (calcularBalance(nodo.getIzquierdo()) < 0) {
                nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
            }
            nodo = rotarDerecha(nodo);
        } else if (balance < -1) {
            if (calcularBalance(nodo.getDerecho()) > 0) {
                nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
            }
            nodo = rotarIzquierda(nodo);
        }
        return nodo;
    }

    private int calcularBalance(NodoDic nodo) {
        int altIzq = -1, altDer = -1;
        if(nodo != null){
            if (nodo.getIzquierdo() != null) altIzq = nodo.getIzquierdo().getAltura();
            if (nodo.getDerecho() != null) altDer = nodo.getDerecho().getAltura();
        }
        return altIzq - altDer;
    }

    private NodoDic rotarIzquierda(NodoDic r) {
        NodoDic h = r.getDerecho();
        NodoDic temp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    private NodoDic rotarDerecha(NodoDic r) {
        NodoDic h = r.getIzquierdo();
        NodoDic temp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    /**
     * Genera una representación en String de la ESTRUCTURA jerárquica del árbol.
     * Este método cumple con el requisito de "Mostrar Sistema",
     * permitiendo ver la altura de cada nodo y sus hijos.
     *
     * @return un String que describe la jerarquía del árbol (recorrido preorden).
     */
    public String toStringEstructura() {
        if (this.raiz == null) {
            return "Diccionario (AVL) vacío.";
        }
        // Llama al método recursivo auxiliar que inicia desde la raíz.
        return toStringEstructuraAux(this.raiz);
    }

    /**
     * Método auxiliar recursivo (en preorden) para construir el String
     * de la estructura del árbol.
     *
     * @param nodo El nodo (NodoDic) actual a procesar.
     * @return El String que representa el subárbol que tiene 'nodo' como raíz.
     */
    private String toStringEstructuraAux(NodoDic nodo) {
        // Usamos StringBuilder para una concatenación eficiente.
        StringBuilder sb = new StringBuilder();

        if (nodo != null) {
            // 1. Procesa el nodo actual (equivale a la visita en Preorden)
            sb.append("Nodo: ").append(nodo.getClave());
            sb.append(" (Altura: ").append(nodo.getAltura()).append(")\n");

            // 2. Muestra el hijo izquierdo
            sb.append("\tHI: ");
            if (nodo.getIzquierdo() != null) {
                sb.append(nodo.getIzquierdo().getClave()).append("\n");
            } else {
                sb.append("-\n"); // Sin hijo izquierdo
            }

            // 3. Muestra el hijo derecho
            sb.append("\tHD: ");
            if (nodo.getDerecho() != null) {
                sb.append(nodo.getDerecho().getClave()).append("\n\n");
            } else {
                sb.append("-\n\n"); // Sin hijo derecho
            }

            // 4. Llamadas recursivas para los hijos
            if (nodo.getIzquierdo() != null) {
                sb.append(toStringEstructuraAux(nodo.getIzquierdo()));
            }
            if (nodo.getDerecho() != null) {
                sb.append(toStringEstructuraAux(nodo.getDerecho()));
            }
        }
        return sb.toString();
    }
}