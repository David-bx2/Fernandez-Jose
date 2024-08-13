package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import org.apache.log4j.Logger;
import java.sql.ResultSet;
import java.util.List;

public class OdontologoDAOH2Test {
    private static final Logger logger = Logger.getLogger(OdontologoDAOH2Test.class);
    private OdontologoDAOH2 odontologoDAO;

    @Before
    public void setUp() throws Exception {
        // Crear una instancia de la subclase anónima de OdontologoDAOH2 para pruebas
        odontologoDAO = new OdontologoDAOH2() {
            @Override
            protected Connection getConnection() throws Exception {
                // Usar el mismo alias global para asegurar que las conexiones compartan la misma base de datos
                return DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
            }
        };

        // Crear la tabla para las pruebas
        crearTabla();
    }


    @After
    public void tearDown() throws Exception {
        // Limpiar la base de datos después de cada prueba
        eliminarTabla();
    }

    @Test
    public void testGuardarYListarTodos() {
        // Guardar algunos odontólogos en la base de datos
        odontologoDAO.guardar(new Odontologo(1, "Juan", "Pérez"));
        odontologoDAO.guardar(new Odontologo(2, "Ana", "Gómez"));

        // Listar todos los odontólogos y verificar
        List<Odontologo> odontologos = odontologoDAO.listarTodos();
        assertEquals(2, odontologos.size());
        assertEquals("Juan", odontologos.get(0).getNombre());
        assertEquals("Ana", odontologos.get(1).getNombre());
    }

    private void crearTabla() throws Exception {
        String sql = "CREATE TABLE IF NOT EXISTS odontologos (" +
                "matricula INT PRIMARY KEY, " +
                "nombre VARCHAR(255) NOT NULL, " +
                "apellido VARCHAR(255) NOT NULL" +
                ");";
        try (Connection conn = odontologoDAO.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logger.info("Tabla 'odontologos' creada o ya existía.");

            // Verificar si la tabla fue creada correctamente
            ResultSet rs = conn.getMetaData().getTables(null, null, "ODONTOLOGOS", null);
            if (rs.next()) {
                logger.info("Tabla 'odontologos' confirmada en la base de datos.");
            } else {
                logger.error("La tabla 'odontologos' no se pudo confirmar.");
            }
        }
    }

    private void eliminarTabla() throws Exception {
        String sql = "DROP TABLE IF EXISTS odontologos";
        try (Connection conn = odontologoDAO.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logger.info("Tabla 'odontologos' eliminada.");
        }
    }
}
