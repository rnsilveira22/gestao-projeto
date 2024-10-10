package com.orla.gestaoprojeto.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orla.gestaoprojeto.model.Funcionario;
import com.orla.gestaoprojeto.model.Projeto;
import com.orla.gestaoprojeto.service.FuncionarioService;
import com.orla.gestaoprojeto.service.ProjetoService;
import jakarta.validation.Valid;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/projeto")
@Validated
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @Autowired
    private FuncionarioService funcionarioService;


    @PostMapping
    public ResponseEntity<Projeto> criarProjeto(@Valid @RequestBody String projetoRec) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(projetoRec);
            Projeto projeto = new Projeto();
            projeto.setNome(jsonNode.get("nome").asText());
            projeto.setDataCriacao(jsonNode.get("dataCriacao").asText());

            Set<Funcionario> funcionarios = new HashSet<>();
            for (JsonNode funcNode : jsonNode.get("funcionarios")) {
                Funcionario funcionario = new Funcionario();
                funcionario.setNome(funcNode.get("nome").asText());
                funcionario.setCpf(funcNode.get("cpf").asInt());
                funcionario.setSalario(funcNode.get("salario").asDouble());
                funcionarios.add(funcionario);
                funcionarioService.criarFuncionario(funcionario);
            }
            projeto.setFuncionarios(funcionarios);
            projetoService.criarProjeto(projeto);
            return new ResponseEntity<>(projeto, HttpStatus.CREATED);
        }catch (Exception err){
            err.printStackTrace();
            return new ResponseEntity("Erro ao processar a requisição: " + err.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Projeto>> lerProjetos() {
        return ResponseEntity.ok(projetoService.lerProjetos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> lerProjeto(@PathVariable Long id) {
        Optional<Projeto> projeto = projetoService.lerProjeto(id);
        return projeto.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
}

