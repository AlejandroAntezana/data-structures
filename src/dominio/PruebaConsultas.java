package dominio;

import lineales.dinamicas.Lista; // Necesario para imprimir los resultados

/**
 * Clase de prueba dedicada a verificar el funcionamiento de todos los métodos
 * de CONSULTA (Fase 3) del sistema TrenesSA.
 * Esta prueba debe ejecutarse DESPUÉS de la carga inicial.
 */
public class PruebaConsultas {

    public static void main(String[] args) {
        System.out.println("--- [TEST CONSULTAS] Iniciando prueba de Consultas (Fase 3) ---");

        // 1. Cargar el sistema con los datos iniciales
        TrenesSA sistema = new TrenesSA();
        sistema.cargarSistemaDesdeArchivo("datos.txt");

        System.out.println("\n--- CARGA INICIAL COMPLETADA ---");
        System.out.println("Iniciando secuencia de pruebas de Consultas...");

        // --- PRUEBA 1: CONSULTAS TRENES (Opción 6) ---
        System.out.println("\n--- Probando Consultas de Trenes ---");
        System.out.println("Info Tren 101: " + sistema.consultarInfoTren(101));
        System.out.println("Ciudades que visita Tren 101 (Línea Mitre): "
                + sistema.consultarCiudadesPorTren(101));
        System.out.println("Ciudades que visita Tren 108 (no-asignado): "
                + sistema.consultarCiudadesPorTren(108));

        // --- PRUEBA 2: CONSULTAS ESTACIONES (Opción 7) ---
        System.out.println("\n--- Probando Consultas de Estaciones ---");
        System.out.println("Info Estación 'Retiro': " + sistema.consultarInfoEstacion("Retiro"));
        System.out.println("Estaciones que empiezan con 'Cor': "
                + sistema.consultarEstacionesPorPrefijo("Cor"));
        System.out.println("Estaciones que empiezan con 'Xyz': "
                + sistema.consultarEstacionesPorPrefijo("Xyz")); // Prueba de prefijo sin match

        // --- PRUEBA 3: CONSULTAS VIAJES (Opción 8) ---
        // Usaremos el tramo Retiro <-> Santa Fe para probar la diferencia
        // entre "menos estaciones" y "menor distancia".
        // - Camino 1 (BFS): Retiro -> Santa Fe (1 salto, 475 km)
        // - Camino 2 (Dijkstra): Retiro -> Rosario Norte -> Santa Fe (2 saltos, 300+170=470 km)
        System.out.println("\n--- Probando Consultas de Viajes (Retiro a Santa Fe) ---");

        System.out.println("Camino con MENOS ESTACIONES (BFS): "
                + sistema.obtenerCaminoMenosEstaciones("Retiro", "Santa Fe"));

        System.out.println("Camino con MENOR DISTANCIA (Dijkstra): "
                + sistema.obtenerCaminoMenorDistancia("Retiro", "Santa Fe"));

        // --- PRUEBA 4: CONSULTAS VIAJES (Opcionales *) ---
        System.out.println("\n--- Probando Consultas de Viajes (Opcionales) ---");

        // Prueba de todos los caminos
        System.out.println("Todos los caminos de 'Retiro' a 'Córdoba' EVITANDO 'Rosario Norte':");
        Lista todosLosCaminos = sistema.obtenerTodosLosCaminos("Retiro", "Córdoba", "Rosario Norte");
        System.out.println(todosLosCaminos.toString()); // Debería mostrar solo [[Retiro, Córdoba]]

        // Prueba de límite de KM
        System.out.println("\n¿Viajar de 'Retiro' a 'Santa Fe' con 472 km?");
        sistema.esPosibleViajarEnKm("Retiro", "Santa Fe", 472.0); // Debería ser Sí (costo 470)

        System.out.println("\n¿Viajar de 'Retiro' a 'Santa Fe' con 460 km?");
        sistema.esPosibleViajarEnKm("Retiro", "Santa Fe", 460.0); // Debería ser No (costo 470)


        // --- VERIFICACIÓN FINAL ---
        System.out.println("\n--- [TEST CONSULTAS] Pruebas finalizadas ---");
        sistema.cerrarLog();
    }
}