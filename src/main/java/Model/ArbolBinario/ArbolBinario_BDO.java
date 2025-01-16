package Model.ArbolBinario;

import Model.Torneo.Nodo;

import java.io.Serializable;

public class ArbolBinario_BDO implements Serializable {
    Nodo raiz;

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
}
