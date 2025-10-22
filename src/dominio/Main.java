package dominio;

/**
 * Clase principal para ejecutar y probar el sistema dominio.TrenesSA.
 * Realiza una prueba de integración de la Fase 1:
 * 1. Carga los datos desde "datos.txt".
 * 2. Muestra el estado final de las estructuras en consola.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("--- [TEST] Iniciando prueba de carga dominio.TrenesSA ---");

        // 1. Crear la instancia del sistema
        // Esto inicializará las estructuras y abrirá el archivo de log.
        TrenesSA sistema = new TrenesSA();

        // 2. Definir la ruta del archivo de carga
        // Asumimos que "datos.txt" está en la raíz del proyecto
        String rutaArchivoCarga = "datos.txt";

        // 3. Ejecutar la carga inicial (Opción 1)
        sistema.cargarSistemaDesdeArchivo(rutaArchivoCarga);

        // 4. Mostrar el estado del sistema en la consola (Opción 9)
        System.out.println("\n--- [TEST] Prueba de 'mostrarSistema()' ---");
        sistema.mostrarSistema();

        // 5. Cerrar el log manualmente para esta prueba
        // (En la versión final, esto lo manejará el método menu() al salir)
        System.out.println("\n--- [TEST] Prueba finalizada. Cerrando log. ---");
        sistema.cerrarLog();
    }
}