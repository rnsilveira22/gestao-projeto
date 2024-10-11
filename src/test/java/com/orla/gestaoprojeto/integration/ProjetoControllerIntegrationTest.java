package com.orla.gestaoprojeto.integration;

import com.orla.gestaoprojeto.model.Funcionario;
import com.orla.gestaoprojeto.model.Projeto;
import com.orla.gestaoprojeto.repository.ProjetoRepository;
import io.swagger.v3.core.util.ObjectMapperFactory;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProjetoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private ObjectMapperFactory objectMapper;

    @Test
    void criarProjeto_deveRetornarProjetoCriado() throws Exception {

        Projeto projetoDTO = new Projeto("Nome do Projeto");
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataAtual.format(formatter);
        projetoDTO.setDataCriacao(dataFormatada);

        Set<Funcionario> funcs = new HashSet<>();
        int qtFunc = 5;
        for (int i=0; i< qtFunc; i++) {
            Funcionario funcionario1 = new Funcionario("Funcionario1");
            funcionario1.setCpf(Long.parseLong(gerarCpf()));
            funcionario1.setSalario(gerarDoubleAleatorio(100.00, 20000.00));
            funcionario1.setEmail("emailDoSalario"+funcionario1.getSalario().toString()+"@gmail.com");
            funcs.add(funcionario1);
        }
        projetoDTO.setFuncionarios(funcs);

        String projetoJson = objectMapper.buildStrictGenericObjectMapper().writeValueAsString(projetoDTO);

        mockMvc.perform(post("/api/projeto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projetoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Nome do Projeto"))
                .andExpect(jsonPath("$.dataCriacao").value(dataFormatada));

        List<Projeto> projetos = projetoRepository.findAll();
        String result = objectMapper.buildStrictGenericObjectMapper().writeValueAsString(projetos);
        assertTrue(projetos.size() == 1);;
        assertEquals(projetos.get(0).getNome(),"Nome do Projeto");

        System.out.println("+++++++++++++++++++++++++\n" +
                "Projeto Encontrados:\n" +result);
    }


    public static String gerarCpf() {
        Random random = new Random();

        StringBuilder cpfBase = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            cpfBase.append(random.nextInt(10)); // Gera um dígito entre 0 e 9
        }

        int primeiroDigito = calcularDigitoVerificador(cpfBase.toString(), 10);
        cpfBase.append(primeiroDigito);

        int segundoDigito = calcularDigitoVerificador(cpfBase.toString(), 11);
        cpfBase.append(segundoDigito);

        return cpfBase.toString();
    }

    private static int calcularDigitoVerificador(String base, int peso) {
        int soma = 0;
        for (int i = 0; i < base.length(); i++) {
            soma += Character.getNumericValue(base.charAt(i)) * peso;
            peso--;
        }
        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }

    public static double gerarDoubleAleatorio(double min, double max) {
        Random random = new Random();
        // Gera um número double aleatório entre min e max
        return min + (max - min) * random.nextDouble();
    }
}

