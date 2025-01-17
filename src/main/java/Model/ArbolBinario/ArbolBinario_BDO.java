package Model.ArbolBinario;

import Model.Tools.SessionHelper;
import Model.Torneo.Competidor;
import Model.Torneo.Nodo;

import java.io.Serializable;

public class ArbolBinario_BDO implements Serializable {
    Nodo raiz;

    public Nodo getRaiz() {
        return raiz;
    }

    public boolean agregar(Object dato) {
        Nodo nuevo = new Nodo(dato);
        if (raiz == null) {
            raiz = nuevo;
            return true; // Inserción exitosa al asignar la raíz
        }

        int altura = calcularAltura(raiz);
        for (int nivel = 1; nivel <= altura; nivel++) {
            if (insertarEnNivel(raiz, nuevo, nivel, 1)) {
                return true; // Inserción exitosa en el nivel actual
            }
        }
        // Intentar insertar en el siguiente nivel
        return insertarEnNivel(raiz, nuevo, altura + 1, 1);

    }

    private int calcularAltura(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        int alturaIzq = calcularAltura(nodo.getIzquierdo());
        int alturaDer = calcularAltura(nodo.getDerecho());
        return Math.max(alturaIzq, alturaDer) + 1;
    }

    private boolean insertarEnNivel(Nodo actual, Nodo nuevo, int targetLevel, int currentLevel) {
        if (actual == null) {
            return false;
        }
        if (currentLevel == targetLevel - 1) {
            if (actual.getIzquierdo() == null) {
                actual.setIzquierdo(nuevo);
                return true;
            } else if (actual.getDerecho() == null) {
                actual.setDerecho(nuevo);
                return true;
            }
            return false;
        }
        boolean insertadoIzq = insertarEnNivel(actual.getIzquierdo(), nuevo, targetLevel, currentLevel + 1);
        if (insertadoIzq) return true;
        boolean insertadoDer = insertarEnNivel(actual.getDerecho(), nuevo, targetLevel, currentLevel + 1);
        return insertadoDer;
    }

    public Nodo buscar(String idCompentidor) {
        return buscarRec(raiz, idCompentidor);
    }

    public Nodo buscarRec(Nodo nodo, String idCompentidor) {
        if (nodo == null) return null;
        Competidor nodoCompetidor = (Competidor) nodo.getValor();
        if (nodoCompetidor.getIdCompetidor().equals(idCompentidor)) {
            return nodo;
        }
        Nodo encontrado = buscarRec(nodo.getIzquierdo(), idCompentidor);
        if (encontrado == null) {
            encontrado = buscarRec(nodo.getDerecho(), idCompentidor);
        }
        return encontrado;
    }

    public String obtenerPreOrden() {
        StringBuilder recorrido = new StringBuilder();
        preOrdenRec(raiz, recorrido);
        return recorrido.toString().trim();
    }
    private void preOrdenRec(Nodo nodo, StringBuilder recorrido) {
        if (nodo == null) return;
        Competidor nodoCompetidor = (Competidor) nodo.getValor();
        recorrido.append(nodoCompetidor.getNombre()).append(" ");
        preOrdenRec(nodo.getIzquierdo(), recorrido);
        preOrdenRec(nodo.getDerecho(), recorrido);
    }

    public String obtenerInOrden() {
        StringBuilder recorrido = new StringBuilder();
        inOrdenRec(raiz, recorrido);
        return recorrido.toString().trim();
    }

    private void inOrdenRec(Nodo nodo, StringBuilder recorrido) {
        if (nodo == null) return;
        inOrdenRec(nodo.getIzquierdo(), recorrido);
        Competidor nodoCompetidor = (Competidor)nodo.getValor();
        recorrido.append(nodoCompetidor.getNombre()).append(" ");
        inOrdenRec(nodo.getDerecho(), recorrido);
    }

    public String obtenerPostOrden() {
        StringBuilder recorrido = new StringBuilder();
        postOrdenRec(raiz, recorrido);
        return recorrido.toString().trim();
    }

    private void postOrdenRec(Nodo nodo, StringBuilder recorrido) {
        if (nodo == null) return;
        Competidor nodoCompetidor = (Competidor) nodo.getValor();
        postOrdenRec(nodo.getIzquierdo(), recorrido);
        postOrdenRec(nodo.getDerecho(), recorrido);
        recorrido.append(nodoCompetidor.getNombre()).append(" ");
    }
}
