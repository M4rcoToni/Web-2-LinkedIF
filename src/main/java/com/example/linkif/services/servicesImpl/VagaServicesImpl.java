package com.example.linkif.services.servicesImpl;

import java.util.List;
import java.util.Optional;

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

	@Override
	public Optional<VagaModel> findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public List<VagaModel> findByTipoLike(int tipo) {
		return repository.findByTipoLike(tipo);
	}

	@Override
	public List<VagaModel> findByempresaNameLike(String empresaName) {
		return repository.findByempresaNameLike(empresaName);
	}

	@Override
	public List<VagaModel> findByusersNamesLike(String user) {

		return repository.findByusersNamesLike(user);
	}

}
