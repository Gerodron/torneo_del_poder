
package Model.Torneo;

import Model.Batalla.Batalla_BDO;
import Model.NodoArbol;
import Model.Peleador.Peleador_BDO;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Clase que representa el torneo de artes marciales, gestionando el árbol de batallas.
 */
public class Torneo_BDO {
    private NodoArbol raiz;
    private Queue<NodoArbol> cola;

    /**
     * Constructor que inicializa el torneo con una lista de participantes.
     *
     * @param listaParticipantes Lista de peleadores participantes.
     */
    public Torneo_BDO(LinkedList<Peleador_BDO> listaParticipantes) {
        cola = new LinkedList<>();
        construirArbolInicial(listaParticipantes);
    }

    /**
     * Método para construir el árbol de enfrentamientos sin determinar ganadores.
     *
     * @param listaParticipantes Lista de peleadores participantes.
     */
    private void construirArbol(LinkedList<Peleador_BDO> listaParticipantes) {
        // Crear nodos hoja con las batallas iniciales
        while (listaParticipantes.size() >= 2) {
            Peleador_BDO p1 = listaParticipantes.poll();
            Peleador_BDO p2 = listaParticipantes.poll();
            Batalla_BDO batallaBDO = new Batalla_BDO(p1, p2);
            NodoArbol nodo = new NodoArbol(batallaBDO);
            cola.add(nodo);
        }

        // Si hay un número impar de participantes, avanzar al siguiente nivel
        if (!listaParticipantes.isEmpty()) {
            Peleador_BDO p = listaParticipantes.poll();
            Batalla_BDO batallaBDO = new Batalla_BDO(p, null); // Sin oponente
            NodoArbol nodo = new NodoArbol(batallaBDO);
            cola.add(nodo);
        }

        // Construir niveles superiores del árbol
        while (cola.size() > 1) {
            int tamañoActual = cola.size();
            for (int i = 0; i < tamañoActual / 2; i++) {
                NodoArbol izquierda = cola.poll();
                NodoArbol derecha = cola.poll();
                Batalla_BDO batallaBDO = new Batalla_BDO(null, null); // Ganadores asignados manualmente
                NodoArbol nodo = new NodoArbol(batallaBDO, izquierda, derecha);
                cola.add(nodo);
            }

            // Manejar si hay un número impar de nodos en la cola
            if (tamañoActual % 2 != 0) {
                NodoArbol ultimo = cola.poll();
                Batalla_BDO batallaBDO = new Batalla_BDO(null, null); // Sin oponente
                NodoArbol nodo = new NodoArbol(batallaBDO, ultimo, null);
                cola.add(nodo);
            }
        }

        // La raíz del árbol es el único nodo restante
        raiz = cola.poll();
    }

    public NodoArbol getRaiz() {
        return raiz;
    }

    // Métodos de traversal que retornan Strings para ser utilizados en la Vista

    /**
     * Obtiene las batallas en preorden.
     *
     * @return String con las batallas en preorden.
     */
    public String obtenerBatallasPreOrdenStr() {
        StringBuilder sb = new StringBuilder();
        obtenerPreOrdenStr(raiz, sb);
        return sb.toString();
    }

    private void obtenerPreOrdenStr(NodoArbol nodo, StringBuilder sb) {
        if (nodo == null) return;
        sb.append(nodo.getBatalla().getId()).append(": ")
                .append(nodo.getBatalla().getP1().getNombre()).append(" vs ")
                .append(nodo.getBatalla().getP2() != null ? nodo.getBatalla().getP2().getNombre() : "N/A")
                .append(" - Ganador: ")
                .append(nodo.getBatalla().getGanador() != null ? nodo.getBatalla().getGanador().getNombre() : "Por definir")
                .append("\n");
        obtenerPreOrdenStr(nodo.getIzquierda(), sb);
        obtenerPreOrdenStr(nodo.getDerecha(), sb);
    }

    /**
     * Obtiene las batallas en inorden.
     *
     * @return String con las batallas en inorden.
     */
    public String obtenerBatallasInOrdenStr() {
        StringBuilder sb = new StringBuilder();
        obtenerInOrdenStr(raiz, sb);
        return sb.toString();
    }

    private void obtenerInOrdenStr(NodoArbol nodo, StringBuilder sb) {
        if (nodo == null) return;
        obtenerInOrdenStr(nodo.getIzquierda(), sb);
        sb.append(nodo.getBatalla().getId()).append(": ")
                .append(nodo.getBatalla().getP1().getNombre()).append(" vs ")
                .append(nodo.getBatalla().getP2() != null ? nodo.getBatalla().getP2().getNombre() : "N/A")
                .append(" - Ganador: ")
                .append(nodo.getBatalla().getGanador() != null ? nodo.getBatalla().getGanador().getNombre() : "Por definir")
                .append("\n");
        obtenerInOrdenStr(nodo.getDerecha(), sb);
    }

    /**
     * Obtiene las batallas en postorden.
     *
     * @return String con las batallas en postorden.
     */
    public String obtenerBatallasPostOrdenStr() {
        StringBuilder sb = new StringBuilder();
        obtenerPostOrdenStr(raiz, sb);
        return sb.toString();
    }

    private void obtenerPostOrdenStr(NodoArbol nodo, StringBuilder sb) {
        if (nodo == null) return;
        obtenerPostOrdenStr(nodo.getIzquierda(), sb);
        obtenerPostOrdenStr(nodo.getDerecha(), sb);
        sb.append(nodo.getBatalla().getId()).append(": ")
                .append(nodo.getBatalla().getP1().getNombre()).append(" vs ")
                .append(nodo.getBatalla().getP2() != null ? nodo.getBatalla().getP2().getNombre() : "N/A")
                .append(" - Ganador: ")
                .append(nodo.getBatalla().getGanador() != null ? nodo.getBatalla().getGanador().getNombre() : "Por definir")
                .append("\n");
    }

    /**
     * Asigna un ganador a una batalla específica y actualiza las batallas superiores.
     *
     * @param id      ID de la batalla.
     * @param ganador Peleador ganador de la batalla.
     * @return true si la asignación fue exitosa, false si no se encontró la batalla o el ganador es inválido.
     */
    public boolean asignarGanador(int id, Peleador_BDO ganador) {
        NodoArbol nodo = buscarNodoPorId(raiz, id);
        if (nodo == null) {
            System.out.println("Batalla con ID " + id + " no encontrada.");
            return false;
        }

        Batalla_BDO batallaBDO = nodo.getBatalla();
        // Validar que el ganador sea uno de los peleadores
        if (batallaBDO.getP1() == null) {
            System.out.println("Batalla con ID " + id + " no tiene Peleador 1 asignado.");
            return false;
        }

        if (batallaBDO.getP2() != null && !ganador.equals(batallaBDO.getP1()) && !ganador.equals(batallaBDO.getP2())) {
            System.out.println("Ganador inválido para la batalla con ID " + id + ".");
            return false;
        }

        // Asignar el ganador
        batallaBDO.setGanador(ganador);
        System.out.println("Ganador asignado: " + ganador.getNombre() + " a la Batalla ID " + id);

        // Actualizar las batallas superiores
        actualizarBatallasSuperiores(nodo);

        return true;
    }

    /**
     * Busca un nodo en el árbol por su ID.
     *
     * @param nodo Nodo actual en la búsqueda.
     * @param id   ID de la batalla a buscar.
     * @return NodoArbol si se encuentra, null de lo contrario.
     */
    private NodoArbol buscarNodoPorId(NodoArbol nodo, int id) {
        if (nodo == null) return null;
        if (nodo.getBatalla().getId() == id) return nodo;
        NodoArbol encontrado = buscarNodoPorId(nodo.getIzquierda(), id);
        if (encontrado != null) return encontrado;
        return buscarNodoPorId(nodo.getDerecha(), id);
    }

    /**
     * Busca un peleador por su nombre en el árbol de batallas.
     *
     * @param nombre Nombre del peleador a buscar.
     * @return Batalla donde el peleador está involucrado, o null si no se encuentra.
     */
    public Batalla_BDO buscarPeleadorPorNombre(String nombre) {
        return buscarPeleadorPorNombreRecursivo(raiz, nombre);
    }

    private Batalla_BDO buscarPeleadorPorNombreRecursivo(NodoArbol nodo, String nombre) {
        if (nodo == null) return null;
        if (nodo.getBatalla().getP1().getNombre().equalsIgnoreCase(nombre) ||
                (nodo.getBatalla().getP2() != null && nodo.getBatalla().getP2().getNombre().equalsIgnoreCase(nombre))) {
            return nodo.getBatalla();
        }
        Batalla_BDO encontrado = buscarPeleadorPorNombreRecursivo(nodo.getIzquierda(), nombre);
        if (encontrado != null) return encontrado;
        return buscarPeleadorPorNombreRecursivo(nodo.getDerecha(), nombre);
    }

    /**
     * Obtiene la altura del árbol de batallas.
     *
     * @return Altura del árbol.
     */
    public int obtenerAltura() {
        return obtenerAlturaRecursivo(raiz);
    }

    private int obtenerAlturaRecursivo(NodoArbol nodo) {
        if (nodo == null) return 0;
        int alturaIzquierda = obtenerAlturaRecursivo(nodo.getIzquierda());
        int alturaDerecha = obtenerAlturaRecursivo(nodo.getDerecha());
        return Math.max(alturaIzquierda, alturaDerecha) + 1;
    }

    /**
     * Obtiene el número total de batallas en el árbol.
     *
     * @return Número de batallas.
     */
    public int obtenerPeso() {
        return obtenerPesoRecursivo(raiz);
    }

    private int obtenerPesoRecursivo(NodoArbol nodo) {
        if (nodo == null) return 0;
        return 1 + obtenerPesoRecursivo(nodo.getIzquierda()) + obtenerPesoRecursivo(nodo.getDerecha());
    }

    /**
     * Actualiza los peleadores de todas las batallas superiores que dependen del nodo dado.
     *
     * @param nodo Nodo desde el cual iniciar la actualización hacia arriba.
     */
    public void actualizarBatallasSuperiores(NodoArbol nodo) {
        NodoArbol padre = nodo.getPadre();
        while (padre != null) {
            // Actualizar peleadores en la batalla padre
            padre.actualizarPeleadores();
            padre = padre.getPadre();
        }
    }

    /**
     * Inserta un nodo en el árbol siguiendo un orden de nivel (BFS), asignando primero a la izquierda y luego a la derecha.
     *
     * @param nuevoNodo NodoArbol a insertar.
     */
    public void insertarNodo(NodoArbol nuevoNodo) {
        if (raiz == null) {
            raiz = nuevoNodo;
            return;
        }

        Queue<NodoArbol> queue = new LinkedList<>();
        queue.add(raiz);

        while (!queue.isEmpty()) {
            NodoArbol actual = queue.poll();

            if (actual.getIzquierda() == null) {
                actual.setIzquierda(nuevoNodo);
                return;
            } else {
                queue.add(actual.getIzquierda());
            }

            if (actual.getDerecha() == null) {
                actual.setDerecha(nuevoNodo);
                return;
            } else {
                queue.add(actual.getDerecha());
            }
        }
    }

    /**
     * Construye el árbol inicial con los participantes proporcionados.
     *
     * @param listaParticipantes Lista de peleadores participantes.
     */
    private void construirArbolInicial(LinkedList<Peleador_BDO> listaParticipantes) {
        while (listaParticipantes.size() >= 2) {
            Peleador_BDO p1 = listaParticipantes.poll();
            Peleador_BDO p2 = listaParticipantes.poll();
            Batalla_BDO batallaBDO = new Batalla_BDO(p1, p2);
            NodoArbol nodo = new NodoArbol(batallaBDO);
            insertarNodo(nodo);
        }

        // Manejar un número impar de participantes
        if (!listaParticipantes.isEmpty()) {
            Peleador_BDO p = listaParticipantes.poll();
            Batalla_BDO batallaBDO = new Batalla_BDO(p, null); // Sin oponente
            NodoArbol nodo = new NodoArbol(batallaBDO);
            insertarNodo(nodo);
        }
    }
}
