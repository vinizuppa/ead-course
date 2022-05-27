package com.ead.course.configs.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationCurrentUserService {//Classe que extrai informações do usuário que está enviando solicitações para um end-point

    public UserDetailsImp getCurrentUser(){
        return (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
