package com.ead.course.configs;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {//Classe de configuração
    @LoadBalanced //Define para utilizar balanceamento de carga do microservice authuser.
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){//Metodo produtor que retorna um RestTemplate.
        return builder.build();
    }
}
