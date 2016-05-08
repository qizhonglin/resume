package com.philips.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.philips.resume.domain.ResumeExperience;
import com.philips.resume.repository.ResumeExperienceRepository;
import com.philips.resume.repository.search.ResumeExperienceSearchRepository;
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
 * REST controller for managing ResumeExperience.
 */
@RestController
@RequestMapping("/api")
public class ResumeExperienceResource {

    private final Logger log = LoggerFactory.getLogger(ResumeExperienceResource.class);
        
    @Inject
    private ResumeExperienceRepository resumeExperienceRepository;
    
    @Inject
    private ResumeExperienceSearchRepository resumeExperienceSearchRepository;
    
    /**
     * POST  /resume-experiences : Create a new resumeExperience.
     *
     * @param resumeExperience the resumeExperience to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resumeExperience, or with status 400 (Bad Request) if the resumeExperience has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/resume-experiences",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumeExperience> createResumeExperience(@Valid @RequestBody ResumeExperience resumeExperience) throws URISyntaxException {
        log.debug("REST request to save ResumeExperience : {}", resumeExperience);
        if (resumeExperience.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("resumeExperience", "idexists", "A new resumeExperience cannot already have an ID")).body(null);
        }
        ResumeExperience result = resumeExperienceRepository.save(resumeExperience);
        resumeExperienceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/resume-experiences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("resumeExperience", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resume-experiences : Updates an existing resumeExperience.
     *
     * @param resumeExperience the resumeExperience to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resumeExperience,
     * or with status 400 (Bad Request) if the resumeExperience is not valid,
     * or with status 500 (Internal Server Error) if the resumeExperience couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/resume-experiences",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumeExperience> updateResumeExperience(@Valid @RequestBody ResumeExperience resumeExperience) throws URISyntaxException {
        log.debug("REST request to update ResumeExperience : {}", resumeExperience);
        if (resumeExperience.getId() == null) {
            return createResumeExperience(resumeExperience);
        }
        ResumeExperience result = resumeExperienceRepository.save(resumeExperience);
        resumeExperienceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("resumeExperience", resumeExperience.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resume-experiences : get all the resumeExperiences.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of resumeExperiences in body
     */
    @RequestMapping(value = "/resume-experiences",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ResumeExperience> getAllResumeExperiences() {
        log.debug("REST request to get all ResumeExperiences");
        List<ResumeExperience> resumeExperiences = resumeExperienceRepository.findAll();
        return resumeExperiences;
    }

    /**
     * GET  /resume-experiences/:id : get the "id" resumeExperience.
     *
     * @param id the id of the resumeExperience to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resumeExperience, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/resume-experiences/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumeExperience> getResumeExperience(@PathVariable Long id) {
        log.debug("REST request to get ResumeExperience : {}", id);
        ResumeExperience resumeExperience = resumeExperienceRepository.findOne(id);
        return Optional.ofNullable(resumeExperience)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /resume-experiences/:id : delete the "id" resumeExperience.
     *
     * @param id the id of the resumeExperience to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/resume-experiences/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteResumeExperience(@PathVariable Long id) {
        log.debug("REST request to delete ResumeExperience : {}", id);
        resumeExperienceRepository.delete(id);
        resumeExperienceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("resumeExperience", id.toString())).build();
    }

    /**
     * SEARCH  /_search/resume-experiences?query=:query : search for the resumeExperience corresponding
     * to the query.
     *
     * @param query the query of the resumeExperience search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/resume-experiences",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ResumeExperience> searchResumeExperiences(@RequestParam String query) {
        log.debug("REST request to search ResumeExperiences for query {}", query);
        return StreamSupport
            .stream(resumeExperienceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
