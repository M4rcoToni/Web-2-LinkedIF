package com.example.linkif.services.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.linkif.model.EmpresaModel;
import com.example.linkif.repository.EmpresaRepository;
import com.example.linkif.services.EmpresaService;

@Service
public class EmpresaServicesImpl implements EmpresaService {

  @Autowired
  EmpresaRepository repository;

  @Override
  public List<EmpresaModel> findAll() {

    return repository.findAll();
  }

  @Override
  public EmpresaModel save(EmpresaModel empresa) {

    return repository.save(empresa);
  }

  @Override
  public EmpresaModel findById(int id) {
    return repository.findById(id).get();
  }

}
