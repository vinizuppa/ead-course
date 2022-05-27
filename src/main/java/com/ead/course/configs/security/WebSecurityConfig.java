package com.ead.course.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//Define que essa é a classe de configuração da instancia global de autentication manager
@EnableWebSecurity//Desliga configurações DEFAULT do spring security
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationEntryPointImpl authenticationEntryPoint;


    @Bean
    public RoleHierarchy roleHierarchy(){//Metodo que define prioridade de hierarquia das ROLES.
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_INSTRUCTOR \n ROLE_INSTRUCTOR > ROLE_STUDENT \n ROLE_STUDENT > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public AuthenticationJwtFilter authenticationJwtFilter(){
        return new AuthenticationJwtFilter();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception{//Esse metodo
        http
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)//define tipo Http
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()//Qualquer uma das requisições, precisarão estar autorizadas
                .anyRequest().authenticated()//Qualquer uma das requisições, precisarão estar autenticadas
                .and()
                .csrf().disable();//Desabilita CSRF
        http.addFilterBefore(authenticationJwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.inMemoryAuthentication()//Define tipo de autenticação.
//                .withUser("admin")//Passamos usuário padrão
//                .password(passwordEncoder().encode("123456"))//passamos senha padrão
//                .roles("ADMIN");
//    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
       //Metodo fica vazio, pois como estamos extendendo nessa classe WebSecurityConfigurerAdapter, é obrigatório definirmos esse metodo.
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
