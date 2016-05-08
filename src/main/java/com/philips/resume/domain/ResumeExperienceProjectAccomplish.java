package com.philips.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ResumeExperienceProjectAccomplish.
 */
@Entity
@Table(name = "resume_experience_project_accomplish")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "resumeexperienceprojectaccomplish")
public class ResumeExperienceProjectAccomplish implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 1024)
    @Column(name = "accomplish", length = 1024)
    private String accomplish;

    @JsonIgnore
    @ManyToOne
    private ResumeExperienceProject project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccomplish() {
        return accomplish;
    }

    public void setAccomplish(String accomplish) {
        this.accomplish = accomplish;
    }

    public ResumeExperienceProject getProject() {
        return project;
    }

    public void setProject(ResumeExperienceProject resumeExperienceProject) {
        this.project = resumeExperienceProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResumeExperienceProjectAccomplish resumeExperienceProjectAccomplish = (ResumeExperienceProjectAccomplish) o;
        if(resumeExperienceProjectAccomplish.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, resumeExperienceProjectAccomplish.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ResumeExperienceProjectAccomplish{" +
            "id=" + id +
            ", accomplish='" + accomplish + "'" +
            '}';
    }
}
