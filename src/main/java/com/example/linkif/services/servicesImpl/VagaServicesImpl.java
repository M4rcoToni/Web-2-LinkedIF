package com.example.linkif.services.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.linkif.model.VagaModel;
import com.example.linkif.repository.VagaRepository;
import com.example.linkif.services.VagaServices;

@Service
public class VagaServicesImpl implements VagaServices {
	@Autowired
	VagaRepository repository;

	@Override
	public List<VagaModel> findAll() {
		return repository.findAll();
	}

	@Override
	public VagaModel save(VagaModel vaga) {

		return repository.save(vaga);
	}

	// @Override
	// public Vaga findById(int id) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	// @Override
	// public Vaga save(Vaga categoria) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	// @Autowired
	// RepositoryVaga repository;

	// @Override
	// public List<Categorias> findAll() {
	// return repository.findAll();
	// }

	// @Override
	// public Categorias findById(int id) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	// @Override
	// public Categorias save(Categorias categoria) {
	// // TODO Auto-generated method stub
	// return null;
	// }
}
