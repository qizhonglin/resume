package com.philips.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.philips.resume.domain.ResumePaper;
import com.philips.resume.repository.ResumePaperRepository;
import com.philips.resume.repository.search.ResumePaperSearchRepository;
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
 * REST controller for managing ResumePaper.
 */
@RestController
@RequestMapping("/api")
public class ResumePaperResource {

    private final Logger log = LoggerFactory.getLogger(ResumePaperResource.class);
        
    @Inject
    private ResumePaperRepository resumePaperRepository;
    
    @Inject
    private ResumePaperSearchRepository resumePaperSearchRepository;
    
    /**
     * POST  /resume-papers : Create a new resumePaper.
     *
     * @param resumePaper the resumePaper to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resumePaper, or with status 400 (Bad Request) if the resumePaper has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/resume-papers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumePaper> createResumePaper(@Valid @RequestBody ResumePaper resumePaper) throws URISyntaxException {
        log.debug("REST request to save ResumePaper : {}", resumePaper);
        if (resumePaper.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("resumePaper", "idexists", "A new resumePaper cannot already have an ID")).body(null);
        }
        ResumePaper result = resumePaperRepository.save(resumePaper);
        resumePaperSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/resume-papers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("resumePaper", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resume-papers : Updates an existing resumePaper.
     *
     * @param resumePaper the resumePaper to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resumePaper,
     * or with status 400 (Bad Request) if the resumePaper is not valid,
     * or with status 500 (Internal Server Error) if the resumePaper couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/resume-papers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumePaper> updateResumePaper(@Valid @RequestBody ResumePaper resumePaper) throws URISyntaxException {
        log.debug("REST request to update ResumePaper : {}", resumePaper);
        if (resumePaper.getId() == null) {
            return createResumePaper(resumePaper);
        }
        ResumePaper result = resumePaperRepository.save(resumePaper);
        resumePaperSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("resumePaper", resumePaper.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resume-papers : get all the resumePapers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of resumePapers in body
     */
    @RequestMapping(value = "/resume-papers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ResumePaper> getAllResumePapers() {
        log.debug("REST request to get all ResumePapers");
        List<ResumePaper> resumePapers = resumePaperRepository.findAll();
        return resumePapers;
    }

    /**
     * GET  /resume-papers/:id : get the "id" resumePaper.
     *
     * @param id the id of the resumePaper to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resumePaper, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/resume-papers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResumePaper> getResumePaper(@PathVariable Long id) {
        log.debug("REST request to get ResumePaper : {}", id);
        ResumePaper resumePaper = resumePaperRepository.findOne(id);
        return Optional.ofNullable(resumePaper)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /resume-papers/:id : delete the "id" resumePaper.
     *
     * @param id the id of the resumePaper to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/resume-papers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteResumePaper(@PathVariable Long id) {
        log.debug("REST request to delete ResumePaper : {}", id);
        resumePaperRepository.delete(id);
        resumePaperSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("resumePaper", id.toString())).build();
    }

    /**
     * SEARCH  /_search/resume-papers?query=:query : search for the resumePaper corresponding
     * to the query.
     *
     * @param query the query of the resumePaper search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/resume-papers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ResumePaper> searchResumePapers(@RequestParam String query) {
        log.debug("REST request to search ResumePapers for query {}", query);
        return StreamSupport
            .stream(resumePaperSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
