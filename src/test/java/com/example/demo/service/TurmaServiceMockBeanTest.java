package com.example.demo.service;

import com.example.demo.database.entities.Estudante;
import com.example.demo.database.entities.Turma;
import com.example.demo.database.repositories.EstudanteRepository;
import com.example.demo.database.repositories.TurmaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("Test")
class TurmaServiceMockBeanTest {

    static Turma turma;
    static  Estudante estudante;

    @MockBean
    private TurmaRepository turmaRepository;

    @Autowired
    private TurmaService turmaService;

    @Mock
    private EstudanteRepository estudanteRepository;

    @InjectMocks
    private EstudanteService estudanteService;

    @BeforeEach
    void init() {
        turma = new Turma(1L,
                "turma",
                new ArrayList<Estudante>());
        estudante = new Estudante(1L,
                "Joaquino",
                "11.22.33",
                new ArrayList<Turma>());

    }

    @Test
    void cadastrarTurma() {
        when(turmaRepository.save(any(Turma.class)))
                .thenReturn(turma);

        Turma resultado = turmaService.cadastrarTurma("turma");

        verify(turmaRepository).save(any(Turma.class));
        assertEquals(turma.getNome(), resultado.getNome());

    }

    @Test
    void removerEstudanteDaTurma() {
        estudante.getTurma().add(turma);
        when(turmaRepository.findById(anyLong()))
                .thenReturn(Optional.of(turma));

        Estudante resultado = turmaService.
                removerEstudanteDaTurma(1L,estudante);

        verify(turmaRepository).findById(anyLong());
        assertEquals(estudante.getMatricula(),resultado.getMatricula() );
        assertEquals(estudante.getNome(),resultado.getNome() );
        assertEquals(0,resultado.getTurma().size() );

    }


    @Test
    void listarTurmas() {
        when(turmaRepository.findAll())
                .thenReturn(List.of(turma));

        List<Turma> resultado = turmaService.listarTurmas();

        verify(turmaRepository).findAll();
        assertEquals(turma.getNome(), resultado.get(0).getNome());
    }

    @Test
    void buscarTurmaPorId() {
        when(turmaRepository.findById(anyLong()))
                .thenReturn(Optional.of(turma));

        Turma resultado = turmaService.buscarTurmaPorId(1L);

        verify(turmaRepository).findById(anyLong());
        assertEquals(turma.getNome(), resultado.getNome());
    }

    @Test
    void atualizarTurma() {
        when(turmaRepository.findById(anyLong()))
                .thenReturn(Optional.of(turma));
        when(turmaRepository.save(any(Turma.class)))
                .thenReturn(turma);

        // when
        Turma resultado = turmaService.atualizarTurma(1L,
                "novaTurma");

        // then
        verify(turmaRepository).findById(anyLong());
        verify(turmaRepository).save(any(Turma.class));
        assertEquals("novaTurma", resultado.getNome());
    }

    @Test
    void removerTurma() {
        // when
        turmaService.removerTurma(1L);

        // then
        verify(turmaRepository).deleteById(anyLong());
    }

    @Test
    void adicionarEstudanteNaTurma() {
        when(turmaRepository.findById(anyLong()))
                .thenReturn(Optional.of(turma));

        Estudante resultado = turmaService.
                adicionarEstudanteNaTurma(1L,estudante);

        verify(turmaRepository).findById(anyLong());

        assertEquals(estudante.getMatricula(),resultado.getMatricula() );
        assertEquals(estudante.getNome(),resultado.getNome() );
        assertEquals(1,resultado.getTurma().size());
        assertEquals(turma.getNome(),resultado.getTurma().get(0).getNome());

    }

}