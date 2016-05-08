package com.philips.resume.web.rest;

import com.philips.resume.ResumeApp;
import com.philips.resume.domain.Resume;
import com.philips.resume.repository.ResumeRepository;
import com.philips.resume.service.ResumeService;
import com.philips.resume.repository.search.ResumeSearchRepository;

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
 * Test class for the ResumeResource REST controller.
 *
 * @see ResumeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ResumeApp.class)
@WebAppConfiguration
@IntegrationTest
public class ResumeResourceIntTest {

    private static final String DEFAULT_INFO_EMAIL = "AAAAA";
    private static final String UPDATED_INFO_EMAIL = "BBBBB";
    private static final String DEFAULT_INFO_PHONE = "AAAAA";
    private static final String UPDATED_INFO_PHONE = "BBBBB";
    private static final String DEFAULT_INFO_GITHUB = "AAAAA";
    private static final String UPDATED_INFO_GITHUB = "BBBBB";
    private static final String DEFAULT_INFO_LINKEDIN = "AAAAA";
    private static final String UPDATED_INFO_LINKEDIN = "BBBBB";
    private static final String DEFAULT_PROFILE_BASIC = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_PROFILE_BASIC = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_PROFILE_TECHNIQUE_DOMAIN = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_PROFILE_TECHNIQUE_DOMAIN = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_PROFILE_SOFTWARE_SYSTEM = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_PROFILE_SOFTWARE_SYSTEM = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_PROFILE_MULTIBRANCH_EXPERIENCE = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_PROFILE_MULTIBRANCH_EXPERIENCE = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_PROFILE_PREFERRED_POSITION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_PROFILE_PREFERRED_POSITION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private ResumeRepository resumeRepository;

    @Inject
    private ResumeService resumeService;

    @Inject
    private ResumeSearchRepository resumeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restResumeMockMvc;

    private Resume resume;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResumeResource resumeResource = new ResumeResource();
        ReflectionTestUtils.setField(resumeResource, "resumeService", resumeService);
        this.restResumeMockMvc = MockMvcBuilders.standaloneSetup(resumeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        resumeSearchRepository.deleteAll();
        resume = new Resume();
        resume.setInfoEmail(DEFAULT_INFO_EMAIL);
        resume.setInfoPhone(DEFAULT_INFO_PHONE);
        resume.setInfoGithub(DEFAULT_INFO_GITHUB);
        resume.setInfoLinkedin(DEFAULT_INFO_LINKEDIN);
        resume.setProfileBasic(DEFAULT_PROFILE_BASIC);
        resume.setProfileTechniqueDomain(DEFAULT_PROFILE_TECHNIQUE_DOMAIN);
        resume.setProfileSoftwareSystem(DEFAULT_PROFILE_SOFTWARE_SYSTEM);
        resume.setProfileMultibranchExperience(DEFAULT_PROFILE_MULTIBRANCH_EXPERIENCE);
        resume.setProfilePreferredPosition(DEFAULT_PROFILE_PREFERRED_POSITION);
    }

    @Test
    @Transactional
    public void createResume() throws Exception {
        int databaseSizeBeforeCreate = resumeRepository.findAll().size();

        // Create the Resume

        restResumeMockMvc.perform(post("/api/resumes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resume)))
                .andExpect(status().isCreated());

        // Validate the Resume in the database
        List<Resume> resumes = resumeRepository.findAll();
        assertThat(resumes).hasSize(databaseSizeBeforeCreate + 1);
        Resume testResume = resumes.get(resumes.size() - 1);
        assertThat(testResume.getInfoEmail()).isEqualTo(DEFAULT_INFO_EMAIL);
        assertThat(testResume.getInfoPhone()).isEqualTo(DEFAULT_INFO_PHONE);
        assertThat(testResume.getInfoGithub()).isEqualTo(DEFAULT_INFO_GITHUB);
        assertThat(testResume.getInfoLinkedin()).isEqualTo(DEFAULT_INFO_LINKEDIN);
        assertThat(testResume.getProfileBasic()).isEqualTo(DEFAULT_PROFILE_BASIC);
        assertThat(testResume.getProfileTechniqueDomain()).isEqualTo(DEFAULT_PROFILE_TECHNIQUE_DOMAIN);
        assertThat(testResume.getProfileSoftwareSystem()).isEqualTo(DEFAULT_PROFILE_SOFTWARE_SYSTEM);
        assertThat(testResume.getProfileMultibranchExperience()).isEqualTo(DEFAULT_PROFILE_MULTIBRANCH_EXPERIENCE);
        assertThat(testResume.getProfilePreferredPosition()).isEqualTo(DEFAULT_PROFILE_PREFERRED_POSITION);

        // Validate the Resume in ElasticSearch
        Resume resumeEs = resumeSearchRepository.findOne(testResume.getId());
        assertThat(resumeEs).isEqualToComparingFieldByField(testResume);
    }

    @Test
    @Transactional
    public void checkInfoEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeRepository.findAll().size();
        // set the field null
        resume.setInfoEmail(null);

        // Create the Resume, which fails.

        restResumeMockMvc.perform(post("/api/resumes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resume)))
                .andExpect(status().isBadRequest());

        List<Resume> resumes = resumeRepository.findAll();
        assertThat(resumes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInfoPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeRepository.findAll().size();
        // set the field null
        resume.setInfoPhone(null);

        // Create the Resume, which fails.

        restResumeMockMvc.perform(post("/api/resumes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resume)))
                .andExpect(status().isBadRequest());

        List<Resume> resumes = resumeRepository.findAll();
        assertThat(resumes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResumes() throws Exception {
        // Initialize the database
        resumeRepository.saveAndFlush(resume);

        // Get all the resumes
        restResumeMockMvc.perform(get("/api/resumes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(resume.getId().intValue())))
                .andExpect(jsonPath("$.[*].infoEmail").value(hasItem(DEFAULT_INFO_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].infoPhone").value(hasItem(DEFAULT_INFO_PHONE.toString())))
                .andExpect(jsonPath("$.[*].infoGithub").value(hasItem(DEFAULT_INFO_GITHUB.toString())))
                .andExpect(jsonPath("$.[*].infoLinkedin").value(hasItem(DEFAULT_INFO_LINKEDIN.toString())))
                .andExpect(jsonPath("$.[*].profileBasic").value(hasItem(DEFAULT_PROFILE_BASIC.toString())))
                .andExpect(jsonPath("$.[*].profileTechniqueDomain").value(hasItem(DEFAULT_PROFILE_TECHNIQUE_DOMAIN.toString())))
                .andExpect(jsonPath("$.[*].profileSoftwareSystem").value(hasItem(DEFAULT_PROFILE_SOFTWARE_SYSTEM.toString())))
                .andExpect(jsonPath("$.[*].profileMultibranchExperience").value(hasItem(DEFAULT_PROFILE_MULTIBRANCH_EXPERIENCE.toString())))
                .andExpect(jsonPath("$.[*].profilePreferredPosition").value(hasItem(DEFAULT_PROFILE_PREFERRED_POSITION.toString())));
    }

    @Test
    @Transactional
    public void getResume() throws Exception {
        // Initialize the database
        resumeRepository.saveAndFlush(resume);

        // Get the resume
        restResumeMockMvc.perform(get("/api/resumes/{id}", resume.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(resume.getId().intValue()))
            .andExpect(jsonPath("$.infoEmail").value(DEFAULT_INFO_EMAIL.toString()))
            .andExpect(jsonPath("$.infoPhone").value(DEFAULT_INFO_PHONE.toString()))
            .andExpect(jsonPath("$.infoGithub").value(DEFAULT_INFO_GITHUB.toString()))
            .andExpect(jsonPath("$.infoLinkedin").value(DEFAULT_INFO_LINKEDIN.toString()))
            .andExpect(jsonPath("$.profileBasic").value(DEFAULT_PROFILE_BASIC.toString()))
            .andExpect(jsonPath("$.profileTechniqueDomain").value(DEFAULT_PROFILE_TECHNIQUE_DOMAIN.toString()))
            .andExpect(jsonPath("$.profileSoftwareSystem").value(DEFAULT_PROFILE_SOFTWARE_SYSTEM.toString()))
            .andExpect(jsonPath("$.profileMultibranchExperience").value(DEFAULT_PROFILE_MULTIBRANCH_EXPERIENCE.toString()))
            .andExpect(jsonPath("$.profilePreferredPosition").value(DEFAULT_PROFILE_PREFERRED_POSITION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResume() throws Exception {
        // Get the resume
        restResumeMockMvc.perform(get("/api/resumes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResume() throws Exception {
        // Initialize the database
        resumeService.save(resume);

        int databaseSizeBeforeUpdate = resumeRepository.findAll().size();

        // Update the resume
        Resume updatedResume = new Resume();
        updatedResume.setId(resume.getId());
        updatedResume.setInfoEmail(UPDATED_INFO_EMAIL);
        updatedResume.setInfoPhone(UPDATED_INFO_PHONE);
        updatedResume.setInfoGithub(UPDATED_INFO_GITHUB);
        updatedResume.setInfoLinkedin(UPDATED_INFO_LINKEDIN);
        updatedResume.setProfileBasic(UPDATED_PROFILE_BASIC);
        updatedResume.setProfileTechniqueDomain(UPDATED_PROFILE_TECHNIQUE_DOMAIN);
        updatedResume.setProfileSoftwareSystem(UPDATED_PROFILE_SOFTWARE_SYSTEM);
        updatedResume.setProfileMultibranchExperience(UPDATED_PROFILE_MULTIBRANCH_EXPERIENCE);
        updatedResume.setProfilePreferredPosition(UPDATED_PROFILE_PREFERRED_POSITION);

        restResumeMockMvc.perform(put("/api/resumes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedResume)))
                .andExpect(status().isOk());

        // Validate the Resume in the database
        List<Resume> resumes = resumeRepository.findAll();
        assertThat(resumes).hasSize(databaseSizeBeforeUpdate);
        Resume testResume = resumes.get(resumes.size() - 1);
        assertThat(testResume.getInfoEmail()).isEqualTo(UPDATED_INFO_EMAIL);
        assertThat(testResume.getInfoPhone()).isEqualTo(UPDATED_INFO_PHONE);
        assertThat(testResume.getInfoGithub()).isEqualTo(UPDATED_INFO_GITHUB);
        assertThat(testResume.getInfoLinkedin()).isEqualTo(UPDATED_INFO_LINKEDIN);
        assertThat(testResume.getProfileBasic()).isEqualTo(UPDATED_PROFILE_BASIC);
        assertThat(testResume.getProfileTechniqueDomain()).isEqualTo(UPDATED_PROFILE_TECHNIQUE_DOMAIN);
        assertThat(testResume.getProfileSoftwareSystem()).isEqualTo(UPDATED_PROFILE_SOFTWARE_SYSTEM);
        assertThat(testResume.getProfileMultibranchExperience()).isEqualTo(UPDATED_PROFILE_MULTIBRANCH_EXPERIENCE);
        assertThat(testResume.getProfilePreferredPosition()).isEqualTo(UPDATED_PROFILE_PREFERRED_POSITION);

        // Validate the Resume in ElasticSearch
        Resume resumeEs = resumeSearchRepository.findOne(testResume.getId());
        assertThat(resumeEs).isEqualToComparingFieldByField(testResume);
    }

    @Test
    @Transactional
    public void deleteResume() throws Exception {
        // Initialize the database
        resumeService.save(resume);

        int databaseSizeBeforeDelete = resumeRepository.findAll().size();

        // Get the resume
        restResumeMockMvc.perform(delete("/api/resumes/{id}", resume.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean resumeExistsInEs = resumeSearchRepository.exists(resume.getId());
        assertThat(resumeExistsInEs).isFalse();

        // Validate the database is empty
        List<Resume> resumes = resumeRepository.findAll();
        assertThat(resumes).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchResume() throws Exception {
        // Initialize the database
        resumeService.save(resume);

        // Search the resume
        restResumeMockMvc.perform(get("/api/_search/resumes?query=id:" + resume.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resume.getId().intValue())))
            .andExpect(jsonPath("$.[*].infoEmail").value(hasItem(DEFAULT_INFO_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].infoPhone").value(hasItem(DEFAULT_INFO_PHONE.toString())))
            .andExpect(jsonPath("$.[*].infoGithub").value(hasItem(DEFAULT_INFO_GITHUB.toString())))
            .andExpect(jsonPath("$.[*].infoLinkedin").value(hasItem(DEFAULT_INFO_LINKEDIN.toString())))
            .andExpect(jsonPath("$.[*].profileBasic").value(hasItem(DEFAULT_PROFILE_BASIC.toString())))
            .andExpect(jsonPath("$.[*].profileTechniqueDomain").value(hasItem(DEFAULT_PROFILE_TECHNIQUE_DOMAIN.toString())))
            .andExpect(jsonPath("$.[*].profileSoftwareSystem").value(hasItem(DEFAULT_PROFILE_SOFTWARE_SYSTEM.toString())))
            .andExpect(jsonPath("$.[*].profileMultibranchExperience").value(hasItem(DEFAULT_PROFILE_MULTIBRANCH_EXPERIENCE.toString())))
            .andExpect(jsonPath("$.[*].profilePreferredPosition").value(hasItem(DEFAULT_PROFILE_PREFERRED_POSITION.toString())));
    }
}
