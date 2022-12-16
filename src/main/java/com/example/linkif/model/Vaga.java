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
@Table(name = "Vaga")
public class Vaga {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotBlank
  private String titulo;

  @NotBlank
  private String salario;

  @NotBlank
  private String descricao;

  @NotNull
  private int tipo;

  @NotNull
  private int vagaId;

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

  public int getVagaId() {
    return vagaId;
  }

  public void setVagaId(int vagaId) {
    this.vagaId = vagaId;
  }

}
