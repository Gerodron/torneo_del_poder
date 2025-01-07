// IBusinessTorneo.java
package Controller;

import Model.Peleador.Peleador_BDO;

/**
 * Interfaz que define las operaciones del negocio del torneo.
 */
public interface IBusinessTorneo {
    void agregaNuevoCombate(Peleador_BDO p1, Peleador_BDO p2);
    void eliminarCombatePorId(int id);
    void editarCombatePorId(int id, Peleador_BDO nuevoP1, Peleador_BDO nuevoP2);
    
    String obtenerCombates_PreOrden();
    String obtenerCombates_InOrden();
    String obtenerCombates_PostOrden();
    
    void asignarGanador(int id, Peleador_BDO ganador);
}
