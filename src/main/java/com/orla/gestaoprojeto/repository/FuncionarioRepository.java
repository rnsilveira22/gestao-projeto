package com.orla.gestaoprojeto.repository;

import com.orla.gestaoprojeto.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
