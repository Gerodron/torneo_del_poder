// NodoArbol.java
package Model;

import Model.Batalla.Batalla_BDO;

/**
 * Clase que representa un nodo en el árbol de enfrentamientos.
 * Cada nodo contiene una Batalla y referencias a sus nodos hijos.
 */
public class NodoArbol {
    private Batalla_BDO batallaBDO;         // Batalla asociada a este nodo
    private NodoArbol izquierda;     // Hijo izquierdo (batalla previa para el Peleador 1)
    private NodoArbol derecha;       // Hijo derecho (batalla previa para el Peleador 2)
    private NodoArbol padre;         // Nodo padre

    /**
     * Constructor para nodos hoja (batallas iniciales sin batallas previas).
     *
     * @param batallaBDO La batalla asociada a este nodo.
     */
    public NodoArbol(Batalla_BDO batallaBDO) {
        this.batallaBDO = batallaBDO;
        this.izquierda = null;
        this.derecha = null;
        this.padre = null;
    }

    /**
     * Constructor para nodos internos (batallas posteriores con batallas previas).
     *
     * @param batallaBDO    La batalla asociada a este nodo.
     * @param izquierda  Nodo hijo izquierdo que determina el Peleador 1.
     * @param derecha    Nodo hijo derecho que determina el Peleador 2.
     */
    public NodoArbol(Batalla_BDO batallaBDO, NodoArbol izquierda, NodoArbol derecha) {
        this.batallaBDO = batallaBDO;
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.padre = null;
        if (izquierda != null) {
            izquierda.setPadre(this);
        }
        if (derecha != null) {
            derecha.setPadre(this);
        }
    }

    // Getters y Setters

    public Batalla_BDO getBatalla() {
        return batallaBDO;
    }

    public void setBatalla(Batalla_BDO batallaBDO) {
        this.batallaBDO = batallaBDO;
    }

    public NodoArbol getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoArbol izquierda) {
        this.izquierda = izquierda;
        if (izquierda != null) {
            izquierda.setPadre(this);
        }
    }

    public NodoArbol getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoArbol derecha) {
        this.derecha = derecha;
        if (derecha != null) {
            derecha.setPadre(this);
        }
    }

    public NodoArbol getPadre() {
        return padre;
    }

    public void setPadre(NodoArbol padre) {
        this.padre = padre;
    }

    /**
     * Verifica si este nodo es una hoja (no tiene hijos).
     *
     * @return true si es una hoja, false de lo contrario.
     */
    public boolean esHoja() {
        return izquierda == null && derecha == null;
    }

    /**
     * Actualiza los peleadores de esta batalla basándose en los ganadores de las batallas previas.
     * Este método debe ser llamado después de asignar los ganadores de las batallas inferiores.
     */
    public void actualizarPeleadores() {
        if (izquierda != null && izquierda.getBatalla().getGanador() != null) {
            batallaBDO.setP1(izquierda.getBatalla().getGanador());
        }
        if (derecha != null && derecha.getBatalla().getGanador() != null) {
            batallaBDO.setP2(derecha.getBatalla().getGanador());
        }
    }
}
