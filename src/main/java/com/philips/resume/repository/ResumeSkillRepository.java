package com.philips.resume.repository;

import com.philips.resume.domain.ResumeSkill;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ResumeSkill entity.
 */
public interface ResumeSkillRepository extends JpaRepository<ResumeSkill,Long> {

}
