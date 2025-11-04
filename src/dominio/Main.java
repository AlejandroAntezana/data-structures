package dominio;
/**
 * Clase principal para ejecutar la aplicación interactiva TrenesSA.
 */
public class Main {

    public static void main(String[] args) {
        // 1. Crear la instancia del sistema
        // (Esto inicializa las estructuras y abre el log)
        TrenesSA sistema = new TrenesSA();

        // 2. Iniciar el menú interactivo
        sistema.menu();
    }
}