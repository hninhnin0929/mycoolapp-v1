package com.lvu2code.springboot.demo.mycoolapp.rest;

import com.lvu2code.springboot.demo.mycoolapp.entity.Student;
import com.lvu2code.springboot.demo.mycoolapp.entity.StudentData;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        // check the studentId again list size

        if ( (studentId >= theStudents.size()) || (studentId <0)) {
            throw new StudentNotFoundException("Student id not found - " + studentId);
        }

         return theStudents.get(studentId);
    }

    // Add an exception handler using @ExceptionHandler

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc) {

        // create a StudentErrorResponse

        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
