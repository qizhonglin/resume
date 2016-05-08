package com.philips.resume.service;

import com.philips.resume.domain.Resume;
import com.philips.resume.repository.ResumeRepository;
import com.philips.resume.repository.search.ResumeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Resume.
 */
@Service
@Transactional
public class ResumeService {

    private final Logger log = LoggerFactory.getLogger(ResumeService.class);

    @Inject
    private ResumeRepository resumeRepository;

    @Inject
    private ResumeSearchRepository resumeSearchRepository;



    /**
     * Save a resume.
     *
     * @param resume the entity to save
     * @return the persisted entity
     */
    public Resume save(Resume resume) {
        log.debug("Request to save Resume : {}", resume);
        Resume result = resumeRepository.save(resume);
        resumeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the resumes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Resume> findAll() {
        log.debug("Request to get all Resumes");
        List<Resume> resumes = resumeRepository.findAll();
        resumes.forEach(resume -> {
            resume.getExperiences().forEach(experience -> {
                experience.getProjects().forEach(project -> {
                    project.getAccomplishes().size();
                });
            });
            resume.getSkills().size();
            resume.getPapers().size();
            resume.getEducatons().size();
        });
        return resumes;
    }

    /**
     *  Get one resume by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Resume findOne(Long id) {
        log.debug("Request to get Resume : {}", id);
        Resume resume = resumeRepository.findOne(id);
        return resume;
    }

    /**
     *  Delete the  resume by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Resume : {}", id);
        resumeRepository.delete(id);
        resumeSearchRepository.delete(id);
    }

    /**
     * Search for the resume corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Resume> search(String query) {
        log.debug("Request to search Resumes for query {}", query);
        return StreamSupport
            .stream(resumeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
