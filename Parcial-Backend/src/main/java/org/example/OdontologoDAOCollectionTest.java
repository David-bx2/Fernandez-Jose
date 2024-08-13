package org.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OdontologoDAOCollectionTest {
    private OdontologoDAO odontologoDAO;

    @Before
    public void setUp() {
        odontologoDAO = new OdontologoDAOCollection();
        odontologoDAO.guardar(new Odontologo(1, "Juan", "Pérez"));
        odontologoDAO.guardar(new Odontologo(2, "Ana", "Gómez"));
    }

    @Test
    public void testListarTodos() {
        assertEquals(2, odontologoDAO.listarTodos().size());
    }
}
