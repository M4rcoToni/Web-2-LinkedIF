package com.example.linkif.services;

import java.util.List;
import java.util.Optional;

import com.example.linkif.model.VagaModel;

public interface VagaServices {
	List<VagaModel> findAll();

	Optional<VagaModel> findById(Integer id);

	List<VagaModel> findByusersNamesLike(String user);

	VagaModel save(VagaModel vaga);

	List<VagaModel> findByTipoLike(int tipo);

	List<VagaModel> findByempresaNameLike(String empresaName);

}
