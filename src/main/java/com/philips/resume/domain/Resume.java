package com.philips.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Resume.
 */
@Entity
@Table(name = "resume")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "resume")
public class Resume implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "info_email", nullable = false)
    private String infoEmail;

    @NotNull
    @Column(name = "info_phone", nullable = false)
    private String infoPhone;

    @Column(name = "info_github")
    private String infoGithub;

    @Column(name = "info_linkedin")
    private String infoLinkedin;

    @Size(max = 1024)
    @Column(name = "profile_basic", length = 1024)
    private String profileBasic;

    @Size(max = 1024)
    @Column(name = "profile_technique_domain", length = 1024)
    private String profileTechniqueDomain;

    @Size(max = 1024)
    @Column(name = "profile_software_system", length = 1024)
    private String profileSoftwareSystem;

    @Size(max = 1024)
    @Column(name = "profile_multibranch_experience", length = 1024)
    private String profileMultibranchExperience;

    @Size(max = 1024)
    @Column(name = "profile_preferred_position", length = 1024)
    private String profilePreferredPosition;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "resume")
    //@JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ResumeExperience> experiences = new HashSet<>();

    @OneToMany(mappedBy = "resume")
    //@JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ResumeSkill> skills = new HashSet<>();

    @OneToMany(mappedBy = "resume")
    //@JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ResumePaper> papers = new HashSet<>();

    @OneToMany(mappedBy = "resume")
    //@JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ResumeEducation> educatons = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfoEmail() {
        return infoEmail;
    }

    public void setInfoEmail(String infoEmail) {
        this.infoEmail = infoEmail;
    }

    public String getInfoPhone() {
        return infoPhone;
    }

    public void setInfoPhone(String infoPhone) {
        this.infoPhone = infoPhone;
    }

    public String getInfoGithub() {
        return infoGithub;
    }

    public void setInfoGithub(String infoGithub) {
        this.infoGithub = infoGithub;
    }

    public String getInfoLinkedin() {
        return infoLinkedin;
    }

    public void setInfoLinkedin(String infoLinkedin) {
        this.infoLinkedin = infoLinkedin;
    }

    public String getProfileBasic() {
        return profileBasic;
    }

    public void setProfileBasic(String profileBasic) {
        this.profileBasic = profileBasic;
    }

    public String getProfileTechniqueDomain() {
        return profileTechniqueDomain;
    }

    public void setProfileTechniqueDomain(String profileTechniqueDomain) {
        this.profileTechniqueDomain = profileTechniqueDomain;
    }

    public String getProfileSoftwareSystem() {
        return profileSoftwareSystem;
    }

    public void setProfileSoftwareSystem(String profileSoftwareSystem) {
        this.profileSoftwareSystem = profileSoftwareSystem;
    }

    public String getProfileMultibranchExperience() {
        return profileMultibranchExperience;
    }

    public void setProfileMultibranchExperience(String profileMultibranchExperience) {
        this.profileMultibranchExperience = profileMultibranchExperience;
    }

    public String getProfilePreferredPosition() {
        return profilePreferredPosition;
    }

    public void setProfilePreferredPosition(String profilePreferredPosition) {
        this.profilePreferredPosition = profilePreferredPosition;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<ResumeExperience> getExperiences() {
        return experiences;
    }

    public void setExperiences(Set<ResumeExperience> resumeExperiences) {
        this.experiences = resumeExperiences;
    }

    public Set<ResumeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<ResumeSkill> resumeSkills) {
        this.skills = resumeSkills;
    }

    public Set<ResumePaper> getPapers() {
        return papers;
    }

    public void setPapers(Set<ResumePaper> resumePapers) {
        this.papers = resumePapers;
    }

    public Set<ResumeEducation> getEducatons() {
        return educatons;
    }

    public void setEducatons(Set<ResumeEducation> resumeEducations) {
        this.educatons = resumeEducations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Resume resume = (Resume) o;
        if(resume.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, resume.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Resume{" +
            "id=" + id +
            ", infoEmail='" + infoEmail + "'" +
            ", infoPhone='" + infoPhone + "'" +
            ", infoGithub='" + infoGithub + "'" +
            ", infoLinkedin='" + infoLinkedin + "'" +
            ", profileBasic='" + profileBasic + "'" +
            ", profileTechniqueDomain='" + profileTechniqueDomain + "'" +
            ", profileSoftwareSystem='" + profileSoftwareSystem + "'" +
            ", profileMultibranchExperience='" + profileMultibranchExperience + "'" +
            ", profilePreferredPosition='" + profilePreferredPosition + "'" +
            '}';
    }
}
