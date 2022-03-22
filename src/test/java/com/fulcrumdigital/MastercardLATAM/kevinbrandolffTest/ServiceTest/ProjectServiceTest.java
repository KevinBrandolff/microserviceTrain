package com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.ServiceTest;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.ProjectDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.ProjectEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.ProjectException.ProjectNameAlreadyRegistred;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.EmployeeRepository;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.ProjectRepository;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Service.Impl.ProjectServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.EmployeeBuilder.EmployeeEntityBuilder.oneEmployeeEntityCompleted;
import static com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.ProjectBuilder.ProjectEntityBuilder.*;
import static com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.ProjectBuilder.ProjectDTOBuilder.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("kevin")
public class ProjectServiceTest {

    @Mock
    private ProjectRepository repository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ProjectServiceImpl service;

    @Test
    public void createAProject() throws ProjectNameAlreadyRegistred {

        ProjectEntity projectEntitySaved = createAProjectEntityWithEmployeesCompleted().now();

        when( repository.save( any( ProjectEntity.class ) ) ).thenReturn( projectEntitySaved );
        when( employeeRepository.findById( projectEntitySaved.getEmployees().get(0).getId() ) ).thenReturn( Optional.of( oneEmployeeEntityCompleted().now() ) );

        ProjectDTO projectDTOtoSave = createAProjectDTOWithEmployeesCompleted().withId(null).withCreateAt(null).now();

        ProjectDTO projectDTOSaved = service.createProject( projectDTOtoSave );

        assertEquals( projectDTOtoSave.getName(), projectDTOSaved.getName() );

    }

    @Test
    public void updateAProject() throws ProjectNameAlreadyRegistred {

        ProjectEntity projectEntitySaved = createAProjectEntityWithEmployeesCompleted().now();

        when( repository.save( any( ProjectEntity.class ) ) ).thenReturn( projectEntitySaved );
        when( employeeRepository.findById( projectEntitySaved.getEmployees().get(0).getId() ) ).thenReturn( Optional.of( oneEmployeeEntityCompleted().now() ) );

        ProjectDTO projectDTOtoSave = createAProjectDTOWithEmployeesCompleted().withId(null).withCreateAt(null).now();

        ProjectDTO projectDTOSaved = service.createProject( projectDTOtoSave );

        projectDTOSaved.setName("project updated");
        projectEntitySaved.setName("project updated");

        when( repository.findById( same( projectEntitySaved.getId() ) ) ).thenReturn( Optional.of( projectEntitySaved ) );
        when( repository.save( same( projectEntitySaved ) ) ).thenReturn( projectEntitySaved );

        ProjectDTO projectDTOUpdated = service.update( projectDTOSaved );

        assertEquals( projectDTOSaved.getName(), projectDTOUpdated.getName() );

    }

    @Test
    public void findAProjectById(){

        ProjectEntity projectEntitySaved = createAProjectEntityWithEmployeesCompleted().now();

        when( repository.findById( projectEntitySaved.getId() ) ).thenReturn( Optional.of( projectEntitySaved ) );

        ProjectDTO projectDTOFound = service.findById(projectEntitySaved.getId() );

        assertTrue( projectDTOFound.getId() == projectEntitySaved.getId() );

    }

    @Test
    public void findAProjectByName(){

        ProjectEntity projectEntitySaved = createAProjectEntityWithEmployeesCompleted().now();

        when( repository.findByName( projectEntitySaved.getName() ) ).thenReturn( Optional.of( projectEntitySaved ) );

        ProjectDTO projectDTOFound = service.findByName(projectEntitySaved.getName() );

        assertTrue( projectDTOFound.getId() == projectEntitySaved.getId() );
    }

    @Test
    public void findAllProjects(){

        ProjectEntity projectEntitySaved = createAProjectEntityWithEmployeesCompleted().now();

        List<ProjectEntity> listOfProjects = Arrays.asList( projectEntitySaved );

        when( repository.findAll() ).thenReturn( listOfProjects );

        List<ProjectDTO> listOfProjectsDTOFound = service.findAll();

        assertTrue( !listOfProjectsDTOFound.isEmpty() );
    }

    @Test
    public void findAllProjectsByEmployeeId(){

        ProjectEntity projectEntitySaved = createAProjectEntityWithEmployeesCompleted().now();

        List<ProjectEntity> listOfProjects = Arrays.asList( projectEntitySaved );

        when( repository.findAllByEmployeesId( listOfProjects.get(0).getId() ) ).thenReturn( listOfProjects );

        List<ProjectDTO> listOfProjectsDTOFound = service.findAllByEmployeesId( listOfProjects.get(0).getId() );

        assertTrue( !listOfProjectsDTOFound.isEmpty() );

    }

    @Test
    public void throwProjectNameAlreadyRegistredWhenTryCreateAProjectWithANameAlreadyExistent(){

        ProjectEntity projectEntitySaved = createAProjectEntityWithEmployeesCompleted().now();

        when( repository.findByName( projectEntitySaved.getName() ) ).thenReturn( Optional.of( projectEntitySaved ) );

        ProjectDTO projectDTO = createAProjectDTOWithEmployeesCompleted().now();

        assertThrows( ProjectNameAlreadyRegistred.class,
                () -> {
                    service.createProject( projectDTO );
                });

    }

    @Test
    public void throwProjectNameAlreadyRegistredWhenTryUpdateAProjectWithANameAlreadyExistent(){

        ProjectEntity projectEntitySaved = createAProjectEntityWithEmployeesCompleted().now();
        ProjectEntity projectEntitySavedWithTheName = createAProjectEntityWithEmployeesCompleted().withName("joazinho").now();

        when( repository.findById( projectEntitySaved.getId() ) ).thenReturn( Optional.of( projectEntitySaved ) );
        when( repository.findByName( "joazinho" ) ).thenReturn( Optional.of( projectEntitySavedWithTheName ) );

        ProjectDTO projectDTO = createAProjectDTOWithEmployeesCompleted().withName("joazinho").now();

        assertThrows( ProjectNameAlreadyRegistred.class,
                () -> {
                    service.update( projectDTO );
                });

    }

}
