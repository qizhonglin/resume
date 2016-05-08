package com.philips.resume.web.rest;

import com.philips.resume.ResumeApp;
import com.philips.resume.domain.ResumeExperienceProject;
import com.philips.resume.repository.ResumeExperienceProjectRepository;
import com.philips.resume.repository.search.ResumeExperienceProjectSearchRepository;

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
 * Test class for the ResumeExperienceProjectResource REST controller.
 *
 * @see ResumeExperienceProjectResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ResumeApp.class)
@WebAppConfiguration
@IntegrationTest
public class ResumeExperienceProjectResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final Integer DEFAULT_INDEX = 1;
    private static final Integer UPDATED_INDEX = 2;
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_TIME_STR = dateTimeFormatter.format(DEFAULT_START_TIME);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_TIME_STR = dateTimeFormatter.format(DEFAULT_END_TIME);
    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_RESPONSIILITY = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_RESPONSIILITY = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_PLATFORM = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_PLATFORM = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private ResumeExperienceProjectRepository resumeExperienceProjectRepository;

    @Inject
    private ResumeExperienceProjectSearchRepository resumeExperienceProjectSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restResumeExperienceProjectMockMvc;

    private ResumeExperienceProject resumeExperienceProject;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResumeExperienceProjectResource resumeExperienceProjectResource = new ResumeExperienceProjectResource();
        ReflectionTestUtils.setField(resumeExperienceProjectResource, "resumeExperienceProjectSearchRepository", resumeExperienceProjectSearchRepository);
        ReflectionTestUtils.setField(resumeExperienceProjectResource, "resumeExperienceProjectRepository", resumeExperienceProjectRepository);
        this.restResumeExperienceProjectMockMvc = MockMvcBuilders.standaloneSetup(resumeExperienceProjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        resumeExperienceProjectSearchRepository.deleteAll();
        resumeExperienceProject = new ResumeExperienceProject();
        resumeExperienceProject.setIndex(DEFAULT_INDEX);
        resumeExperienceProject.setName(DEFAULT_NAME);
        resumeExperienceProject.setStartTime(DEFAULT_START_TIME);
        resumeExperienceProject.setEndTime(DEFAULT_END_TIME);
        resumeExperienceProject.setIntroduction(DEFAULT_INTRODUCTION);
        resumeExperienceProject.setResponsiility(DEFAULT_RESPONSIILITY);
        resumeExperienceProject.setPlatform(DEFAULT_PLATFORM);
    }

    @Test
    @Transactional
    public void createResumeExperienceProject() throws Exception {
        int databaseSizeBeforeCreate = resumeExperienceProjectRepository.findAll().size();

        // Create the ResumeExperienceProject

        restResumeExperienceProjectMockMvc.perform(post("/api/resume-experience-projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeExperienceProject)))
                .andExpect(status().isCreated());

        // Validate the ResumeExperienceProject in the database
        List<ResumeExperienceProject> resumeExperienceProjects = resumeExperienceProjectRepository.findAll();
        assertThat(resumeExperienceProjects).hasSize(databaseSizeBeforeCreate + 1);
        ResumeExperienceProject testResumeExperienceProject = resumeExperienceProjects.get(resumeExperienceProjects.size() - 1);
        assertThat(testResumeExperienceProject.getIndex()).isEqualTo(DEFAULT_INDEX);
        assertThat(testResumeExperienceProject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testResumeExperienceProject.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testResumeExperienceProject.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testResumeExperienceProject.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);
        assertThat(testResumeExperienceProject.getResponsiility()).isEqualTo(DEFAULT_RESPONSIILITY);
        assertThat(testResumeExperienceProject.getPlatform()).isEqualTo(DEFAULT_PLATFORM);

        // Validate the ResumeExperienceProject in ElasticSearch
        ResumeExperienceProject resumeExperienceProjectEs = resumeExperienceProjectSearchRepository.findOne(testResumeExperienceProject.getId());
        assertThat(resumeExperienceProjectEs).isEqualToComparingFieldByField(testResumeExperienceProject);
    }

    @Test
    @Transactional
    public void checkIndexIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeExperienceProjectRepository.findAll().size();
        // set the field null
        resumeExperienceProject.setIndex(null);

        // Create the ResumeExperienceProject, which fails.

        restResumeExperienceProjectMockMvc.perform(post("/api/resume-experience-projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeExperienceProject)))
                .andExpect(status().isBadRequest());

        List<ResumeExperienceProject> resumeExperienceProjects = resumeExperienceProjectRepository.findAll();
        assertThat(resumeExperienceProjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeExperienceProjectRepository.findAll().size();
        // set the field null
        resumeExperienceProject.setName(null);

        // Create the ResumeExperienceProject, which fails.

        restResumeExperienceProjectMockMvc.perform(post("/api/resume-experience-projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeExperienceProject)))
                .andExpect(status().isBadRequest());

        List<ResumeExperienceProject> resumeExperienceProjects = resumeExperienceProjectRepository.findAll();
        assertThat(resumeExperienceProjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntroductionIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeExperienceProjectRepository.findAll().size();
        // set the field null
        resumeExperienceProject.setIntroduction(null);

        // Create the ResumeExperienceProject, which fails.

        restResumeExperienceProjectMockMvc.perform(post("/api/resume-experience-projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeExperienceProject)))
                .andExpect(status().isBadRequest());

        List<ResumeExperienceProject> resumeExperienceProjects = resumeExperienceProjectRepository.findAll();
        assertThat(resumeExperienceProjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResponsiilityIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeExperienceProjectRepository.findAll().size();
        // set the field null
        resumeExperienceProject.setResponsiility(null);

        // Create the ResumeExperienceProject, which fails.

        restResumeExperienceProjectMockMvc.perform(post("/api/resume-experience-projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeExperienceProject)))
                .andExpect(status().isBadRequest());

        List<ResumeExperienceProject> resumeExperienceProjects = resumeExperienceProjectRepository.findAll();
        assertThat(resumeExperienceProjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlatformIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeExperienceProjectRepository.findAll().size();
        // set the field null
        resumeExperienceProject.setPlatform(null);

        // Create the ResumeExperienceProject, which fails.

        restResumeExperienceProjectMockMvc.perform(post("/api/resume-experience-projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeExperienceProject)))
                .andExpect(status().isBadRequest());

        List<ResumeExperienceProject> resumeExperienceProjects = resumeExperienceProjectRepository.findAll();
        assertThat(resumeExperienceProjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResumeExperienceProjects() throws Exception {
        // Initialize the database
        resumeExperienceProjectRepository.saveAndFlush(resumeExperienceProject);

        // Get all the resumeExperienceProjects
        restResumeExperienceProjectMockMvc.perform(get("/api/resume-experience-projects?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(resumeExperienceProject.getId().intValue())))
                .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME_STR)))
                .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME_STR)))
                .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
                .andExpect(jsonPath("$.[*].responsiility").value(hasItem(DEFAULT_RESPONSIILITY.toString())))
                .andExpect(jsonPath("$.[*].platform").value(hasItem(DEFAULT_PLATFORM.toString())));
    }

    @Test
    @Transactional
    public void getResumeExperienceProject() throws Exception {
        // Initialize the database
        resumeExperienceProjectRepository.saveAndFlush(resumeExperienceProject);

        // Get the resumeExperienceProject
        restResumeExperienceProjectMockMvc.perform(get("/api/resume-experience-projects/{id}", resumeExperienceProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(resumeExperienceProject.getId().intValue()))
            .andExpect(jsonPath("$.index").value(DEFAULT_INDEX))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME_STR))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME_STR))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()))
            .andExpect(jsonPath("$.responsiility").value(DEFAULT_RESPONSIILITY.toString()))
            .andExpect(jsonPath("$.platform").value(DEFAULT_PLATFORM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResumeExperienceProject() throws Exception {
        // Get the resumeExperienceProject
        restResumeExperienceProjectMockMvc.perform(get("/api/resume-experience-projects/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResumeExperienceProject() throws Exception {
        // Initialize the database
        resumeExperienceProjectRepository.saveAndFlush(resumeExperienceProject);
        resumeExperienceProjectSearchRepository.save(resumeExperienceProject);
        int databaseSizeBeforeUpdate = resumeExperienceProjectRepository.findAll().size();

        // Update the resumeExperienceProject
        ResumeExperienceProject updatedResumeExperienceProject = new ResumeExperienceProject();
        updatedResumeExperienceProject.setId(resumeExperienceProject.getId());
        updatedResumeExperienceProject.setIndex(UPDATED_INDEX);
        updatedResumeExperienceProject.setName(UPDATED_NAME);
        updatedResumeExperienceProject.setStartTime(UPDATED_START_TIME);
        updatedResumeExperienceProject.setEndTime(UPDATED_END_TIME);
        updatedResumeExperienceProject.setIntroduction(UPDATED_INTRODUCTION);
        updatedResumeExperienceProject.setResponsiility(UPDATED_RESPONSIILITY);
        updatedResumeExperienceProject.setPlatform(UPDATED_PLATFORM);

        restResumeExperienceProjectMockMvc.perform(put("/api/resume-experience-projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedResumeExperienceProject)))
                .andExpect(status().isOk());

        // Validate the ResumeExperienceProject in the database
        List<ResumeExperienceProject> resumeExperienceProjects = resumeExperienceProjectRepository.findAll();
        assertThat(resumeExperienceProjects).hasSize(databaseSizeBeforeUpdate);
        ResumeExperienceProject testResumeExperienceProject = resumeExperienceProjects.get(resumeExperienceProjects.size() - 1);
        assertThat(testResumeExperienceProject.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testResumeExperienceProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResumeExperienceProject.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testResumeExperienceProject.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testResumeExperienceProject.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);
        assertThat(testResumeExperienceProject.getResponsiility()).isEqualTo(UPDATED_RESPONSIILITY);
        assertThat(testResumeExperienceProject.getPlatform()).isEqualTo(UPDATED_PLATFORM);

        // Validate the ResumeExperienceProject in ElasticSearch
        ResumeExperienceProject resumeExperienceProjectEs = resumeExperienceProjectSearchRepository.findOne(testResumeExperienceProject.getId());
        assertThat(resumeExperienceProjectEs).isEqualToComparingFieldByField(testResumeExperienceProject);
    }

    @Test
    @Transactional
    public void deleteResumeExperienceProject() throws Exception {
        // Initialize the database
        resumeExperienceProjectRepository.saveAndFlush(resumeExperienceProject);
        resumeExperienceProjectSearchRepository.save(resumeExperienceProject);
        int databaseSizeBeforeDelete = resumeExperienceProjectRepository.findAll().size();

        // Get the resumeExperienceProject
        restResumeExperienceProjectMockMvc.perform(delete("/api/resume-experience-projects/{id}", resumeExperienceProject.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean resumeExperienceProjectExistsInEs = resumeExperienceProjectSearchRepository.exists(resumeExperienceProject.getId());
        assertThat(resumeExperienceProjectExistsInEs).isFalse();

        // Validate the database is empty
        List<ResumeExperienceProject> resumeExperienceProjects = resumeExperienceProjectRepository.findAll();
        assertThat(resumeExperienceProjects).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchResumeExperienceProject() throws Exception {
        // Initialize the database
        resumeExperienceProjectRepository.saveAndFlush(resumeExperienceProject);
        resumeExperienceProjectSearchRepository.save(resumeExperienceProject);

        // Search the resumeExperienceProject
        restResumeExperienceProjectMockMvc.perform(get("/api/_search/resume-experience-projects?query=id:" + resumeExperienceProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumeExperienceProject.getId().intValue())))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME_STR)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME_STR)))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].responsiility").value(hasItem(DEFAULT_RESPONSIILITY.toString())))
            .andExpect(jsonPath("$.[*].platform").value(hasItem(DEFAULT_PLATFORM.toString())));
    }
}
