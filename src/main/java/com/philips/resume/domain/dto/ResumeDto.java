package com.philips.resume.domain.dto;

import com.philips.resume.domain.ResumeEducation;
import com.philips.resume.domain.ResumeExperience;
import com.philips.resume.domain.ResumePaper;
import com.philips.resume.domain.User;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 310031267 on 2016/5/8.
 */
public class ResumeDto {
    private Long id;
    private String infoEmail;
    private String infoPhone;
    private String infoGithub;
    private String infoLinkedin;
    private String profileBasic;
    private String profileTechniqueDomain;
    private String profileSoftwareSystem;
    private String profileMultibranchExperience;
    private String profilePreferredPosition;
    private User user;
    private Set<ResumeExperience> experiences = new HashSet<>();
    private Set<ResumeSkillDto> skills = new HashSet<>();
    private Set<ResumePaper> papers = new HashSet<>();
    private Set<ResumeEducation> educatons = new HashSet<>();



}
