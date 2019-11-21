package fi.academy.dao;

import fi.academy.Tehtava;

import java.sql.SQLException;
import java.util.List;

public interface TehtavaDaoInterface {
    List<Tehtava> naytaTehtavat();
    Tehtava naytaTehtava(int id);
    int lisaa (Tehtava t) throws SQLException;
    void poista(int id) throws SQLException;
    boolean muuta(int id, Tehtava t);

}
