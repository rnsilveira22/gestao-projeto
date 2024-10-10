package com.orla.gestaoprojeto.service;

import com.orla.gestaoprojeto.model.Funcionario;
import com.orla.gestaoprojeto.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario criarFuncionario(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }
    public List<Funcionario> lerFuncionarios() {
        return funcionarioRepository.findAll();
    }

    public Optional<Funcionario> lerFuncionario(Long id) {
        return funcionarioRepository.findById(id);
    }
}
