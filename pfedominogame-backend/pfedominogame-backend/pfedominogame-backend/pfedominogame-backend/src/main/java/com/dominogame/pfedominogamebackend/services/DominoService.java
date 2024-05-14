package com.dominogame.pfedominogamebackend.services;

import com.dominogame.pfedominogamebackend.model.Domino;
import com.dominogame.pfedominogamebackend.repository.DominoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DominoService {

    @Autowired
    private DominoRepository dominoRepository;

    public List<Domino> getAllDominoes() {
        return dominoRepository.findAll();
    }

    public Optional<Domino> getDominoById(Long id) {
        return dominoRepository.findById(id);
    }

    public Domino createDomino(Domino domino) {
        return dominoRepository.save(domino);
    }

    public Domino updateDomino(Long id, Domino updatedDomino) {
        updatedDomino.setDominoId(id);
        return dominoRepository.save(updatedDomino);
    }

    public void deleteDomino(Long id) {
        dominoRepository.deleteById(id);
    }
}
