package com.example.linkif.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// // @Configuration
// public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

// // salvar usuarios no banco de dados
// final UserDetailsServiceImpl userDetailsService;

// public SecurityConfiguration(UserDetailsServiceImpl userDetailsService) {
// this.userDetailsService = userDetailsService;
// }

// @Override
// protected void configure(HttpSecurity http) throws Exception {
// http
// .httpBasic()
// .and()
// .authorizeRequests()
// .antMatchers(HttpMethod.GET, "/index/**").hasAnyRole("USER", "ADMIN")
// // .antMatchers(HttpMethod.GET, "/save/").hasRole("USER") // hasAnyRoles
// // .antMatchers(HttpMethod.POST, "/save/").hasRole("USER") // hasAnyRoles
// // .antMatchers(HttpMethod.DELETE, "/index/**").hasRole("ADMIN")
// // .antMatchers(HttpMethod.GET, "/index/categorias/**").hasRole("ADMIN")
// .anyRequest().authenticated()
// .and()
// .formLogin()
// // .loginPage("/login")
// // .defaultSuccessUrl("/index")
// // .usernameParameter("username")
// // .passwordParameter("password").permitAll()
// // .and()
// // .logout()
// // .logoutRequestMatcher(new
// // AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
// // .deleteCookies("JSESSIONID")
// .and()
// .csrf().disable();

// }

// @Override
// protected void configure(AuthenticationManagerBuilder auth) throws Exception
// {
// auth.userDetailsService(userDetailsService)
// .passwordEncoder(passwordEncoder());
// }

// @Bean
// public PasswordEncoder passwordEncoder() {
// return new BCryptPasswordEncoder();
// }

// }

// // @Override
// // protected void configure(AuthenticationManagerBuilder auth) throws
// Exception{
// // auth.inMemoryAuthentication()
// // .withUser("admin").password("{noop}123").roles("ADMIN");
// // }

// // @Override
// // protected void configure(AuthenticationManagerBuilder auth) throws
// Exception
// // {
// // auth.inMemoryAuthentication()
// // .withUser("admin")
// // .password(passwordEncoder().encode("123"))
// // .roles("ADMIN");
// // }