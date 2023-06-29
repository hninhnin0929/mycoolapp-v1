package com.lvu2code.springboot.demo.mycoolapp.learninjection;

import com.lvu2code.springboot.demo.mycoolapp.learninjection.Coach;
import org.springframework.stereotype.Component;

@Component  // @Component annotation marks the class as a Spring bean
public class CricketCoach implements Coach {

    @Override
    public String getDailyWorkout() {
        return "Practice fast bowling for 15 minutes. NOW!!!!";
    }
}
