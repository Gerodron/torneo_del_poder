package Model.Torneo;

import Model.Tools.Tool;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Competidor implements Serializable {
    private String idCompetidor;
    private String nombre;
    private String poder;
    private String icono;
    private String edad;
    private  int contador = 1;

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getIdCompetidor() {
        return idCompetidor;
    }

    public String getPoder() {
        return poder;
    }

    public void setPoder(String poder) {
        this.poder = poder;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setIdCompetidor() {
        this.idCompetidor = Tool.generarIDUnico(nombre);
    }

    public void setIdCompetidor(String idCompetidor) {
        this.idCompetidor = idCompetidor;
    }

}
