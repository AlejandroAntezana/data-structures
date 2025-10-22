package dominio;

/*
  @author Alejandro ANTEZANA DE LA RIVERA.
 */

/**
 * Representa una estación de tren en el sistema dominio.TrenesSA.
 * Esta clase almacena toda la información de la estación, según
 * lo especificado en el enunciado.
 * * Implementa 'Comparable' para poder ser utilizada como clave en
 * estructuras de ordenamiento (como el Diccionario) y 'equals'
 * para funcionar con los métodos de búsqueda (como Lista.localizar).
 */
public class Estacion implements Comparable {

    // Atributos inmutables (Clave)
    private String nombre; // Clave única

    // Atributos mutables (Datos)
    private String calle;
    private int numero;
    private String ciudad;
    private String codigoPostal;
    private int cantVias;
    private int cantPlataformas;

    /**
     * Constructor para crear una nueva Estacion.
     * @param nombre          Nombre único de la estación (ej. "Retiro").
     * @param calle           Calle del domicilio.
     * @param numero          Número del domicilio.
     * @param ciudad          Ciudad donde se ubica.
     * @param codigoPostal    Código Postal.
     * @param cantVias        Cantidad de vías que posee.
     * @param cantPlataformas Cantidad de plataformas.
     */
    public Estacion(String nombre, String calle, int numero, String ciudad, String codigoPostal, int cantVias, int cantPlataformas) {
        this.nombre = nombre;
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.cantVias = cantVias;
        this.cantPlataformas = cantPlataformas;
    }

    // --- Observadores  ---
    public String getNombre() {
        return nombre;
    }

    public String getCalle() {
        return calle;
    }

    public int getNumero() {
        return numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public int getCantVias() {
        return cantVias;
    }

    public int getCantPlataformas() {
        return cantPlataformas;
    }

    // --- Modificadores  ---
    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public void setCantVias(int cantVias) {
        this.cantVias = cantVias;
    }

    public void setCantPlataformas(int cantPlataformas) {
        this.cantPlataformas = cantPlataformas;
    }

    // --- Métodos 'Comparable' y 'equals' (basados en la clave 'nombre') ---

    /**
     * Compara esta Estacion con otro objeto.
     * La comparación se basa únicamente en el 'nombre'.
     * @param otro El objeto a comparar (se espera que sea una Estacion).
     * @return un valor negativo, cero o positivo si este nombre es
     * lexicográficamente menor, igual o mayor que el otro.
     */
    public int compareTo(Object otro) {
        // Se asume que 'otro' es una Estacion
        Estacion otraEstacion = (Estacion) otro;
        return this.nombre.compareTo(otraEstacion.getNombre());
    }

    /**
     * Verifica si esta Estacion es igual a otro objeto.
     * Dos estaciones se consideran iguales si tienen el mismo 'nombre'.
     * @param obj El objeto a comparar.
     * @return verdadero si son iguales, falso en caso contrario.
     */
    public boolean equals(Object obj) {
        boolean sonIguales = false;
        if (obj instanceof Estacion) {
            Estacion otraEstacion = (Estacion) obj;
            sonIguales = this.nombre.equals(otraEstacion.getNombre());
        }
        return sonIguales;
    }

    /**
     * Genera una representación en String de la estación.
     * @return Un String con todos los datos de la estación.
     */
    public String toString() {
        return "Estacion: " + this.nombre + "\n" +
                "  - Domicilio: " + this.calle + " " + this.numero + ", " +
                this.ciudad + " (" + this.codigoPostal + ")\n" +
                "  - Vías: " + this.cantVias + "\n" +
                "  - Plataformas: " + this.cantPlataformas;
    }
}