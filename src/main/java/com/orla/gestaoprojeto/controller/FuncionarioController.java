package com.orla.gestaoprojeto.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orla.gestaoprojeto.model.Funcionario;
import com.orla.gestaoprojeto.model.Projeto;
import com.orla.gestaoprojeto.service.FuncionarioService;
import com.orla.gestaoprojeto.service.ProjetoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/funcionario")

public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    private final ProjetoService projetoService;

    public FuncionarioController(FuncionarioService funcionarioService, ProjetoService projetoService) {
        this.funcionarioService = funcionarioService;
        this.projetoService = projetoService;
    }

    @PostMapping
    public ResponseEntity criarFuncionario(@RequestBody String funcionarioRec) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(funcionarioRec);
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(jsonNode.get("nome").asText());
            funcionario.setCpf(jsonNode.get("cpf").asInt());
            funcionario.setSalario(jsonNode.get("salario").asDouble());

            Set<Projeto> projetos = new HashSet<>();
            for (JsonNode projNode : jsonNode.get("projetos")) {
                Projeto projeto = new Projeto();
                projeto.setNome(projNode.get("nome").asText());
                projeto.setDataCriacao(projNode.get("dataCriacao").asText());
                projetos.add(projeto);
                projetoService.criarProjeto(projeto);
            }
            funcionario.setProjetos(projetos);
            return new ResponseEntity<>(funcionario, HttpStatus.CREATED);
        }catch (Exception err){
            err.printStackTrace();
            return new ResponseEntity<>("Erro ao processar a requisição: " + err.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
