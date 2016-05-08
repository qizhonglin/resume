package com.philips.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.philips.resume.domain.ResumeEducation;
import com.philips.resume.repository.ResumeEducationRepository;
import com.philips.resume.repository.search.ResumeEducationSearchRepository;
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
 * REST controller for managing ResumeEducation.
 */
@RestController
@RequestMapping("/api")
public class ResumeEducationResource {

    private final Logger log = LoggerFactory.getLogger(ResumeEducationResource.class);
        
    @Inject
    private ResumeEducationRepository resumeEducationRepository;
    
    @Inject
    private ResumeEducationSearchRepository resumeEducationSearchRepository;
    
    /**
     * POST  /resume-educations : Create a new resumeEducation.
     *
     * @param resumeEducation the resumeEducation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resumeEducation, or with status 400 (Bad Request) if the resumeEducation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/resume-educations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumeEducation> createResumeEducation(@Valid @RequestBody ResumeEducation resumeEducation) throws URISyntaxException {
        log.debug("REST request to save ResumeEducation : {}", resumeEducation);
        if (resumeEducation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("resumeEducation", "idexists", "A new resumeEducation cannot already have an ID")).body(null);
        }
        ResumeEducation result = resumeEducationRepository.save(resumeEducation);
        resumeEducationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/resume-educations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("resumeEducation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resume-educations : Updates an existing resumeEducation.
     *
     * @param resumeEducation the resumeEducation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resumeEducation,
     * or with status 400 (Bad Request) if the resumeEducation is not valid,
     * or with status 500 (Internal Server Error) if the resumeEducation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/resume-educations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumeEducation> updateResumeEducation(@Valid @RequestBody ResumeEducation resumeEducation) throws URISyntaxException {
        log.debug("REST request to update ResumeEducation : {}", resumeEducation);
        if (resumeEducation.getId() == null) {
            return createResumeEducation(resumeEducation);
        }
        ResumeEducation result = resumeEducationRepository.save(resumeEducation);
        resumeEducationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("resumeEducation", resumeEducation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resume-educations : get all the resumeEducations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of resumeEducations in body
     */
    @RequestMapping(value = "/resume-educations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ResumeEducation> getAllResumeEducations() {
        log.debug("REST request to get all ResumeEducations");
        List<ResumeEducation> resumeEducations = resumeEducationRepository.findAll();
        return resumeEducations;
    }

    /**
     * GET  /resume-educations/:id : get the "id" resumeEducation.
     *
     * @param id the id of the resumeEducation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resumeEducation, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/resume-educations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumeEducation> getResumeEducation(@PathVariable Long id) {
        log.debug("REST request to get ResumeEducation : {}", id);
        ResumeEducation resumeEducation = resumeEducationRepository.findOne(id);
        return Optional.ofNullable(resumeEducation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /resume-educations/:id : delete the "id" resumeEducation.
     *
     * @param id the id of the resumeEducation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/resume-educations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteResumeEducation(@PathVariable Long id) {
        log.debug("REST request to delete ResumeEducation : {}", id);
        resumeEducationRepository.delete(id);
        resumeEducationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("resumeEducation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/resume-educations?query=:query : search for the resumeEducation corresponding
     * to the query.
     *
     * @param query the query of the resumeEducation search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/resume-educations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ResumeEducation> searchResumeEducations(@RequestParam String query) {
        log.debug("REST request to search ResumeEducations for query {}", query);
        return StreamSupport
            .stream(resumeEducationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
