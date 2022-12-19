package com.example.linkif.services;

import java.util.List;

import com.example.linkif.model.VagaModel;

public interface VagaServices {
	List<VagaModel> findAll();

	// Vaga findById(int id);

	VagaModel save(VagaModel vaga);
}
