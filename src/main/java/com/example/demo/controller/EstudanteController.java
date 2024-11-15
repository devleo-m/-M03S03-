package com.example.demo.controller;

import com.example.demo.database.entities.Estudante;
import com.example.demo.service.EstudanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudantes")
@RequiredArgsConstructor
public class EstudanteController {

    private final EstudanteService estudanteService;

    @GetMapping
    public List<Estudante> listarEstudantes() {
        return estudanteService.listarEstudantes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudante> buscarEstudantePorId(@PathVariable Long id) {
        return ResponseEntity.ok(estudanteService.buscarEstudantePorId(id));
    }

    @PostMapping
    public Estudante cadastrarEstudante(@RequestBody Estudante estudante) {
        return estudanteService.cadastrarEstudante(estudante.getNome(), estudante.getMatricula());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudante> atualizarEstudante(@PathVariable Long id, @RequestBody Estudante estudante) {
        return ResponseEntity.ok(estudanteService.atualizarEstudante(id, estudante.getNome(), estudante.getMatricula()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerEstudante(@PathVariable Long id) {
        estudanteService.removerEstudante(id);
        return ResponseEntity.noContent().build();
    }
}
