package com.example.linkif.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigurationV2 {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()

                .antMatchers(HttpMethod.GET, "/vagas").permitAll()
                .antMatchers(HttpMethod.GET, "/cadastro/user").permitAll()
                .antMatchers(HttpMethod.GET, "/cadastro/empresa").permitAll()
                .antMatchers(HttpMethod.GET, "/cadastro/vaga").hasRole("EMPRESA")
                .antMatchers(HttpMethod.GET, "/vagas/**").permitAll()
                .antMatchers(HttpMethod.GET, "/vagas/candidata/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/img/**").permitAll()
                .antMatchers(HttpMethod.GET, "/empresa/**").hasRole("EMPRESA")

                .antMatchers(HttpMethod.GET, "/vagas/pesquisar").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/empresa/candidatos/**").hasRole("EMPRESA")
                .antMatchers(HttpMethod.POST, "/empresa/**").hasRole("EMPRESA")
                .antMatchers(HttpMethod.POST, "/cadastro/user").permitAll()
                .antMatchers(HttpMethod.POST, "/cadastro/empresa").permitAll()
                .antMatchers(HttpMethod.POST, "/cadastro/vagas").hasRole("EMPRESA")

                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/vagas")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout().clearAuthentication(true).permitAll()
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
