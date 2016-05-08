package com.philips.resume.repository;

import com.philips.resume.domain.ResumeExperienceProject;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ResumeExperienceProject entity.
 */
public interface ResumeExperienceProjectRepository extends JpaRepository<ResumeExperienceProject,Long> {

}
