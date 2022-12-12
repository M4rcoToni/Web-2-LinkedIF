package com.example.linkif.services;

import java.util.List;

import com.example.linkif.model.UserModel;

public interface UsersServices {
	List<UserModel> findAll();

	UserModel findById(int id);

	UserModel save(UserModel user);

	List<UserModel> findUsersByModeloLike(String modelo);

	List<UserModel> findUsersByAnoLike(int ano);

	List<UserModel> findUsersBycategoriaLike(int categoria);

}
