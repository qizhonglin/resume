package com.philips.resume.repository;

import com.philips.resume.domain.ResumePaper;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ResumePaper entity.
 */
public interface ResumePaperRepository extends JpaRepository<ResumePaper,Long> {

}
