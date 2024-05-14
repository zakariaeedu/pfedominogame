package com.dominogame.pfedominogamebackend.controller;
import com.dominogame.pfedominogamebackend.model.Domino;
import com.dominogame.pfedominogamebackend.services.DominoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/domino/")
public class DominoController {
    @Autowired
    private DominoService dominoService;
    @GetMapping({"/","all",""})
    public ResponseEntity<List<Domino>> getAllDominoes() {
        List<Domino> dominoes = dominoService.getAllDominoes();
        return ResponseEntity.ok(dominoes);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Domino> getDominoById(@PathVariable Long id) {
        Optional<Domino> domino = dominoService.getDominoById(id);
        return domino.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Domino> createDomino(@RequestBody Domino domino) {
        Domino savedDomino = dominoService.createDomino(domino);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDomino);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Domino> updateDomino(@PathVariable Long id, @RequestBody Domino updatedDomino) {
        Domino result = dominoService.updateDomino(id, updatedDomino);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDomino(@PathVariable Long id) {
        dominoService.deleteDomino(id);
        return ResponseEntity.noContent().build();
    }
}
