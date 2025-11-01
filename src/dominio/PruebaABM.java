package dominio;

// Importamos las clases necesarias
import dominio.Estacion;
import dominio.Tren;
import lineales.dinamicas.Lista;

/**
 * Clase de prueba dedicada a verificar el funcionamiento de todos los métodos
 * de ABM (Fase 2) del sistema TrenesSA.
 * * Esta prueba debe ejecutarse DESPUÉS de la carga inicial.
 */
public class PruebaABM {

    public static void main(String[] args) {
        System.out.println("--- [TEST ABM] Iniciando prueba de ABM ---");

        // 1. Cargar el sistema con los datos iniciales
        TrenesSA sistema = new TrenesSA();
        sistema.cargarSistemaDesdeArchivo("datos.txt");

        System.out.println("\n--- CARGA INICIAL COMPLETADA ---");
        System.out.println("Iniciando secuencia de pruebas de ABM...");

        // --- PRUEBA 1: ABM ESTACIONES (Opción 3) ---
        System.out.println("\n--- Probando ABM Estaciones ---");
        // ALTA: Añadimos una nueva estación
        sistema.altaEstacion("General Roca", "Av. Roca", 1200, "General Roca", "R8332", 2, 1);

        // MODIFICAR: Modificamos una estación existente (Once)
        sistema.modificarEstacion("Once", "Av. Pueyrredón", 101, "CABA", "C1081", 6, 5); // Le sumamos 1 plataforma

        // BAJA: Eliminamos una estación existente (Viedma)
        sistema.bajaEstacion("Viedma");


        // --- PRUEBA 2: ABM RIELES (Opción 5) ---
        System.out.println("\n--- Probando ABM Rieles ---");
        // ALTA: Conectamos la nueva estación "General Roca" con "Neuquén"
        sistema.altaRiel("General Roca", "Neuquén", 50.5);

        // MODIFICAR: Cambiamos la distancia del riel Retiro <-> Córdoba
        sistema.modificarRiel("Retiro", "Córdoba", 710.0); // Era 700.0

        // BAJA: Eliminamos el riel entre Plaza Constitución y La Plata
        sistema.bajaRiel("Plaza Constitución", "La Plata");


        // --- PRUEBA 3: ABM TRENES (Opción 2) ---
        System.out.println("\n--- Probando ABM Trenes ---");
        // ALTA: Añadimos un nuevo tren
        sistema.altaTren(999, "electrico", 10, 0, "Roca");

        // MODIFICAR: Cambiamos el tren 101 a la línea "San Martín"
        sistema.modificarTren(101, "diesel", 5, 2, "San Martín"); // Estaba en "Mitre"

        // BAJA: Eliminamos el tren 122
        sistema.bajaTren(122);


        // --- PRUEBA 4: ABM LÍNEAS (Opción 4) ---
        System.out.println("\n--- Probando ABM Líneas ---");
        // CREAR LÍNEA: Creamos la línea "Tren del Valle"
        sistema.crearLinea("Tren del Valle");

        // AGREGAR ESTACIÓN A LÍNEA: Añadimos las estaciones
        sistema.agregarEstacionALinea("Tren del Valle", "General Roca");
        sistema.agregarEstacionALinea("Tren del Valle", "Neuquén");

        // QUITAR ESTACIÓN DE LÍNEA: Quitamos "Santa Fe" de la línea "Sarmiento"
        sistema.quitarEstacionDeLinea("Sarmiento", "Santa Fe");

        // ELIMINAR LÍNEA: Eliminamos la línea "San Martín"
        sistema.eliminarLinea("San Martín");


        // --- VERIFICACIÓN FINAL ---
        System.out.println("\n--- [TEST ABM] Secuencia de pruebas finalizada ---");
        System.out.println("Mostrando estado final del sistema para verificación:");

        sistema.mostrarSistema();

        // Cerramos el log para guardar todos los cambios
        sistema.cerrarLog();
        System.out.println("\n--- [TEST ABM] Prueba finalizada. Revise 'log_trenesSA.txt' ---");
    }
}
