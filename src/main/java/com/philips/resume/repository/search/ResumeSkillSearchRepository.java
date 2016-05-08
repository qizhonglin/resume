package com.philips.resume.repository.search;

import com.philips.resume.domain.ResumeSkill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ResumeSkill entity.
 */
public interface ResumeSkillSearchRepository extends ElasticsearchRepository<ResumeSkill, Long> {
}
