package com.philips.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.philips.resume.domain.ResumeExperienceProjectAccomplish;
import com.philips.resume.repository.ResumeExperienceProjectAccomplishRepository;
import com.philips.resume.repository.search.ResumeExperienceProjectAccomplishSearchRepository;
import com.philips.resume.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ResumeExperienceProjectAccomplish.
 */
@RestController
@RequestMapping("/api")
public class ResumeExperienceProjectAccomplishResource {

    private final Logger log = LoggerFactory.getLogger(ResumeExperienceProjectAccomplishResource.class);
        
    @Inject
    private ResumeExperienceProjectAccomplishRepository resumeExperienceProjectAccomplishRepository;
    
    @Inject
    private ResumeExperienceProjectAccomplishSearchRepository resumeExperienceProjectAccomplishSearchRepository;
    
    /**
     * POST  /resume-experience-project-accomplishes : Create a new resumeExperienceProjectAccomplish.
     *
     * @param resumeExperienceProjectAccomplish the resumeExperienceProjectAccomplish to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resumeExperienceProjectAccomplish, or with status 400 (Bad Request) if the resumeExperienceProjectAccomplish has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/resume-experience-project-accomplishes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumeExperienceProjectAccomplish> createResumeExperienceProjectAccomplish(@Valid @RequestBody ResumeExperienceProjectAccomplish resumeExperienceProjectAccomplish) throws URISyntaxException {
        log.debug("REST request to save ResumeExperienceProjectAccomplish : {}", resumeExperienceProjectAccomplish);
        if (resumeExperienceProjectAccomplish.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("resumeExperienceProjectAccomplish", "idexists", "A new resumeExperienceProjectAccomplish cannot already have an ID")).body(null);
        }
        ResumeExperienceProjectAccomplish result = resumeExperienceProjectAccomplishRepository.save(resumeExperienceProjectAccomplish);
        resumeExperienceProjectAccomplishSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/resume-experience-project-accomplishes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("resumeExperienceProjectAccomplish", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resume-experience-project-accomplishes : Updates an existing resumeExperienceProjectAccomplish.
     *
     * @param resumeExperienceProjectAccomplish the resumeExperienceProjectAccomplish to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resumeExperienceProjectAccomplish,
     * or with status 400 (Bad Request) if the resumeExperienceProjectAccomplish is not valid,
     * or with status 500 (Internal Server Error) if the resumeExperienceProjectAccomplish couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/resume-experience-project-accomplishes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumeExperienceProjectAccomplish> updateResumeExperienceProjectAccomplish(@Valid @RequestBody ResumeExperienceProjectAccomplish resumeExperienceProjectAccomplish) throws URISyntaxException {
        log.debug("REST request to update ResumeExperienceProjectAccomplish : {}", resumeExperienceProjectAccomplish);
        if (resumeExperienceProjectAccomplish.getId() == null) {
            return createResumeExperienceProjectAccomplish(resumeExperienceProjectAccomplish);
        }
        ResumeExperienceProjectAccomplish result = resumeExperienceProjectAccomplishRepository.save(resumeExperienceProjectAccomplish);
        resumeExperienceProjectAccomplishSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("resumeExperienceProjectAccomplish", resumeExperienceProjectAccomplish.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resume-experience-project-accomplishes : get all the resumeExperienceProjectAccomplishes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of resumeExperienceProjectAccomplishes in body
     */
    @RequestMapping(value = "/resume-experience-project-accomplishes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ResumeExperienceProjectAccomplish> getAllResumeExperienceProjectAccomplishes() {
        log.debug("REST request to get all ResumeExperienceProjectAccomplishes");
        List<ResumeExperienceProjectAccomplish> resumeExperienceProjectAccomplishes = resumeExperienceProjectAccomplishRepository.findAll();
        return resumeExperienceProjectAccomplishes;
    }

    /**
     * GET  /resume-experience-project-accomplishes/:id : get the "id" resumeExperienceProjectAccomplish.
     *
     * @param id the id of the resumeExperienceProjectAccomplish to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resumeExperienceProjectAccomplish, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/resume-experience-project-accomplishes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumeExperienceProjectAccomplish> getResumeExperienceProjectAccomplish(@PathVariable Long id) {
        log.debug("REST request to get ResumeExperienceProjectAccomplish : {}", id);
        ResumeExperienceProjectAccomplish resumeExperienceProjectAccomplish = resumeExperienceProjectAccomplishRepository.findOne(id);
        return Optional.ofNullable(resumeExperienceProjectAccomplish)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /resume-experience-project-accomplishes/:id : delete the "id" resumeExperienceProjectAccomplish.
     *
     * @param id the id of the resumeExperienceProjectAccomplish to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/resume-experience-project-accomplishes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteResumeExperienceProjectAccomplish(@PathVariable Long id) {
        log.debug("REST request to delete ResumeExperienceProjectAccomplish : {}", id);
        resumeExperienceProjectAccomplishRepository.delete(id);
        resumeExperienceProjectAccomplishSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("resumeExperienceProjectAccomplish", id.toString())).build();
    }

    /**
     * SEARCH  /_search/resume-experience-project-accomplishes?query=:query : search for the resumeExperienceProjectAccomplish corresponding
     * to the query.
     *
     * @param query the query of the resumeExperienceProjectAccomplish search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/resume-experience-project-accomplishes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ResumeExperienceProjectAccomplish> searchResumeExperienceProjectAccomplishes(@RequestParam String query) {
        log.debug("REST request to search ResumeExperienceProjectAccomplishes for query {}", query);
        return StreamSupport
            .stream(resumeExperienceProjectAccomplishSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
