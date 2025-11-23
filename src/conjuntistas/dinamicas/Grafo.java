package conjuntistas.dinamicas;

import lineales.dinamicas.ColaPrioridad;
import lineales.dinamicas.CeldaCP;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;
import conjuntistas.dinamicas.Diccionario;

/**
 * Implementación dinámica (listas de adyacencia) del TDA Grafo Etiquetado.
 * Las operaciones de inserción y eliminación de arcos están adaptadas
 * para un grafo NO DIRIGIDO (ambos sentidos).
 */
public class Grafo {

    private NodoVert inicio;

    // ====================================================================
    // 1. CONSTRUCTOR
    // ====================================================================

    /**
     * Crea un grafo vacío.
     */
    public Grafo() {
        this.inicio = null;
    }

    // ====================================================================
    // 2. METODOS DE ACCESO (ABM y Observadores)
    // ====================================================================

    /**
     * Inserta un nuevo vértice en el grafo.
     * Controla que no se inserten vértices repetidos.
     * La implementación se basa en el Algoritmo 5.4 del apunte de grafos de la catedra.
     *
     * @param nuevoVertice el elemento a insertar como vértice.
     * @return verdadero si se pudo insertar, falso si ya existía.
     */
    public boolean insertarVertice(Object nuevoVertice) {
        boolean exito = false;
        // Busca si el vértice ya existe
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        if (aux == null) {
            // Si no existe, lo inserta al inicio de la lista de vértices
            this.inicio = new NodoVert(nuevoVertice, this.inicio);
            exito = true;
        }
        return exito;
    }

    /**
     * Elimina un vértice del grafo.
     * Si se encuentra, también deben eliminarse todos los arcos que lo tengan
     * como origen o destino.
     *
     * @param elem el elemento del vértice a eliminar.
     * @return verdadero si se encontró y eliminó el vértice, falso en caso contrario.
     */
    public boolean eliminarVertice(Object elem) {
        boolean exito = false;
        NodoVert prev = null;
        NodoVert actual = this.inicio;

        // 1. Buscar el vértice a eliminar y su predecesor
        while (actual != null && !actual.getElem().equals(elem)) {
            prev = actual;
            actual = actual.getSigVertice();
        }

        if (actual != null) { // Si encontramos el vértice (actual)
            exito = true;

            // 2. Eliminar todos los arcos que INCIDEN en él (él es destino)
            // Recorremos toda la lista de vértices
            NodoVert auxVert = this.inicio;
            while (auxVert != null) {
                // No es necesario procesar los arcos del propio nodo a eliminar
                if (auxVert != actual) {
                    // Eliminamos cualquier arco desde auxVert hacia 'actual'
                    eliminarArcoDesde(auxVert, elem);
                }
                auxVert = auxVert.getSigVertice();
            }

            // 3. Eliminar el vértice de la lista de vértices
            if (prev == null) {
                // El vértice a eliminar era el inicio
                this.inicio = actual.getSigVertice();
            } else {
                // El vértice a eliminar estaba en otra posición
                prev.setSigVertice(actual.getSigVertice());
            }
        }
        return exito;
    }

    /**
     * Inserta un arco entre dos vértices. Dado que el TP requiere un grafo
     * no dirigido (ambos sentidos), esta operación inserta
     * el arco (origen -> destino) y el arco (destino -> origen), ambos
     * con la misma etiqueta.
     *
     * @param origen   el elemento del vértice origen.
     * @param destino  el elemento del vértice destino.
     * @param etiqueta la etiqueta numérica (distancia) del arco.
     * @return verdadero si ambos vértices existen y se pudo insertar, falso en caso contrario.
     */
    public boolean insertarArco(Object origen, Object destino, double etiqueta) {
        boolean exito = false;
        // 1. Ubicar ambos vértices
        NodoVert nodoO = this.ubicarVertice(origen);
        NodoVert nodoD = this.ubicarVertice(destino);

        if (nodoO != null && nodoD != null) {
            // 2. Insertar arco (Origen -> Destino)
            // Se inserta al inicio de la lista de adyacentes de Origen
            NodoAdy nuevoAdyO = new NodoAdy(nodoD, nodoO.getPrimerAdy(), etiqueta);
            nodoO.setPrimerAdy(nuevoAdyO);

            // 3. Insertar arco (Destino -> Origen) por ser no dirigido
            NodoAdy nuevoAdyD = new NodoAdy(nodoO, nodoD.getPrimerAdy(), etiqueta);
            nodoD.setPrimerAdy(nuevoAdyD);

            exito = true;
        }
        return exito;
    }

    /**
     * Elimina el arco entre dos vértices. Dado que es un grafo no dirigido,
     * elimina el arco en AMBOS sentidos (O -> D y D -> O).
     *
     * @param origen  el elemento del vértice origen.
     * @param destino el elemento del vértice destino.
     * @return verdadero si se encontró y eliminó el arco, falso en caso contrario.
     */
    public boolean eliminarArco(Object origen, Object destino) {
        boolean exito = false;
        NodoVert nodoO = this.ubicarVertice(origen);
        NodoVert nodoD = this.ubicarVertice(destino);

        if (nodoO != null && nodoD != null) {
            // 1. Eliminar arco (Origen -> Destino)
            eliminarArcoDesde(nodoO, destino);

            // 2. Eliminar arco (Destino -> Origen)
            eliminarArcoDesde(nodoD, origen);

            exito = true; // Asumimos éxito si los vértices existían
        }
        return exito;
    }

    /**
     * Modifica la etiqueta (distancia) de un arco existente.
     * Dado que es un grafo no dirigido, modifica la etiqueta en ambos sentidos
     * (Origen -> Destino y Destino -> Origen).
     *
     * @param origen        el elemento del vértice origen.
     * @param destino       el elemento del vértice destino.
     * @param nuevaEtiqueta el nuevo valor (distancia) para el arco.
     * @return verdadero si el arco fue encontrado y modificado, falso en caso contrario.
     */
    public boolean modificarEtiqueta(Object origen, Object destino, double nuevaEtiqueta) {
        boolean exito = false;
        // 1. Ubicar ambos vértices
        NodoVert nodoO = this.ubicarVertice(origen);
        NodoVert nodoD = this.ubicarVertice(destino);

        if (nodoO != null && nodoD != null) {
            // 2. Modificar la etiqueta en el sentido Origen -> Destino
            boolean exito1 = modificarEtiquetaUnSentido(nodoO, nodoD, nuevaEtiqueta);

            // 3. Modificar la etiqueta en el sentido Destino -> Origen
            boolean exito2 = modificarEtiquetaUnSentido(nodoD, nodoO, nuevaEtiqueta);

            // Se considera exitoso si al menos una dirección pudo ser modificada
            exito = exito1 && exito2;
        }
        return exito;
    }

    /**
     * Verifica si un vértice existe en el grafo.
     *
     * @param buscado el elemento a buscar.
     * @return verdadero si está en la estructura, falso en caso contrario.
     */
    public boolean existeVertice(Object buscado) {
        return this.ubicarVertice(buscado) != null;
    }

    /**
     * Verifica si existe un arco entre dos vértices.
     *
     * @param origen  elemento del vértice origen.
     * @param destino elemento del vértice destino.
     * @return verdadero si existe el arco, falso en caso contrario.
     */
    public boolean existeArco(Object origen, Object destino) {
        boolean exito = false;
        NodoVert nodoO = this.ubicarVertice(origen);
        NodoVert nodoD = this.ubicarVertice(destino);

        if (nodoO != null && nodoD != null) {
            // Buscamos a 'destino' en la lista de adyacentes de 'origen'
            NodoAdy auxAdy = nodoO.getPrimerAdy();
            while (auxAdy != null && !exito) {
                if (auxAdy.getVertice() == nodoD) {
                    exito = true;
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
        return exito;
    }

    /**
     * Verifica si el grafo está vacío.
     *
     * @return verdadero si no hay vértices, falso en caso contrario.
     */
    public boolean esVacio() {
        return this.inicio == null;
    }

    /**
     * Genera una copia exacta (clon) del grafo.
     *
     * @return un nuevo objeto Grafo con la misma estructura y contenido.
     */
    @Override
    public Grafo clone() {
        Grafo clon = new Grafo();
        if (this.inicio == null) {
            return clon;
        }
        // 1. Clonar todos los vértices
        NodoVert auxVert = this.inicio;
        while (auxVert != null) {
            clon.insertarVertice(auxVert.getElem());
            auxVert = auxVert.getSigVertice();
        }
        // 2. Clonar todos los arcos
        auxVert = this.inicio;
        while (auxVert != null) {
            NodoAdy auxAdy = auxVert.getPrimerAdy();
            while (auxAdy != null) {
                // Obtenemos el elemento del destino
                Object elemDestino = auxAdy.getVertice().getElem();

                if (!clon.existeArco(auxVert.getElem(), elemDestino)) {
                    clon.insertarArco(auxVert.getElem(), elemDestino, auxAdy.getEtiqueta());
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
            auxVert = auxVert.getSigVertice();
        }
        return clon;
    }

    /**
     * Genera una representación en String del grafo, mostrando cada vértice
     * y su lista de adyacencia (destino y etiqueta).
     *
     * @return un String que describe la estructura del grafo.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        NodoVert auxVert = this.inicio;
        if (this.inicio != null) {
            while (auxVert != null) {
                sb.append("\nVERTICE: ");
                sb.append(auxVert.getElem().toString());
                sb.append(" -> [");

                NodoAdy auxAdy = auxVert.getPrimerAdy();
                while (auxAdy != null) {
                    sb.append(auxAdy.getVertice().getElem().toString());
                    sb.append(" (E: ");
                    sb.append(auxAdy.getEtiqueta());
                    sb.append(")");
                    if (auxAdy.getSigAdyacente() != null) {
                        sb.append(", ");
                    }
                    auxAdy = auxAdy.getSigAdyacente();
                }
                sb.append("]");
                auxVert = auxVert.getSigVertice();
            }
        } else {
            sb.append("Grafo Vacio");
        }
        return sb.toString();
    }

    // ====================================================================
    // 3. METODOS PROPIOS (Auxiliares y Privados)
    // ====================================================================

    /**
     * Método auxiliar privado que ubica un vértice en la lista de vértices.
     *
     * @param buscado el elemento del vértice a buscar.
     * @return el NodoVert si se encuentra, o null si no existe.
     */
    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    /**
     * Método auxiliar privado para eliminarVertice.
     * Elimina un arco de UN SOLO SENTIDO (desde nodoOrigen hacia el vértice
     * con elemento 'elemDestino').
     */
    private void eliminarArcoDesde(NodoVert nodoOrigen, Object elemDestino) {
        NodoAdy prevAdy = null;
        NodoAdy actualAdy = nodoOrigen.getPrimerAdy();
        boolean encontrado = false; // Bandera

        while (actualAdy != null && !encontrado) { // Condición compuesta
            if (actualAdy.getVertice().getElem().equals(elemDestino)) {
                if (prevAdy == null) {
                    nodoOrigen.setPrimerAdy(actualAdy.getSigAdyacente());
                } else {
                    prevAdy.setSigAdyacente(actualAdy.getSigAdyacente());
                }
                encontrado = true; // Activamos bandera para salir
            } else {
                // Solo avanzamos si no encontramos (para no saltar nada)
                prevAdy = actualAdy;
                actualAdy = actualAdy.getSigAdyacente();
            }
        }
    }

    /**
     * Método auxiliar privado que modifica la etiqueta de un arco en un solo sentido.
     *
     * @param nodoOrigen    El vértice de origen.
     * @param nodoDestino   El vértice de destino.
     * @param nuevaEtiqueta El nuevo valor.
     * @return verdadero si el arco fue encontrado y modificado, falso en caso contrario.
     */
    private boolean modificarEtiquetaUnSentido(NodoVert nodoOrigen, NodoVert nodoDestino, double nuevaEtiqueta) {
        boolean exito = false;
        NodoAdy auxAdy = nodoOrigen.getPrimerAdy();

        // Buscamos el arco en la lista de adyacentes del origen
        while (auxAdy != null && !exito) {
            if (auxAdy.getVertice() == nodoDestino) {
                // Arco encontrado, actualizamos la etiqueta y terminamos
                auxAdy.setEtiqueta(nuevaEtiqueta);
                exito = true;
            }
            auxAdy = auxAdy.getSigAdyacente();
        }
        return exito;
    }

    /**
     * Método auxiliar recursivo para el recorrido en profundidad (listar).
     */
    private void listarEnProfundidadAux(NodoVert n, Lista vis) {
        if (n != null) {
            // 1. Marca al vértice n como visitado
            vis.insertar(n.getElem(), vis.longitud() + 1);

            // 2. Recorre sus adyacentes
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {
                NodoVert vertDestino = ady.getVertice();
                // 3. Si el adyacente no fue visitado, llama recursivamente
                if (vis.localizar(vertDestino.getElem()) < 0) {
                    listarEnProfundidadAux(vertDestino, vis);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    /**
     * Método auxiliar para el recorrido en anchura desde un vértice.
     */
    private void anchuraDesde(NodoVert verticeInicial, Lista visitados) {
        Cola q = new Cola();
        q.poner(verticeInicial);
        visitados.insertar(verticeInicial.getElem(), visitados.longitud() + 1);

        while (!q.esVacia()) {
            NodoVert u = (NodoVert) q.obtenerFrente();
            q.sacar();
            // "para cada vértice v adyacente de u"
            NodoAdy ady = u.getPrimerAdy();
            while (ady != null) {
                NodoVert v = ady.getVertice();
                // "si v no está en visitados"
                if (visitados.localizar(v.getElem()) < 0) {
                    visitados.insertar(v.getElem(), visitados.longitud() + 1);
                    q.poner(v);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    /**
     * Método auxiliar recursivo para existeCamino (DFS).
     */
    private boolean existeCaminoAux(NodoVert n, Object dest, Lista vis) {
        boolean exito = false;
        if (n != null) {
            // 1. Si vértice n es el destino: HAY CAMINO
            if (n.getElem().equals(dest)) {
                exito = true;
            } else {
                // 2. Si no es el destino, marcar como visitado y explorar
                vis.insertar(n.getElem(), vis.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (!exito && ady != null) {
                    // 3. Visitar adyacentes no visitados
                    if (vis.localizar(ady.getVertice().getElem()) < 0) {
                        exito = existeCaminoAux(ady.getVertice(), dest, vis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    /**
     * Método auxiliar recursivo (DFS/Backtracking) para encontrar el camino más largo.
     */
    private void caminoMasLargoAux(NodoVert n, Object dest, Lista visitados, Lista caminoActual, Lista[] mejorCamino) {
        // Marcamos vértice actual como visitado para este camino
        visitados.insertar(n.getElem(), 1);
        caminoActual.insertar(n.getElem(), caminoActual.longitud() + 1);

        if (n.getElem().equals(dest)) {
            // Llegamos al destino. Verificamos si este camino es el más largo
            if (caminoActual.longitud() > mejorCamino[0].longitud()) {
                mejorCamino[0] = caminoActual.clone();
            }
        } else {
            // Si no es el destino, seguimos explorando
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {
                if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                    caminoMasLargoAux(ady.getVertice(), dest, visitados, caminoActual, mejorCamino);
                }
                ady = ady.getSigAdyacente();
            }
        }

        // Backtracking: desmarcamos el vértice para explorar otros caminos
        visitados.eliminar(visitados.localizar(n.getElem()));
        caminoActual.eliminar(caminoActual.longitud());
    }

    /**
     * Método auxiliar recursivo (DFS/Backtracking) para encontrar todos los caminos.
     */
    private void todosCaminosAux(NodoVert n, Object dest, Lista visitados, Lista caminoActual, Lista listaDeCaminos) {
        // 1. Agregamos el nodo actual al camino y lo marcamos como visitado
        caminoActual.insertar(n.getElem(), caminoActual.longitud() + 1);
        visitados.insertar(n.getElem(), 1); // Posición 1 es la más rápida

        // 2. Verificamos si llegamos al destino
        if (n.getElem().equals(dest)) {
            listaDeCaminos.insertar(caminoActual.clone(), 1);
        } else {
            // 3. Si no es el destino, exploramos adyacentes
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {
                if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                    todosCaminosAux(ady.getVertice(), dest, visitados, caminoActual, listaDeCaminos);
                }
                ady = ady.getSigAdyacente();
            }
        }

        // 4. BACKTRACKING
        caminoActual.eliminar(caminoActual.longitud());
        visitados.eliminar(visitados.localizar(n.getElem()));
    }

    /**
     * Auxiliar (privado) para reconstruir el camino desde el mapa de predecesores
     * generado por Dijkstra o BFS.
     */
    private Lista reconstruirCamino(Diccionario predecesores, Object origen, Object destino) {
        Lista camino = new Lista();
        Object elemActual = destino;

        // Iteramos hacia atrás desde el destino
        while (elemActual != null) {
            camino.insertar(elemActual, 1); // Insertar al frente de la lista

            if (elemActual.equals(origen)) {
                elemActual = null; // Llegamos al origen, terminamos
            } else {
                // Buscamos el predecesor en el mapa
                NodoVert predecesor = (NodoVert) predecesores.obtener((Comparable) elemActual);
                // El siguiente elemento a procesar es el elemento del predecesor
                elemActual = (predecesor != null) ? predecesor.getElem() : null;
            }
        }
        return camino;
    }

    /**
     * Método auxiliar privado para obtener la etiqueta de un arco en un sentido.
     */
    private double obtenerEtiqueta(Object origen, Object destino) {
        NodoVert nodoO = this.ubicarVertice(origen);
        NodoVert nodoD = this.ubicarVertice(destino);
        double etiqueta = -1.0;

        if (nodoO != null && nodoD != null) {
            NodoAdy ady = nodoO.getPrimerAdy();
            boolean encontrado = false;
            while (ady != null && !encontrado) {
                if (ady.getVertice() == nodoD) {
                    etiqueta = ady.getEtiqueta();
                    encontrado = true;
                }
                ady = ady.getSigAdyacente();
            }
        }
        return etiqueta;
    }

    // ====================================================================
    // 4. METODOS DE BUSQUEDA DEL TP (Consultas y Algoritmos)
    // ====================================================================

    /**
     * Devuelve una lista con los vértices del grafo visitados según
     * el recorrido en profundidad (DFS).
     *
     * @return una Lista con los elementos visitados en orden DFS.
     */
    public Lista listarEnProfundidad() {
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        // Itera sobre todos los vértices para asegurar que se visitan
        // todos los componentes conexos del grafo
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                // Si el vértice no fue visitado, inicia el DFS desde él
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    /**
     * Devuelve una lista con los vértices del grafo visitados según
     * el recorrido en anchura (BFS).
     *
     * @return una Lista con los elementos visitados en orden BFS.
     */
    public Lista listarEnAnchura() {
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        // Itera sobre todos los vértices para asegurar que se visitan
        // todos los componentes conexos
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                // Si no fue visitado, inicia BFS desde él
                anchuraDesde(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    /**
     * Verifica si existe al menos un camino entre origen y destino.
     * Utiliza una búsqueda en profundidad (DFS).
     *
     * @param origen  elemento del vértice origen.
     * @param destino elemento del vértice destino.
     * @return verdadero si existe un camino, falso en caso contrario.
     */
    public boolean existeCamino(Object origen, Object destino) {
        boolean exito = false;
        // 1. Verificar que ambos vértices existen
        NodoVert nodoO = this.ubicarVertice(origen);
        NodoVert nodoD = this.ubicarVertice(destino);

        if (nodoO != null && nodoD != null) {
            // 2. Si existen, busca el camino
            Lista visitados = new Lista(); // Lista para evitar ciclos
            exito = existeCaminoAux(nodoO, destino, visitados);
        }
        return exito;
    }

    /**
     * Devuelve el camino que pasa por MENOS VÉRTICES entre origen y destino.
     * Esto se resuelve eficientemente con una Búsqueda en Anchura (BFS).
     *
     * @param origen  elemento del vértice origen.
     * @param destino elemento del vértice destino.
     * @return una Lista con los elementos del camino más corto. Si no hay
     * camino, devuelve una lista vacía.
     */
    public Lista caminoMasCorto(Object origen, Object destino) {
        Lista camino = new Lista();
        NodoVert nodoO = this.ubicarVertice(origen);
        NodoVert nodoD = this.ubicarVertice(destino);

        if (nodoO != null && nodoD != null) {

            // Usamos una Lista para 'visitados' y un Diccionario para 'predecesores'
            Cola q = new Cola();
            Diccionario predecesores = new Diccionario();
            Lista visitados = new Lista();
            boolean encontrado = false;

            // 1. Encolar y marcar el origen
            q.poner(nodoO);
            visitados.insertar(origen, 1);
            predecesores.insertar((Comparable) origen, null);

            while (!q.esVacia() && !encontrado) {
                NodoVert u = (NodoVert) q.obtenerFrente();
                q.sacar();

                // 2. Explorar vecinos de 'u'
                NodoAdy ady = u.getPrimerAdy();
                while (ady != null && !encontrado) {
                    NodoVert v = ady.getVertice();
                    Object elemV = v.getElem();

                    // 3. Si 'v' no fue visitado
                    if (visitados.localizar(elemV) < 0) {
                        // 4. Marcar como visitado y guardar predecesor
                        visitados.insertar(elemV, 1);
                        predecesores.insertar((Comparable) elemV, u);
                        q.poner(v);

                        // 5. Verificar si 'v' es el destino
                        if (v == nodoD) {
                            encontrado = true;
                        }
                    }
                    ady = ady.getSigAdyacente();
                }
            }

            // 6. Reconstruir el camino si fue encontrado
            if (encontrado) {
                camino = reconstruirCamino(predecesores, origen, destino);
            }
        }
        return camino;
    }

    /**
     * Devuelve el camino que pasa por MÁS VÉRTICES (sin ciclos).
     * Esto se resuelve con una Búsqueda en Profundidad (DFS) modificada.
     *
     * @param origen  elemento del vértice origen.
     * @param destino elemento del vértice destino.
     * @return una Lista con los elementos del camino más largo. Si no hay
     * camino, devuelve una lista vacía.
     */
    public Lista caminoMasLargo(Object origen, Object destino) {
        NodoVert nodoO = this.ubicarVertice(origen);
        NodoVert nodoD = this.ubicarVertice(destino);

        if (nodoO == null || nodoD == null) {
            return new Lista();
        }

        Lista[] mejorCamino = {new Lista()};
        Lista visitados = new Lista();

        caminoMasLargoAux(nodoO, destino, visitados, new Lista(), mejorCamino);
        return mejorCamino[0];
    }

    /**
     * Busca el camino de menor costo (distancia) desde un vértice origen
     * a un vértice destino, utilizando el algoritmo de Dijkstra con una
     * Cola de Prioridad (Min-Heap).
     *
     * @param origen  el elemento del vértice origen.
     * @param destino el elemento del vértice destino.
     * @return una Lista con el camino de menor distancia. Si no hay
     * camino, devuelve una lista vacía.
     */
    public Lista caminoMenorDistancia(Object origen, Object destino) {
        Lista camino = new Lista();
        NodoVert nodoO = this.ubicarVertice(origen);
        NodoVert nodoD = this.ubicarVertice(destino);

        if (nodoO == null || nodoD == null) {
            return camino;
        }

        // --- Inicialización de Dijkstra ---
        Diccionario distancias = new Diccionario();
        Diccionario predecesores = new Diccionario();
        ColaPrioridad pq = new ColaPrioridad();

        // 1. Inicializar todas las distancias a "infinito"
        NodoVert aux = this.inicio;
        while (aux != null) {
            Comparable elem = (Comparable) aux.getElem();
            distancias.insertar(elem, Double.MAX_VALUE);
            predecesores.insertar(elem, null);
            aux = aux.getSigVertice();
        }

        // 2. Distancia al origen es 0 y lo añadimos a la cola
        distancias.insertar((Comparable) origen, 0.0);
        pq.insertar(nodoO, 0.0);

        // --- Bucle principal de Dijkstra ---
        while (!pq.esVacia()) {
            CeldaCP celdaMin = pq.obtenerFrenteCelda();
            pq.eliminarFrente();

            NodoVert u = (NodoVert) celdaMin.getElemento();
            double distPrioU = celdaMin.getPrioridad();
            Comparable elemU = (Comparable) u.getElem();

            double distActualU = (Double) distancias.obtener(elemU);

            if (distPrioU > distActualU) {
                continue;
            }

            if (u == nodoD) {
                break;
            }

            NodoAdy ady = u.getPrimerAdy();
            while (ady != null) {
                NodoVert v = ady.getVertice();
                Comparable elemV = (Comparable) v.getElem();
                double pesoUV = ady.getEtiqueta();

                double nuevaDistV = distActualU + pesoUV;
                double distActualV = (Double) distancias.obtener(elemV);

                if (nuevaDistV < distActualV) {
                    distancias.insertar(elemV, nuevaDistV);
                    predecesores.insertar(elemV, u);
                    pq.insertar(v, nuevaDistV);
                }
                ady = ady.getSigAdyacente();
            }
        }

        if ((Double) distancias.obtener((Comparable) destino) != Double.MAX_VALUE) {
            camino = reconstruirCamino(predecesores, origen, destino);
        }

        return camino;
    }

    /**
     * Devuelve una lista con TODOS los caminos posibles entre origen y destino
     * que NO pasen por el vértice 'evitar'.
     *
     * @param origen  el elemento del vértice origen.
     * @param destino el elemento del vértice destino.
     * @param evitar  el elemento del vértice que no debe ser visitado.
     * @return una Lista que contiene otras Listas (cada una es un camino).
     */
    public Lista obtenerTodosCaminos(Object origen, Object destino, Object evitar) {
        Lista listaDeCaminos = new Lista();
        NodoVert nodoO = this.ubicarVertice(origen);
        NodoVert nodoD = this.ubicarVertice(destino);
        NodoVert nodoE = this.ubicarVertice(evitar);

        if (nodoO == null || nodoD == null || nodoE == null) {
            System.err.println("Error: Origen, destino o vértice a evitar no existen.");
            return listaDeCaminos;
        }

        Lista visitados = new Lista();
        Lista caminoActual = new Lista();

        visitados.insertar(evitar, 1);

        todosCaminosAux(nodoO, destino, visitados, caminoActual, listaDeCaminos);

        return listaDeCaminos;
    }

    /**
     * Calcula el costo total (distancia en km) de un camino dado.
     *
     * @param camino La Lista (camino) a evaluar.
     * @return la suma de las etiquetas (distancias) del camino, o -1.0
     * si el camino es inválido o no se encuentra un arco.
     */
    public double calcularCostoCamino(Lista camino) {
        double costoTotal = 0.0;
        boolean error = false;

        if (camino != null && camino.longitud() >= 2) {
            int i = 1;
            while (i < camino.longitud() && !error) {
                Object elemOrigen = camino.recuperar(i);
                Object elemDestino = camino.recuperar(i + 1);

                double etiqueta = obtenerEtiqueta(elemOrigen, elemDestino);

                if (etiqueta == -1.0) {
                    System.err.println("Error: No existe riel entre " + elemOrigen + " y " + elemDestino);
                    error = true;
                    costoTotal = -1.0;
                } else {
                    costoTotal += etiqueta;
                }
                i++;
            }
        }
        return costoTotal;
    }
}