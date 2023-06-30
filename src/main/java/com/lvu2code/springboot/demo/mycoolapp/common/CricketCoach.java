package com.lvu2code.springboot.demo.mycoolapp.common;

import org.springframework.stereotype.Component;

@Component  // @Component annotation marks the class as a Spring bean
public class CricketCoach implements Coach {

    public CricketCoach(){
        System.out.println("In constructor: " + getClass().getSimpleName());
    }
    @Override
    public String getDailyWorkout() {
        return "Practice fast bowling for 15 minutes. NOW!!!!";
    }
}
