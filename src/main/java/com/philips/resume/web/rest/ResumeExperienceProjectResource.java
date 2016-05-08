package com.philips.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.philips.resume.domain.ResumeExperienceProject;
import com.philips.resume.repository.ResumeExperienceProjectRepository;
import com.philips.resume.repository.search.ResumeExperienceProjectSearchRepository;
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
 * REST controller for managing ResumeExperienceProject.
 */
@RestController
@RequestMapping("/api")
public class ResumeExperienceProjectResource {

    private final Logger log = LoggerFactory.getLogger(ResumeExperienceProjectResource.class);
        
    @Inject
    private ResumeExperienceProjectRepository resumeExperienceProjectRepository;
    
    @Inject
    private ResumeExperienceProjectSearchRepository resumeExperienceProjectSearchRepository;
    
    /**
     * POST  /resume-experience-projects : Create a new resumeExperienceProject.
     *
     * @param resumeExperienceProject the resumeExperienceProject to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resumeExperienceProject, or with status 400 (Bad Request) if the resumeExperienceProject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/resume-experience-projects",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumeExperienceProject> createResumeExperienceProject(@Valid @RequestBody ResumeExperienceProject resumeExperienceProject) throws URISyntaxException {
        log.debug("REST request to save ResumeExperienceProject : {}", resumeExperienceProject);
        if (resumeExperienceProject.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("resumeExperienceProject", "idexists", "A new resumeExperienceProject cannot already have an ID")).body(null);
        }
        ResumeExperienceProject result = resumeExperienceProjectRepository.save(resumeExperienceProject);
        resumeExperienceProjectSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/resume-experience-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("resumeExperienceProject", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resume-experience-projects : Updates an existing resumeExperienceProject.
     *
     * @param resumeExperienceProject the resumeExperienceProject to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resumeExperienceProject,
     * or with status 400 (Bad Request) if the resumeExperienceProject is not valid,
     * or with status 500 (Internal Server Error) if the resumeExperienceProject couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/resume-experience-projects",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumeExperienceProject> updateResumeExperienceProject(@Valid @RequestBody ResumeExperienceProject resumeExperienceProject) throws URISyntaxException {
        log.debug("REST request to update ResumeExperienceProject : {}", resumeExperienceProject);
        if (resumeExperienceProject.getId() == null) {
            return createResumeExperienceProject(resumeExperienceProject);
        }
        ResumeExperienceProject result = resumeExperienceProjectRepository.save(resumeExperienceProject);
        resumeExperienceProjectSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("resumeExperienceProject", resumeExperienceProject.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resume-experience-projects : get all the resumeExperienceProjects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of resumeExperienceProjects in body
     */
    @RequestMapping(value = "/resume-experience-projects",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ResumeExperienceProject> getAllResumeExperienceProjects() {
        log.debug("REST request to get all ResumeExperienceProjects");
        List<ResumeExperienceProject> resumeExperienceProjects = resumeExperienceProjectRepository.findAll();
        return resumeExperienceProjects;
    }

    /**
     * GET  /resume-experience-projects/:id : get the "id" resumeExperienceProject.
     *
     * @param id the id of the resumeExperienceProject to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resumeExperienceProject, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/resume-experience-projects/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumeExperienceProject> getResumeExperienceProject(@PathVariable Long id) {
        log.debug("REST request to get ResumeExperienceProject : {}", id);
        ResumeExperienceProject resumeExperienceProject = resumeExperienceProjectRepository.findOne(id);
        return Optional.ofNullable(resumeExperienceProject)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /resume-experience-projects/:id : delete the "id" resumeExperienceProject.
     *
     * @param id the id of the resumeExperienceProject to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/resume-experience-projects/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteResumeExperienceProject(@PathVariable Long id) {
        log.debug("REST request to delete ResumeExperienceProject : {}", id);
        resumeExperienceProjectRepository.delete(id);
        resumeExperienceProjectSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("resumeExperienceProject", id.toString())).build();
    }

    /**
     * SEARCH  /_search/resume-experience-projects?query=:query : search for the resumeExperienceProject corresponding
     * to the query.
     *
     * @param query the query of the resumeExperienceProject search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/resume-experience-projects",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ResumeExperienceProject> searchResumeExperienceProjects(@RequestParam String query) {
        log.debug("REST request to search ResumeExperienceProjects for query {}", query);
        return StreamSupport
            .stream(resumeExperienceProjectSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
