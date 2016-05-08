package com.philips.resume.web.rest;

import com.philips.resume.ResumeApp;
import com.philips.resume.domain.ResumeEducation;
import com.philips.resume.repository.ResumeEducationRepository;
import com.philips.resume.repository.search.ResumeEducationSearchRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ResumeEducationResource REST controller.
 *
 * @see ResumeEducationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ResumeApp.class)
@WebAppConfiguration
@IntegrationTest
public class ResumeEducationResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_MAJOR = "AAAAA";
    private static final String UPDATED_MAJOR = "BBBBB";
    private static final String DEFAULT_UNIVERSITY = "AAAAA";
    private static final String UPDATED_UNIVERSITY = "BBBBB";

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_TIME_STR = dateTimeFormatter.format(DEFAULT_START_TIME);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_TIME_STR = dateTimeFormatter.format(DEFAULT_END_TIME);

    @Inject
    private ResumeEducationRepository resumeEducationRepository;

    @Inject
    private ResumeEducationSearchRepository resumeEducationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restResumeEducationMockMvc;

    private ResumeEducation resumeEducation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResumeEducationResource resumeEducationResource = new ResumeEducationResource();
        ReflectionTestUtils.setField(resumeEducationResource, "resumeEducationSearchRepository", resumeEducationSearchRepository);
        ReflectionTestUtils.setField(resumeEducationResource, "resumeEducationRepository", resumeEducationRepository);
        this.restResumeEducationMockMvc = MockMvcBuilders.standaloneSetup(resumeEducationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        resumeEducationSearchRepository.deleteAll();
        resumeEducation = new ResumeEducation();
        resumeEducation.setMajor(DEFAULT_MAJOR);
        resumeEducation.setUniversity(DEFAULT_UNIVERSITY);
        resumeEducation.setStartTime(DEFAULT_START_TIME);
        resumeEducation.setEndTime(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    public void createResumeEducation() throws Exception {
        int databaseSizeBeforeCreate = resumeEducationRepository.findAll().size();

        // Create the ResumeEducation

        restResumeEducationMockMvc.perform(post("/api/resume-educations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeEducation)))
                .andExpect(status().isCreated());

        // Validate the ResumeEducation in the database
        List<ResumeEducation> resumeEducations = resumeEducationRepository.findAll();
        assertThat(resumeEducations).hasSize(databaseSizeBeforeCreate + 1);
        ResumeEducation testResumeEducation = resumeEducations.get(resumeEducations.size() - 1);
        assertThat(testResumeEducation.getMajor()).isEqualTo(DEFAULT_MAJOR);
        assertThat(testResumeEducation.getUniversity()).isEqualTo(DEFAULT_UNIVERSITY);
        assertThat(testResumeEducation.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testResumeEducation.getEndTime()).isEqualTo(DEFAULT_END_TIME);

        // Validate the ResumeEducation in ElasticSearch
        ResumeEducation resumeEducationEs = resumeEducationSearchRepository.findOne(testResumeEducation.getId());
        assertThat(resumeEducationEs).isEqualToComparingFieldByField(testResumeEducation);
    }

    @Test
    @Transactional
    public void checkMajorIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeEducationRepository.findAll().size();
        // set the field null
        resumeEducation.setMajor(null);

        // Create the ResumeEducation, which fails.

        restResumeEducationMockMvc.perform(post("/api/resume-educations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeEducation)))
                .andExpect(status().isBadRequest());

        List<ResumeEducation> resumeEducations = resumeEducationRepository.findAll();
        assertThat(resumeEducations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUniversityIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeEducationRepository.findAll().size();
        // set the field null
        resumeEducation.setUniversity(null);

        // Create the ResumeEducation, which fails.

        restResumeEducationMockMvc.perform(post("/api/resume-educations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeEducation)))
                .andExpect(status().isBadRequest());

        List<ResumeEducation> resumeEducations = resumeEducationRepository.findAll();
        assertThat(resumeEducations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeEducationRepository.findAll().size();
        // set the field null
        resumeEducation.setStartTime(null);

        // Create the ResumeEducation, which fails.

        restResumeEducationMockMvc.perform(post("/api/resume-educations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeEducation)))
                .andExpect(status().isBadRequest());

        List<ResumeEducation> resumeEducations = resumeEducationRepository.findAll();
        assertThat(resumeEducations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeEducationRepository.findAll().size();
        // set the field null
        resumeEducation.setEndTime(null);

        // Create the ResumeEducation, which fails.

        restResumeEducationMockMvc.perform(post("/api/resume-educations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeEducation)))
                .andExpect(status().isBadRequest());

        List<ResumeEducation> resumeEducations = resumeEducationRepository.findAll();
        assertThat(resumeEducations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResumeEducations() throws Exception {
        // Initialize the database
        resumeEducationRepository.saveAndFlush(resumeEducation);

        // Get all the resumeEducations
        restResumeEducationMockMvc.perform(get("/api/resume-educations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(resumeEducation.getId().intValue())))
                .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR.toString())))
                .andExpect(jsonPath("$.[*].university").value(hasItem(DEFAULT_UNIVERSITY.toString())))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME_STR)))
                .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME_STR)));
    }

    @Test
    @Transactional
    public void getResumeEducation() throws Exception {
        // Initialize the database
        resumeEducationRepository.saveAndFlush(resumeEducation);

        // Get the resumeEducation
        restResumeEducationMockMvc.perform(get("/api/resume-educations/{id}", resumeEducation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(resumeEducation.getId().intValue()))
            .andExpect(jsonPath("$.major").value(DEFAULT_MAJOR.toString()))
            .andExpect(jsonPath("$.university").value(DEFAULT_UNIVERSITY.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME_STR))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingResumeEducation() throws Exception {
        // Get the resumeEducation
        restResumeEducationMockMvc.perform(get("/api/resume-educations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResumeEducation() throws Exception {
        // Initialize the database
        resumeEducationRepository.saveAndFlush(resumeEducation);
        resumeEducationSearchRepository.save(resumeEducation);
        int databaseSizeBeforeUpdate = resumeEducationRepository.findAll().size();

        // Update the resumeEducation
        ResumeEducation updatedResumeEducation = new ResumeEducation();
        updatedResumeEducation.setId(resumeEducation.getId());
        updatedResumeEducation.setMajor(UPDATED_MAJOR);
        updatedResumeEducation.setUniversity(UPDATED_UNIVERSITY);
        updatedResumeEducation.setStartTime(UPDATED_START_TIME);
        updatedResumeEducation.setEndTime(UPDATED_END_TIME);

        restResumeEducationMockMvc.perform(put("/api/resume-educations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedResumeEducation)))
                .andExpect(status().isOk());

        // Validate the ResumeEducation in the database
        List<ResumeEducation> resumeEducations = resumeEducationRepository.findAll();
        assertThat(resumeEducations).hasSize(databaseSizeBeforeUpdate);
        ResumeEducation testResumeEducation = resumeEducations.get(resumeEducations.size() - 1);
        assertThat(testResumeEducation.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testResumeEducation.getUniversity()).isEqualTo(UPDATED_UNIVERSITY);
        assertThat(testResumeEducation.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testResumeEducation.getEndTime()).isEqualTo(UPDATED_END_TIME);

        // Validate the ResumeEducation in ElasticSearch
        ResumeEducation resumeEducationEs = resumeEducationSearchRepository.findOne(testResumeEducation.getId());
        assertThat(resumeEducationEs).isEqualToComparingFieldByField(testResumeEducation);
    }

    @Test
    @Transactional
    public void deleteResumeEducation() throws Exception {
        // Initialize the database
        resumeEducationRepository.saveAndFlush(resumeEducation);
        resumeEducationSearchRepository.save(resumeEducation);
        int databaseSizeBeforeDelete = resumeEducationRepository.findAll().size();

        // Get the resumeEducation
        restResumeEducationMockMvc.perform(delete("/api/resume-educations/{id}", resumeEducation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean resumeEducationExistsInEs = resumeEducationSearchRepository.exists(resumeEducation.getId());
        assertThat(resumeEducationExistsInEs).isFalse();

        // Validate the database is empty
        List<ResumeEducation> resumeEducations = resumeEducationRepository.findAll();
        assertThat(resumeEducations).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchResumeEducation() throws Exception {
        // Initialize the database
        resumeEducationRepository.saveAndFlush(resumeEducation);
        resumeEducationSearchRepository.save(resumeEducation);

        // Search the resumeEducation
        restResumeEducationMockMvc.perform(get("/api/_search/resume-educations?query=id:" + resumeEducation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumeEducation.getId().intValue())))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR.toString())))
            .andExpect(jsonPath("$.[*].university").value(hasItem(DEFAULT_UNIVERSITY.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME_STR)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME_STR)));
    }
}
