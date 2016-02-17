package com.java.rigor.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

/**
 * Created by sanandasena on 2/1/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubjectCode implements Serializable {
    private static final long serialVersionUID = 3781343655926038728L;
    private String prefix;
    private Integer codeNumber;
    private String subjectCodeStr;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(Integer codeNumber) {
        this.codeNumber = codeNumber;
    }

    public String getSubjectCodeStr() {
        return subjectCodeStr;
    }

    public void setSubjectCodeStr(String prefix, Integer codeNumber) {
        this.prefix = prefix;
        this.codeNumber = codeNumber;
        this.subjectCodeStr = prefix.concat("-").concat(codeNumber.toString());
    }

    @Override
    public String toString() {
        return "SubjectCode{" +
                "prefix='" + prefix + '\'' +
                ", codeNumber=" + codeNumber +
                ", subjectCodeStr='" + subjectCodeStr + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubjectCode that = (SubjectCode) o;

        if (!prefix.equals(that.prefix)) return false;
        return codeNumber.equals(that.codeNumber);

    }

    @Override
    public int hashCode() {
        int result = prefix.hashCode();
        result = 31 * result + codeNumber.hashCode();
        return result;
    }
}
