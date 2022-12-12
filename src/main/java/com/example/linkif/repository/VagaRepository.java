package com.example.linkif.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.linkif.model.Vaga;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Integer> {
  // List<Categorias> findCategoriasByTituloLike(String titulo);

  // List<Categorias> findCategoriasByTipo(int tipo);
}
