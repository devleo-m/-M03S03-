package com.example.demo.controller;


import com.example.demo.controller.dto.TurmaRequest;
import com.example.demo.database.entities.Turma;
import com.example.demo.service.TurmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turmas")
@RequiredArgsConstructor
public class TurmaController {

    private final TurmaService turmaService;

    @GetMapping
    public List<Turma> listarTurmas() {
        return turmaService.listarTurmas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> buscarTurmaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(turmaService.buscarTurmaPorId(id));
    }

    @PostMapping
    public ResponseEntity<Turma> cadastrarTurma(@RequestBody TurmaRequest turma) {
        return ResponseEntity.ok(turmaService.cadastrarTurma(turma.nome()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turma> atualizarTurma(@PathVariable Long id, @RequestBody TurmaRequest turma) {
        return ResponseEntity.ok(turmaService.atualizarTurma(id, turma.nome()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerTurma(@PathVariable Long id) {
        turmaService.removerTurma(id);
        return ResponseEntity.noContent().build();

    }
}