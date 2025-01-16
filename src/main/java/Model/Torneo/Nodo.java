package Model.Torneo;

import java.io.Serializable;

public class Nodo implements Serializable{
    private Object valor;
    private Nodo izquierdo, derecho;

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public Nodo getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(Nodo izquierdo) {
        this.izquierdo = izquierdo;
    }

    public Nodo getDerecho() {
        return derecho;
    }

    public void setDerecho(Nodo derecho) {
        this.derecho = derecho;
    }
    public Nodo(Object valor) {
        this.valor = valor;
    }
}
