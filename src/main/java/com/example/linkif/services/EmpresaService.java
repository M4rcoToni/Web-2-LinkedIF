package com.example.linkif.services;

import java.util.List;

import com.example.linkif.model.EmpresaModel;

public interface EmpresaService {
  List<EmpresaModel> findAll();

  EmpresaModel findById(int id);

  EmpresaModel save(EmpresaModel empresa);

}
