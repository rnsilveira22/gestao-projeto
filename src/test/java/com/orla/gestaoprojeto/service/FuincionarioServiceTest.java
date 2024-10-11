package com.orla.gestaoprojeto.service;


import com.orla.gestaoprojeto.model.Funcionario;
import com.orla.gestaoprojeto.model.Projeto;
import com.orla.gestaoprojeto.repository.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FuincionarioServiceTest {


    @Mock
    private FuncionarioRepository funcionarioRepository ;

    @InjectMocks
    private FuncionarioService funcionarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarFuncionario_deveSalvarFuncionario() {
        Funcionario funcionario = new Funcionario("Novo Funcionario");
        funcionario.setCpf(34324342342L);
        funcionario.setSalario(2000.00);
        funcionario.setEmail("teste@gmail.com");

        Projeto projeto1 = new Projeto("Projeto1");
        projeto1.setDataCriacao("10/10/2024");

        Projeto projeto2 = new Projeto("Projeto2");
        projeto2.setDataCriacao("11/10/2024");

        Set<Projeto> projetos = new HashSet<>();
        projetos.add(projeto1);
        projetos.add(projeto2);
        funcionario.setProjetos(projetos);

        when(funcionarioRepository.save(funcionario)).thenReturn(funcionario);

        Funcionario funcionarioSalvo = funcionarioService.criarFuncionario(funcionario);

        assertNotNull(funcionarioSalvo);
        assertEquals("Novo Funcionario", funcionarioSalvo.getNome());
        assertEquals(34324342342L, funcionarioSalvo.getCpf());
        assertEquals(2000.00, funcionarioSalvo.getSalario());
        assertEquals("teste@gmail.com", funcionario.getEmail());
        assertEquals(2, funcionario.getProjetos().size());

        verify(funcionarioRepository).save(funcionario);

    }

    @Test
    void lerFuncionarios_deveRetornarListaDeFuncionarios() {
        Funcionario funcionario1 = new Funcionario("Funcionario1");
        funcionario1.setCpf(34324342342L);
        funcionario1.setSalario(2000.00);
        funcionario1.setEmail("teste@gmail.com");

        Funcionario funcionario2 = new Funcionario("Funcionario2");
        funcionario2.setCpf(34320000342L);
        funcionario2.setSalario(33000.00);
        funcionario2.setEmail("teste2@gmail.com");

        List<Funcionario> listaDeFuncionarios = Arrays.asList(funcionario1, funcionario2);

        when(funcionarioRepository.findAll()).thenReturn(listaDeFuncionarios);


        List<Funcionario> funcionarios = funcionarioService.lerFuncionarios();


        assertNotNull(funcionarios);
        assertEquals(2, funcionarios.size());
        verify(funcionarioRepository, times(1)).findAll();
    }

    @Test
    void buscarFuncionariosPorNome_deveRetornarFuncionariosQueContenhamONome() {
        String nomeFuncionario1 = "Medeiros";
        String nomeFuncionario2 = "José Medeiros";

        Funcionario funcionario1 = new Funcionario(nomeFuncionario1);
        funcionario1.setCpf(34324342342L);
        funcionario1.setSalario(2000.00);
        funcionario1.setEmail("teste@gmail.com");

        Funcionario funcionario2 = new Funcionario(nomeFuncionario2);
        funcionario2.setCpf(3000342342L);
        funcionario2.setSalario(33000.00);
        funcionario2.setEmail("teste2@gmail.com");

        when(funcionarioRepository.findByNomeContaining(nomeFuncionario1)).thenReturn(Arrays.asList(funcionario1, funcionario2));
        List<Funcionario> funcionarios1 = funcionarioService.buscarFuncionariosPorNome(nomeFuncionario1);

        assertNotNull(funcionarios1);
        assertEquals(2, funcionarios1.size());
        verify(funcionarioRepository, times(1)).findByNomeContaining("Medeiros");

        when(funcionarioRepository.findByNomeContaining("José")).thenReturn(List.of(funcionario2));
        List<Funcionario> funcionarios2 = funcionarioService.buscarFuncionariosPorNome("José");

        assertNotNull(funcionarios2);
        assertEquals(1, funcionarios2.size());
        verify(funcionarioRepository, times(1)).findByNomeContaining("José");
    }

    @Test
    void lerFuncionario_deveRetornarVazioQuandoNomeNaoExistir() {
        String nomeFuncionario = "João da Silva";
        when(funcionarioRepository.findByNomeContaining(nomeFuncionario)).thenReturn(new ArrayList<>());
        List<Funcionario> funcionarioNaoEncontrado = funcionarioService.buscarFuncionariosPorNome(nomeFuncionario);

        assertFalse(funcionarioNaoEncontrado.size() >  0);
        verify(funcionarioRepository, times(1)).findByNomeContaining(nomeFuncionario);
    }
}
