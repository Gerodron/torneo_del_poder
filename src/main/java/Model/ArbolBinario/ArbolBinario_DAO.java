package Model.ArbolBinario;

import Model.Database.ConnectionDatabase;
import Model.Tools.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArbolBinario_DAO {
    public void guardarArbolBinario(ArbolBinario_BDO arbolBinario, String sessionAuth) {
        try {
            ConnectionDatabase connectionDB = new ConnectionDatabase();
            Connection conn = connectionDB.openConnection();

            if (existeRegistro(conn, sessionAuth)) {
                actualizarArbol(conn, arbolBinario, sessionAuth);
            } else {
                insertarArbol(conn, arbolBinario, sessionAuth);
            }
        } catch (Exception e) {
            System.out.println("guardarOActualizarArbolBinario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArbolBinario_BDO buscarArbolBinario(String sessionAuth) {
        String sql = "SELECT arbol_blob FROM Arbol_Torneo WHERE idSessionAuth = ? ORDER BY fecha_guardado DESC LIMIT 1";
        try {
            ConnectionDatabase connectionDB = new ConnectionDatabase();
            Connection conn = connectionDB.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sessionAuth);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    byte[] arbolBytes = rs.getBytes("arbol_blob");
                    Object obj = Tool.deserializarObjecto(arbolBytes);
                    if (obj instanceof ArbolBinario_BDO) {
                        System.out.println("Árbol obtenido exitosamente de la base de datos.");
                        return (ArbolBinario_BDO) obj;
                    } else {
                        System.out.println("El objeto obtenido no es una instancia de ArbolBinario_BDO.");
                    }
                } else {
                    System.out.println("No se encontró un árbol con el sessionAuth proporcionado.");
                }
            }
        } catch (Exception e) {
            System.out.println("buscarArbolBinario: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private boolean existeRegistro(Connection conn, String sessionAuth) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM Arbol_Torneo WHERE idSessionAuth = ?";
        PreparedStatement selectStmt = conn.prepareStatement(selectSql);
        selectStmt.setString(1, sessionAuth);
        ResultSet rs = selectStmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }

    private void actualizarArbol(Connection conn, ArbolBinario_BDO arbolBinario, String sessionAuth) throws Exception {
        String updateSql = "UPDATE Arbol_Torneo SET arbol_blob = ?, fecha_guardado = CURRENT_TIMESTAMP WHERE idSessionAuth = ?";
        PreparedStatement updateStmt = conn.prepareStatement(updateSql);
        byte[] arbolBytes = Tool.serializarObjecto(arbolBinario);
        updateStmt.setBytes(1, arbolBytes);
        updateStmt.setString(2, sessionAuth);
        int filasActualizadas = updateStmt.executeUpdate();
        if (filasActualizadas > 0) {
            System.out.println("Árbol actualizado exitosamente en la base de datos.");
        } else {
            System.out.println("No se pudo actualizar el árbol en la base de datos.");
        }
    }

    private void insertarArbol(Connection conn, ArbolBinario_BDO arbolBinario, String sessionAuth) throws Exception {
        String insertSql = "INSERT INTO Arbol_Torneo (nombre, arbol_blob, idSessionAuth, fecha_guardado) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        PreparedStatement insertStmt = conn.prepareStatement(insertSql);
        byte[] arbolBytes = Tool.serializarObjecto(arbolBinario);
        insertStmt.setString(1, "UG_SOFTWARE");
        insertStmt.setBytes(2, arbolBytes);
        insertStmt.setString(3, sessionAuth);
        int filasInsertadas = insertStmt.executeUpdate();
        if (filasInsertadas > 0) {
            System.out.println("Árbol guardado exitosamente en la base de datos.");
        } else {
            System.out.println("No se pudo guardar el árbol en la base de datos.");
        }
    }
}
