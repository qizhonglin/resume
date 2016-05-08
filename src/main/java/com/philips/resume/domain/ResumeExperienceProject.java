package com.philips.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ResumeExperienceProject.
 */
@Entity
@Table(name = "resume_experience_project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "resumeexperienceproject")
public class ResumeExperienceProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "index", nullable = false)
    private Integer index;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @NotNull
    @Size(max = 1024)
    @Column(name = "introduction", length = 1024, nullable = false)
    private String introduction;

    @NotNull
    @Size(max = 1024)
    @Column(name = "responsiility", length = 1024, nullable = false)
    private String responsiility;

    @NotNull
    @Size(max = 1024)
    @Column(name = "platform", length = 1024, nullable = false)
    private String platform;

    @OneToMany(mappedBy = "project")
    //@JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ResumeExperienceProjectAccomplish> accomplishes = new HashSet<>();

    @JsonIgnore
    @ManyToOne
    private ResumeExperience experience;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getResponsiility() {
        return responsiility;
    }

    public void setResponsiility(String responsiility) {
        this.responsiility = responsiility;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Set<ResumeExperienceProjectAccomplish> getAccomplishes() {
        return accomplishes;
    }

    public void setAccomplishes(Set<ResumeExperienceProjectAccomplish> resumeExperienceProjectAccomplishes) {
        this.accomplishes = resumeExperienceProjectAccomplishes;
    }

    public ResumeExperience getExperience() {
        return experience;
    }

    public void setExperience(ResumeExperience resumeExperience) {
        this.experience = resumeExperience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResumeExperienceProject resumeExperienceProject = (ResumeExperienceProject) o;
        if(resumeExperienceProject.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, resumeExperienceProject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ResumeExperienceProject{" +
            "id=" + id +
            ", index='" + index + "'" +
            ", name='" + name + "'" +
            ", startTime='" + startTime + "'" +
            ", endTime='" + endTime + "'" +
            ", introduction='" + introduction + "'" +
            ", responsiility='" + responsiility + "'" +
            ", platform='" + platform + "'" +
            '}';
    }
}
