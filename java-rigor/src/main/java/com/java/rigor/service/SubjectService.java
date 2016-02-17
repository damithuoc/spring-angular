package com.java.rigor.service;

import com.java.rigor.entity.Subject;
import com.java.rigor.entity.SubjectCode;
import com.java.rigor.exception.JavaRigorException;

import java.util.List;

/**
 * Created by sanandasena on 1/29/2016.
 */
public interface SubjectService {

    Boolean addSubjects(List<Subject> subjectList) throws JavaRigorException;

    Boolean saveSubject(Subject subject) throws JavaRigorException;

    List<Subject> getAllActiveSubjects() throws JavaRigorException;

    List<Long> getAllActiveSubjectIds() throws JavaRigorException;

    List<SubjectCode> getAllSubjectPrefixes() throws JavaRigorException;

    Boolean updateSubject(Subject subject) throws JavaRigorException;

    Boolean deleteSubjectBySubjectId(Long subjectId) throws JavaRigorException;

    Boolean validateSubjectIds(List<Long> subjectIdList) throws JavaRigorException;
}
