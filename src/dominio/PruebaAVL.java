package dominio;

// Importa la clase de prueba que acabamos de crear
import conjuntistas.dinamicas.VisualDic;

/**
 * Este programa demuestra visualmente la inserción y las rotaciones
 * de un Árbol AVL, usando el orden de carga de las estaciones del TP.
 * * Es una herramienta de estudio para la defensa.
 */
public class PruebaAVL {

    public static void main(String[] args) {

        // 1. El orden de inserción de las estaciones (de datos.txt)
        String[] ordenDeInsercion = {
                // --- Bloque 1: Fuerza Rotación Doble LR (C, A, B) ---
                "Mendoza",
                "Bahía Blanca",
                "Bariloche", // <- Desbalance LR en "Mendoza"

                // --- Bloque 2: Fuerza Rotación Simple RR (A, B, C) ---
                "Resistencia",
                "Retiro",
                "Rosario Norte", // <- Desbalance RR en "Resistencia"

                // --- Bloque 3: Fuerza Rotación Doble RL (A, C, B) ---
                "Salta",
                "Tucumán",
                "Santa Fe", // <- Desbalance RL en "Salta"

                // --- Bloque 4: Fuerza Rotación Simple LL (C, B, A) ---
                "Paraná",
                "Once",
                "Neuquén", // <- Desbalance LL en "Paraná"

                // --- Relleno: Inserta el resto para provocar más rotaciones ---
                "Córdoba",
                "Plaza Constitución",
                "Federico Lacroze",
                "La Plata",
                "Mar del Plata",
                "Jujuy",
                "Viedma",
                "San Juan",
                "Posadas",
                "Corrientes"
        };

        // 2. Creamos el árbol de prueba visual
        VisualDic arbolDePrueba = new VisualDic();

        // 3. Iteramos e insertamos uno por uno
        System.out.println("=== INICIANDO PRUEBA DE CARGA AVL (ESTACIONES) ===");

        for (int i = 0; i < ordenDeInsercion.length; i++) {
            String estacion = ordenDeInsercion[i];

            System.out.println("\n=======================================================");
            System.out.println("PASO " + (i + 1) + ": INSERTANDO '" + estacion + "'");
            System.out.println("=======================================================");

            // El dato (segundo parámetro) es irrelevante para la prueba
            arbolDePrueba.insertar(estacion, new Object());

            // 4. Mostramos el estado del árbol DESPUÉS de la inserción y balanceo
            System.out.println("\n--- ESTADO DEL ÁRBOL RESULTANTE ---");
            System.out.println(arbolDePrueba.toStringEstructura());
        }

        System.out.println("\n=======================================================");
        System.out.println("Prueba de AVL finalizada.");
        System.out.println("=======================================================");
    }
}