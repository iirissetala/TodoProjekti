package fi.academy.dao;

import fi.academy.Tehtava;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.*;
import java.util.*;

@Service
//tämä kertoo controllerille, että on käytettävissä (Service tai Component mahdollisia
//jos luokka on Component, controlleriin autowired kun sitä käytetään, jolla injektoi sinne
public class TehtavaDao implements TehtavaDaoInterface {
    private Connection con;
    private List<Tehtava> haetut;

    public TehtavaDao() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tehtavat", "oppilas", "salasana");
            System.out.println("Yhteys saatu");
    }

    @Override
    public List<Tehtava> naytaTehtavat (){
        String sql = "SELECT * FROM tehtava"; //no data sources are configured to run this SQL and provide advanced code assistance
        List<Tehtava> haetut = new ArrayList<>();
        try (PreparedStatement pstm = con.prepareStatement(sql)){
            for (ResultSet tulos = pstm.executeQuery(); tulos.next() ;){
                Tehtava t = new Tehtava();
                t.setTehtava(tulos.getString("tehtava"));
                t.setId(tulos.getInt("id"));
                haetut.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Tulostetaan tyhjä lista");
            return Collections.EMPTY_LIST; //jotta palautetaan jotakin virhetilanteessa
        }
        return haetut;
    }

    @Override
    public int lisaa (Tehtava t) throws SQLException {
        String tehtava = t.getTehtava();
        String sql = "INSERT INTO tehtava (tehtava) VALUES (?)";
        PreparedStatement lause = con.prepareStatement(sql);
            lause.setString(1, tehtava);
            lause.executeUpdate();
            return t.getId();
    }

    @Override
    public void poista (int id) throws SQLException {
        String sql = "DELETE FROM tehtava WHERE (id=?)";
        PreparedStatement lause = con.prepareStatement(sql);
        lause.setInt(1,id);
        lause.executeUpdate();
    }
    @Override
    public boolean muuta (int id, Tehtava t){
        String sql = "UPDATE tehtava SET tehtava = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, t.getTehtava());
            ps.setInt(2, t.getId());
            ps.executeUpdate();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                t.setId(rs.getInt("id"));
                t.setTehtava(rs.getString("tehtava"));
            }

            } catch (SQLException e){
            e.printStackTrace();
        }
        return true;

    }

}

