package org.example;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class OdontologoDAOH2 implements OdontologoDAO {
    private static final Logger logger = Logger.getLogger(OdontologoDAOH2.class);

    protected Connection getConnection() throws Exception {
        // Usar un alias global para asegurar que todas las conexiones usen la misma instancia de la base de datos
        return DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
    }

    @Override
    public void guardar(Odontologo odontologo) {
        String sql = "INSERT INTO odontologos (matricula, nombre, apellido) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, odontologo.getMatricula());
            ps.setString(2, odontologo.getNombre());
            ps.setString(3, odontologo.getApellido());
            ps.executeUpdate();
            logger.info("Odontólogo guardado: " + odontologo);
        } catch (Exception e) {
            logger.error("Error guardando odontólogo", e);
        }
    }

    @Override
    public List<Odontologo> listarTodos() {
        List<Odontologo> odontologos = new ArrayList<>();
        String sql = "SELECT * FROM odontologos";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Odontologo odontologo = new Odontologo(
                        rs.getInt("matricula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"));
                odontologo.setMatricula(rs.getInt("matricula"));
                odontologo.setNombre(rs.getString("nombre"));
                odontologo.setApellido(rs.getString("apellido"));
                odontologos.add(odontologo);
            }
            logger.info("Listado de odontólogos: " + odontologos);
        } catch (Exception e) {
            logger.error("Error listando odontólogos", e);
        }
        return odontologos;
    }
    }
