package com.orla.gestaoprojeto.controller;

import com.orla.gestaoprojeto.model.Projeto;
import com.orla.gestaoprojeto.service.FuncionarioService;
import com.orla.gestaoprojeto.service.ProjetoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjetoController.class)
public class ProjetoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ProjetoController projetoController;

    @MockBean
    private ProjetoService projetoService;

    @MockBean
    private FuncionarioService funcionarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void lerProjetos_deveRetornarListaVaziaQuandoNaoExistemProjetos() throws Exception {
        when(projetoService.lerProjetos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/projeto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void criarProjeto_deveRetornarProjetoCriado() throws Exception {
        Projeto projeto = new Projeto("Nome do Projeto");
        projeto.setDataCriacao("10/10/2024");

        when(projetoService.criarProjeto(any(Projeto.class))).thenReturn(projeto);

        mockMvc.perform(post("/api/projeto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Nome do Projeto\", \"dataCriacao\": \"10/10/2024\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Nome do Projeto"))
                .andExpect(jsonPath("$.dataCriacao").value("10/10/2024"));
    }


    @Test
    void lerProjetos_deveRetornarListaDeProjetos() throws Exception {
        Projeto projeto1 = new Projeto("Projeto 1");
        projeto1.setDataCriacao("11/10/2024");

        Projeto projeto2 = new Projeto("Projeto 2");
        projeto2.setDataCriacao("12/10/2024");

        when(projetoService.lerProjetos()).thenReturn(Arrays.asList(projeto1, projeto2));

        mockMvc.perform(get("/api/projeto"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"nome\": \"Projeto 1\", \"dataCriacao\": \"11/10/2024\"}," +
                        " {\"nome\": \"Projeto 2\", \"dataCriacao\": \"12/10/2024\"}]"));
    }
}
