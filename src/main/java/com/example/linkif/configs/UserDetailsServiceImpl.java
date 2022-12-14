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

import com.example.linkif.model.UserModel;
import com.example.linkif.repository.UserRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

  final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserModel userModel = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    if (userModel.getUsername() == null) {

      throw new UsernameNotFoundException("Usuario não encontrado!");
    }
    System.out.println("///////////" + userModel.getUsername());
    System.out.println("///////////" + userModel.getPassword());
    System.out.println("///////////" + userModel.getAuthorities());
    return new User(userModel.getUsername(), userModel.getPassword(), true, true, true, true,
        userModel.getAuthorities());
  }

}
