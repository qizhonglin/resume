package com.philips.resume.web.rest;

import com.philips.resume.ResumeApp;
import com.philips.resume.domain.ResumeExperience;
import com.philips.resume.repository.ResumeExperienceRepository;
import com.philips.resume.repository.search.ResumeExperienceSearchRepository;

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
 * Test class for the ResumeExperienceResource REST controller.
 *
 * @see ResumeExperienceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ResumeApp.class)
@WebAppConfiguration
@IntegrationTest
public class ResumeExperienceResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final Integer DEFAULT_INDEX = 1;
    private static final Integer UPDATED_INDEX = 2;
    private static final String DEFAULT_POSITION = "AAAAA";
    private static final String UPDATED_POSITION = "BBBBB";
    private static final String DEFAULT_COMPANY = "AAAAA";
    private static final String UPDATED_COMPANY = "BBBBB";

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_TIME_STR = dateTimeFormatter.format(DEFAULT_START_TIME);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_TIME_STR = dateTimeFormatter.format(DEFAULT_END_TIME);

    @Inject
    private ResumeExperienceRepository resumeExperienceRepository;

    @Inject
    private ResumeExperienceSearchRepository resumeExperienceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restResumeExperienceMockMvc;

    private ResumeExperience resumeExperience;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResumeExperienceResource resumeExperienceResource = new ResumeExperienceResource();
        ReflectionTestUtils.setField(resumeExperienceResource, "resumeExperienceSearchRepository", resumeExperienceSearchRepository);
        ReflectionTestUtils.setField(resumeExperienceResource, "resumeExperienceRepository", resumeExperienceRepository);
        this.restResumeExperienceMockMvc = MockMvcBuilders.standaloneSetup(resumeExperienceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        resumeExperienceSearchRepository.deleteAll();
        resumeExperience = new ResumeExperience();
        resumeExperience.setIndex(DEFAULT_INDEX);
        resumeExperience.setPosition(DEFAULT_POSITION);
        resumeExperience.setCompany(DEFAULT_COMPANY);
        resumeExperience.setStartTime(DEFAULT_START_TIME);
        resumeExperience.setEndTime(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    public void createResumeExperience() throws Exception {
        int databaseSizeBeforeCreate = resumeExperienceRepository.findAll().size();

        // Create the ResumeExperience

        restResumeExperienceMockMvc.perform(post("/api/resume-experiences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeExperience)))
                .andExpect(status().isCreated());

        // Validate the ResumeExperience in the database
        List<ResumeExperience> resumeExperiences = resumeExperienceRepository.findAll();
        assertThat(resumeExperiences).hasSize(databaseSizeBeforeCreate + 1);
        ResumeExperience testResumeExperience = resumeExperiences.get(resumeExperiences.size() - 1);
        assertThat(testResumeExperience.getIndex()).isEqualTo(DEFAULT_INDEX);
        assertThat(testResumeExperience.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testResumeExperience.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testResumeExperience.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testResumeExperience.getEndTime()).isEqualTo(DEFAULT_END_TIME);

        // Validate the ResumeExperience in ElasticSearch
        ResumeExperience resumeExperienceEs = resumeExperienceSearchRepository.findOne(testResumeExperience.getId());
        assertThat(resumeExperienceEs).isEqualToComparingFieldByField(testResumeExperience);
    }

    @Test
    @Transactional
    public void checkIndexIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeExperienceRepository.findAll().size();
        // set the field null
        resumeExperience.setIndex(null);

        // Create the ResumeExperience, which fails.

        restResumeExperienceMockMvc.perform(post("/api/resume-experiences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeExperience)))
                .andExpect(status().isBadRequest());

        List<ResumeExperience> resumeExperiences = resumeExperienceRepository.findAll();
        assertThat(resumeExperiences).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeExperienceRepository.findAll().size();
        // set the field null
        resumeExperience.setPosition(null);

        // Create the ResumeExperience, which fails.

        restResumeExperienceMockMvc.perform(post("/api/resume-experiences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeExperience)))
                .andExpect(status().isBadRequest());

        List<ResumeExperience> resumeExperiences = resumeExperienceRepository.findAll();
        assertThat(resumeExperiences).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompanyIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeExperienceRepository.findAll().size();
        // set the field null
        resumeExperience.setCompany(null);

        // Create the ResumeExperience, which fails.

        restResumeExperienceMockMvc.perform(post("/api/resume-experiences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeExperience)))
                .andExpect(status().isBadRequest());

        List<ResumeExperience> resumeExperiences = resumeExperienceRepository.findAll();
        assertThat(resumeExperiences).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeExperienceRepository.findAll().size();
        // set the field null
        resumeExperience.setStartTime(null);

        // Create the ResumeExperience, which fails.

        restResumeExperienceMockMvc.perform(post("/api/resume-experiences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeExperience)))
                .andExpect(status().isBadRequest());

        List<ResumeExperience> resumeExperiences = resumeExperienceRepository.findAll();
        assertThat(resumeExperiences).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResumeExperiences() throws Exception {
        // Initialize the database
        resumeExperienceRepository.saveAndFlush(resumeExperience);

        // Get all the resumeExperiences
        restResumeExperienceMockMvc.perform(get("/api/resume-experiences?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(resumeExperience.getId().intValue())))
                .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX)))
                .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
                .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME_STR)))
                .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME_STR)));
    }

    @Test
    @Transactional
    public void getResumeExperience() throws Exception {
        // Initialize the database
        resumeExperienceRepository.saveAndFlush(resumeExperience);

        // Get the resumeExperience
        restResumeExperienceMockMvc.perform(get("/api/resume-experiences/{id}", resumeExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(resumeExperience.getId().intValue()))
            .andExpect(jsonPath("$.index").value(DEFAULT_INDEX))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME_STR))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingResumeExperience() throws Exception {
        // Get the resumeExperience
        restResumeExperienceMockMvc.perform(get("/api/resume-experiences/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResumeExperience() throws Exception {
        // Initialize the database
        resumeExperienceRepository.saveAndFlush(resumeExperience);
        resumeExperienceSearchRepository.save(resumeExperience);
        int databaseSizeBeforeUpdate = resumeExperienceRepository.findAll().size();

        // Update the resumeExperience
        ResumeExperience updatedResumeExperience = new ResumeExperience();
        updatedResumeExperience.setId(resumeExperience.getId());
        updatedResumeExperience.setIndex(UPDATED_INDEX);
        updatedResumeExperience.setPosition(UPDATED_POSITION);
        updatedResumeExperience.setCompany(UPDATED_COMPANY);
        updatedResumeExperience.setStartTime(UPDATED_START_TIME);
        updatedResumeExperience.setEndTime(UPDATED_END_TIME);

        restResumeExperienceMockMvc.perform(put("/api/resume-experiences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedResumeExperience)))
                .andExpect(status().isOk());

        // Validate the ResumeExperience in the database
        List<ResumeExperience> resumeExperiences = resumeExperienceRepository.findAll();
        assertThat(resumeExperiences).hasSize(databaseSizeBeforeUpdate);
        ResumeExperience testResumeExperience = resumeExperiences.get(resumeExperiences.size() - 1);
        assertThat(testResumeExperience.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testResumeExperience.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testResumeExperience.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testResumeExperience.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testResumeExperience.getEndTime()).isEqualTo(UPDATED_END_TIME);

        // Validate the ResumeExperience in ElasticSearch
        ResumeExperience resumeExperienceEs = resumeExperienceSearchRepository.findOne(testResumeExperience.getId());
        assertThat(resumeExperienceEs).isEqualToComparingFieldByField(testResumeExperience);
    }

    @Test
    @Transactional
    public void deleteResumeExperience() throws Exception {
        // Initialize the database
        resumeExperienceRepository.saveAndFlush(resumeExperience);
        resumeExperienceSearchRepository.save(resumeExperience);
        int databaseSizeBeforeDelete = resumeExperienceRepository.findAll().size();

        // Get the resumeExperience
        restResumeExperienceMockMvc.perform(delete("/api/resume-experiences/{id}", resumeExperience.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean resumeExperienceExistsInEs = resumeExperienceSearchRepository.exists(resumeExperience.getId());
        assertThat(resumeExperienceExistsInEs).isFalse();

        // Validate the database is empty
        List<ResumeExperience> resumeExperiences = resumeExperienceRepository.findAll();
        assertThat(resumeExperiences).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchResumeExperience() throws Exception {
        // Initialize the database
        resumeExperienceRepository.saveAndFlush(resumeExperience);
        resumeExperienceSearchRepository.save(resumeExperience);

        // Search the resumeExperience
        restResumeExperienceMockMvc.perform(get("/api/_search/resume-experiences?query=id:" + resumeExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumeExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME_STR)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME_STR)));
    }
}
