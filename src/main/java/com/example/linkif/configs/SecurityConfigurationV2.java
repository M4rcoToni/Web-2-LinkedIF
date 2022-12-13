package com.example.linkif.configs;

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
                .antMatchers(HttpMethod.GET, "/cadastro").permitAll()
                .antMatchers(HttpMethod.GET, "/forms/**").permitAll()
                .antMatchers(HttpMethod.POST, "/cadastro").permitAll()
                .antMatchers(HttpMethod.GET, "/index").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/save").hasRole("USER") // hasAnyRole
                .antMatchers(HttpMethod.POST, "/save").hasRole("ADMIN") // hasAnyRole
                // .antMatchers(HttpMethod.POST, "/index/categorias/**").hasRole("ADMIN")
                // .antMatchers(HttpMethod.GET, "/veiculos/**").hasRole("ADMIN")
                // .antMatchers(HttpMethod.DELETE, "/index/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/index")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout().clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
