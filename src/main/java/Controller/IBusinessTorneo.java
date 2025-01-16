package Controller;

import Model.Torneo.Competidor;

public interface IBusinessTorneo {
    boolean agregarCompetidor(Competidor competidor);
    void recorrerArbol();
    void buscarCompetidor();
    void eliminarCompetidor();
    void modificarCompetidor();
    void informacionArbol();
    //void guardarArbol();
    void cargarArbol();

}
