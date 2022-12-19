package com.example.linkif.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import net.bytebuddy.implementation.bind.annotation.Default;

@Entity
@Table(name = "TB_EMPRESA")
public class EmpresaModel implements UserDetails {

  @Id
  private int empresaId;
  @Column(nullable = false, unique = true)
  private String username;
  @Column(nullable = false)
  private String password;

  @NotNull
  private int role_id;

  @NotBlank
  private String nome;

  @NotBlank
  private String regiao;

  @NotNull
  private int cnpjoucpf;

  private String imagem;

  @ManyToMany
  @JoinTable(name = "TB_EMPRESA_ROLES", joinColumns = @JoinColumn(name = "empresa_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private List<RoleModel> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getImagem() {
    return imagem;
  }

  public void setImagem(String imagem) {
    this.imagem = imagem;
  }

  public String getRegiao() {
    return regiao;
  }

  public void setRegiao(String regiao) {
    this.regiao = regiao;
  }

  public int getEmpresaId() {
    return empresaId;
  }

  public int getCnpjoucpf() {
    return cnpjoucpf;
  }

  public void setCnpjoucpf(int cnpjoucpf) {
    this.cnpjoucpf = cnpjoucpf;
  }

  public void setEmpresaId(int empresaId) {
    this.empresaId = empresaId;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getPassword() {
    // TODO Auto-generated method stub
    return this.password;
  }

  @Override
  public String getUsername() {
    // TODO Auto-generated method stub
    return this.username;
  }

  public int getRole_id() {
    return role_id;
  }

  public void setRole_id(int role_id) {
    this.role_id = role_id;
  }

  @Override
  public boolean isAccountNonExpired() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isEnabled() {
    // TODO Auto-generated method stub
    return true;
  }

}
