package com.java.rigor.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.java.rigor.to.BaseClass;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

/**
 * Created by sanandasena on 1/27/2016.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subject extends BaseClass implements Serializable {
    private static final long serialVersionUID = -141459562823742154L;

    private Long id;
    private SubjectCode subjectCode;
    private String subjectName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubjectCode getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(SubjectCode subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", subjectCode='" + subjectCode + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (!subjectCode.equals(subject.subjectCode)) return false;
        return subjectName.equals(subject.subjectName);

    }

    @Override
    public int hashCode() {
        int result = subjectCode.hashCode();
        result = 31 * result + subjectName.hashCode();
        return result;
    }
}
