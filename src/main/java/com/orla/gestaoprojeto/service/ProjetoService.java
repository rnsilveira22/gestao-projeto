package com.orla.gestaoprojeto.service;

import com.orla.gestaoprojeto.model.Projeto;
import com.orla.gestaoprojeto.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SpringBootApplication(scanBasePackages = {"com.orla.gestaoprojeto"})
public class ProjetoService {
    @Autowired
    private ProjetoRepository projetoRepository;

    public Projeto criarProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public List<Projeto> lerProjetos() {
        return projetoRepository.findAll();
    }


    public List<Projeto> buscarProjetosPorNome(String nome) {
        return projetoRepository.findByNomeContaining(nome);
    }
}
