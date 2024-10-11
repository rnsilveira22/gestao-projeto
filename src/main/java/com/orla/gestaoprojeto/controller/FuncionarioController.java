package com.orla.gestaoprojeto.controller;

import com.orla.gestaoprojeto.model.Funcionario;
import com.orla.gestaoprojeto.model.Projeto;
import com.orla.gestaoprojeto.service.FuncionarioService;
import com.orla.gestaoprojeto.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public ResponseEntity<Funcionario> criarFuncionario(@RequestBody Funcionario funcionarioRec) {
        try {
            Funcionario funcResult = new Funcionario(funcionarioRec.getNome());
            Optional.of(funcionarioRec.getCpf()).ifPresent(funcResult::setCpf);
            Optional.of(funcionarioRec.getSalario()).ifPresent(funcResult::setSalario);
            Optional.ofNullable(funcionarioRec.getEmail()).ifPresent(funcResult::setEmail);

            Set<Projeto> projetoSet = funcionarioRec.getProjetos()
                    .stream()
                    .map(this::montaObjetoProjeto)
                    .filter(projeto -> projeto.getNome() != null && !projeto.getNome().isBlank())
                    .collect(Collectors.toSet());
            if (!projetoSet.isEmpty()) funcResult.setProjetos(projetoSet);
            funcionarioService.criarFuncionario(funcResult);
            return new ResponseEntity<>(funcResult, HttpStatus.CREATED);
        } catch (Exception err) {
            err.printStackTrace();
            return new ResponseEntity("Erro ao processar a requisição: " + err.getMessage(), HttpStatus.BAD_REQUEST);
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

    private Projeto montaObjetoProjeto(Projeto projetoDto) {
        Projeto projetoResult = new Projeto(projetoDto.getNome());
        projetoResult.setNome(projetoDto.getNome());
        projetoResult.setDataCriacao(projetoDto.getDataCriacao());
        projetoService.criarProjeto(projetoResult);
        return projetoResult;
    }

}
