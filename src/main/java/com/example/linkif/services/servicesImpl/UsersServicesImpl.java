package com.example.linkif.services.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.linkif.model.UserModel;
import com.example.linkif.repository.UserRepository;
import com.example.linkif.services.UsersServices;

@Service
public class UsersServicesImpl implements UsersServices {
	@Autowired
	UserRepository repository;

	@Override
	public List<UserModel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel save(UserModel user) {
		// TODO Auto-generated method stub
		return repository.save(user);
	}

	@Override
	public List<UserModel> findUsersByModeloLike(String modelo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserModel> findUsersByAnoLike(int ano) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserModel> findUsersBycategoriaLike(int categoria) {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public List<UserModel> findAll() {
	// return repository.findAll();
	// }

	// @Override
	// public UserModel findById(int id) {

	// return repository.findById(id).get();
	// }

	// @Override
	// public UserModel save(UserModel user) {
	// return repository.save(user);
	// }

	// @Override
	// public List<UserModel> findUserModelByModeloLike(String modelo) {
	// return findUserModelByModeloLike(modelo);
	// }

	// @Override
	// public List<UserModel> findUserModelByAnoLike(int ano) {
	// return findUserModelByAnoLike(ano);
	// }

	// @Override
	// public List<UserModel> findUserModelBycategoriaLike(int categoria) {
	// return findusersBycategoriaLike(categoria);
	// }
}
