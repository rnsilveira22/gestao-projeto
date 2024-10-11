package com.orla.gestaoprojeto.service;


import com.orla.gestaoprojeto.model.Projeto;
import com.orla.gestaoprojeto.repository.ProjetoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjetoServiceTest {


    @Mock
    private ProjetoRepository projetoRepository ;

    @InjectMocks
    private ProjetoService projetoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarProjeto_deveSalvarProjeto() {
        Projeto projeto = new Projeto("Novo Projeto");
        projeto.setDataCriacao("11/10/2024");

        when(projetoRepository.save(projeto)).thenReturn(projeto);

        Projeto projetoSalvo = projetoService.criarProjeto(projeto);

        assertNotNull(projetoSalvo);
        assertEquals("Novo Projeto", projetoSalvo.getNome());
        verify(projetoRepository).save(projeto);

    }

    @Test
    void lerProjetos_deveRetornarListaDeProjetos() {
        Projeto projeto1 = new Projeto("Projeto1");
        projeto1.setDataCriacao("10/10/2024");
        Projeto projeto2 = new Projeto("Projeto2");
        projeto2.setDataCriacao("11/10/2024");

        List<Projeto> listaDeProjetos = Arrays.asList(projeto1, projeto2);

        when(projetoRepository.findAll()).thenReturn(listaDeProjetos);


        List<Projeto> projetos = projetoService.lerProjetos();


        assertNotNull(projetos);
        assertEquals(2, projetos.size());
        verify(projetoRepository, times(1)).findAll();
    }

    @Test
    void buscarProjetosPorNome_deveRetornarProjetosQueContenhamONome() {
        String nomeProjeto1 = "Projeto de Teste";
        String nomeProjeto2 = "Outro Projeto de Teste";

        Projeto projeto1 = new Projeto(nomeProjeto1);
        projeto1.setDataCriacao("10/10/2024");

        Projeto projeto2 = new Projeto(nomeProjeto2);
        projeto2.setDataCriacao("11/10/2024");

        when(projetoRepository.findByNomeContaining(nomeProjeto1)).thenReturn(Arrays.asList(projeto1, projeto2));
        List<Projeto> projetos1 = projetoService.buscarProjetosPorNome(nomeProjeto1);

        assertNotNull(projetos1);
        assertEquals(2, projetos1.size());
        verify(projetoRepository, times(1)).findByNomeContaining("Projeto de Teste");

        when(projetoRepository.findByNomeContaining("Outro")).thenReturn(List.of(projeto2));
        List<Projeto> projetos2 = projetoService.buscarProjetosPorNome("Outro");

        assertNotNull(projetos2);
        assertEquals(1, projetos2.size());
        verify(projetoRepository, times(1)).findByNomeContaining("Outro");
    }

    @Test
    void lerProjeto_deveRetornarVazioQuandoNomeNaoExistir() {
        String nomeProjeto = "Batatinha";
        when(projetoRepository.findByNomeContaining(nomeProjeto)).thenReturn(new ArrayList<>());
        List<Projeto> projetoNaoEncontrado = projetoService.buscarProjetosPorNome(nomeProjeto);

        assertFalse(projetoNaoEncontrado.size() >  0);
        verify(projetoRepository, times(1)).findByNomeContaining(nomeProjeto);
    }
}
