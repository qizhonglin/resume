package com.philips.resume.repository.search;

import com.philips.resume.domain.ResumeExperienceProject;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ResumeExperienceProject entity.
 */
public interface ResumeExperienceProjectSearchRepository extends ElasticsearchRepository<ResumeExperienceProject, Long> {
}
