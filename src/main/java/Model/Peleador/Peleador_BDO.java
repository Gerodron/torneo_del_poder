// Peleador.java
package Model.Peleador;

/**
 * Clase que representa a un peleador en el torneo de artes marciales.
 */
public class Peleador_BDO {
    public int getIdPeleador() {
        return idPeleador;
    }

    public void setIdPeleador(int idPeleador) {
        this.idPeleador = idPeleador;
    }

    private int idPeleador;
    private String nombre;
    private String icono;
    private int edad;
    private int poder;

    /**
     * Constructor para crear un nuevo peleador.
     *
     * @param nombre Nombre del peleador.
     * @param edad   Edad del peleador.
     * @param poder  Poder del peleador.
     */
    public Peleador_BDO(String nombre, int edad, int poder, String icono) {
        this.nombre = nombre;
        this.edad = edad;
        this.poder = poder;
        this.icono = icono;
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getPoder() {
        return poder;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    @Override
    public String toString() {
        return "Peleador{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", poder=" + poder +
                '}';
    }
}
