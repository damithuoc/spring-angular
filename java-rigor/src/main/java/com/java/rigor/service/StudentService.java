package com.java.rigor.service;

import com.java.rigor.entity.Student;
import com.java.rigor.exception.JavaRigorException;

import java.util.List;

/**
 * Created by sanandasena on 1/11/2016.
 */
public interface StudentService {

    List<Student> getAllActiveStudents() throws JavaRigorException;

    void saveStudent(Student student) throws JavaRigorException;

    void updateStudent(Student student) throws JavaRigorException;

    Boolean deleteStudent(Long id) throws JavaRigorException;

    Student getStudentByUsingRegNumber(String regNumber) throws JavaRigorException;

    Student getStudentById(Long id) throws JavaRigorException;

    Boolean sendEmail(String email) throws JavaRigorException;

    List<Long> getAllActiveStudentIds() throws JavaRigorException;

}
