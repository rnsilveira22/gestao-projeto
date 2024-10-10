package com.orla.gestaoprojeto.service;

import com.orla.gestaoprojeto.model.Funcionario;
import com.orla.gestaoprojeto.model.Projeto;
import com.orla.gestaoprojeto.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepository projetoRepository;

    public Projeto criarProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public List<Projeto> lerProjetos() {
        return projetoRepository.findAll();
    }

    public Optional<Projeto> lerProjeto(Long id) {
        return projetoRepository.findById(id);
    }
}
