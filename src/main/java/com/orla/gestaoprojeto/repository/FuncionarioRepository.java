package com.orla.gestaoprojeto.repository;

import com.orla.gestaoprojeto.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    List<Funcionario> findByNomeContaining(String nome);

    Optional<Funcionario> findById(Long id);
}
