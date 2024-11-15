package com.example.demo.controller;

import com.example.demo.database.entities.Estudante;
import com.example.demo.database.entities.Turma;
import com.example.demo.service.EstudanteService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EstudanteControllerTest {

    static  Estudante estudante;

    @Autowired
    EstudanteController estudanteController;

    @MockBean
    private EstudanteService estudanteService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void init() {
        estudante = new Estudante(1L,
                "Joaquino",
                "11.22.33",
                Collections.emptyList());
    }

    @SneakyThrows
    @Test
    void listarEstudantes() {
        when(estudanteService.listarEstudantes())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/estudantes"))
                .andExpect(status().isOk());

    }

    @Test
    void buscarEstudantePorId() throws Exception {
        when(estudanteService.buscarEstudantePorId(anyLong()))
                .thenReturn(estudante);
        MvcResult mvcResult = mockMvc.perform(get("/estudantes/{id}",1L))
                .andExpect(status().isOk())
                .andReturn();
        verify(estudanteService).buscarEstudantePorId(anyLong());
        assertEquals("{\"id\":1,\"nome\":\"Joaquino\",\"matricula\":\"11.22.33\",\"turma\":[]}", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void cadastrarEstudante() throws Exception {
         when(estudanteService.cadastrarEstudante(estudante.getNome(),
                 estudante.getMatricula()))
            .thenReturn(estudante);

        MvcResult mvcResult = mockMvc.perform(
                        post("/estudantes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "\t\"nome\":\""+ estudante.getNome()+"\",\n" +
                                        "\t\"matricula\": \""+ estudante.getMatricula()+"\"\n" +
                                        "}")
                )
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("{\"id\":1,\"nome\":\"Joaquino\",\"matricula\":\"11.22.33\",\"turma\":[]}", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void atualizarEstudante() throws Exception {
        var novoNome = "novoAluno";
        estudante.setNome(novoNome);
        when(estudanteService.atualizarEstudante(anyLong(),anyString(),anyString()))
                .thenReturn(estudante);


        MvcResult mvcResult = mockMvc.perform(
                        put("/estudantes/{id}",1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "\t\"nome\":\""+ novoNome +"\",\n" +
                                        "\t\"matricula\": \""+ estudante.getMatricula()+"\"\n" +
                                        "}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(novoNome))
                .andReturn();

        assertEquals("{\"id\":1,\"nome\":\""+novoNome+"\",\"matricula\":\"11.22.33\",\"turma\":[]}", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void removerEstudante() throws Exception {

        mockMvc.perform(
                        delete("/estudantes/{id}",1L)
                )
                .andExpect(status().isNoContent());
    }
}