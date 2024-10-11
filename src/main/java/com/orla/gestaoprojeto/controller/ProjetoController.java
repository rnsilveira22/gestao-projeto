package com.orla.gestaoprojeto.controller;

import com.orla.gestaoprojeto.model.Funcionario;
import com.orla.gestaoprojeto.model.Projeto;
import com.orla.gestaoprojeto.service.FuncionarioService;
import com.orla.gestaoprojeto.service.ProjetoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projeto")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @Autowired
    private FuncionarioService funcionarioService;

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }


    @PostMapping
    public ResponseEntity<Projeto> criarProjeto(@Valid @RequestBody Projeto projetoDto) {
        try {
            Projeto projeto = new Projeto(projetoDto.getNome());
            Optional.ofNullable(projetoDto.getDataCriacao()).ifPresent(projeto::setDataCriacao);

            Set<Funcionario> funcionarios = projetoDto.getFuncionarios()
                    .stream()
                    .map(this::converterFuncionarioDtoParaFuncionario)
                    .filter(funcionario -> funcionario.getNome() != null && !funcionario.getNome().isBlank())
                    .collect(Collectors.toSet());

            if (!funcionarios.isEmpty()) {
                projeto.setFuncionarios(funcionarios);
            }

            projetoService.criarProjeto(projeto);
            return new ResponseEntity<>(projeto, HttpStatus.CREATED);
        } catch (Exception err) {
            err.printStackTrace();
            return new ResponseEntity("Erro ao processar a requisição: " + err.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private Funcionario converterFuncionarioDtoParaFuncionario(Funcionario funcionarioDto) {
        Funcionario funcionario = new Funcionario(funcionarioDto.getNome());
        funcionario.setNome(funcionarioDto.getNome());
        funcionario.setCpf(funcionarioDto.getCpf());
        funcionario.setSalario(funcionarioDto.getSalario());
        funcionarioService.criarFuncionario(funcionario); // Mover se necessário
        return funcionario;
    }


    @GetMapping
    public ResponseEntity<List<Projeto>> lerProjetos() {
        return ResponseEntity.ok(projetoService.lerProjetos());
    }

    @GetMapping("/{nome}")
    public ResponseEntity<List<Projeto>> buscarProjetosPorNome(@PathVariable String  nome) {
        List<Projeto> projeto = projetoService.buscarProjetosPorNome(nome);
        return projeto != null ? ResponseEntity.ok(projeto) : ResponseEntity.notFound().build();
    }
}

