package com.philips.resume.repository;

import com.philips.resume.domain.Resume;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Resume entity.
 */
public interface ResumeRepository extends JpaRepository<Resume,Long> {

}
