package com.lvu2code.springboot.demo.mycoolapp.rest;

import com.lvu2code.springboot.demo.mycoolapp.entity.Student;
import com.lvu2code.springboot.demo.mycoolapp.entity.StudentData;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<StudentData> theStudents;

    // define @PostConstruct to load the student data ... only once!
    @PostConstruct
    public void loadData() {
        System.out.println("@PostConstruct Working---");
        theStudents = new ArrayList<>();

        theStudents.add(new StudentData("Poornima", "Patel"));
        theStudents.add(new StudentData("Mario", "Rossi"));
        theStudents.add(new StudentData("Mary", "Smith"));
    }

    // define endpoint for "/students" - return a list of students

    @GetMapping("/students")
    public List<StudentData> getStudents() {

        return  theStudents;
    }

    // define endpoint or "/students/{studentId}" -return student at index

    @GetMapping("/students/{studentId}")
    public StudentData getStudent(@PathVariable int studentId) {

        // just index into the list ... keep it simple for now
         return theStudents.get(studentId);
    }
}
