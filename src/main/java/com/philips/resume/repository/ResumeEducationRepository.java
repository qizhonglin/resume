package com.philips.resume.repository;

import com.philips.resume.domain.ResumeEducation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ResumeEducation entity.
 */
public interface ResumeEducationRepository extends JpaRepository<ResumeEducation,Long> {

}
