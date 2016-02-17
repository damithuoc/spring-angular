package com.java.rigor.dao;

import com.java.rigor.entity.Student;

import java.util.Collection;
import java.util.List;

/**
 * Created by sanandasena on 2/2/2016.
 */
public interface StudentSubjectDao {

    Boolean addStudentSubjectsByUsingStudentId(Long studentId, List<Long> subjectIdList);

    Collection<Student> getAllStudentWithSubjects();

    Student getStudentWithSubjects(Long studentId);

    Boolean deleteStudentSubject(Long studentId, List<Long> subjectIdList);
}
