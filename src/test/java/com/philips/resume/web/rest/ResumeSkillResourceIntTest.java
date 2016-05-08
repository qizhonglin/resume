package com.philips.resume.web.rest;

import com.philips.resume.ResumeApp;
import com.philips.resume.domain.ResumeSkill;
import com.philips.resume.repository.ResumeSkillRepository;
import com.philips.resume.repository.search.ResumeSkillSearchRepository;

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
 * Test class for the ResumeSkillResource REST controller.
 *
 * @see ResumeSkillResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ResumeApp.class)
@WebAppConfiguration
@IntegrationTest
public class ResumeSkillResourceIntTest {

    private static final String DEFAULT_SKILL = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_SKILL = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private ResumeSkillRepository resumeSkillRepository;

    @Inject
    private ResumeSkillSearchRepository resumeSkillSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restResumeSkillMockMvc;

    private ResumeSkill resumeSkill;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResumeSkillResource resumeSkillResource = new ResumeSkillResource();
        ReflectionTestUtils.setField(resumeSkillResource, "resumeSkillSearchRepository", resumeSkillSearchRepository);
        ReflectionTestUtils.setField(resumeSkillResource, "resumeSkillRepository", resumeSkillRepository);
        this.restResumeSkillMockMvc = MockMvcBuilders.standaloneSetup(resumeSkillResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        resumeSkillSearchRepository.deleteAll();
        resumeSkill = new ResumeSkill();
        resumeSkill.setSkill(DEFAULT_SKILL);
    }

    @Test
    @Transactional
    public void createResumeSkill() throws Exception {
        int databaseSizeBeforeCreate = resumeSkillRepository.findAll().size();

        // Create the ResumeSkill

        restResumeSkillMockMvc.perform(post("/api/resume-skills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resumeSkill)))
                .andExpect(status().isCreated());

        // Validate the ResumeSkill in the database
        List<ResumeSkill> resumeSkills = resumeSkillRepository.findAll();
        assertThat(resumeSkills).hasSize(databaseSizeBeforeCreate + 1);
        ResumeSkill testResumeSkill = resumeSkills.get(resumeSkills.size() - 1);
        assertThat(testResumeSkill.getSkill()).isEqualTo(DEFAULT_SKILL);

        // Validate the ResumeSkill in ElasticSearch
        ResumeSkill resumeSkillEs = resumeSkillSearchRepository.findOne(testResumeSkill.getId());
        assertThat(resumeSkillEs).isEqualToComparingFieldByField(testResumeSkill);
    }

    @Test
    @Transactional
    public void getAllResumeSkills() throws Exception {
        // Initialize the database
        resumeSkillRepository.saveAndFlush(resumeSkill);

        // Get all the resumeSkills
        restResumeSkillMockMvc.perform(get("/api/resume-skills?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(resumeSkill.getId().intValue())))
                .andExpect(jsonPath("$.[*].skill").value(hasItem(DEFAULT_SKILL.toString())));
    }

    @Test
    @Transactional
    public void getResumeSkill() throws Exception {
        // Initialize the database
        resumeSkillRepository.saveAndFlush(resumeSkill);

        // Get the resumeSkill
        restResumeSkillMockMvc.perform(get("/api/resume-skills/{id}", resumeSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(resumeSkill.getId().intValue()))
            .andExpect(jsonPath("$.skill").value(DEFAULT_SKILL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResumeSkill() throws Exception {
        // Get the resumeSkill
        restResumeSkillMockMvc.perform(get("/api/resume-skills/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResumeSkill() throws Exception {
        // Initialize the database
        resumeSkillRepository.saveAndFlush(resumeSkill);
        resumeSkillSearchRepository.save(resumeSkill);
        int databaseSizeBeforeUpdate = resumeSkillRepository.findAll().size();

        // Update the resumeSkill
        ResumeSkill updatedResumeSkill = new ResumeSkill();
        updatedResumeSkill.setId(resumeSkill.getId());
        updatedResumeSkill.setSkill(UPDATED_SKILL);

        restResumeSkillMockMvc.perform(put("/api/resume-skills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedResumeSkill)))
                .andExpect(status().isOk());

        // Validate the ResumeSkill in the database
        List<ResumeSkill> resumeSkills = resumeSkillRepository.findAll();
        assertThat(resumeSkills).hasSize(databaseSizeBeforeUpdate);
        ResumeSkill testResumeSkill = resumeSkills.get(resumeSkills.size() - 1);
        assertThat(testResumeSkill.getSkill()).isEqualTo(UPDATED_SKILL);

        // Validate the ResumeSkill in ElasticSearch
        ResumeSkill resumeSkillEs = resumeSkillSearchRepository.findOne(testResumeSkill.getId());
        assertThat(resumeSkillEs).isEqualToComparingFieldByField(testResumeSkill);
    }

    @Test
    @Transactional
    public void deleteResumeSkill() throws Exception {
        // Initialize the database
        resumeSkillRepository.saveAndFlush(resumeSkill);
        resumeSkillSearchRepository.save(resumeSkill);
        int databaseSizeBeforeDelete = resumeSkillRepository.findAll().size();

        // Get the resumeSkill
        restResumeSkillMockMvc.perform(delete("/api/resume-skills/{id}", resumeSkill.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean resumeSkillExistsInEs = resumeSkillSearchRepository.exists(resumeSkill.getId());
        assertThat(resumeSkillExistsInEs).isFalse();

        // Validate the database is empty
        List<ResumeSkill> resumeSkills = resumeSkillRepository.findAll();
        assertThat(resumeSkills).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchResumeSkill() throws Exception {
        // Initialize the database
        resumeSkillRepository.saveAndFlush(resumeSkill);
        resumeSkillSearchRepository.save(resumeSkill);

        // Search the resumeSkill
        restResumeSkillMockMvc.perform(get("/api/_search/resume-skills?query=id:" + resumeSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumeSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].skill").value(hasItem(DEFAULT_SKILL.toString())));
    }
}
