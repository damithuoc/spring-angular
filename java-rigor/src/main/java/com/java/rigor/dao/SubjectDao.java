package com.java.rigor.dao;

import com.java.rigor.entity.Subject;

import java.util.List;

/**
 * Created by sanandasena on 1/29/2016.
 */
public interface SubjectDao {

    Boolean addSubjects(List<Subject> subjectList);

    Boolean saveSubject(Subject subject);

    List<Subject> getAllActiveSubjects();

    List<Long> getAllActiveSubjectIds();

    Boolean updateSubject(Subject subject);

    Boolean deleteSubjectBySubjectId(Long subjectId);

    Subject getSubjectBySubjectId(Long subjectId);
}
