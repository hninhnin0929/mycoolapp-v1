package com.lvu2code.springboot.demo.mycoolapp.common;

import com.lvu2code.springboot.demo.mycoolapp.common.Coach;
import org.springframework.stereotype.Component;

@Component  // @Component annotation marks the class as a Spring bean
public class CricketCoach implements Coach {

    @Override
    public String getDailyWorkout() {
        return "Practice fast bowling for 15 minutes. NOW!!!!";
    }
}
