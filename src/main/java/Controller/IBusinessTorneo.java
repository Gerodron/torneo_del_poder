package Controller;

import Model.Torneo.Competidor;

public interface IBusinessTorneo {
    boolean agregarCompetidor(Competidor competidor);
    String recorrerArbol(int tipoOperacion);
    void buscarCompetidor();
    void eliminarCompetidor();
    void modificarCompetidor();
    void informacionArbol();
    //void guardarArbol();
    void cargarArbol();

}
