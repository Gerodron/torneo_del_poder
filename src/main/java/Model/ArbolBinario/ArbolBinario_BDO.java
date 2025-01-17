package Model.ArbolBinario;

import Model.Tools.SessionHelper;
import Model.Torneo.Competidor;
import Model.Torneo.Nodo;

import javax.swing.*;
import java.awt.*;
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



    // Método para dibujar el árbol con nodos personalizados
    public void drawTree(Graphics g, Nodo nodo, int x, int y, int deltaX) {
        if (nodo != null) {
            Competidor nodoCompetidor = (Competidor) nodo.getValor();

            // Cambiar color de los nodos
            g.setColor(Color.WHITE); // Color del nodo (puedes cambiar a cualquier color)

            // Cambiar forma del nodo: En este caso, un rectángulo
            g.fillRect(x, y, 40, 40);  // Cambié de círculo a rectángulo (ancho, alto)
            g.setColor(Color.BLACK); // Establecer color del texto
            g.drawString(nodoCompetidor.getNombre(), x + 5, y + 25); // Mostrar el nombre del competidor

            // Dibujar las líneas entre nodos y sus hijos
            if (nodo.getIzquierdo() != null) {
                g.setColor(Color.BLACK); // Color de las líneas
                g.drawLine(x + 20, y + 40, x - deltaX + 20, y + 80); // Línea al hijo izquierdo
                drawTree(g, nodo.getIzquierdo(), x - deltaX, y + 80, deltaX / 2);
            }
            if (nodo.getDerecho() != null) {
                g.setColor(Color.BLACK); // Color de las líneas
                g.drawLine(x + 20, y + 40, x + deltaX + 20, y + 80); // Línea al hijo derecho
                drawTree(g, nodo.getDerecho(), x + deltaX, y + 80, deltaX / 2);
            }
        }
    }


    public class ArbolBinarioPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Llamar al método de dibujo con la raíz del árbol y valores de posición inicial
            drawTree(g, raiz, 300, 50, 100);
        }
    }

    public void mostrarArbol() {
        JFrame frame = new JFrame("Árbol Binario");
        ArbolBinarioPanel panel = new ArbolBinarioPanel();
        frame.add(panel);
        frame.setSize(600, 600);  // Tamaño de la ventana
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
