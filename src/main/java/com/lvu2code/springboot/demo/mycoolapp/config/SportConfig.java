package com.lvu2code.springboot.demo.mycoolapp.config;

import com.lvu2code.springboot.demo.mycoolapp.common.Coach;
import com.lvu2code.springboot.demo.mycoolapp.common.SwimCoach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SportConfig {

    @Bean
    public Coach swimCoach() {
        return new SwimCoach();
    }
}
