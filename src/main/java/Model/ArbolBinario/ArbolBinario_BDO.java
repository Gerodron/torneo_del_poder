package Model.ArbolBinario;

import Model.Tools.SessionHelper;
import Model.Torneo.Competidor;
import Model.Torneo.Nodo;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class ArbolBinario_BDO implements Serializable {
    Nodo raiz;
    private int idx = 0;

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

    public boolean eliminar(String idCompentidor) {
        if (raiz == null) {
            System.out.println("El árbol está vacío.");
            return false;
        }
        Nodo nodoAEliminar = buscar(idCompentidor);
        Competidor competidorEliminar = (Competidor) nodoAEliminar.getValor();
        if (nodoAEliminar == null) {
            System.out.println("Competidor no encontrado.");
            return false;
        }
        Nodo ultimo = obtenerUltimoNodo();
        Competidor competidorUltimo = (Competidor) ultimo.getValor();
        if (ultimo == null || ultimo == nodoAEliminar) {
            if (ultimo == raiz) {
                raiz = null;
            }
            return false;
        }
        competidorEliminar.setIdCompetidor(competidorEliminar.getIdCompetidor());
        competidorEliminar.setNombre(competidorEliminar.getNombre());
        competidorEliminar.setEdad(competidorEliminar.getEdad());
        competidorEliminar.setPoder(competidorEliminar.getPoder());
        competidorEliminar.setPoder(competidorEliminar.getPoder());
        eliminarUltimoNodo(ultimo);
        return true;
    }
    private void eliminarUltimoNodo(Nodo ultimo) {
        if (raiz == null) return;
        if (raiz == ultimo && raiz.getIzquierdo() == null && raiz.getDerecho() == null) {
            raiz = null;
            return;
        }
        int altura = calcularAltura(raiz);
        for (int nivel = 1; nivel <= altura; nivel++) {
            Nodo[] arrayNivel = obtenerNodosDeNivel(raiz, nivel, 1);
            for (Nodo n : arrayNivel) {
                if (n == null) break;
                if (n.getIzquierdo() == ultimo) {
                    n.setIzquierdo(null);
                    return;
                }
                if (n.getDerecho() == ultimo) {
                    n.setDerecho(null);
                    return;
                }
            }
        }
    }

    private Nodo obtenerUltimoNodo() {
        if (raiz == null) return null;
        int altura = calcularAltura(raiz);
        Nodo ultimo = null;
        for (int nivel = 1; nivel <= altura; nivel++) {
            Nodo[] arrayNivel = obtenerNodosDeNivel(raiz, nivel, 1);
            for (Nodo n : arrayNivel) {
                if (n != null) {
                    ultimo = n;
                }
            }
        }
        return ultimo;
    }
    private Nodo[] obtenerNodosDeNivel(Nodo actual, int targetLevel, int currentLevel) {
        Nodo[] nivel = new Nodo[100];
        idx = 0;
        llenarNivel(actual, targetLevel, currentLevel, nivel);
        return nivel;
    }
    private void llenarNivel(Nodo actual, int targetLevel, int currentLevel, Nodo[] nivel) {
        if (actual == null) {
            return;
        }
        if (currentLevel == targetLevel) {
            nivel[idx++] = actual;
            return;
        }
        llenarNivel(actual.getIzquierdo(), targetLevel, currentLevel + 1, nivel);
        llenarNivel(actual.getDerecho(), targetLevel, currentLevel + 1, nivel);
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
            Graphics2D g2 = (Graphics2D) g;

            // Habilitar anti-aliasing para mejor calidad gráfica
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Competidor nodoCompetidor = (Competidor) nodo.getValor();

            // Preparar el texto para mostrar más información
            ArrayList<Object> lineasTexto = new ArrayList<>();
            lineasTexto.add("ID: " + nodoCompetidor.getIdCompetidor());
            lineasTexto.add("Nombre: " + nodoCompetidor.getNombre());
            // Puedes agregar más información aquí, por ejemplo:
            // lineasTexto.add("ID: " + nodoCompetidor.getIdCompetidor());
            // lineasTexto.add("Otra Info: " + nodoCompetidor.getOtraInfo());

            // Calcular el tamaño del texto
            FontMetrics fm = g2.getFontMetrics();
            int anchoMaximo = 0;
            for (Object linea : lineasTexto) {
                int anchoLinea = fm.stringWidth(linea.toString());
                if (anchoLinea > anchoMaximo) {
                    anchoMaximo = anchoLinea;
                }
            }
            int alturaTexto = fm.getHeight() * lineasTexto.size();

            // Margen interno
            int margen = 10;

            // Tamaño del nodo
            int anchoNodo = anchoMaximo + margen * 2;
            int altoNodo = alturaTexto + margen * 2;

            // Dibujar el rectángulo del nodo
            g2.setColor(Color.WHITE); // Color del nodo
            g2.fillRect(x, y, anchoNodo, altoNodo);
            g2.setColor(Color.BLACK); // Borde del nodo
            g2.drawRect(x, y, anchoNodo, altoNodo);

            // Dibujar el texto dentro del nodo
            int textoY = y + margen + fm.getAscent();
            for (Object linea : lineasTexto) {
                g2.drawString(linea.toString(), x + margen, textoY);
                textoY += fm.getHeight();
            }

            // Dibujar las líneas hacia los hijos
            if (nodo.getIzquierdo() != null) {
                g2.setColor(Color.BLACK);
                // Calcular posiciones de conexión basadas en el tamaño del nodo
                int xPadreCentro = x + anchoNodo / 2;
                int yPadreFin = y + altoNodo;
                int xHijo = x - deltaX;
                int yHijoInicio = y + 80; // Ajusta según la distancia vertical entre niveles
                int xHijoCentro = xHijo + anchoNodo / 2;
                int yHijoFin = yHijoInicio;

                g2.drawLine(xPadreCentro, yPadreFin, xHijoCentro, yHijoFin);
                drawTree(g2, nodo.getIzquierdo(), x - deltaX, y + 80, deltaX / 2);
            }
            if (nodo.getDerecho() != null) {
                g2.setColor(Color.BLACK);
                // Calcular posiciones de conexión basadas en el tamaño del nodo
                int xPadreCentro = x + anchoNodo / 2;
                int yPadreFin = y + altoNodo;
                int xHijo = x + deltaX;
                int yHijoInicio = y + 80; // Ajusta según la distancia vertical entre niveles
                int xHijoCentro = xHijo + anchoNodo / 2;
                int yHijoFin = yHijoInicio;

                g2.drawLine(xPadreCentro, yPadreFin, xHijoCentro, yHijoFin);
                drawTree(g2, nodo.getDerecho(), x + deltaX, y + 80, deltaX / 2);
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
