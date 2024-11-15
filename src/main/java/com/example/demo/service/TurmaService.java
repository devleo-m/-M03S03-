package com.example.demo.service;

import com.example.demo.database.entities.Estudante;
import com.example.demo.database.entities.Turma;
import com.example.demo.database.repositories.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurmaService {
    final TurmaRepository turmaRepository;

    public Turma cadastrarTurma(String nome) {
        Turma turma = new Turma();
        turma.setNome(nome);
        turmaRepository.save(turma);
        return turma;
    }

    public List<Turma> listarTurmas() {
        return turmaRepository.findAll();
    }

    public Turma buscarTurmaPorId(Long id) {
        return turmaRepository.findById(id).orElseThrow( ()-> {
            throw new RuntimeException("Turma não encontrada");
        });
    }

    public Turma atualizarTurma(Long id, String novoNome) {
        Turma turma = buscarTurmaPorId(id);
        turma.setNome(novoNome);
        turmaRepository.save(turma);
        return turma;
    }

    public void removerTurma(Long id) {
        turmaRepository.deleteById(id);
    }

    public Estudante adicionarEstudanteNaTurma(Long turmaId, Estudante estudante) {
        Turma turma = buscarTurmaPorId(turmaId);
        estudante.getTurma().add(turma);
        return estudante;
    }

    public Estudante removerEstudanteDaTurma(Long turmaId, Estudante estudante) {
        Turma turma = buscarTurmaPorId(turmaId);
        estudante.getTurma().remove(turma);
        return estudante;

    }
}
