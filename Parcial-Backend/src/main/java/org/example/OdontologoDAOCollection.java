package org.example;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.example.Odontologo;
import org.example.OdontologoDAO;

public class OdontologoDAOCollection implements OdontologoDAO {
    private static final Logger logger = Logger.getLogger(OdontologoDAOCollection.class);
    private List<Odontologo> odontologos = new ArrayList<>();

    @Override
    public void guardar(Odontologo odontologo) {
        odontologos.add(odontologo);
        logger.info("Odontólogo guardado en memoria: " + odontologo);
    }

    @Override
    public List<Odontologo> listarTodos() {
        logger.info("Listado de odontólogos en memoria: " + odontologos);
        return odontologos;
    }
}
