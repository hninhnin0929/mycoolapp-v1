package com.lvu2code.springboot.demo.mycoolapp.service;

import com.lvu2code.springboot.demo.mycoolapp.entity.Student;
import com.lvu2code.springboot.demo.mycoolapp.repository.StudentRepository;
import com.lvu2code.springboot.demo.mycoolapp.rest.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    public void updateResetPasswordToken(String token, String email){
        Student student = studentRepository.findByEmail(email);

        if(student != null){
            student.setResetPasswordToken(token);
            studentRepository.save(student);
        }else {
            throw new StudentNotFoundException("Could not find any student with email " + email);
        }
    }

    public Student get(String resetPasswordToken){
        return studentRepository.findByResetPasswordToken(resetPasswordToken);
    }

    public void updatePassword(Student student, String newPassword){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(newPassword);

        student.setPassword(encodePassword);
        student.setResetPasswordToken(null);

        studentRepository.save(student);
    }

}
