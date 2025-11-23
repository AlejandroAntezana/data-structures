package conjuntistas.dinamicas;
// Asegúrate de que estas clases estén disponibles para este paquete

/**
 * ESTA ES UNA VERSIÓN DE PRUEBA DEL DICCIONARIO.
 * Su único propósito es imprimir en consola cada vez que se
 * produce una rotación, para fines de demostración y defensa.
 */
public class VisualDic {
    private NodoDic raiz;

    public VisualDic() {
        this.raiz = null;
    }

    public boolean insertar(Comparable clave, Object dato) {
        boolean[] exito = {true};
        this.raiz = insertarAux(this.raiz, clave, dato, exito);
        return exito[0];
    }

    private NodoDic insertarAux(NodoDic nodo, Comparable clave, Object dato, boolean[] exito) {
        if (nodo == null) {
            nodo = new NodoDic(clave, dato);
        } else {
            int comparacion = clave.compareTo(nodo.getClave());
            if (comparacion == 0) {
                nodo.setDato(dato);
                exito[0] = false;
            } else if (comparacion < 0) {
                nodo.setIzquierdo(insertarAux(nodo.getIzquierdo(), clave, dato, exito));
            } else {
                nodo.setDerecho(insertarAux(nodo.getDerecho(), clave, dato, exito));
            }
        }

        if (exito[0]) {
            nodo.recalcularAltura();
            // Llama al balancear modificado
            nodo = balancear(nodo);
        }

        return nodo;
    }

    // --- MÉTODO DE BALANCEO MODIFICADO CON IMPRESIONES ---

    private NodoDic balancear(NodoDic nodo) {
        int balance = calcularBalance(nodo);

        if (balance > 1) {
            // Desbalanceado a la izquierda (LL o LR)
            if (calcularBalance(nodo.getIzquierdo()) < 0) {
                // --- Rotación Doble Izquierda-Derecha (LR) ---
                System.out.println(">>> [ROTACIÓN DOBLE LR] en nodo '" + nodo.getClave() + "'");
                System.out.println("    (Primero rotación Izquierda en '" + nodo.getIzquierdo().getClave() + "')");
                nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
            } else {
                // --- Rotación Simple Derecha (LL) ---
                System.out.println(">>> [ROTACIÓN SIMPLE DERECHA (LL)] en nodo '" + nodo.getClave() + "'");
            }
            nodo = rotarDerecha(nodo);

        } else if (balance < -1) {
            // Desbalanceado a la derecha (RR o RL)
            if (calcularBalance(nodo.getDerecho()) > 0) {
                // --- Rotación Doble Derecha-Izquierda (RL) ---
                System.out.println(">>> [ROTACIÓN DOBLE RL] en nodo '" + nodo.getClave() + "'");
                System.out.println("    (Primero rotación Derecha en '" + nodo.getDerecho().getClave() + "')");
                nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
            } else {
                // --- Rotación Simple Izquierda (RR) ---
                System.out.println(">>> [ROTACIÓN SIMPLE IZQUIERDA (RR)] en nodo '" + nodo.getClave() + "'");
            }
            nodo = rotarIzquierda(nodo);
        }
        // Si no entra en los if, está balanceado (0, 1, -1) y no hace nada.
        return nodo;
    }

    // --- Métodos de rotación (sin cambios) ---

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

    // --- Método de visualización (copiado de Diccionario) ---

    public String toStringEstructura() {
        if (this.raiz == null) {
            return "Arbol vacío.";
        }
        return toStringEstructuraAux(this.raiz, "");
    }

    private String toStringEstructuraAux(NodoDic nodo, String prefijo) {
        StringBuilder sb = new StringBuilder();
        if (nodo != null) {
            // Muestra el nodo actual con su altura
            sb.append(prefijo).append(nodo.getClave());
            sb.append(" (h:").append(nodo.getAltura());
            sb.append(", b:").append(calcularBalance(nodo)).append(")\n");

            // Prepara prefijos para los hijos
            String prefijoHijo = prefijo + "    |";
            String prefijoHijoDer = prefijo + "     ";

            // Recorre recursivamente HI
            if (nodo.getIzquierdo() != null) {
                sb.append(prefijoHijo).append("—HI: ");
                sb.append(toStringEstructuraAux(nodo.getIzquierdo(), prefijoHijo));
            } else {
                sb.append(prefijoHijo).append("—HI: -\n");
            }

            // Recorre recursivamente HD
            if (nodo.getDerecho() != null) {
                sb.append(prefijoHijo).append("—HD: ");
                sb.append(toStringEstructuraAux(nodo.getDerecho(), prefijoHijoDer));
            } else {
                sb.append(prefijoHijo).append("—HD: -\n");
            }
        }
        return sb.toString();
    }
}