package org.example;

import java.util.List;

public class MainCollection {
    public static void main(String[] args) {
        OdontologoDAO odontologoDAO = new OdontologoDAOCollection();

        Odontologo odontologo1 = new Odontologo(101, "Juan", "Pérez");
        Odontologo odontologo2 = new Odontologo(102, "Ana", "Gómez");

        odontologoDAO.guardar(odontologo1);
        odontologoDAO.guardar(odontologo2);

        List<Odontologo> odontologos = odontologoDAO.listarTodos();
        for (Odontologo o : odontologos) {
            System.out.println(o.getMatricula() + " - " + o.getNombre() + " " + o.getApellido());
        }
    }
}
