// BusinessTorneo.java
package Controller;

import Model.Peleador.Peleador_BDO;
import Model.Batalla.Batalla_BDO;
import Model.NodoArbol;
import Model.Torneo.Torneo_BDO;
import Model.Catalogo.Catalogo_DAO;
import java.util.LinkedList;


/**
 * Clase que implementa la lógica del negocio para gestionar el torneo.
 */
public class BusinessTorneo implements IBusinessTorneo {

    private Torneo_BDO torneo;


    /**
     * Constructor que inicializa el torneo con una lista de participantes.
     *
     * @param listaParticipantes Lista de peleadores participantes.
     */
    public BusinessTorneo(LinkedList<Peleador_BDO> listaParticipantes) {
        this.torneo = new Torneo_BDO(listaParticipantes);
    }

    /**
     * Agrega un nuevo combate al torneo.
     *
     * @param p1 Peleador 1.
     * @param p2 Peleador 2.
     */
    @Override
    public void agregaNuevoCombate(Peleador_BDO p1, Peleador_BDO p2) {
        System.out.println("agregaNuevoCombate: CHECK");
        Batalla_BDO batallaBDO = new Batalla_BDO(p1, p2);
        NodoArbol nuevoNodo = new NodoArbol(batallaBDO);
        torneo.insertarNodo(nuevoNodo);


    }

    /**
     * Elimina una batalla del torneo por su ID.
     *
     * @param id ID de la batalla a eliminar.
     */
    @Override
    public void eliminarCombatePorId(int id) {
        System.out.println("eliminarCombatePorId: CHECK");
        NodoArbol nodo = buscarNodoPorId(torneo.getRaiz(), id);
        if (nodo == null) {
            System.out.println("Batalla con ID " + id + " no encontrada.");
            return;
        }

        eliminarNodo(torneo.getRaiz(), nodo);
        System.out.println("Batalla con ID " + id + " eliminada.");

        // Re-construir el árbol después de la eliminación
        LinkedList<Peleador_BDO> listaActual = new LinkedList<>();
        recolectarParticipantes(torneo.getRaiz(), listaActual);
        torneo = new Torneo_BDO(listaActual);
    }

    /**
     * Edita una batalla existente por su ID.
     *
     * @param id ID de la batalla a editar.
     * @param nuevoP1 Nuevo Peleador 1.
     * @param nuevoP2 Nuevo Peleador 2.
     */
    @Override
    public void editarCombatePorId(int id, Peleador_BDO nuevoP1, Peleador_BDO nuevoP2) {
        System.out.println("editarCombatePorId: CHECK");
        NodoArbol nodo = buscarNodoPorId(torneo.getRaiz(), id);
        if (nodo == null) {
            System.out.println("Batalla con ID " + id + " no encontrada.");
            return;
        }

        // Editar los peleadores
        nodo.getBatalla().setP1(nuevoP1);
        nodo.getBatalla().setP2(nuevoP2);
        nodo.getBatalla().setGanador(null); // Reiniciar el ganador si los peleadores cambian

        System.out.println("Batalla con ID " + id + " editada exitosamente.");

        // Actualizar las batallas superiores si es necesario
        torneo.actualizarBatallasSuperiores(nodo);
    }

    /**
     * Obtiene las batallas en preorden.
     *
     * @return String con las batallas en preorden.
     */
    @Override
    public String obtenerCombates_PreOrden() {
        System.out.println("obtenerCombates_PreOrden: CHECK");
        return torneo.obtenerBatallasPreOrdenStr();
    }

    /**
     * Obtiene las batallas en inorden.
     *
     * @return String con las batallas en inorden.
     */
    @Override
    public String obtenerCombates_InOrden() {
        System.out.println("obtenerCombates_InOrden: CHECK");
        return torneo.obtenerBatallasInOrdenStr();
    }

    /**
     * Obtiene las batallas en postorden.
     *
     * @return String con las batallas en postorden.
     */
    @Override
    public String obtenerCombates_PostOrden() {
        System.out.println("obtenerCombates_PostOrden: CHECK");
        return torneo.obtenerBatallasPostOrdenStr();
    }

    /**
     * Asigna un ganador a una batalla específica.
     *
     * @param id ID de la batalla.
     * @param ganador Peleador ganador de la batalla.
     */
    @Override
    public void asignarGanador(int id, Peleador_BDO ganador) {
        torneo.asignarGanador(id, ganador);
    }

    /**
     * Recolecta los participantes actuales basándose en los ganadores
     * asignados.
     *
     * @param nodo Nodo actual en el árbol.
     * @param lista Lista donde se recolectan los ganadores.
     */
    private void recolectarParticipantes(NodoArbol nodo, LinkedList<Peleador_BDO> lista) {
        if (nodo == null) {
            return;
        }
        recolectarParticipantes(nodo.getIzquierda(), lista);
        recolectarParticipantes(nodo.getDerecha(), lista);
        Peleador_BDO ganador = nodo.getBatalla().getGanador();
        if (ganador != null) {
            lista.add(ganador);
        }
    }

    /**
     * Busca un nodo en el árbol por su ID.
     *
     * @param nodo Nodo actual en la búsqueda.
     * @param id ID de la batalla a buscar.
     * @return NodoArbol si se encuentra, null de lo contrario.
     */
    private NodoArbol buscarNodoPorId(NodoArbol nodo, int id) {
        if (nodo == null) {
            return null;
        }
        if (nodo.getBatalla().getId() == id) {
            return nodo;
        }
        NodoArbol encontrado = buscarNodoPorId(nodo.getIzquierda(), id);
        if (encontrado != null) {
            return encontrado;
        }
        return buscarNodoPorId(nodo.getDerecha(), id);
    }

    /**
     * Elimina un nodo del árbol.
     *
     * @param raiz Nodo raíz del árbol.
     * @param nodoEliminar Nodo a eliminar.
     */
    private void eliminarNodo(NodoArbol raiz, NodoArbol nodoEliminar) {
        if (raiz == null) {
            return;
        }

        if (raiz.getIzquierda() == nodoEliminar || raiz.getDerecha() == nodoEliminar) {
            // Eliminar la referencia en el padre
            if (raiz.getIzquierda() == nodoEliminar) {
                raiz.setIzquierda(null);
            } else {
                raiz.setDerecha(null);
            }
        } else {
            eliminarNodo(raiz.getIzquierda(), nodoEliminar);
            eliminarNodo(raiz.getDerecha(), nodoEliminar);
        }
    }
}
