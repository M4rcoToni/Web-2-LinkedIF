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
		return repository.findAll();
	}

	@Override
	public UserModel findById(int id) {

		return null;
	}

	@Override
	public UserModel save(UserModel user) {
		return repository.save(user);
	}

	@Override
	public Integer insertIntoTbUsers(Integer id, Integer role_id) {
		return repository.insertIntoTbUsers(id, role_id);
	}

}