package conjuntistas.dinamicas;

import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola; // Necesaria para listarEnAnchura y caminoMasCorto
import conjuntistas.dinamicas.Diccionario; // Necesario para caminoMasCorto (BFS)

/**
 * Implementación dinámica (listas de adyacencia) del TDA Grafo Etiquetado.
 * Las operaciones de inserción y eliminación de arcos están adaptadas
 * para un grafo NO DIRIGIDO (ambos sentidos), según lo requerido
 * por el trabajo práctico de dominio.TrenesSA.
 */
public class Grafo {

    private NodoVert inicio;

    /**
     * Crea un grafo vacío[cite: 397].
     */
    public Grafo() {
        this.inicio = null;
    }

    /**
     * Inserta un nuevo vértice en el grafo.
     * Controla que no se inserten vértices repetidos[cite: 400].
     * La implementación se basa en el Algoritmo 5.4[cite: 645].
     *
     * @param nuevoVertice el elemento a insertar como vértice.
     * @return verdadero si se pudo insertar, falso si ya existía.
     */
    public boolean insertarVertice(Object nuevoVertice) {
        boolean exito = false;
        // Busca si el vértice ya existe [cite: 641]
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        if (aux == null) {
            // Si no existe, lo inserta al inicio de la lista de vértices [cite: 642]
            this.inicio = new NodoVert(nuevoVertice, this.inicio);
            exito = true;
        }
        return exito;
    }

    /**
     * Elimina un vértice del grafo.
     * Si se encuentra, también deben eliminarse todos los arcos que lo tengan
     * como origen o destino[cite: 404].
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
     * Método auxiliar privado para eliminarVertice.
     * Elimina un arco de UN SOLO SENTIDO (desde nodoOrigen hacia el vértice
     * con elemento 'elemDestino').
     */
    private void eliminarArcoDesde(NodoVert nodoOrigen, Object elemDestino) {
        NodoAdy prevAdy = null;
        NodoAdy actualAdy = nodoOrigen.getPrimerAdy();

        while (actualAdy != null) {
            if (actualAdy.getVertice().getElem().equals(elemDestino)) {
                // Encontramos el arco, lo eliminamos
                if (prevAdy == null) {
                    nodoOrigen.setPrimerAdy(actualAdy.getSigAdyacente());
                } else {
                    prevAdy.setSigAdyacente(actualAdy.getSigAdyacente());
                }
                // Como no es multigrafo, no puede haber otro, salimos.
                break;
            }
            prevAdy = actualAdy;
            actualAdy = actualAdy.getSigAdyacente();
        }
    }


    /**
     * Inserta un arco entre dos vértices. Dado que el TP requiere un grafo
     * no dirigido (ambos sentidos), esta operación inserta
     * el arco (origen -> destino) y el arco (destino -> origen), ambos
     * con la misma etiqueta[cite: 521].
     *
     * @param origen  el elemento del vértice origen.
     * @param destino el elemento del vértice destino.
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
     * elimina el arco en AMBOS sentidos (O -> D y D -> O)[cite: 521].
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
     * Método auxiliar privado que ubica un vértice en la lista de vértices.
     * Basado en el Algoritmo 5.4[cite: 643, 645].
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
     * Verifica si un vértice existe en el grafo[cite: 412].
     * @param buscado el elemento a buscar.
     * @return verdadero si está en la estructura, falso en caso contrario.
     */
    public boolean existeVertice(Object buscado) {
        return this.ubicarVertice(buscado) != null;
    }

    /**
     * Verifica si existe un arco entre dos vértices[cite: 414].
     * En esta implementación, solo verifica el sentido (origen -> destino).
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
     * @return verdadero si no hay vértices, falso en caso contrario.
     */
    public boolean esVacio() {
        return this.inicio == null;
    }

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
        // todos los componentes conexos del grafo [cite: 648]
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
     * Método auxiliar recursivo para el recorrido en profundidad.
     *
     * @param n   el nodo vértice actual.
     * @param vis la lista de visitados (se modifica por referencia).
     */
    private void listarEnProfundidadAux(NodoVert n, Lista vis) {
        if (n != null) {
            // 1. Marca al vértice n como visitado [cite: 652]
            vis.insertar(n.getElem(), vis.longitud() + 1);

            // 2. Recorre sus adyacentes
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {
                NodoVert vertDestino = ady.getVertice();
                // 3. Si el adyacente no fue visitado, llama recursivamente [cite: 652]
                if (vis.localizar(vertDestino.getElem()) < 0) {
                    listarEnProfundidadAux(vertDestino, vis);
                }
                ady = ady.getSigAdyacente();
            }
        }
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
     * Método auxiliar para el recorrido en anchura desde un vértice.
     * Implementa la lógica de la cola (Algoritmo 5.2).
     *
     * @param verticeInicial el nodo vértice donde comienza el BFS.
     * @param visitados      la lista de visitados (se modifica por referencia).
     */
    private void anchuraDesde(NodoVert verticeInicial, Lista visitados) {
        Cola q = new Cola();
        q.poner(verticeInicial);
        visitados.insertar(verticeInicial.getElem(), visitados.longitud() + 1);

        while (!q.esVacia()) {
            NodoVert u = (NodoVert) q.obtenerFrente();
            q.sacar();
            // "para cada vértice v adyacente de u" [cite: 318]
            NodoAdy ady = u.getPrimerAdy();
            while (ady != null) {
                NodoVert v = ady.getVertice();
                // "si v no está en visitados" [cite: 318]
                if (visitados.localizar(v.getElem()) < 0) {
                    visitados.insertar(v.getElem(), visitados.longitud() + 1);
                    q.poner(v);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    /**
     * Verifica si existe al menos un camino entre origen y destino[cite: 416].
     * Utiliza una búsqueda en profundidad (DFS).
     * Implementación basada en el Algoritmo 5.6.
     *
     * @param origen  elemento del vértice origen.
     * @param destino elemento del vértice destino.
     * @return verdadero si existe un camino, falso en caso contrario.
     */
    public boolean existeCamino(Object origen, Object destino) {
        boolean exito = false;
        // 1. Verificar que ambos vértices existen [cite: 661]
        NodoVert nodoO = this.ubicarVertice(origen);
        NodoVert nodoD = this.ubicarVertice(destino);

        if (nodoO != null && nodoD != null) {
            // 2. Si existen, busca el camino [cite: 677]
            Lista visitados = new Lista(); // Lista para evitar ciclos [cite: 655]
            exito = existeCaminoAux(nodoO, destino, visitados);
        }
        return exito;
    }

    /**
     * Método auxiliar recursivo para existeCamino (DFS).
     * Basado en el Algoritmo 5.6.
     */
    private boolean existeCaminoAux(NodoVert n, Object dest, Lista vis) {
        boolean exito = false;
        if (n != null) {
            // 1. Si vértice n es el destino: HAY CAMINO! [cite: 685]
            if (n.getElem().equals(dest)) {
                exito = true;
            } else {
                // 2. Si no es el destino, marcar como visitado y explorar
                vis.insertar(n.getElem(), vis.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (!exito && ady != null) {
                    // 3. Visitar adyacentes no visitados [cite: 692]
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
     * Devuelve el camino que pasa por MENOS VÉRTICES entre origen y destino.
     * Esto se resuelve eficientemente con una Búsqueda en Anchura (BFS).
     *
     * @param origen  elemento del vértice origen.
     * @param destino elemento del vértice destino.
     * @return una Lista con los elementos del camino más corto. Si no hay
     * camino, devuelve una lista vacía.
            **/
    public Lista caminoMasCorto(Object origen, Object destino) {
        Lista camino = new Lista();
        NodoVert nodoO = this.ubicarVertice(origen);
        NodoVert nodoD = this.ubicarVertice(destino);

        if (nodoO == null || nodoD == null) {
            return camino; // Retorna lista vacía si no existen
        }

        // Usamos un Diccionario para rastrear (vertice.elem -> predecesor)
        Diccionario predecesores = new Diccionario();
        Cola q = new Cola();
        boolean encontrado = false;

        q.poner(nodoO);

        // --- CORRECCIÓN 1 ---
        // Se castea el elemento 'Object' a 'Comparable' antes de insertarlo.
        predecesores.insertar((Comparable) nodoO.getElem(), null); // Origen no tiene predecesor

        while (!q.esVacia() && !encontrado) {
            NodoVert u = (NodoVert) q.obtenerFrente();
            q.sacar();

            if (u == nodoD) {
                encontrado = true;
            } else {
                NodoAdy ady = u.getPrimerAdy();
                while (ady != null) {
                    NodoVert v = ady.getVertice();

                    // --- CORRECCIÓN 2 ---
                    // Se castea el elemento de 'v' a 'Comparable' para buscarlo.
                    Comparable claveV = (Comparable) v.getElem();

                    if (!predecesores.pertenece(claveV)) {
                        predecesores.insertar(claveV, u); // Guardamos u como predecesor de v
                        q.poner(v);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }

        if (encontrado) {
            // Reconstruir el camino desde el destino hacia atrás
            Object elemActual = destino;
            while (elemActual != null) {
                camino.insertar(elemActual, 1); // Insertar al frente de la lista

                // --- CORRECCIÓN 3 ---
                // Se castea el elemento 'Object' a 'Comparable' para obtenerlo.
                NodoVert predecesor = (NodoVert) predecesores.obtener((Comparable) elemActual);

                elemActual = (predecesor != null) ? predecesor.getElem() : null;
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
            return new Lista(); // Lista vacía
        }

        // Usamos un arreglo para pasar por referencia el "mejor camino" encontrado
        Lista[] mejorCamino = {new Lista()};
        Lista visitados = new Lista();

        caminoMasLargoAux(nodoO, destino, visitados, new Lista(), mejorCamino);
        return mejorCamino[0];
    }

    /**
     * Auxiliar recursivo (DFS/Backtracking) para encontrar el camino más largo.
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

                // IMPORTANTE: Solo insertamos el arco en un sentido (O->D)
                // para evitar duplicarlo, ya que nuestro 'insertarArco'
                // es no-dirigido y lo inserta en ambos sentidos.
                // Para que esto funcione, necesitamos una forma de no
                // volver a insertar (D->O).
                //
                // Corrección: Es más simple si 'insertarArco' solo insertara
                // en un sentido, y el clon lo llamara 2 veces.
                //
                // Estrategia más segura:
                // Solo insertamos el arco si NO existe ya en el clon.
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
        if (this.inicio == null) {
            return "Grafo vacío.";
        }
        StringBuilder sb = new StringBuilder();
        NodoVert auxVert = this.inicio;
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
        return sb.toString();
    }
}