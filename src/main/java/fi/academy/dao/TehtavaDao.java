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
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tehtavat", "puser", "super");
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
    public Tehtava naytaTehtava(int id){
        Tehtava t = new Tehtava();
        String sql = "SELECT * FROM tehtava where (id=?)";
        try (PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setInt(1, id);
            for (ResultSet tulos = pstm.executeQuery(); tulos.next();){
                t.setTehtava(tulos.getString("tehtava"));
                t.setId(tulos.getInt("id"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public int lisaa (Tehtava t) throws SQLException {
        int id = 0;
        String sql = "INSERT INTO tehtava (tehtava) VALUES (?)";
        PreparedStatement lause = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            lause.setString(1, t.getTehtava());
            lause.executeUpdate();
            ResultSet rs = lause.getGeneratedKeys();
            while (rs.next()){
                id = rs.getInt(1);
                System.out.println("Uuden tehtävän id: " + id);
            }
            return id;
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
            ps.setInt(2, id);
            ps.executeUpdate();

            } catch (SQLException e){
            e.printStackTrace();
        }
        return true;

    }

}

