package com.philips.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.philips.resume.domain.ResumeSkill;
import com.philips.resume.repository.ResumeSkillRepository;
import com.philips.resume.repository.search.ResumeSkillSearchRepository;
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
 * REST controller for managing ResumeSkill.
 */
@RestController
@RequestMapping("/api")
public class ResumeSkillResource {

    private final Logger log = LoggerFactory.getLogger(ResumeSkillResource.class);
        
    @Inject
    private ResumeSkillRepository resumeSkillRepository;
    
    @Inject
    private ResumeSkillSearchRepository resumeSkillSearchRepository;
    
    /**
     * POST  /resume-skills : Create a new resumeSkill.
     *
     * @param resumeSkill the resumeSkill to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resumeSkill, or with status 400 (Bad Request) if the resumeSkill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/resume-skills",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumeSkill> createResumeSkill(@Valid @RequestBody ResumeSkill resumeSkill) throws URISyntaxException {
        log.debug("REST request to save ResumeSkill : {}", resumeSkill);
        if (resumeSkill.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("resumeSkill", "idexists", "A new resumeSkill cannot already have an ID")).body(null);
        }
        ResumeSkill result = resumeSkillRepository.save(resumeSkill);
        resumeSkillSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/resume-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("resumeSkill", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resume-skills : Updates an existing resumeSkill.
     *
     * @param resumeSkill the resumeSkill to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resumeSkill,
     * or with status 400 (Bad Request) if the resumeSkill is not valid,
     * or with status 500 (Internal Server Error) if the resumeSkill couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/resume-skills",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumeSkill> updateResumeSkill(@Valid @RequestBody ResumeSkill resumeSkill) throws URISyntaxException {
        log.debug("REST request to update ResumeSkill : {}", resumeSkill);
        if (resumeSkill.getId() == null) {
            return createResumeSkill(resumeSkill);
        }
        ResumeSkill result = resumeSkillRepository.save(resumeSkill);
        resumeSkillSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("resumeSkill", resumeSkill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resume-skills : get all the resumeSkills.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of resumeSkills in body
     */
    @RequestMapping(value = "/resume-skills",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ResumeSkill> getAllResumeSkills() {
        log.debug("REST request to get all ResumeSkills");
        List<ResumeSkill> resumeSkills = resumeSkillRepository.findAll();
        return resumeSkills;
    }

    /**
     * GET  /resume-skills/:id : get the "id" resumeSkill.
     *
     * @param id the id of the resumeSkill to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resumeSkill, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/resume-skills/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumeSkill> getResumeSkill(@PathVariable Long id) {
        log.debug("REST request to get ResumeSkill : {}", id);
        ResumeSkill resumeSkill = resumeSkillRepository.findOne(id);
        return Optional.ofNullable(resumeSkill)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /resume-skills/:id : delete the "id" resumeSkill.
     *
     * @param id the id of the resumeSkill to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/resume-skills/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteResumeSkill(@PathVariable Long id) {
        log.debug("REST request to delete ResumeSkill : {}", id);
        resumeSkillRepository.delete(id);
        resumeSkillSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("resumeSkill", id.toString())).build();
    }

    /**
     * SEARCH  /_search/resume-skills?query=:query : search for the resumeSkill corresponding
     * to the query.
     *
     * @param query the query of the resumeSkill search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/resume-skills",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ResumeSkill> searchResumeSkills(@RequestParam String query) {
        log.debug("REST request to search ResumeSkills for query {}", query);
        return StreamSupport
            .stream(resumeSkillSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
