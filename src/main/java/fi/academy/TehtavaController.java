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

    @PostMapping("")
    public ResponseEntity<?> luoUuusi(@RequestBody Tehtava t) {
        System.out.println("****** Luodaan uutta: " + t);
        int id = 0;
        try {
            id = dao.lisaa(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("****** Luotu uusi: " + t);
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

}
