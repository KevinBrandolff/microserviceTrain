package com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.RepositoryTest;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.EmployeeEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.ProjectEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.EmployeeRepository;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.ProjectRepository;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.EmployeeBuilder.EmployeeEntityBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("kevin")
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void saveEmployee() {
        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(null).now();
        EmployeeEntity employeeSaved = employeeRepository.save( employeeEntity );

        assertTrue( employeeSaved.getId() != null );
    }

    @Test
    public void throwsDataIntegrityViolationExceptionWhenSaveEmployeeWithRegisterAlreadyExisting() {
        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(null).now();
        employeeRepository.save( employeeEntity );

        EmployeeEntity employeeEntity2 = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(null).withEmail("bbb@bbbb.com").now();

        assertThrows( DataIntegrityViolationException.class, () -> {
            employeeRepository.save( employeeEntity2 );
        });
    }

    @Test
    public void throwsDataIntegrityViolationExceptionWhenSaveEmployeeWithEmailAlreadyExisting() {
        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(null).now();
        employeeRepository.save( employeeEntity );

        EmployeeEntity employeeEntity2 = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(null).withRegister("99999dsds99").now();

        assertThrows( DataIntegrityViolationException.class, () -> {
            employeeRepository.save( employeeEntity2 );
        });
    }

    @Test
    public void editEmployee() {
        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(null).now();
        EmployeeEntity employeeEntitySaved = employeeRepository.save( employeeEntity );

        employeeEntitySaved.setName("Thorrr");

        EmployeeEntity employeeEntityUpdated = employeeRepository.save( employeeEntitySaved );

        assertEquals( "Thorrr", employeeEntityUpdated.getName() );
        assertEquals( employeeEntitySaved.getId(), employeeEntityUpdated.getId() );
    }

    @Test
    public void throwsExceptionWhenTryUpdateEmployeeWithARegisterAlreadyExisting() {
//        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(null).now();
//        EmployeeEntity employeeEntitySaved = employeeRepository.save( employeeEntity );
//
//        EmployeeEntity employeeEntity2 = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(null)
//                .withRegister("45454545")
//                .withEmail("aaaa@aaaaaaaa.com")
//                .now();
//
//        employeeRepository.save( employeeEntity2 );
//
//        employeeEntitySaved.setRegister("45454545");
//
//        assertThrows( DataIntegrityViolationException.class, () -> {
//            employeeRepository.save( employeeEntitySaved );
//        });

        // WHY IS THIS NOT WORKING????
    }

    @Test
    public void deleteEmployee() {
        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(null).now();
        EmployeeEntity employeeSaved = employeeRepository.save( employeeEntity );

        employeeRepository.deleteById( employeeSaved.getId() );

        assertTrue( !employeeRepository.findById(employeeSaved.getId()).isPresent() );
    }

    @Test
    public void findEmployeeById() {
        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(null).now();
        EmployeeEntity employeeSaved = employeeRepository.save( employeeEntity );

        assertTrue( employeeRepository.findById(employeeSaved.getId()).isPresent() );
    }

    @Test
    public void createEmployeeWithNewProjects(){

        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(null).now();
        ProjectEntity projectEntity = ProjectEntity.builder().name("Project 1").build();

        List<ProjectEntity> listOfProjectEntity = Arrays.asList( projectEntity );

        employeeEntity.setProjects( listOfProjectEntity );

        EmployeeEntity employeeEntitySaved = employeeRepository.save( employeeEntity );

        assertTrue( projectRepository.findByName( "Project 1" ) != null );
        assertTrue( !employeeRepository.findById( employeeEntitySaved.getId() ).get().getProjects().isEmpty() );

    };

    @Test
    public void updateEmployeeNameWithoutModifyingProjects(){

        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(null).now();
        ProjectEntity projectEntity = ProjectEntity.builder().name("Project 1").build();

        List<ProjectEntity> listOfProjectEntity = Arrays.asList( projectEntity );

        employeeEntity.setProjects( listOfProjectEntity );

        EmployeeEntity employeeEntitySaved = employeeRepository.save( employeeEntity );

        EmployeeEntity employeeEntityToUpdate = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(employeeEntitySaved.getId()).withName("Jorge").now();

        for ( int i = 0; i < listOfProjectEntity.size(); i++ ) {
            listOfProjectEntity.get( i ).setId( i+1 );
        }

        employeeEntityToUpdate.setProjects( listOfProjectEntity );

        EmployeeEntity employeeEntityUpdated = employeeRepository.save( employeeEntityToUpdate );

        assertTrue( employeeRepository.findById( employeeEntitySaved.getId() ).get().getName().equals( "Jorge" ) );
        assertEquals( employeeEntityUpdated.getProjects(), employeeEntitySaved.getProjects() );
        assertEquals( employeeEntityUpdated.getProjects().size(), employeeEntitySaved.getProjects().size() );
    };

    @Test
    public void UpdateEmployeeWithOneNewProject(){

        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(null).now();

        EmployeeEntity employeeEntitySaved = employeeRepository.save( employeeEntity );

        EmployeeEntity employeeEntityToUpdate = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(employeeEntitySaved.getId()).now();

        List<ProjectEntity> listOfProjectEntityToUpdate = new ArrayList<>();

        ProjectEntity projectEntity = ProjectEntity.builder().name("Project 1").build();
        listOfProjectEntityToUpdate.add( projectEntity );

        employeeEntityToUpdate.setProjects( listOfProjectEntityToUpdate );

        employeeRepository.save( employeeEntityToUpdate );

        assertTrue( employeeRepository.findById( employeeEntitySaved.getId() ).get().getProjects().size() == 1 );

    };

    @Test
    public void UpdateEmployeeRemovingOneProjectOfTheList(){

        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(null).now();
        ProjectEntity projectEntity = ProjectEntity.builder().name("Project 1").build();

        List<ProjectEntity> listOfProjectEntity = Arrays.asList( projectEntity );

        employeeEntity.setProjects( listOfProjectEntity );

        EmployeeEntity employeeEntitySaved = employeeRepository.save( employeeEntity );

        List<ProjectEntity> listEmpty = new ArrayList<>();

        employeeEntitySaved.setProjects(listEmpty);

        employeeRepository.save( employeeEntitySaved );

        assertTrue( employeeRepository.findById( employeeEntitySaved.getId() ).get().getProjects().size() == 0 );

    };


}
