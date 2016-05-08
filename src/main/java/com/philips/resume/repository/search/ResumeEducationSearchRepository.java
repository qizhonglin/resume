package com.philips.resume.repository.search;

import com.philips.resume.domain.ResumeEducation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ResumeEducation entity.
 */
public interface ResumeEducationSearchRepository extends ElasticsearchRepository<ResumeEducation, Long> {
}
