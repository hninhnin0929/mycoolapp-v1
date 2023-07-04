package com.lvu2code.springboot.demo.mycoolapp.dao;

import com.lvu2code.springboot.demo.mycoolapp.entity.Student;

import java.util.List;

public interface StudentDAO {

    void save(Student theStudent);

    Student findById(Integer id);

    List<Student> findAll();

    List<Student> findByLastName(String theLastName);

}
