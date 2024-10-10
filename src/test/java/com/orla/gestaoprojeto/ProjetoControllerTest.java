package com.orla.gestaoprojeto;

import com.orla.gestaoprojeto.controller.ProjetoController;
import com.orla.gestaoprojeto.service.FuncionarioService;
import com.orla.gestaoprojeto.service.ProjetoService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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
    public void testCriarProjeto() throws Exception {
        var json = "{\"id\": 1,\"nome\": \"Meu primeiro primeiro Projeto\",\"dataCriacao\": \"10/10/2024\",\"funcionarios\": [ { \"id\": 3, \"nome\": \"Rodrigo Silveira\", \"cpf\": \"213131\",\"email\": \"rsilveira22@gmail.com\",\"salario\": \"10000\",\"projetos\": [{}]}]}";


        mockMvc.perform(post("/projeto")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());
    }
}
