package Model.Catalogo;// Asegúrate de importar las clases necesarias
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Importa tu clase Catalogo_BDO (ajusta el paquete según tu estructura)
import Model.Catalogo.Catalogo_BDO;

// Asegúrate de que la clase ConnectionDatabase esté en el paquete correcto y
// que implemente AutoCloseable si planeas usarla en try-with-resources
import Model.Database.ConnectionDatabase;

public class Catalogo_DAO {

    /**
     * Método para obtener una lista de Catalogo_BDO filtrada por nombre y codigo.
     *
     * @param nombre Nombre del catálogo a filtrar.
     * @return Lista de Catalogo_BDO que coinciden con los criterios.
     */
    public List<Catalogo_BDO> getCatalogos(String nombre) {
        List<Catalogo_BDO> catalogos = new ArrayList<>();

        // Definir la consulta SQL con parámetros
        String sql = "SELECT * FROM Catalogo WHERE nombre = ?";

        // Usar try-with-resources para asegurar el cierre de recursos
        try {
            ConnectionDatabase connectionDB = new ConnectionDatabase(); // Ajusta usuario y contraseña
            Connection conn = connectionDB.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Establecer los parámetros en el PreparedStatement
            pstmt.setString(1, nombre);

            // Ejecutar la consulta
            try (ResultSet rs = pstmt.executeQuery()) {
                // Procesar el ResultSet
                while (rs.next()) {
                    // Asumiendo que Catalogo_BDO tiene un constructor adecuado o setters
                    Catalogo_BDO catalogo = new Catalogo_BDO();
                    catalogo.setId(rs.getInt("id")); // Ajusta según los nombres de columnas
                    catalogo.setNombre(rs.getString("nombre"));
                    catalogo.setCodigo(rs.getString("codigo"));
                    catalogo.setDescripcion(rs.getString("descripcion"));
                    catalogo.setComplemento(rs.getString("complemento"));
                    // Añade más campos según tu tabla

                    catalogos.add(catalogo);
                }

            }

        } catch (SQLException e) {
            System.err.println("ERROR: Falló la consulta a la tabla 'catalogo'.");
            e.printStackTrace();
            // Puedes optar por manejar la excepción de otra manera según tu diseño
        }

        return catalogos;
    }
}
