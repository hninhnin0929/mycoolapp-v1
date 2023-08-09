package com.lvu2code.springboot.demo.mycoolapp.repository;

import com.lvu2code.springboot.demo.mycoolapp.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {

    public Student findByEmail(String email);
    public Student findByResetPasswordToken(String token);

}
