package com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.ProjectDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.ProjectEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.ProjectBuilder.ProjectDTOBuilder.createAProjectDTOWithEmployeesCompleted;
import static com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.ProjectBuilder.ProjectEntityBuilder.createAProjectEntityWithEmployeesCompleted;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc( addFilters = false )
@ActiveProfiles("kevin")
public class ProjectControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectRepository repository;

    @Test
    public void createAProject() throws Exception {

        ProjectEntity projectEntitySaved = createAProjectEntityWithEmployeesCompleted().now();
        ProjectDTO projectDTO = createAProjectDTOWithEmployeesCompleted().withId(null).withCreateAt(null).now();

        when( repository.save( any( ProjectEntity.class ) ) ).thenReturn( projectEntitySaved );

        MvcResult mvcResult = this.mockMvc.perform(post("/kevin/project/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(projectDTO)))
                        .andExpect( status().isCreated() )
                        .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        projectDTO.setId( projectEntitySaved.getId() );
        projectDTO.setCreatedAt( projectEntitySaved.getCreatedAt() );

        assertEquals( objectMapper.writeValueAsString( projectDTO ), response );

    }

    @Test
    public void updateAProject() throws Exception {

        ProjectEntity projectEntitySaved = createAProjectEntityWithEmployeesCompleted().now();
        ProjectDTO projectDTO = createAProjectDTOWithEmployeesCompleted().withCreateAt(null).withName("project updated").now();
        ProjectEntity projectEntityUpdated = createAProjectEntityWithEmployeesCompleted().withName("project updated").now();

        when( repository.findById( projectDTO.getId() ) ).thenReturn( Optional.of( projectEntitySaved ) );
        when( repository.save( projectEntityUpdated ) ).thenReturn( projectEntityUpdated );

        MvcResult mvcResult = this.mockMvc.perform(put("/kevin/project/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(projectDTO)))
                .andExpect( status().isOk() )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        projectDTO.setCreatedAt(LocalDate.now());

        assertEquals( objectMapper.writeValueAsString( projectDTO ), response );

    }

    @Test
    public void findAProjectById() throws Exception {

        ProjectEntity projectEntity = createAProjectEntityWithEmployeesCompleted().now();
        ProjectDTO projectDTO = createAProjectDTOWithEmployeesCompleted().now();

        when( repository.findById( projectEntity.getId() ) ).thenReturn( Optional.of( projectEntity ) );

        MvcResult mvcResult = this.mockMvc.perform(get("/kevin/project/" + projectEntity.getId() ) )
                .andExpect( status().isOk() )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( projectDTO ), response );

    }

    @Test
    public void findAProjectByName() throws Exception {

        ProjectEntity projectEntity = createAProjectEntityWithEmployeesCompleted().now();
        ProjectDTO projectDTO = createAProjectDTOWithEmployeesCompleted().now();

        when( repository.findByName( projectEntity.getName() ) ).thenReturn( Optional.of( projectEntity ) );

        MvcResult mvcResult = this.mockMvc.perform(get("/kevin/project/name/" + projectEntity.getName() ) )
                .andExpect( status().isOk() )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( projectDTO ), response );

    }

    @Test
    public void findAllProjects() throws Exception {

        ProjectEntity projectEntity = createAProjectEntityWithEmployeesCompleted().now();
        ProjectDTO projectDTO = createAProjectDTOWithEmployeesCompleted().now();

        List<ProjectEntity> listOfProjectEntity = Arrays.asList( projectEntity );
        List<ProjectDTO> listOfProjectDTO = Arrays.asList( projectDTO );

        when( repository.findAll() ).thenReturn( listOfProjectEntity );

        MvcResult mvcResult = this.mockMvc.perform(get("/kevin/project/" ) )
                .andExpect( status().isOk() )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( listOfProjectDTO ), response );

    }

    @Test
    public void findAllProjectsByEmployeeId() throws Exception {

        ProjectEntity projectEntity = createAProjectEntityWithEmployeesCompleted().now();
        ProjectDTO projectDTO = createAProjectDTOWithEmployeesCompleted().now();

        List<ProjectEntity> listOfProjectEntity = Arrays.asList( projectEntity );
        List<ProjectDTO> listOfProjectDTO = Arrays.asList( projectDTO );

        when( repository.findAllByEmployeesId( projectEntity.getEmployees().get(0).getId() ) ).thenReturn( listOfProjectEntity );

        MvcResult mvcResult = this.mockMvc.perform(get("/kevin/project/employee/" + projectEntity.getEmployees().get(0).getId() ) )
                .andExpect( status().isOk() )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( listOfProjectDTO ), response );
    }

}
