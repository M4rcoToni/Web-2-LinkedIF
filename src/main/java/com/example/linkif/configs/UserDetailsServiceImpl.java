package com.example.linkif.configs;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    EmpresaModel EmpresaModel = empresaRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    System.out.println(username);

    if (EmpresaModel.getUsername() == null) {
      throw new UsernameNotFoundException("Usuario não encontrado!");
    } else if (EmpresaModel.getUsername() != null) {
      // System.out.println("///////////" + EmpresaModel.getUsername());
      // System.out.println("///////////" + EmpresaModel.getPassword());
      // System.out.println("///////////" + EmpresaModel.getAuthorities());
      return new User(EmpresaModel.getUsername(), EmpresaModel.getPassword(), true, true, true, true,
          EmpresaModel.getAuthorities());
    }

    UserModel userModel = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    if (userModel.getUsername() == null) {
      throw new UsernameNotFoundException("Usuario não encontrado!");
    }
    // System.out.println("///////////" + userModel.getUsername());
    // System.out.println("///////////" + userModel.getPassword());
    // System.out.println("///////////" + userModel.getAuthorities());
    return new User(userModel.getUsername(), userModel.getPassword(), true, true,
        true, true,
        userModel.getAuthorities());

  }

}
