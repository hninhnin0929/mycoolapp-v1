package com.lvu2code.springboot.demo.mycoolapp.rest;

import com.lvu2code.springboot.demo.mycoolapp.common.Coach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    // define a private field for the dependency
    private Coach myCoach;

    // define a constructor for dependency injection
//    @Autowired  //@Autowired annotation tells Spring to inject a dependency
//    public DemoController(Coach theCoach){
//        myCoach = theCoach;
//    }

    //    @Autowired
//    public void setCoach(Coach theCoach){
//        myCoach = theCoach;
//    }
    @Autowired
    public DemoController(@Qualifier("cricketCoach") Coach theCoach){

        System.out.println("In constructor: " + getClass().getSimpleName());

        myCoach = theCoach;
    }


    @GetMapping("/dailyworkout")
    public String getDailyWorkout(){
        return myCoach.getDailyWorkout();
    }
}
