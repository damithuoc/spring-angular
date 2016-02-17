package com.java.rigor.dao;

import com.java.rigor.entity.Student;

import java.util.List;

/**
 * Created by sanandasena on 1/11/2016.
 */

public interface StudentDao {

    List<Student> getAllActiveStudents();

    void saveStudent(Student student);

    void updateStudent(Student student);

    Boolean deleteStudent(Student student);

    Student getStudentByUsingRegNumber(String regNumber);

    Student getStudentById(Long studentId);

    List<Long> getAllStudentIds();

    List<Long> getAllActiveStudentIds();

    Boolean deleteOldStudentData(Long lastUpdatedDate);

}
