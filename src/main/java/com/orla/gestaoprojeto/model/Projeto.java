package com.orla.gestaoprojeto.model;

import jakarta.persistence.*;


import java.util.HashSet;
import java.util.Set;

@Entity
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String dataCriacao;

    @ManyToMany
    @JoinTable(
            name = "projeto_funcionario",
            joinColumns = @JoinColumn(name = "projeto_id"),
            inverseJoinColumns = @JoinColumn(name = "funcionario_id"))
    private Set<Funcionario> funcionarios = new HashSet<>();

    public Projeto(String nome) {
        this.nome = nome;
    }

    public Projeto() {

    }

    public void setFuncionarios(Set<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setDataCriacao(String dataCriacao) {
        if (!validarData(dataCriacao)) {
            throw new IllegalArgumentException("Data deve estar no formato DD/MM/AAAA");
        }
        this.dataCriacao = dataCriacao;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public Set<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    private boolean validarData(String data) {
        return data != null && data.matches(("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$"));
    }
}
