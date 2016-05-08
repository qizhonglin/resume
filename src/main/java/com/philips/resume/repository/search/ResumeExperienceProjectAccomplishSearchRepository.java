package com.philips.resume.repository.search;

import com.philips.resume.domain.ResumeExperienceProjectAccomplish;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ResumeExperienceProjectAccomplish entity.
 */
public interface ResumeExperienceProjectAccomplishSearchRepository extends ElasticsearchRepository<ResumeExperienceProjectAccomplish, Long> {
}
