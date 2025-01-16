package Controller;

import Model.ArbolBinario.ArbolBinario_BDO;
import Model.ArbolBinario.ArbolBinario_DAO;
import Model.Tools.SessionHelper;
import Model.Torneo.Competidor;

public class BusinessTorneo implements IBusinessTorneo {
    private ArbolBinario_BDO _arbolBinarioBDO;

    public BusinessTorneo(){
        _arbolBinarioBDO = new ArbolBinario_BDO();
    }

    public boolean agregarCompetidor(Competidor competidor) {
        var result =  _arbolBinarioBDO.agregar(competidor);
        guardarArbol(_arbolBinarioBDO);
        return  result;
    }

    public void recorrerArbol() {

    }

    public void buscarCompetidor() {

    }

    public void eliminarCompetidor() {

    }

    public void modificarCompetidor() {

    }

    public void informacionArbol() {

    }

    public void guardarArbol(ArbolBinario_BDO arbolBinarioBDO) {
        ArbolBinario_DAO arbolBinario_DAO = new ArbolBinario_DAO();
        arbolBinario_DAO.guardarArbolBinario(arbolBinarioBDO, SessionHelper.getSessionAUTH());
    }

    public ArbolBinario_BDO obtenerArbol() {
        ArbolBinario_DAO arbolBinario_DAO = new ArbolBinario_DAO();
        return arbolBinario_DAO.buscarArbolBinario(SessionHelper.getSessionAUTH());
    }

    public void cargarArbol() {

    }
}
