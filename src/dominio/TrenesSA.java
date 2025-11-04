package dominio;

import conjuntistas.dinamicas.Diccionario;
import conjuntistas.dinamicas.Grafo;
import lineales.dinamicas.Lista;
import java.util.Scanner;
import java.util.InputMismatchException;

// Para el Mapeo de Líneas, usamos el HashMap de Java como pide el enunciado
import java.util.HashMap;

// Para la Carga Inicial y el Log
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Clase principal del sistema dominio.TrenesSA.
 * Esta clase gestiona todas las estructuras de datos y la lógica
 * de negocio para la administración de la red ferroviaria.
 */
public class TrenesSA {

    // --- ESTRUCTURAS DE DATOS PRINCIPALES ---

    /**
     * Diccionario de Estaciones.
     * Almacena objetos 'Estacion'.
     * Clave: String (nombre de la estación)
     * Dato: Object (objeto Estacion)
     */
    private Diccionario estaciones;

    /**
     * Diccionario de Trenes.
     * Almacena objetos 'Tren'.
     * Clave: Integer (ID numérico del tren)
     * Dato: Object (objeto Tren)
     */
    private Diccionario trenes;

    /**
     * Grafo de la Red de Rieles.
     * Grafo etiquetado no dirigido.
     * Vértices: Object (objeto Estacion)
     * Etiquetas: double (distancia en km)
     */
    private Grafo redDeRieles;

    /**
     * Mapeo de Líneas de Trenes.
     * Almacena las estaciones que componen cada línea.
     * Clave: String (nombre de la línea)
     * Valor: Lista (de objetos Estacion)
     */
    private HashMap mapeoLineas; // El enunciado permite usar HashMap de Java

    // --- ATRIBUTOS PARA GESTIÓN ---

    private static final String RUTA_LOG = "log_trenesSA.txt";
    private PrintWriter logWriter; // Escritor para el archivo de log

    /**
     * Constructor.
     * Inicializa todas las estructuras de datos y prepara el archivo de log.
     */
    public TrenesSA() {
        // Inicializamos las estructuras de datos personalizadas
        this.estaciones = new Diccionario();
        this.trenes = new Diccionario();
        this.redDeRieles = new Grafo();

        // Inicializamos el HashMap de Java
        this.mapeoLineas = new HashMap();

        // Preparamos el archivo de log
        try {
            // El 'true' en FileWriter es para modo 'append' (agregar al final)
            this.logWriter = new PrintWriter(new FileWriter(RUTA_LOG, true), true);
            logWriter.println("--- INICIO DE SESIÓN: " + new java.util.Date().toString() + " ---");
        } catch (IOException e) {
            System.err.println("ERROR: No se pudo abrir el archivo de log.");
            e.printStackTrace();
        }
    }

    /**
     * Muestra el menú principal interactivo y gestiona la entrada del usuario.
     */
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        System.out.println("=======================================================");
        System.out.println("     BIENVENIDO AL SISTEMA DE GESTIÓN TRENESSA     ");
        System.out.println("=======================================================");

        while (!salir) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Cargar sistema desde archivo");
            System.out.println("2. ABM de Trenes");
            System.out.println("3. ABM de Estaciones");
            System.out.println("4. ABM de Líneas");
            System.out.println("5. ABM de Red de Rieles");
            System.out.println("6. Consultas sobre Trenes");
            System.out.println("7. Consultas sobre Estaciones");
            System.out.println("8. Consultas sobre Viajes");
            System.out.println("9. Mostrar estado completo del sistema");
            System.out.println("0. Salir del sistema");
            System.out.print("Seleccione una opción: ");

            int opcion = leerInt(scanner);

            try {
                switch (opcion) {
                    case 1:
                        menuCargaInicial(scanner);
                        break;
                    case 2:
                        menuABMTrenes(scanner);
                        break;
                    case 3:
                        menuABMEstaciones(scanner);
                        break;
                    case 4:
                        menuABMLineas(scanner);
                        break;
                    case 5:
                        menuABMRieles(scanner);
                        break;
                    case 6:
                        menuConsultasTrenes(scanner);
                        break;
                    case 7:
                        menuConsultasEstaciones(scanner);
                        break;
                    case 8:
                        menuConsultasViajes(scanner);
                        break;
                    case 9:
                        mostrarSistema();
                        break;
                    case 0:
                        salir = true;
                        break;
                    default:
                        System.err.println("Opción no válida. Intente de nuevo.");
                        break;
                }
            } catch (Exception e) {
                System.err.println("Ha ocurrido un error inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println("Guardando estado del sistema en el log...");
        logEstadoSistema();
        System.out.println("Cerrando el sistema. ¡Hasta luego!");
        cerrarLog();
        scanner.close();
    }

    // --- Métodos Auxiliares del Menú (Privados) ---

    private void menuCargaInicial(Scanner scanner) {
        System.out.print("Ingrese la ruta del archivo de carga (ej. datos.txt): ");
        String ruta = leerString(scanner);
        cargarSistemaDesdeArchivo(ruta);
    }

    private void menuABMTrenes(Scanner scanner) {
        System.out.println("\n--- ABM Trenes ---");
        System.out.println("1. Alta de Tren");
        System.out.println("2. Baja de Tren");
        System.out.println("3. Modificación de Tren");
        System.out.print("Seleccione: ");
        int opcion = leerInt(scanner);

        int id;
        switch (opcion) {
            case 1:
                System.out.print("ID (numérico): ");
                id = leerInt(scanner);
                System.out.print("Propulsión (diesel, electrico, etc.): ");
                String prop = leerString(scanner);
                System.out.print("Vagones de Pasajeros: ");
                int vagPas = leerInt(scanner);
                System.out.print("Vagones de Carga: ");
                int vagCar = leerInt(scanner);
                System.out.print("Línea asignada (o 'libre'): ");
                String lin = leerString(scanner);
                if (altaTren(id, prop, vagPas, vagCar, lin)) {
                    System.out.println("Tren dado de alta exitosamente.");
                }
                break;
            case 2:
                System.out.print("ID del tren a dar de baja: ");
                id = leerInt(scanner);
                if (bajaTren(id)) {
                    System.out.println("Tren dado de baja exitosamente.");
                }
                break;
            case 3:
                System.out.print("ID del tren a modificar: ");
                id = leerInt(scanner);
                System.out.print("NUEVA Propulsión: ");
                String nProp = leerString(scanner);
                System.out.print("NUEVOS Vagones de Pasajeros: ");
                int nVagPas = leerInt(scanner);
                System.out.print("NUEVOS Vagones de Carga: ");
                int nVagCar = leerInt(scanner);
                System.out.print("NUEVA Línea asignada: ");
                String nLin = leerString(scanner);
                if (modificarTren(id, nProp, nVagPas, nVagCar, nLin)) {
                    System.out.println("Tren modificado exitosamente.");
                }
                break;
            default:
                System.err.println("Opción no válida.");
                break;
        }
    }

    private void menuABMEstaciones(Scanner scanner) {
        System.out.println("\n--- ABM Estaciones ---");
        System.out.println("1. Alta de Estación");
        System.out.println("2. Baja de Estación");
        System.out.println("3. Modificación de Estación");
        System.out.print("Seleccione: ");
        int opcion = leerInt(scanner);

        String nombre;
        switch (opcion) {
            case 1:
                System.out.print("Nombre (Clave Única): ");
                nombre = leerString(scanner);
                System.out.print("Calle: ");
                String calle = leerString(scanner);
                System.out.print("Número: ");
                int num = leerInt(scanner);
                System.out.print("Ciudad: ");
                String ciu = leerString(scanner);
                System.out.print("Código Postal: ");
                String cp = leerString(scanner);
                System.out.print("Cantidad de Vías: ");
                int vias = leerInt(scanner);
                System.out.print("Cantidad de Plataformas: ");
                int plat = leerInt(scanner);
                if (altaEstacion(nombre, calle, num, ciu, cp, vias, plat)) {
                    System.out.println("Estación dada de alta exitosamente.");
                }
                break;
            case 2:
                System.out.print("Nombre de la estación a dar de baja: ");
                nombre = leerString(scanner);
                if (bajaEstacion(nombre)) {
                    System.out.println("Estación dada de baja exitosamente.");
                }
                break;
            case 3:
                System.out.print("Nombre de la estación a modificar: ");
                nombre = leerString(scanner);
                System.out.print("NUEVA Calle: ");
                String nCalle = leerString(scanner);
                System.out.print("NUEVO Número: ");
                int nNum = leerInt(scanner);
                System.out.print("NUEVA Ciudad: ");
                String nCiu = leerString(scanner);
                System.out.print("NUEVO Código Postal: ");
                String nCp = leerString(scanner);
                System.out.print("NUEVA Cant. de Vías: ");
                int nVias = leerInt(scanner);
                System.out.print("NUEVA Cant. de Plataformas: ");
                int nPlat = leerInt(scanner);
                if (modificarEstacion(nombre, nCalle, nNum, nCiu, nCp, nVias, nPlat)) {
                    System.out.println("Estación modificada exitosamente.");
                }
                break;
            default:
                System.err.println("Opción no válida.");
                break;
        }
    }

    private void menuABMLineas(Scanner scanner) {
        System.out.println("\n--- ABM Líneas ---");
        System.out.println("1. Crear Línea (vacía)");
        System.out.println("2. Eliminar Línea");
        System.out.println("3. Agregar Estación a Línea");
        System.out.println("4. Quitar Estación de Línea");
        System.out.print("Seleccione: ");
        int opcion = leerInt(scanner);

        String nomLin, nomEst;
        switch (opcion) {
            case 1:
                System.out.print("Nombre de la nueva línea: ");
                nomLin = leerString(scanner);
                if (crearLinea(nomLin)) System.out.println("Línea creada.");
                break;
            case 2:
                System.out.print("Nombre de la línea a eliminar: ");
                nomLin = leerString(scanner);
                if (eliminarLinea(nomLin)) System.out.println("Línea eliminada.");
                break;
            case 3:
                System.out.print("Nombre de la línea: ");
                nomLin = leerString(scanner);
                System.out.print("Nombre de la estación a agregar: ");
                nomEst = leerString(scanner);
                if (agregarEstacionALinea(nomLin, nomEst)) System.out.println("Estación agregada.");
                break;
            case 4:
                System.out.print("Nombre de la línea: ");
                nomLin = leerString(scanner);
                System.out.print("Nombre de la estación a quitar: ");
                nomEst = leerString(scanner);
                if (quitarEstacionDeLinea(nomLin, nomEst)) System.out.println("Estación quitada.");
                break;
            default:
                System.err.println("Opción no válida.");
                break;
        }
    }

    private void menuABMRieles(Scanner scanner) {
        System.out.println("\n--- ABM Rieles ---");
        System.out.println("1. Alta de Riel");
        System.out.println("2. Baja de Riel");
        System.out.println("3. Modificación de Riel (Distancia)");
        System.out.print("Seleccione: ");
        int opcion = leerInt(scanner);

        String est1, est2;
        switch (opcion) {
            case 1:
                System.out.print("Nombre Estación A: ");
                est1 = leerString(scanner);
                System.out.print("Nombre Estación B: ");
                est2 = leerString(scanner);
                System.out.print("Distancia (km) (ej. 450.5): ");
                double km = leerDouble(scanner);
                if (altaRiel(est1, est2, km)) System.out.println("Riel creado.");
                break;
            case 2:
                System.out.print("Nombre Estación A: ");
                est1 = leerString(scanner);
                System.out.print("Nombre Estación B: ");
                est2 = leerString(scanner);
                if (bajaRiel(est1, est2)) System.out.println("Riel eliminado.");
                break;
            case 3:
                System.out.print("Nombre Estación A: ");
                est1 = leerString(scanner);
                System.out.print("Nombre Estación B: ");
                est2 = leerString(scanner);
                System.out.print("NUEVA Distancia (km): ");
                double nKm = leerDouble(scanner);
                if (modificarRiel(est1, est2, nKm)) System.out.println("Riel modificado.");
                break;
            default:
                System.err.println("Opción no válida.");
                break;
        }
    }

    private void menuConsultasTrenes(Scanner scanner) {
        System.out.println("\n--- Consultas de Trenes ---");
        System.out.println("1. Mostrar información de un tren");
        System.out.println("2. Mostrar ciudades que visita un tren");
        System.out.print("Seleccione: ");
        int opcion = leerInt(scanner);

        int id;
        switch (opcion) {
            case 1:
                System.out.print("ID del tren: ");
                id = leerInt(scanner);
                Tren t = consultarInfoTren(id);
                if (t != null) System.out.println(t.toString());
                break;
            case 2:
                System.out.print("ID del tren: ");
                id = leerInt(scanner);
                Lista ciudades = consultarCiudadesPorTren(id);
                System.out.println("Ciudades (sin repetir): " + ciudades.toString());
                break;
            default:
                System.err.println("Opción no válida.");
                break;
        }
    }

    private void menuConsultasEstaciones(Scanner scanner) {
        System.out.println("\n--- Consultas de Estaciones ---");
        System.out.println("1. Mostrar información de una estación");
        System.out.println("2. Listar estaciones por prefijo");
        System.out.print("Seleccione: ");
        int opcion = leerInt(scanner);

        switch (opcion) {
            case 1:
                System.out.print("Nombre de la estación: ");
                String nom = leerString(scanner);
                Estacion e = consultarInfoEstacion(nom);
                if (e != null) System.out.println(e.toString());
                break;
            case 2:
                System.out.print("Prefijo a buscar (ej. 'Cor'): ");
                String pref = leerString(scanner);
                Lista estaciones = consultarEstacionesPorPrefijo(pref);
                System.out.println("Estaciones encontradas: " + estaciones.toString());
                break;
            default:
                System.err.println("Opción no válida.");
                break;
        }
    }

    private void menuConsultasViajes(Scanner scanner) {
        System.out.println("\n--- Consultas de Viajes ---");
        System.out.println("1. Camino con MENOS ESTACIONES");
        System.out.println("2. Camino con MENOR DISTANCIA (KM)");
        System.out.println("3. Todos los caminos (evitando una estación)");
        System.out.println("4. Verificar viaje por KM máximos");
        System.out.print("Seleccione: ");
        int opcion = leerInt(scanner);

        String estA, estB, estC;
        switch (opcion) {
            case 1:
                System.out.print("Estación Origen: ");
                estA = leerString(scanner);
                System.out.print("Estación Destino: ");
                estB = leerString(scanner);
                System.out.println("Calculando...");
                Lista cBFS = obtenerCaminoMenosEstaciones(estA, estB);
                System.out.println("Camino con menos paradas: " + cBFS.toString());
                break;
            case 2:
                System.out.print("Estación Origen: ");
                estA = leerString(scanner);
                System.out.print("Estación Destino: ");
                estB = leerString(scanner);
                System.out.println("Calculando...");
                Lista cDijkstra = obtenerCaminoMenorDistancia(estA, estB);
                System.out.println("Camino más corto (km): " + cDijkstra.toString());
                break;
            case 3:
                System.out.print("Estación Origen: ");
                estA = leerString(scanner);
                System.out.print("Estación Destino: ");
                estB = leerString(scanner);
                System.out.print("Estación a EVITAR: ");
                estC = leerString(scanner);
                System.out.println("Calculando...");
                Lista todos = obtenerTodosLosCaminos(estA, estB, estC);
                System.out.println("Caminos encontrados: " + todos.toString());
                break;
            case 4:
                System.out.print("Estación Origen: ");
                estA = leerString(scanner);
                System.out.print("Estación Destino: ");
                estB = leerString(scanner);
                System.out.print("Límite de KM (ej. 500.0): ");
                double km = leerDouble(scanner);
                System.out.println("Calculando...");
                // El método esPosibleViajarEnKm ya imprime el resultado
                esPosibleViajarEnKm(estA, estB, km);
                break;
            default:
                System.err.println("Opción no válida.");
                break;
        }
    }

    // --- Métodos de Ayuda para Lectura Segura ---

    /**
     * Lee un String de la consola (línea completa), asegurando que no esté vacío.
     */
    private String leerString(Scanner scanner) {
        String entrada = "";
        while (entrada.trim().isEmpty()) {
            entrada = scanner.nextLine();
            if (entrada.trim().isEmpty()) {
                System.err.print("Error: La entrada no puede estar vacía. Intente de nuevo: ");
            }
        }
        return entrada;
    }

    /**
     * Lee un int de la consola, con validación de tipo.
     */
    private int leerInt(Scanner scanner) {
        int valor = -1;
        boolean valido = false;
        while (!valido) {
            try {
                valor = scanner.nextInt();
                valido = true;
            } catch (InputMismatchException e) {
                System.err.print("Error: Ingrese un número entero. Intente de nuevo: ");
                scanner.next(); // Limpiar el buffer del scanner
            }
        }
        scanner.nextLine();
        return valor;
    }

    /**
     * Lee un double de la consola, con validación de tipo.
     */
    private double leerDouble(Scanner scanner) {
        double valor = -1.0;
        boolean valido = false;
        while (!valido) {
            try {
                valor = scanner.nextDouble();
                valido = true;
            } catch (InputMismatchException e) {
                System.err.print("Error: Ingrese un número (ej. 123.4). Intente de nuevo: ");
                scanner.next(); // Limpiar el buffer del scanner
            }
        }
        scanner.nextLine();
        return valor;
    }

    /**
     * Escribe un mensaje en el archivo de log.
     * @param mensaje El texto a registrar.
     */
    private void escribirLog(String mensaje) {
        if (this.logWriter != null) {
            this.logWriter.println(java.time.LocalTime.now() + " - " + mensaje);
        }
    }

    /**
     * Cierra el archivo de log al terminar la ejecución.
     */
    public void cerrarLog() {
        if (this.logWriter != null) {
            logWriter.println("--- FIN DE SESIÓN --- \n");
            this.logWriter.close();
        }
    }

    // --- (Aquí irán los métodos para Carga Inicial, ABM, Consultas, etc.) ---
    /**
     * Carga la configuración inicial del sistema desde un archivo de texto.
     * Lee el archivo línea por línea y procesa estaciones, trenes, rieles y líneas.
     *
     * @param rutaArchivo La ruta completa al archivo de texto (ej. "datos.txt").
     */
    public void cargarSistemaDesdeArchivo(String rutaArchivo) {
        System.out.println("Iniciando carga inicial desde: " + rutaArchivo);
        escribirLog("INICIO Carga Inicial desde " + rutaArchivo);

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int contadorLineas = 0;
            while ((linea = br.readLine()) != null) {
                contadorLineas++;
                linea = linea.trim(); // Quitar espacios en blanco
                if (linea.isEmpty() || linea.startsWith("#")) {
                    // Ignorar líneas vacías o comentarios
                    continue;
                }

                // Separamos los campos por el delimitador '; '
                String[] campos = linea.split(";");

                if (campos.length < 2) {
                    System.err.println("Error L" + contadorLineas + ": Línea mal formada.");
                    continue; // Saltar a la siguiente línea
                }

                // Obtenemos el tipo de dato de la línea
                String tipo = campos[0].toUpperCase();

                try {
                    // Derivamos al método de procesamiento adecuado
                    switch (tipo) {
                        case "E": // Estación
                            procesarLineaEstacion(campos);
                            break;
                        case "T": // Tren
                            procesarLineaTren(campos);
                            break;
                        case "R": // Riel
                            procesarLineaRiel(campos);
                            break;
                        case "L": // Línea
                            procesarLineaLinea(campos);
                            break;
                        default:
                            System.err.println("Error L" + contadorLineas + ": Tipo de dato '" + tipo + "' no reconocido.");
                            break;
                    }
                } catch (Exception e) {
                    // Captura errores de parseo (NumberFormatException, etc.)
                    System.err.println("Error L" + contadorLineas + ": Datos inválidos. " + e.getMessage());
                }
            }
            System.out.println("Carga inicial completada.");
            escribirLog("FIN Carga Inicial. Se procesaron " + contadorLineas + " líneas.");

            // Registramos el estado del sistema postcarga
            logEstadoSistema();

        } catch (IOException e) {
            System.err.println("ERROR CRÍTICO al leer el archivo de carga: " + e.getMessage());
            escribirLog("ERROR CRÍTICO en Carga Inicial: " + e.getMessage());
        }
    }

    /**
     * Registra el estado actual de todas las estructuras en el log.
     * Se utiliza al finalizar la carga inicial y al cerrar el sistema.
     */
    public void logEstadoSistema() {
        escribirLog("--- INICIO ESTADO DEL SISTEMA ---");

        // 1. Loguear Estaciones
        escribirLog("--- 1. ESTRUCTURA DE ESTACIONES (AVL) ---");
        escribirLog(this.estaciones.toStringEstructura());

        // 2. Loguear Trenes
        escribirLog("--- 2. ESTRUCTURA DE TRENES (AVL) ---");
        escribirLog(this.trenes.toStringEstructura());

        // 3. Loguear Rieles
        escribirLog("--- 3. ESTRUCTURA DE RIELES (GRAFO) ---");
        escribirLog(this.redDeRieles.toString());

        // 4. Loguear Líneas
        escribirLog("--- 4. MAPEO DE LÍNEAS (HASHMAP) ---");
        if (this.mapeoLineas.isEmpty()) {
            escribirLog("Mapeo de líneas vacío.");
        } else {
            for (Object key : this.mapeoLineas.keySet()) {
                String nombreLinea = (String) key;
                lineales.dinamicas.Lista estacionesEnLinea =
                        (lineales.dinamicas.Lista) this.mapeoLineas.get(nombreLinea);
                escribirLog("Línea: " + nombreLinea + " -> " + estacionesEnLinea.toString());
            }
        }
        escribirLog("--- FIN ESTADO DEL SISTEMA ---");
    }

    /**
     * Procesa una línea de tipo 'Estación' (E) y la agrega al sistema.
     * Formato esperado: E;nombre;calle;numero;ciudad;cp;vias;plataformas
     */
    private void procesarLineaEstacion(String[] campos) {
        // Validación de cantidad de campos
        if (campos.length != 8) {
            throw new IllegalArgumentException("Formato 'E' incorrecto. Se esperaban 8 campos.");
        }

        // Parseo de datos
        String nombre = campos[1];
        String calle = campos[2];
        int numero = Integer.parseInt(campos[3]);
        String ciudad = campos[4];
        String cp = campos[5];
        int vias = Integer.parseInt(campos[6]);
        int plataformas = Integer.parseInt(campos[7]);

        // Creación del objeto Estacion
        Estacion nuevaEstacion = new Estacion(nombre, calle, numero, ciudad, cp, vias, plataformas);

        // Inserción en las dos estructuras
        boolean exitoDic = this.estaciones.insertar(nombre, nuevaEstacion);
        boolean exitoGrafo = this.redDeRieles.insertarVertice(nuevaEstacion);

        if (!exitoDic || !exitoGrafo) {
            System.err.println("Advertencia: La estación '" + nombre + "' ya existía.");
        }
    }

    /**
     * Procesa una línea de tipo 'Tren' (T) y la agrega al sistema.
     * Formato esperado: T;id;propulsion;vagonesPas;vagonesCarga;linea
     */
    private void procesarLineaTren(String[] campos) {
        if (campos.length != 6) {
            throw new IllegalArgumentException("Formato 'T' incorrecto. Se esperaban 6 campos.");
        }

        // Parseo de datos
        int id = Integer.parseInt(campos[1]);
        String propulsion = campos[2];
        int vagonesPas = Integer.parseInt(campos[3]);
        int vagonesCarga = Integer.parseInt(campos[4]);
        String linea = campos[5];

        // Creación del objeto Tren
        Tren nuevoTren = new Tren(id, propulsion, vagonesPas, vagonesCarga, linea);

        // Inserción en el diccionario de trenes
        if (!this.trenes.insertar(id, nuevoTren)) {
            System.err.println("Advertencia: El tren ID '" + id + "' ya existía.");
        }
    }

    /**
     * Procesa una línea de tipo 'Riel' (R) y la agrega al sistema.
     * Formato esperado: R;estacionOrigen;estacionDestino;distanciaKm
     */
    private void procesarLineaRiel(String[] campos) {
        if (campos.length != 4) {
            throw new IllegalArgumentException("Formato 'R' incorrecto. Se esperaban 4 campos.");
        }

        // Parseo de datos
        String nombreEst1 = campos[1];
        String nombreEst2 = campos[2];
        double distancia = Double.parseDouble(campos[3]);

        // Verificación de consistencia: buscamos las estaciones en el diccionario
        Estacion e1 = (Estacion) this.estaciones.obtener(nombreEst1);
        Estacion e2 = (Estacion) this.estaciones.obtener(nombreEst2);

        if (e1 != null && e2 != null) {
            // Si ambas estaciones existen, insertamos el riel (arco no dirigido)
            this.redDeRieles.insertarArco(e1, e2, distancia);
        } else {
            System.err.println("Error de consistencia: No se pudo crear riel. Estación no encontrada: "
                    + (e1 == null ? nombreEst1 : "") + (e2 == null ? " " + nombreEst2 : ""));
        }
    }

    /**
     * Procesa una línea de tipo 'Línea' (L) y la agrega al sistema.
     * Formato esperado: L;nombreLinea;estacion1;estacion2;...;estacionN
     */
    private void procesarLineaLinea(String[] campos) {
        if (campos.length < 3) {
            throw new IllegalArgumentException("Formato 'L' incorrecto. Se esperaban al menos 3 campos.");
        }

        String nombreLinea = campos[1];

        // Creamos la nueva lista de estaciones para esta línea
        // (Usamos la Lista de la cátedra)
        lineales.dinamicas.Lista estacionesDeLinea = new lineales.dinamicas.Lista();

        // Iteramos por las estaciones (a partir del índice 2)
        for (int i = 2; i < campos.length; i++) {
            String nombreEst = campos[i];
            // Verificación de consistencia: buscamos la estación
            Estacion e = (Estacion) this.estaciones.obtener(nombreEst);

            if (e != null) {
                // Si existe, la agregamos al final de la lista
                estacionesDeLinea.insertar(e, estacionesDeLinea.longitud() + 1);
            } else {
                System.err.println("Error de consistencia: Estación '" + nombreEst
                        + "' no encontrada. No se agregará a la línea '" + nombreLinea + "'.");
            }
        }

        // Guardamos la línea (String) y su lista de estaciones (Lista) en el Mapeo
        this.mapeoLineas.put(nombreLinea, estacionesDeLinea);
    }

    /**
     * Muestra el estado y contenido de todas las estructuras de datos
     * principales en la consola.
     * Cumple con el requisito de la "Opción 9: Mostrar sistema".
     */
    public void mostrarSistema() {
        System.out.println("=======================================================");
        System.out.println("          ESTADO ACTUAL DEL SISTEMA TRENESSA           ");
        System.out.println("=======================================================");

        // 1. Mostrar Diccionario de Estaciones (AVL)
        System.out.println("\n--- 1. ESTRUCTURA DE ESTACIONES (AVL) ---");
        // Usamos el método que acabamos de crear en Diccionario.java
        System.out.println(this.estaciones.toStringEstructura());

        // 2. Mostrar Diccionario de Trenes (AVL)
        System.out.println("\n--- 2. ESTRUCTURA DE TRENES (AVL) ---");
        System.out.println(this.trenes.toStringEstructura());

        // 3. Mostrar Grafo de Rieles (Listas de Adyacencia)
        System.out.println("\n--- 3. ESTRUCTURA DE RIELES (GRAFO) ---");
        // El toString() del Grafo ya muestra la lista de adyacencia
        System.out.println(this.redDeRieles.toString());

        // 4. Mostrar Mapeo de Líneas (HashMap)
        System.out.println("\n--- 4. MAPEO DE LÍNEAS (HASHMAP) ---");
        // El toString() de HashMap de Java es adecuado
        if (this.mapeoLineas.isEmpty()) {
            System.out.println("Mapeo de líneas vacío.");
        } else {
            // Iteramos para un formato más legible que el toString() por defecto
            for (Object key : this.mapeoLineas.keySet()) {
                String nombreLinea = (String) key;
                lineales.dinamicas.Lista estacionesEnLinea =
                        (lineales.dinamicas.Lista) this.mapeoLineas.get(nombreLinea);
                System.out.println("Línea: " + nombreLinea + " -> " + estacionesEnLinea.toString());
            }
        }
        System.out.println("\n=======================================================");
    }

    // ---- METODOS ABM ----
    /**
     * Da de ALTA una nueva estación en el sistema.
     * La inserta tanto en el diccionario de estaciones como en el grafo.
     *
     * @param nombre          Nombre único (Clave)
     * @param calle           Calle del domicilio
     * @param numero          Número del domicilio
     * @param ciudad          Ciudad
     * @param codigoPostal    Código Postal
     * @param cantVias        Cantidad de vías
     * @param cantPlataformas Cantidad de plataformas
     * @return verdadero si se pudo insertar, falso si la estación ya existía.
     */
    public boolean altaEstacion(String nombre, String calle, int numero, String ciudad,
                                String codigoPostal, int cantVias, int cantPlataformas) {

        // 1. Crear el objeto Estacion
        Estacion nuevaEstacion = new Estacion(nombre, calle, numero, ciudad,
                codigoPostal, cantVias, cantPlataformas);

        // 2. Intentar insertar en el diccionario de estaciones
        boolean exitoDic = this.estaciones.insertar(nombre, nuevaEstacion);

        // 3. Si tuvo éxito, insertar también en el grafo como vértice
        boolean exitoGrafo = false;
        if (exitoDic) {
            exitoGrafo = this.redDeRieles.insertarVertice(nuevaEstacion);
        }

        // 4. Registrar en el log y devolver
        if (exitoDic && exitoGrafo) {
            escribirLog("ALTA Estación: " + nombre);
        } else if (!exitoDic) {
            System.err.println("Error en ALTA: La estación '" + nombre + "' ya existe.");
        }

        return exitoDic && exitoGrafo;
    }

    /**
     * Da de BAJA una estación del sistema.
     * La elimina del diccionario de estaciones y del grafo.
     *
     * @param nombre El nombre de la estación a eliminar.
     * @return verdadero si se encontró y eliminó, falso en caso contrario.
     */
    public boolean bajaEstacion(String nombre) {
        // 1. Obtener el OBJETO Estacion desde el diccionario (lo necesitamos para el grafo)
        Estacion estacionAEliminar = (Estacion) this.estaciones.obtener(nombre);

        if (estacionAEliminar == null) {
            System.err.println("Error en BAJA: No se encontró la estación '" + nombre + "'.");
            return false;
        }

        // 2. Eliminar la estación del grafo (esto elimina el vértice y todos los rieles incidentes)
        boolean exitoGrafo = this.redDeRieles.eliminarVertice(estacionAEliminar);

        // 3. Eliminar la estación del diccionario
        boolean exitoDic = this.estaciones.eliminar(nombre);

        // 4. Registrar en el log
        if (exitoGrafo && exitoDic) {
            escribirLog("BAJA Estación: " + nombre + ". Se eliminaron también todos sus rieles asociados.");
        } else {
            // Esto sería un error de sincronización interna si ocurriera
            escribirLog("ERROR INESPERADO en BAJA: No se pudo eliminar '" + nombre + "' de ambas estructuras.");
        }

        // NOTA: La estación también debería ser eliminada de las Líneas donde aparece.
        // Se puede implementar aquí o asumir que el usuario lo hará manualmente
        // con el ABM de Líneas. Por ahora lo dejamos así por modularidad.

        return exitoGrafo && exitoDic;
    }

    /**
     * MODIFICA los datos de una estación existente.
     * No se permite modificar el nombre, ya que es la clave.
     *
     * @param nombre          El nombre de la estación a buscar.
     * @param calle           El *nuevo* domicilio
     * @param numero          El *nuevo* número
     * @param ciudad          La *nueva* ciudad
     * @param codigoPostal    El *nuevo* CP
     * @param cantVias        La *nueva* cantidad de vías
     * @param cantPlataformas La *nueva* cantidad de plataformas
     * @return verdadero si se encontró y modificó, falso si no se encontró.
     */
    public boolean modificarEstacion(String nombre, String calle, int numero, String ciudad,
                                     String codigoPostal, int cantVias, int cantPlataformas) {

        // 1. Obtener la estación del diccionario
        Estacion estacionAModificar = (Estacion) this.estaciones.obtener(nombre);

        if (estacionAModificar == null) {
            System.err.println("Error en MODIFICACIÓN: No se encontró la estación '" + nombre + "'.");
            return false;
        }

        // 2. Actualizar los datos usando los setters (los datos mutables)
        estacionAModificar.setCalle(calle);
        estacionAModificar.setNumero(numero);
        estacionAModificar.setCiudad(ciudad);
        estacionAModificar.setCodigoPostal(codigoPostal);
        estacionAModificar.setCantVias(cantVias);
        estacionAModificar.setCantPlataformas(cantPlataformas);

        // 3. Registrar en el log
        escribirLog("MODIFICACIÓN Estación: " + nombre);
        return true;
    }

    /**
     * Da de ALTA un nuevo riel (arco no dirigido) entre dos estaciones.
     *
     * @param nombreEst1 El nombre de la estación origen.
     * @param nombreEst2 El nombre de la estación destino.
     * @param km         La distancia (etiqueta) en kilómetros entre ellas.
     * @return verdadero si ambas estaciones existen y el riel se pudo crear.
     */
    public boolean altaRiel(String nombreEst1, String nombreEst2, double km) {
        // 1. Obtener los objetos Estacion desde el diccionario
        Estacion e1 = (Estacion) this.estaciones.obtener(nombreEst1);
        Estacion e2 = (Estacion) this.estaciones.obtener(nombreEst2);

        // 2. Validar que ambas estaciones existan
        if (e1 == null || e2 == null) {
            System.err.println("Error en ALTA Riel: Una o ambas estaciones no existen.");
            System.err.println("  - Estación 1 ('" + nombreEst1 + "'): " + (e1 != null ? "Encontrada" : "NO Encontrada"));
            System.err.println("  - Estación 2 ('" + nombreEst2 + "'): " + (e2 != null ? "Encontrada" : "NO Encontrada"));
            return false;
        }

        // 3. Validar que la distancia sea positiva
        if (km <= 0) {
            System.err.println("Error en ALTA Riel: La distancia debe ser mayor a 0 km.");
            return false;
        }

        // 4. Insertar el arco no dirigido en el grafo
        boolean exito = this.redDeRieles.insertarArco(e1, e2, km);

        // 5. Registrar en el log
        if (exito) {
            escribirLog("ALTA Riel: " + nombreEst1 + " <-> " + nombreEst2 + " (" + km + " km)");
        } else {
            // Nota: nuestro insertarArco no devuelve falso, pero si lo hiciera (ej. si ya existe),
            // aquí se manejaría.
            escribirLog("ALTA Riel fallida (posiblemente ya existía): " + nombreEst1 + " <-> " + nombreEst2);
        }

        return exito;
    }

    /**
     * Da de BAJA un riel (arco no dirigido) entre dos estaciones.
     *
     * @param nombreEst1 El nombre de la estación origen.
     * @param nombreEst2 El nombre de la estación destino.
     * @return verdadero si ambas estaciones existen y el riel se pudo eliminar.
     */
    public boolean bajaRiel(String nombreEst1, String nombreEst2) {
        // 1. Obtener los objetos Estacion desde el diccionario
        Estacion e1 = (Estacion) this.estaciones.obtener(nombreEst1);
        Estacion e2 = (Estacion) this.estaciones.obtener(nombreEst2);

        // 2. Validar que ambas estaciones existan
        if (e1 == null || e2 == null) {
            System.err.println("Error en BAJA Riel: Una o ambas estaciones no existen.");
            return false;
        }

        // 3. Eliminar el arco no dirigido del grafo
        boolean exito = this.redDeRieles.eliminarArco(e1, e2);

        // 4. Registrar en el log
        if (exito) {
            escribirLog("BAJA Riel: " + nombreEst1 + " <-> " + nombreEst2);
        } else {
            // Nota: nuestro eliminarArco no devuelve falso si el arco no existía.
            escribirLog("BAJA Riel fallida (posiblemente no existía): " + nombreEst1 + " <-> " + nombreEst2);
        }

        return exito;
    }

    /**
     * MODIFICA la distancia (etiqueta) de un riel existente.
     * (Solo podemos modificar la distancia de un Riel, dado que las estaciones de origen y destino son claves).
     *
     * @param nombreEst1 El nombre de la estación origen.
     * @param nombreEst2 El nombre de la estación destino.
     * @param nuevaDistanciaKm La *nueva* distancia en kilómetros.
     * @return verdadero si el riel fue encontrado y modificado.
     */
    public boolean modificarRiel(String nombreEst1, String nombreEst2, double nuevaDistanciaKm) {
        // 1. Obtener los objetos Estacion
        Estacion e1 = (Estacion) this.estaciones.obtener(nombreEst1);
        Estacion e2 = (Estacion) this.estaciones.obtener(nombreEst2);

        // 2. Validar que ambas estaciones existan
        if (e1 == null || e2 == null) {
            System.err.println("Error en MODIFICAR Riel: Una o ambas estaciones no existen.");
            return false;
        }

        // 3. Validar que la nueva distancia sea positiva
        if (nuevaDistanciaKm <= 0) {
            System.err.println("Error en MODIFICAR Riel: La distancia debe ser mayor a 0 km.");
            return false;
        }

        // 4. Llamar al nuevo método del grafo
        boolean exito = this.redDeRieles.modificarEtiqueta(e1, e2, nuevaDistanciaKm);

        // 5. Registrar en el log
        if (exito) {
            escribirLog("MODIFICACIÓN Riel: " + nombreEst1 + " <-> " + nombreEst2 +
                    ". Nueva distancia: " + nuevaDistanciaKm + " km");
        } else {
            escribirLog("MODIFICACIÓN Riel fallida (el riel no existe): " + nombreEst1 + " <-> " + nombreEst2);
            System.err.println("Error en MODIFICAR Riel: No se encontró un riel entre " +
                    nombreEst1 + " y " + nombreEst2 + ".");
        }

        return exito;
    }

    /**
     * Da de ALTA un nuevo tren en el sistema.
     *
     * @param id               El ID numérico único (Clave).
     * @param propulsion       Tipo de propulsión (ej. "diesel", "electrico").
     * @param vagonesPasajeros Cantidad de vagones de pasajeros.
     * @param vagonesCarga     Cantidad de vagones de carga.
     * @param linea            Nombre de la línea asignada (o "libre", "no-asignado").
     * @return verdadero si se pudo insertar, falso si el ID ya existía.
     */
    public boolean altaTren(int id, String propulsion, int vagonesPasajeros, int vagonesCarga, String linea) {

        // 1. Validar que el ID sea positivo
        if (id <= 0) {
            System.err.println("Error en ALTA Tren: El ID debe ser un número positivo.");
            return false;
        }

        // 2. Crear el objeto Tren
        Tren nuevoTren = new Tren(id, propulsion, vagonesPasajeros, vagonesCarga, linea);

        // 3. Insertar en el diccionario de trenes
        boolean exito = this.trenes.insertar(id, nuevoTren);

        // 4. Registrar en el log
        if (exito) {
            escribirLog("ALTA Tren: ID " + id);
        } else {
            System.err.println("Error en ALTA Tren: El ID " + id + " ya existe.");
        }

        return exito;
    }

    /**
     * Da de BAJA un tren del sistema usando su ID.
     * (Implementa parte de la Opción 2 del menú).
     *
     * @param id El ID numérico del tren a eliminar.
     * @return verdadero si se encontró y eliminó, falso en caso contrario.
     */
    public boolean bajaTren(int id) {
        // 1. Intentar eliminar el tren del diccionario
        boolean exito = this.trenes.eliminar(id);

        // 2. Registrar en el log
        if (exito) {
            escribirLog("BAJA Tren: ID " + id);
        } else {
            System.err.println("Error en BAJA Tren: No se encontró un tren con ID " + id + ".");
        }

        return exito;
    }

    /**
     * MODIFICA los datos de un tren existente.
     * No se permite modificar el ID, ya que es la clave.
     *
     * @param id               El ID del tren a buscar.
     * @param propulsion       La *nueva* propulsión.
     * @param vagonesPasajeros La *nueva* cantidad de vagones de pasajeros.
     * @param vagonesCarga     La *nueva* cantidad de vagones de carga.
     * @param linea            La *nueva* línea asignada.
     * @return verdadero si se encontró y modificó, falso si no se encontró.
     */
    public boolean modificarTren(int id, String propulsion, int vagonesPasajeros, int vagonesCarga, String linea) {

        // 1. Obtener el tren del diccionario
        Tren trenAModificar = (Tren) this.trenes.obtener(id);

        if (trenAModificar == null) {
            System.err.println("Error en MODIFICACIÓN Tren: No se encontró un tren con ID " + id + ".");
            return false;
        }

        // 2. Actualizar los datos usando los setters (datos mutables)
        trenAModificar.setPropulsion(propulsion);
        trenAModificar.setVagonesPasajeros(vagonesPasajeros);
        trenAModificar.setVagonesCarga(vagonesCarga);
        trenAModificar.setLinea(linea);

        // 3. Registrar en el log
        escribirLog("MODIFICACIÓN Tren: ID " + id);
        return true;
    }

    /**
     * Da de ALTA (crea) una nueva línea de tren vacía en el sistema.
     * (Implementa parte de la Opción 4 del menú).
     *
     * @param nombreLinea El nombre único de la nueva línea (ej. "Roca").
     * @return verdadero si se pudo crear, falso si la línea ya existía.
     */
    public boolean crearLinea(String nombreLinea) {
        boolean exito = false;
        // 1. Verificar si la línea ya existe en el HashMap
        if (this.mapeoLineas.containsKey(nombreLinea)) {
            System.err.println("Error en ALTA Línea: La línea '" + nombreLinea + "' ya existe.");
        } else {
            // 2. Si no existe, crear una nueva lista vacía para ella
            lineales.dinamicas.Lista nuevaListaEstaciones = new lineales.dinamicas.Lista();
            // 3. Insertar la línea y su lista en el mapeo
            this.mapeoLineas.put(nombreLinea, nuevaListaEstaciones);
            escribirLog("ALTA Línea: Se creó la línea '" + nombreLinea + "'.");
            exito = true;
        }
        return exito;
    }

    /**
     * Da de BAJA (elimina) una línea de tren completa del sistema.
     * (Implementa parte de la Opción 4 del menú).
     *
     * @param nombreLinea El nombre de la línea a eliminar.
     * @return verdadero si la línea se encontró y eliminó.
     */
    public boolean eliminarLinea(String nombreLinea) {
        // 1. Intentar remover la línea del HashMap
        Object lineaEliminada = this.mapeoLineas.remove(nombreLinea);

        if (lineaEliminada != null) {
            escribirLog("BAJA Línea: Se eliminó la línea '" + nombreLinea + "'.");
            return true;
        } else {
            System.err.println("Error en BAJA Línea: No se encontró la línea '" + nombreLinea + "'.");
            return false;
        }
    }

    /**
     * MODIFICA una línea agregándole una estación.
     * (Implementa parte de la Opción 4 del menú).
     *
     * @param nombreLinea    El nombre de la línea a modificar.
     * @param nombreEstacion El nombre de la estación a agregar.
     * @return verdadero si la línea y la estación existen, y la estación no
     * estaba ya en la línea.
     */
    public boolean agregarEstacionALinea(String nombreLinea, String nombreEstacion) {
        // 1. Obtener la lista de estaciones de la línea
        Lista lista = (lineales.dinamicas.Lista) this.mapeoLineas.get(nombreLinea);
        if (lista == null) {
            System.err.println("Error en MODIFICACIÓN Línea: No se encontró la línea '" + nombreLinea + "'.");
            return false;
        }

        // 2. Obtener el objeto Estacion
        Estacion estacion = (Estacion) this.estaciones.obtener(nombreEstacion);
        if (estacion == null) {
            System.err.println("Error en MODIFICACIÓN Línea: No se encontró la estación '" + nombreEstacion + "'.");
            return false;
        }

        // 3. Verificar que la estación no esté ya en la lista
        // Usamos el .localizar() que corregimos en Lista.java
        if (lista.localizar(estacion) >= 0) {
            System.err.println("Error en MODIFICACIÓN Línea: La estación '" + nombreEstacion +
                    "' ya se encuentra en la línea '" + nombreLinea + "'.");
            return false;
        }

        // 4. Insertar la estación (la agregamos al final)
        lista.insertar(estacion, lista.longitud() + 1);
        escribirLog("MODIFICACIÓN Línea: Se agregó estación '" + nombreEstacion +
                "' a la línea '" + nombreLinea + "'.");
        return true;
    }

    /**
     * MODIFICA una línea quitándole una estación.
     * (Implementa parte de la Opción 4 del menú).
     *
     * @param nombreLinea    El nombre de la línea a modificar.
     * @param nombreEstacion El nombre de la estación a quitar.
     * @return verdadero si la línea y la estación existen, y la estación
     * se pudo quitar.
     */
    public boolean quitarEstacionDeLinea(String nombreLinea, String nombreEstacion) {
        // 1. Obtener la lista de estaciones de la línea
        lineales.dinamicas.Lista lista = (lineales.dinamicas.Lista) this.mapeoLineas.get(nombreLinea);
        if (lista == null) {
            System.err.println("Error en MODIFICACIÓN Línea: No se encontró la línea '" + nombreLinea + "'.");
            return false;
        }

        // 2. Obtener el objeto Estacion (solo para buscarlo)
        Estacion estacion = (Estacion) this.estaciones.obtener(nombreEstacion);
        if (estacion == null) {
            System.err.println("Error en MODIFICACIÓN Línea: La estación '" + nombreEstacion + "' no existe en el sistema.");
            return false;
        }

        // 3. Localizar la estación en la lista
        int pos = lista.localizar(estacion);
        if (pos < 0) {
            System.err.println("Error en MODIFICACIÓN Línea: La estación '" + nombreEstacion +
                    "' no pertenece a la línea '" + nombreLinea + "'.");
            return false;
        }

        // 4. Eliminar la estación de la lista por su posición
        boolean exito = lista.eliminar(pos);
        if (exito) {
            escribirLog("MODIFICACIÓN Línea: Se quitó estación '" + nombreEstacion +
                    "' de la línea '" + nombreLinea + "'.");
        }
        return exito;
    }

    // --- FASE 3: CONSULTAS ---

    /**
     * Devuelve la información completa de un tren dado su ID.
     * (Implementa la Opción 6, parte A).
     *
     * @param id El ID numérico del tren a buscar.
     * @return el objeto Tren si se encuentra, o null si no existe.
     */
    public Tren consultarInfoTren(int id) {
        Tren tren = (Tren) this.trenes.obtener(id);
        if (tren == null) {
            System.err.println("Consulta: No se encontró un tren con ID " + id + ".");
        }
        return tren;
    }

    /**
     * Devuelve una lista de las CIUDADES (sin repetir) que visita un tren.
     * Lo hace buscando la línea asignada al tren y luego las ciudades
     * de las estaciones de esa línea.
     * (Implementa la Opción 6, parte B).
     *
     * @param id El ID numérico del tren a consultar.
     * @return una Lista de Strings con los nombres de las ciudades.
     */
    public Lista consultarCiudadesPorTren(int id) {
        Lista ciudades = new Lista(); // Lista de Strings (ciudades)

        // 1. Obtener el tren
        Tren t = (Tren) this.trenes.obtener(id);
        if (t == null) {
            System.err.println("Consulta: No se encontró el tren con ID " + id + ".");
            return ciudades; // Devuelve lista vacía
        }

        // 2. Obtener la línea del tren
        String nombreLinea = t.getLinea();
        if (nombreLinea.equalsIgnoreCase("libre") || nombreLinea.equalsIgnoreCase("no-asignado")) {
            System.out.println("Consulta: El tren " + id + " está libre, no visita ciudades.");
            return ciudades; // Devuelve lista vacía
        }

        // 3. Obtener la lista de estaciones de esa línea
        Lista estacionesDeLinea = (Lista) this.mapeoLineas.get(nombreLinea);
        if (estacionesDeLinea == null) {
            System.err.println("Consulta: La línea '" + nombreLinea + "' asignada al tren " + id + " no existe.");
            return ciudades; // Devuelve lista vacía
        }

        // 4. Recorrer las estaciones y recolectar ciudades sin repetir
        for (int i = 1; i <= estacionesDeLinea.longitud(); i++) {
            Estacion e = (Estacion) estacionesDeLinea.recuperar(i);
            String ciudad = e.getCiudad();

            // Si la ciudad no está en nuestra lista de ciudades, la agregamos
            if (ciudades.localizar(ciudad) < 0) {
                ciudades.insertar(ciudad, 1);
            }
        }
        return ciudades;
    }

    /**
     * Devuelve la información completa de una estación dado su nombre.
     * (Implementa la Opción 7, parte A).
     *
     * @param nombre El nombre de la estación a buscar.
     * @return el objeto Estacion si se encuentra, o null si no existe.
     */
    public Estacion consultarInfoEstacion(String nombre) {
        Estacion estacion = (Estacion) this.estaciones.obtener(nombre);
        if (estacion == null) {
            System.err.println("Consulta: No se encontró la estación '" + nombre + "'.");
        }
        return estacion;
    }

    /**
     * Devuelve una lista de todas las estaciones cuyo nombre comienza
     * con una subcadena dada.
     * (Implementa la Opción 7, parte B).
     *
     * @param prefijo El prefijo a buscar (ej. "Villa").
     * @return una Lista de objetos Estacion que coinciden con el prefijo.
     */
    public Lista consultarEstacionesPorPrefijo(String prefijo) {
        // Usamos el método listarRango del Diccionario (AVL).
        // Para esto, definimos un rango de búsqueda [prefijo, prefijo + "ZZZZ"]
        String claveInicio = prefijo;
        String claveFin = prefijo + "ZZZZ";

        Lista estacionesEnRango = this.estaciones.listarRango(claveInicio, claveFin);

        if (estacionesEnRango.esVacia()) {
            System.out.println("Consulta: No se encontraron estaciones que comiencen con '" + prefijo + "'.");
        }

        return estacionesEnRango;
    }

    /**
     * Método auxiliar privado para obtener un objeto Estacion desde el
     * diccionario. Simplifica el código en las consultas.
     * @param nombre El nombre de la estación.
     * @return El objeto Estacion, o null si no se encuentra.
     */
    private Estacion helperObtenerEstacion(String nombre) {
        Estacion e = (Estacion) this.estaciones.obtener(nombre);
        if (e == null) {
            System.err.println("Error: No se pudo encontrar la estación '" + nombre + "'.");
        }
        return e;
    }

    /**
     * Obtiene el camino entre dos estaciones que pase por MENOS estaciones.
     * (Implementa la Opción 8, parte A).
     *
     * @param nombreEstA Nombre de la estación Origen.
     * @param nombreEstB Nombre de la estación Destino.
     * @return una Lista con el camino (objetos Estacion). Vacía si no hay camino.
     */
    public Lista obtenerCaminoMenosEstaciones(String nombreEstA, String nombreEstB) {
        // 1. Obtener los objetos Estacion
        Estacion eA = helperObtenerEstacion(nombreEstA);
        Estacion eB = helperObtenerEstacion(nombreEstB);

        // 2. Llamar al método del grafo (BFS)
        if (eA != null && eB != null) {
            return this.redDeRieles.caminoMasCorto(eA, eB);
        }
        return new Lista(); // Devuelve lista vacía si las estaciones no existen
    }

    /**
     * Obtiene el camino entre dos estaciones con la MENOR distancia en KM.
     * (Implementa la Opción 8, parte B).
     *
     * @param nombreEstA Nombre de la estación Origen.
     * @param nombreEstB Nombre de la estación Destino.
     * @return una Lista con el camino (objetos Estacion). Vacía si no hay camino.
     */
    public Lista obtenerCaminoMenorDistancia(String nombreEstA, String nombreEstB) {
        // 1. Obtener los objetos Estacion
        Estacion eA = helperObtenerEstacion(nombreEstA);
        Estacion eB = helperObtenerEstacion(nombreEstB);

        // 2. Llamar al método del grafo (Dijkstra)
        if (eA != null && eB != null) {
            return this.redDeRieles.caminoMenorDistancia(eA, eB);
        }
        return new Lista(); // Devuelve lista vacía si las estaciones no existen
    }

    /**
     * Obtiene TODOS los caminos posibles entre dos estaciones sin pasar por una tercera.
     * (Implementa la Opción 8, parte C - Opcional *).
     *
     * @param nombreEstA Nombre de la estación Origen.
     * @param nombreEstB Nombre de la estación Destino.
     * @param nombreEstC Nombre de la estación a Evitar.
     * @return una Lista de Listas (cada sub-lista es un camino).
     */
    public Lista obtenerTodosLosCaminos(String nombreEstA, String nombreEstB, String nombreEstC) {
        // 1. Obtener los objetos Estacion
        Estacion eA = helperObtenerEstacion(nombreEstA);
        Estacion eB = helperObtenerEstacion(nombreEstB);
        Estacion eC = helperObtenerEstacion(nombreEstC);

        // 2. Llamar al método del grafo (DFS/Backtracking)
        if (eA != null && eB != null && eC != null) {
            return this.redDeRieles.obtenerTodosCaminos(eA, eB, eC);
        }
        return new Lista(); // Devuelve lista vacía si las estaciones no existen
    }

    /**
     * Verifica si es posible viajar de A a B con un límite de KM.
     * (Implementa la Opción 8, parte D - Opcional *).
     *
     * @param nombreEstA Nombre de la estación Origen.
     * @param nombreEstB Nombre de la estación Destino.
     * @param maxKm      La cantidad máxima de kilómetros permitidos.
     * @return verdadero si el camino más corto es <= maxKm, falso en caso contrario.
     */
    public boolean esPosibleViajarEnKm(String nombreEstA, String nombreEstB, double maxKm) {
        // 1. Obtener el camino más corto en distancia
        Lista caminoMasCorto = obtenerCaminoMenorDistancia(nombreEstA, nombreEstB);

        if (caminoMasCorto.esVacia()) {
            System.err.println("Consulta: No existe ningún camino entre " + nombreEstA + " y " + nombreEstB + ".");
            return false;
        }

        // 2. Calcular el costo de ese camino
        double costo = this.redDeRieles.calcularCostoCamino(caminoMasCorto);

        if (costo == -1.0) {
            // Esto no debería pasar si Dijkstra funcionó, pero es un control
            System.err.println("Error: El camino de Dijkstra es inválido.");
            return false;
        }

        // 3. Comparar y devolver
        boolean esPosible = (costo <= maxKm);
        System.out.println("Consulta: El camino más corto es de " + costo + " km.");
        System.out.println("  - Límite: " + maxKm + " km.");
        System.out.println("  - ¿Es posible? " + (esPosible ? "Sí" : "No"));

        return esPosible;
    }

}