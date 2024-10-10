package com.orla.gestaoprojeto.repository;

import com.orla.gestaoprojeto.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
