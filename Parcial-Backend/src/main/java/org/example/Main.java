package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
            crearTablaSiNoExiste();


            OdontologoDAO odontologoDAO = new OdontologoDAOH2();

            Odontologo odontologo1 = new Odontologo(101, "Juan", "Pérez");
            Odontologo odontologo2 = new Odontologo(102, "Ana", "Gómez");

            odontologoDAO.guardar(odontologo1);
            odontologoDAO.guardar(odontologo2);

            List<Odontologo> odontologos = odontologoDAO.listarTodos();
            for (Odontologo o : odontologos) {
                System.out.println(o.getMatricula() + " - " + o.getNombre() + " " + o.getApellido());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static void crearTablaSiNoExiste() throws Exception {
        String sql = "CREATE TABLE IF NOT EXISTS odontologos (" +
                "matricula INT PRIMARY KEY, " +
                "nombre VARCHAR(255) NOT NULL, " +
                "apellido VARCHAR(255) NOT NULL" +
                ");";
        try (Connection conn = DriverManager.getConnection("jdbc:h2:~/odontologos", "sa", "");
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'odontologos' verificada o creada.");
        }
    }
}