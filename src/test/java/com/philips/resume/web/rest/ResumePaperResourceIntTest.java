package com.philips.resume.web.rest;

import com.philips.resume.ResumeApp;
import com.philips.resume.domain.ResumePaper;
import com.philips.resume.repository.ResumePaperRepository;
import com.philips.resume.repository.search.ResumePaperSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ResumePaperResource REST controller.
 *
 * @see ResumePaperResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ResumeApp.class)
@WebAppConfiguration
@IntegrationTest
public class ResumePaperResourceIntTest {

    private static final String DEFAULT_PAPER = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_PAPER = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private ResumePaperRepository resumePaperRepository;

    @Inject
    private ResumePaperSearchRepository resumePaperSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restResumePaperMockMvc;

    private ResumePaper resumePaper;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResumePaperResource resumePaperResource = new ResumePaperResource();
        ReflectionTestUtils.setField(resumePaperResource, "resumePaperSearchRepository", resumePaperSearchRepository);
        ReflectionTestUtils.setField(resumePaperResource, "resumePaperRepository", resumePaperRepository);
        this.restResumePaperMockMvc = MockMvcBuilders.standaloneSetup(resumePaperResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        resumePaperSearchRepository.deleteAll();
        resumePaper = new ResumePaper();
        resumePaper.setPaper(DEFAULT_PAPER);
    }

    @Test
    @Transactional
    public void createResumePaper() throws Exception {
        int databaseSizeBeforeCreate = resumePaperRepository.findAll().size();

        // Create the ResumePaper

        restResumePaperMockMvc.perform(post("/api/resume-papers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumePaper)))
                .andExpect(status().isCreated());

        // Validate the ResumePaper in the database
        List<ResumePaper> resumePapers = resumePaperRepository.findAll();
        assertThat(resumePapers).hasSize(databaseSizeBeforeCreate + 1);
        ResumePaper testResumePaper = resumePapers.get(resumePapers.size() - 1);
        assertThat(testResumePaper.getPaper()).isEqualTo(DEFAULT_PAPER);

        // Validate the ResumePaper in ElasticSearch
        ResumePaper resumePaperEs = resumePaperSearchRepository.findOne(testResumePaper.getId());
        assertThat(resumePaperEs).isEqualToComparingFieldByField(testResumePaper);
    }

    @Test
    @Transactional
    public void getAllResumePapers() throws Exception {
        // Initialize the database
        resumePaperRepository.saveAndFlush(resumePaper);

        // Get all the resumePapers
        restResumePaperMockMvc.perform(get("/api/resume-papers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(resumePaper.getId().intValue())))
                .andExpect(jsonPath("$.[*].paper").value(hasItem(DEFAULT_PAPER.toString())));
    }

    @Test
    @Transactional
    public void getResumePaper() throws Exception {
        // Initialize the database
        resumePaperRepository.saveAndFlush(resumePaper);

        // Get the resumePaper
        restResumePaperMockMvc.perform(get("/api/resume-papers/{id}", resumePaper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(resumePaper.getId().intValue()))
            .andExpect(jsonPath("$.paper").value(DEFAULT_PAPER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResumePaper() throws Exception {
        // Get the resumePaper
        restResumePaperMockMvc.perform(get("/api/resume-papers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResumePaper() throws Exception {
        // Initialize the database
        resumePaperRepository.saveAndFlush(resumePaper);
        resumePaperSearchRepository.save(resumePaper);
        int databaseSizeBeforeUpdate = resumePaperRepository.findAll().size();

        // Update the resumePaper
        ResumePaper updatedResumePaper = new ResumePaper();
        updatedResumePaper.setId(resumePaper.getId());
        updatedResumePaper.setPaper(UPDATED_PAPER);

        restResumePaperMockMvc.perform(put("/api/resume-papers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedResumePaper)))
                .andExpect(status().isOk());

        // Validate the ResumePaper in the database
        List<ResumePaper> resumePapers = resumePaperRepository.findAll();
        assertThat(resumePapers).hasSize(databaseSizeBeforeUpdate);
        ResumePaper testResumePaper = resumePapers.get(resumePapers.size() - 1);
        assertThat(testResumePaper.getPaper()).isEqualTo(UPDATED_PAPER);

        // Validate the ResumePaper in ElasticSearch
        ResumePaper resumePaperEs = resumePaperSearchRepository.findOne(testResumePaper.getId());
        assertThat(resumePaperEs).isEqualToComparingFieldByField(testResumePaper);
    }

    @Test
    @Transactional
    public void deleteResumePaper() throws Exception {
        // Initialize the database
        resumePaperRepository.saveAndFlush(resumePaper);
        resumePaperSearchRepository.save(resumePaper);
        int databaseSizeBeforeDelete = resumePaperRepository.findAll().size();

        // Get the resumePaper
        restResumePaperMockMvc.perform(delete("/api/resume-papers/{id}", resumePaper.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean resumePaperExistsInEs = resumePaperSearchRepository.exists(resumePaper.getId());
        assertThat(resumePaperExistsInEs).isFalse();

        // Validate the database is empty
        List<ResumePaper> resumePapers = resumePaperRepository.findAll();
        assertThat(resumePapers).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchResumePaper() throws Exception {
        // Initialize the database
        resumePaperRepository.saveAndFlush(resumePaper);
        resumePaperSearchRepository.save(resumePaper);

        // Search the resumePaper
        restResumePaperMockMvc.perform(get("/api/_search/resume-papers?query=id:" + resumePaper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumePaper.getId().intValue())))
            .andExpect(jsonPath("$.[*].paper").value(hasItem(DEFAULT_PAPER.toString())));
    }
}
