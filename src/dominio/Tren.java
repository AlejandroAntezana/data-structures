package dominio;
/*
  @author Alejandro ANTEZANA DE LA RIVERA.
 */

/**
 * Representa un Tren en el sistema dominio.TrenesSA.
 * Almacena la información de identificación, propulsión, capacidad y
 * línea asignada.
 */
public class Tren {

    // Atributo inmutable (Clave)
    private int id; // Identificador numérico único

    // Atributos mutables (Datos)
    private String propulsion;
    private int vagonesPasajeros;
    private int vagonesCarga;
    private String linea; // "libre" o "no-asignado" si no tiene

    /**
     * Constructor para crear un nuevo Tren.
     * @param id               El ID numérico único.
     * @param propulsion       Tipo de propulsión (ej. "diesel", "electrico").
     * @param vagonesPasajeros Cantidad de vagones de pasajeros.
     * @param vagonesCarga     Cantidad de vagones de carga.
     * @param linea            Nombre de la línea asignada (o "libre").
     */
    public Tren(int id, String propulsion, int vagonesPasajeros, int vagonesCarga, String linea) {
        this.id = id;
        this.propulsion = propulsion;
        this.vagonesPasajeros = vagonesPasajeros;
        this.vagonesCarga = vagonesCarga;
        this.linea = linea;
    }

    // --- Observadores ---
    public int getId() {
        return id;
    }

    public String getPropulsion() {
        return propulsion;
    }

    public int getVagonesPasajeros() {
        return vagonesPasajeros;
    }

    public int getVagonesCarga() {
        return vagonesCarga;
    }

    public String getLinea() {
        return linea;
    }

    // --- Modificadores ---
    public void setPropulsion(String propulsion) {
        this.propulsion = propulsion;
    }

    public void setVagonesPasajeros(int vagonesPasajeros) {
        this.vagonesPasajeros = vagonesPasajeros;
    }

    public void setVagonesCarga(int vagonesCarga) {
        this.vagonesCarga = vagonesCarga;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    /**
     * Genera una representación en String del tren.
     * @return Un String con todos los datos del tren.
     */
    public String toString() {
        return "Tren ID: " + this.id + "\n" +
                "  - Propulsión: " + this.propulsion + "\n" +
                "  - Vagones Pasajeros: " + this.vagonesPasajeros + "\n" +
                "  - Vagones Carga: " + this.vagonesCarga + "\n" +
                "  - Línea Asignada: " + this.linea;
    }
}