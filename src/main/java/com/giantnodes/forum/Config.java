package com.giantnodes.forum;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@EnableMongoAuditing
public class Config {

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        System.out.println("Reslover file");

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000);
        return new CommonsMultipartResolver();
    }

}