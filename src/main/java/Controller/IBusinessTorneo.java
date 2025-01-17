package Controller;

import Model.Torneo.Competidor;

public interface IBusinessTorneo {
    boolean agregarCompetidor(Competidor competidor);
    String recorrerArbol(int tipoOperacion);
    Competidor buscarCompetidor(String idCompetidor);
    void eliminarCompetidor();
    boolean modificarCompetidor(String idCompetidor, String nombre, String poder, String edad);
    void informacionArbol();
    //void guardarArbol();
    void cargarArbol();

}
