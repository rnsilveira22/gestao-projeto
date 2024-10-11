package com.orla.gestaoprojeto.repository;

import com.orla.gestaoprojeto.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    List<Projeto> findByNomeContaining(String nome);

    Optional<Projeto> findById(Long id);
}
