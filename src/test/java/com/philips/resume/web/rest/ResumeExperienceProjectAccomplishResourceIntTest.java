package com.philips.resume.web.rest;

import com.philips.resume.ResumeApp;
import com.philips.resume.domain.ResumeExperienceProjectAccomplish;
import com.philips.resume.repository.ResumeExperienceProjectAccomplishRepository;
import com.philips.resume.repository.search.ResumeExperienceProjectAccomplishSearchRepository;

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
 * Test class for the ResumeExperienceProjectAccomplishResource REST controller.
 *
 * @see ResumeExperienceProjectAccomplishResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ResumeApp.class)
@WebAppConfiguration
@IntegrationTest
public class ResumeExperienceProjectAccomplishResourceIntTest {

    private static final String DEFAULT_ACCOMPLISH = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_ACCOMPLISH = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private ResumeExperienceProjectAccomplishRepository resumeExperienceProjectAccomplishRepository;

    @Inject
    private ResumeExperienceProjectAccomplishSearchRepository resumeExperienceProjectAccomplishSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restResumeExperienceProjectAccomplishMockMvc;

    private ResumeExperienceProjectAccomplish resumeExperienceProjectAccomplish;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResumeExperienceProjectAccomplishResource resumeExperienceProjectAccomplishResource = new ResumeExperienceProjectAccomplishResource();
        ReflectionTestUtils.setField(resumeExperienceProjectAccomplishResource, "resumeExperienceProjectAccomplishSearchRepository", resumeExperienceProjectAccomplishSearchRepository);
        ReflectionTestUtils.setField(resumeExperienceProjectAccomplishResource, "resumeExperienceProjectAccomplishRepository", resumeExperienceProjectAccomplishRepository);
        this.restResumeExperienceProjectAccomplishMockMvc = MockMvcBuilders.standaloneSetup(resumeExperienceProjectAccomplishResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        resumeExperienceProjectAccomplishSearchRepository.deleteAll();
        resumeExperienceProjectAccomplish = new ResumeExperienceProjectAccomplish();
        resumeExperienceProjectAccomplish.setAccomplish(DEFAULT_ACCOMPLISH);
    }

    @Test
    @Transactional
    public void createResumeExperienceProjectAccomplish() throws Exception {
        int databaseSizeBeforeCreate = resumeExperienceProjectAccomplishRepository.findAll().size();

        // Create the ResumeExperienceProjectAccomplish

        restResumeExperienceProjectAccomplishMockMvc.perform(post("/api/resume-experience-project-accomplishes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeExperienceProjectAccomplish)))
                .andExpect(status().isCreated());

        // Validate the ResumeExperienceProjectAccomplish in the database
        List<ResumeExperienceProjectAccomplish> resumeExperienceProjectAccomplishes = resumeExperienceProjectAccomplishRepository.findAll();
        assertThat(resumeExperienceProjectAccomplishes).hasSize(databaseSizeBeforeCreate + 1);
        ResumeExperienceProjectAccomplish testResumeExperienceProjectAccomplish = resumeExperienceProjectAccomplishes.get(resumeExperienceProjectAccomplishes.size() - 1);
        assertThat(testResumeExperienceProjectAccomplish.getAccomplish()).isEqualTo(DEFAULT_ACCOMPLISH);

        // Validate the ResumeExperienceProjectAccomplish in ElasticSearch
        ResumeExperienceProjectAccomplish resumeExperienceProjectAccomplishEs = resumeExperienceProjectAccomplishSearchRepository.findOne(testResumeExperienceProjectAccomplish.getId());
        assertThat(resumeExperienceProjectAccomplishEs).isEqualToComparingFieldByField(testResumeExperienceProjectAccomplish);
    }

    @Test
    @Transactional
    public void getAllResumeExperienceProjectAccomplishes() throws Exception {
        // Initialize the database
        resumeExperienceProjectAccomplishRepository.saveAndFlush(resumeExperienceProjectAccomplish);

        // Get all the resumeExperienceProjectAccomplishes
        restResumeExperienceProjectAccomplishMockMvc.perform(get("/api/resume-experience-project-accomplishes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(resumeExperienceProjectAccomplish.getId().intValue())))
                .andExpect(jsonPath("$.[*].accomplish").value(hasItem(DEFAULT_ACCOMPLISH.toString())));
    }

    @Test
    @Transactional
    public void getResumeExperienceProjectAccomplish() throws Exception {
        // Initialize the database
        resumeExperienceProjectAccomplishRepository.saveAndFlush(resumeExperienceProjectAccomplish);

        // Get the resumeExperienceProjectAccomplish
        restResumeExperienceProjectAccomplishMockMvc.perform(get("/api/resume-experience-project-accomplishes/{id}", resumeExperienceProjectAccomplish.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(resumeExperienceProjectAccomplish.getId().intValue()))
            .andExpect(jsonPath("$.accomplish").value(DEFAULT_ACCOMPLISH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResumeExperienceProjectAccomplish() throws Exception {
        // Get the resumeExperienceProjectAccomplish
        restResumeExperienceProjectAccomplishMockMvc.perform(get("/api/resume-experience-project-accomplishes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResumeExperienceProjectAccomplish() throws Exception {
        // Initialize the database
        resumeExperienceProjectAccomplishRepository.saveAndFlush(resumeExperienceProjectAccomplish);
        resumeExperienceProjectAccomplishSearchRepository.save(resumeExperienceProjectAccomplish);
        int databaseSizeBeforeUpdate = resumeExperienceProjectAccomplishRepository.findAll().size();

        // Update the resumeExperienceProjectAccomplish
        ResumeExperienceProjectAccomplish updatedResumeExperienceProjectAccomplish = new ResumeExperienceProjectAccomplish();
        updatedResumeExperienceProjectAccomplish.setId(resumeExperienceProjectAccomplish.getId());
        updatedResumeExperienceProjectAccomplish.setAccomplish(UPDATED_ACCOMPLISH);

        restResumeExperienceProjectAccomplishMockMvc.perform(put("/api/resume-experience-project-accomplishes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedResumeExperienceProjectAccomplish)))
                .andExpect(status().isOk());

        // Validate the ResumeExperienceProjectAccomplish in the database
        List<ResumeExperienceProjectAccomplish> resumeExperienceProjectAccomplishes = resumeExperienceProjectAccomplishRepository.findAll();
        assertThat(resumeExperienceProjectAccomplishes).hasSize(databaseSizeBeforeUpdate);
        ResumeExperienceProjectAccomplish testResumeExperienceProjectAccomplish = resumeExperienceProjectAccomplishes.get(resumeExperienceProjectAccomplishes.size() - 1);
        assertThat(testResumeExperienceProjectAccomplish.getAccomplish()).isEqualTo(UPDATED_ACCOMPLISH);

        // Validate the ResumeExperienceProjectAccomplish in ElasticSearch
        ResumeExperienceProjectAccomplish resumeExperienceProjectAccomplishEs = resumeExperienceProjectAccomplishSearchRepository.findOne(testResumeExperienceProjectAccomplish.getId());
        assertThat(resumeExperienceProjectAccomplishEs).isEqualToComparingFieldByField(testResumeExperienceProjectAccomplish);
    }

    @Test
    @Transactional
    public void deleteResumeExperienceProjectAccomplish() throws Exception {
        // Initialize the database
        resumeExperienceProjectAccomplishRepository.saveAndFlush(resumeExperienceProjectAccomplish);
        resumeExperienceProjectAccomplishSearchRepository.save(resumeExperienceProjectAccomplish);
        int databaseSizeBeforeDelete = resumeExperienceProjectAccomplishRepository.findAll().size();

        // Get the resumeExperienceProjectAccomplish
        restResumeExperienceProjectAccomplishMockMvc.perform(delete("/api/resume-experience-project-accomplishes/{id}", resumeExperienceProjectAccomplish.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean resumeExperienceProjectAccomplishExistsInEs = resumeExperienceProjectAccomplishSearchRepository.exists(resumeExperienceProjectAccomplish.getId());
        assertThat(resumeExperienceProjectAccomplishExistsInEs).isFalse();

        // Validate the database is empty
        List<ResumeExperienceProjectAccomplish> resumeExperienceProjectAccomplishes = resumeExperienceProjectAccomplishRepository.findAll();
        assertThat(resumeExperienceProjectAccomplishes).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchResumeExperienceProjectAccomplish() throws Exception {
        // Initialize the database
        resumeExperienceProjectAccomplishRepository.saveAndFlush(resumeExperienceProjectAccomplish);
        resumeExperienceProjectAccomplishSearchRepository.save(resumeExperienceProjectAccomplish);

        // Search the resumeExperienceProjectAccomplish
        restResumeExperienceProjectAccomplishMockMvc.perform(get("/api/_search/resume-experience-project-accomplishes?query=id:" + resumeExperienceProjectAccomplish.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumeExperienceProjectAccomplish.getId().intValue())))
            .andExpect(jsonPath("$.[*].accomplish").value(hasItem(DEFAULT_ACCOMPLISH.toString())));
    }
}
