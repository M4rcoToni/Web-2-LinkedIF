package com.example.linkif.configs;

import org.apache.tomcat.jni.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurationV2 {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()

                .antMatchers(HttpMethod.GET, "/vagas").permitAll()
                .antMatchers(HttpMethod.GET, "/index").hasAnyRole("USER", "EMPRESA")

                .antMatchers(HttpMethod.GET, "/cadastro/user").permitAll()
                .antMatchers(HttpMethod.GET, "/cadastro/empresa").permitAll()
                .antMatchers(HttpMethod.GET, "/cadastro/vaga").hasRole("EMPRESA")

                .antMatchers(HttpMethod.POST, "/cadastro/user").permitAll()
                .antMatchers(HttpMethod.POST, "/cadastro/empresa").permitAll()
                .antMatchers(HttpMethod.POST, "/cadastro/VAGA").hasRole("EMPRESA")

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
