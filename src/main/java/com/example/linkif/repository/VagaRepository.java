package com.example.linkif.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.linkif.model.VagaModel;

@Repository
public interface VagaRepository extends JpaRepository<VagaModel, Integer> {
  List<VagaModel> findByTipoLike(int tipo);

  List<VagaModel> findByusersNamesLike(String user);

  List<VagaModel> findByempresaNameLike(String empresaName);

  @Modifying
  @Transactional
  @Query(value = "UPDATE tb_vaga SET `status`=(:status) WHERE `id` = (:id);", nativeQuery = true)
  Integer upadteTbVagasStatus(@Param("id") Integer id, @Param("status") Boolean status);

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO vaga_model_users_names VALUES (:empresa_id,:username);", nativeQuery = true)
  Integer insertIntoVagaUsersNames(@Param("empresa_id") Integer empresa_id, @Param("username") String username);
}
