// ConnectionDatabase.java
package Model.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos MySQL.
 */
public class ConnectionDatabase {
    private String database = "TorneoPoder";
    private String host = "autorack.proxy.rlwy.net";
    private String port = "15062";
    private String user = "root";          // Usuario predeterminado
    private String password = "AJhhFqzkQwmrsWdOpnrAjsOTwNGFtKzm";    // Contraseña predeterminada
    private String drive = "com.mysql.cj.jdbc.Driver"; // Controlador corregido
    private String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&serverTimezone=UTC";
    
    private Connection ctx;

    /**
     * Constructor vacío que usa las credenciales predeterminadas.
     */
    public ConnectionDatabase() {
        // Puedes dejarlo vacío o inicializar valores predeterminados si lo deseas.
    }

    /**
     * Constructor que permite establecer usuario y contraseña personalizados.
     *
     * @param user     Nombre de usuario para la base de datos.
     * @param password Contraseña para la base de datos.
     */
    public ConnectionDatabase(String user, String password) {
        this.user = user;
        this.password = password;
    }

    /**
     * Método para establecer la conexión a la base de datos.
     *
     * @return Objeto Connection si la conexión es exitosa.
     */
    public Connection openConnection() {
        try {
            // Cargar el controlador JDBC
            Class.forName(this.drive);
            
            // Establecer la conexión
            ctx = DriverManager.getConnection(url, user, password);
            System.out.println("CONEXIÓN ESTABLECIDA");
            return ctx;
            
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: No se encontró el controlador JDBC.");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("ERROR: Falló la conexión a la base de datos.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para cerrar la conexión a la base de datos.
     */
    public void closeConnection() {
        if (ctx != null) {
            try {
                ctx.close();
                System.out.println("CONEXIÓN CERRADA");
            } catch (SQLException e) {
                System.out.println("ERROR: Falló al cerrar la conexión.");
                e.printStackTrace();
            }
        }
    }

    // Getters y Setters para permitir cambiar las credenciales si es necesario

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
