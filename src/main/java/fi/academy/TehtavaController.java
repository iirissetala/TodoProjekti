package fi.academy;

import fi.academy.dao.TehtavaDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
//jos tarvitaan komponentteja muista luokista, lisätään annotaatio ComponentScan,
//jonne("paketin nimi esim fi.academy.harjoitukset")
//etsii automaattisesti komponentteja samasta paketista! vain jos on muualla, pitää lisätä
@RequestMapping("/api/todot")
public class TehtavaController {
    private TehtavaDao dao;

    public TehtavaController (TehtavaDao dao){
        this.dao = dao;
    }

    @GetMapping("/testisivu")
    public String sanoJotain(){
        return "Sanon jotain";
    }

    @GetMapping("/kaikki")
    public List<Tehtava> tulostaTehtavat(){
        List<Tehtava> tehtavat = dao.naytaTehtavat();
        System.out.println("Listataan kaikki tekemättömät tehtävät");
        return tehtavat;
    }
    @GetMapping("/{id}")
    public Tehtava tulostaTehtava(@PathVariable int id){
        Tehtava t = dao.naytaTehtava(id);
        return t;
    }

    @PostMapping("")
    public ResponseEntity<?> luoUusi(@RequestBody Tehtava t){
        int id = 0;
        try {
            id = dao.lisaa(t);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).body(t);
    }

    @DeleteMapping("/{id}")
    public void poista(@PathVariable int id) throws SQLException {
        dao.poista(id);

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> muokkaa (@RequestBody Tehtava tehtava, @PathVariable int id){
        boolean muuttiko = dao.muuta(id, tehtava);
        if (muuttiko){
            tehtava.setId(id);
            return ResponseEntity.ok(tehtava);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new Virheviesti(String.format("Ei löytynyt tehtävää annetulla id:llä.")));
    }

}
