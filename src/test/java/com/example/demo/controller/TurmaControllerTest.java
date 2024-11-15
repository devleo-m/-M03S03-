package com.example.demo.controller;

import com.example.demo.database.entities.Estudante;
import com.example.demo.database.entities.Turma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
class TurmaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    Turma turma;
    Estudante estudante;

    @Test
    void listarTurmas()  throws Exception{
        mockMvc.perform(get("/turmas")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void buscarTurmaPorId()  throws Exception{

        mockMvc.perform(post("/turmas")
                        .content("{\"nome\":\"nome\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk());

        mockMvc.perform(get("/turmas/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("nome"));
    }


    @Test
    void cadastrarTurma()  throws Exception{
        mockMvc.perform(post("/turmas")
                        .content("{\"nome\":\"nome\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk());
    }

    @Test
    void atualizarTurma()  throws Exception{
        mockMvc.perform(put("/turmas/{id}",1L)
                        .content("{\"nome\":\"nomeNovo\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk());
    }

    @Test
    void removerTurma()  throws Exception{
        mockMvc.perform(delete("/turmas/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isNoContent());
    }
}