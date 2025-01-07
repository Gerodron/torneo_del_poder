package Model.Peleador;

import Model.Database.ConnectionDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Peleador_DAO {
    private ConnectionDatabase connectionDB;

    public Peleador_DAO() {
        connectionDB = new ConnectionDatabase();
    }

    public int insertarPeleador(Peleador_BDO peleador) {
        String sql = "INSERT INTO Peleador (nombre, icono, edad, poder) VALUES (?, ?, ?, ?, ?);";
        try {
            Connection conn = connectionDB.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, peleador.getNombre());
            //pstmt.setString(2, peleador.getIcono());
            pstmt.setInt(3, peleador.getEdad());
            pstmt.setInt(4, peleador.getPoder());
            //pstmt.setString(5, peleador.getEspecialAttack());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La inserción del peleador falló, no se pudo obtener el ID.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    peleador.setIdPeleador(id); // Asumiendo que tienes un setter para el ID
                    return id;
                } else {
                    throw new SQLException("La inserción del peleador falló, no se pudo obtener el ID.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
