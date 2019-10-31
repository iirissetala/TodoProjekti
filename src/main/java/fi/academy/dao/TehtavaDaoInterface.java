package fi.academy.dao;

import fi.academy.Tehtava;

import java.sql.SQLException;
import java.util.List;

public interface TehtavaDaoInterface {
    List<Tehtava> naytaTehtavat();
    int lisaa (Tehtava t) throws SQLException;
    void poista(int id) throws SQLException;
    boolean muuta(int id, Tehtava t);

}
