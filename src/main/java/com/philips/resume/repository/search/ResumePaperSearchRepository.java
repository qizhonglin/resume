package com.philips.resume.repository.search;

import com.philips.resume.domain.ResumePaper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ResumePaper entity.
 */
public interface ResumePaperSearchRepository extends ElasticsearchRepository<ResumePaper, Long> {
}
