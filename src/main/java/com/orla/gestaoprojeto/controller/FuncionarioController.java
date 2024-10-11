package com.orla.gestaoprojeto.controller;

import com.orla.gestaoprojeto.model.Funcionario;
import com.orla.gestaoprojeto.service.FuncionarioService;
import com.orla.gestaoprojeto.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionario")
public class FuncionarioController {
    @Autowired
    private final FuncionarioService funcionarioService;
    @Autowired
    private final ProjetoService projetoService;


    public FuncionarioController(FuncionarioService funcionarioService, ProjetoService projetoService) {
        this.funcionarioService = funcionarioService;
        this.projetoService = projetoService;
    }

    @PostMapping
    public ResponseEntity criarFuncionario(@RequestBody Funcionario funcionarioRec) {
        Funcionario funcResult = new Funcionario(funcionarioRec.getNome());

        return new ResponseEntity<>(funcResult, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> lerFuncionarios() {
        return ResponseEntity.ok(funcionarioService.lerFuncionarios());
    }

    @GetMapping("/id")
    public ResponseEntity<Funcionario> lerFuncionario(@RequestParam Long id) {
        Optional<Funcionario> funcionario = funcionarioService.lerFuncionario(id);
        return funcionario.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
}
