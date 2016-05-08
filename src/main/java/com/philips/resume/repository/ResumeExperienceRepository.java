package com.philips.resume.repository;

import com.philips.resume.domain.ResumeExperience;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ResumeExperience entity.
 */
public interface ResumeExperienceRepository extends JpaRepository<ResumeExperience,Long> {

}
