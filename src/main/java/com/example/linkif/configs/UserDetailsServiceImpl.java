package com.example.linkif.configs;

import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.linkif.model.EmpresaModel;
import com.example.linkif.model.UserModel;
import com.example.linkif.repository.EmpresaRepository;
import com.example.linkif.repository.UserRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserRepository userRepository;

  private EmpresaRepository empresaRepository;

  public UserDetailsServiceImpl(UserRepository userRepository, EmpresaRepository empresaRepository) {
    this.userRepository = userRepository;
    this.empresaRepository = empresaRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<EmpresaModel> EmpresaModel = empresaRepository.findByUsername(username);

    if (!EmpresaModel.isEmpty()) {
      return new User(EmpresaModel.get().getUsername(), EmpresaModel.get().getPassword(), true,
          true, true, true,
          EmpresaModel.get().getAuthorities());
    }

    UserModel userModel = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    if (userModel.getUsername() == null) {
      throw new UsernameNotFoundException("Usuario não encontrado!");
    }

    return new User(userModel.getUsername(), userModel.getPassword(), true, true,
        true, true,
        userModel.getAuthorities());

  }
}
