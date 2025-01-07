// Batalla.java
package Model.Batalla;

import Model.Peleador.Peleador_BDO;

/**
 * Clase que representa una batalla entre dos peleadores en el torneo.
 */
public class Batalla_BDO {
    private static int contador = 0; // Contador estático para IDs únicos
    private int id;
    private Peleador_BDO p1;
    private Peleador_BDO p2;
    private Peleador_BDO ganador;

    /**
     * Constructor para crear una batalla entre dos peleadores.
     *
     * @param p1 Peleador 1.
     * @param p2 Peleador 2 (puede ser null si no hay oponente).
     */
    public Batalla_BDO(Peleador_BDO p1, Peleador_BDO p2) {
        this.id = ++contador;
        this.p1 = p1;
        this.p2 = p2;
        this.ganador = null; // Ganador no definido inicialmente
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public Peleador_BDO getP1() {
        return p1;
    }

    public void setP1(Peleador_BDO p1) {
        this.p1 = p1;
    }

    public Peleador_BDO getP2() {
        return p2;
    }

    public void setP2(Peleador_BDO p2) {
        this.p2 = p2;
    }

    public Peleador_BDO getGanador() {
        return ganador;
    }

    public void setGanador(Peleador_BDO ganador) {
        this.ganador = ganador;
    }

    @Override
    public String toString() {
        return "Batalla{" +
                "id=" + id +
                ", p1=" + (p1 != null ? p1.getNombre() : "N/A") +
                ", p2=" + (p2 != null ? p2.getNombre() : "N/A") +
                ", ganador=" + (ganador != null ? ganador.getNombre() : "Por definir") +
                '}';
    }
}
