package com.example.linkif.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.linkif.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
  Optional<UserModel> findByUsername(String username);

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO tb_users_roles VALUES(:user_id, :roleId);", nativeQuery = true)
  Integer insertIntoTbUsers(@Param("user_id") Integer user_id, @Param("roleId") Integer roleId);
}
