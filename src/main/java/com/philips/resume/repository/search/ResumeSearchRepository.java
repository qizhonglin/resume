package com.philips.resume.repository.search;

import com.philips.resume.domain.Resume;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Resume entity.
 */
public interface ResumeSearchRepository extends ElasticsearchRepository<Resume, Long> {
}
