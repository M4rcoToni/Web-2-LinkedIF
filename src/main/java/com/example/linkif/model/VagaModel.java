package com.example.linkif.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_VAGA")
public class VagaModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotBlank
  private String titulo;

  @NotBlank
  private String salario;

  @NotBlank
  private String descricao;

  @NotBlank
  private String regiao;

  @NotNull
  private int tipo;

  private String empresaName;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getSalario() {
    return salario;
  }

  public String getRegiao() {
    return regiao;
  }

  public void setRegiao(String regiao) {
    this.regiao = regiao;
  }

  public void setSalario(String salario) {
    this.salario = salario;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String gettitulo() {
    return titulo;
  }

  public void settitulo(String titulo) {
    this.titulo = titulo;
  }

  public int getTipo() {
    return tipo;
  }

  public void setTipo(int tipo) {
    this.tipo = tipo;
  }

  public String getEmpresaName() {
    return empresaName;
  }

  public void setEmpresaName(String empresaName) {
    this.empresaName = empresaName;
  }

}
