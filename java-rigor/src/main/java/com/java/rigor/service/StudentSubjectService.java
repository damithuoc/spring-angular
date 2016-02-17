package com.java.rigor.service;

import com.java.rigor.entity.Student;
import com.java.rigor.entity.Subject;
import com.java.rigor.exception.JavaRigorException;

import java.util.Collection;
import java.util.List;

/**
 * Created by sanandasena on 2/2/2016.
 */
public interface StudentSubjectService {

    Boolean addStudentSubjectsByUsingStudentId(Long studentId, List<Long> subjectIdList) throws JavaRigorException;

    Collection<Student> getAllStudentWithSubjects() throws JavaRigorException;

    Boolean editStudentSubjects(Long studentId, List<Subject> subjectIdList) throws JavaRigorException;

    Student getStudentWithSubjects(Long studentId) throws JavaRigorException;

    Boolean deleteStudentSubject(Long studentId, List<Long> subjectIdList) throws JavaRigorException;

}
