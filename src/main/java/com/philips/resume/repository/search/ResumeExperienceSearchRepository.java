package com.philips.resume.repository.search;

import com.philips.resume.domain.ResumeExperience;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ResumeExperience entity.
 */
public interface ResumeExperienceSearchRepository extends ElasticsearchRepository<ResumeExperience, Long> {
}
