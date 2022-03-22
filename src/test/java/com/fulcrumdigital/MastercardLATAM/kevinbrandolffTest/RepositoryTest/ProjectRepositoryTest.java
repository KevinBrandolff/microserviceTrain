package com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.RepositoryTest;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.ProjectEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.ProjectBuilder.ProjectEntityBuilder.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("kevin")
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository repository;

    @Test
    public void saveAProjectWithEmployess(){
        ProjectEntity projectEntity = createAProjectEntityWithEmployeesSavedCompleted().withId(null).withCreateAt(null).now();

        repository.save(projectEntity);

        assertTrue( projectEntity.getId() != null );
    }

    @Test
    public void deleteAProject(){
        ProjectEntity projectEntity = createAProjectEntityWithEmployeesSavedCompleted().withId(null).withCreateAt(null).now();

        repository.save(projectEntity);
        repository.deleteById(projectEntity.getId());

        assertFalse( repository.findById( projectEntity.getId() ).isPresent() );
    }

    @Test
    public void findAProjectById(){
        ProjectEntity projectEntity = createAProjectEntityWithEmployeesSavedCompleted().withId(null).withCreateAt(null).now();

        repository.save(projectEntity);

        assertEquals( projectEntity.getId(), repository.findById(projectEntity.getId()).get().getId() );
    }

    @Test
    public void updateAProject(){
        ProjectEntity projectEntity = createAProjectEntityWithEmployeesSavedCompleted().withId(null).withCreateAt(null).now();

        ProjectEntity projectEntitySaved = repository.save( projectEntity );

        projectEntitySaved.setName("project updated");
        projectEntitySaved.setEmployees( null );

        ProjectEntity projectEntityUpdated = repository.save( projectEntitySaved );

        assertTrue( projectEntityUpdated.getName().equals(projectEntitySaved.getName()) );
        assertTrue( projectEntityUpdated.getId().equals(projectEntity.getId()) );
    }

    @Test
    public void findAllProjects(){
        ProjectEntity projectEntity = createAProjectEntityWithEmployeesSavedCompleted().withId(null).withCreateAt(null).now();

        repository.save(projectEntity);

        assertEquals( projectEntity.getId(), repository.findAll().get(0).getId());
    }

    @Test
    public void findAProjectByName(){
        ProjectEntity projectEntity = createAProjectEntityWithEmployeesSavedCompleted().withId(null).withCreateAt(null).now();

        repository.save(projectEntity);

        assertEquals( projectEntity.getId(), repository.findByName(projectEntity.getName()).get().getId() );
    }

    @Test
    public void findAllProjectsByEmployeeId(){
//        ProjectEntity projectEntity = createAProjectEntityWithEmployeesCompleted().withId(null).withCreateAt(null).now();
//
//        repository.save(projectEntity);
//
//        EmployeeEntity employeeEntity = repository.findById( projectEntity.getId() ).get().getEmployees().get(0);
//
//        assertEquals( projectEntity.getId(), repository.findAllByEmployeesId(projectEntity.getEmployees().get(0).getId()).get(0).getId() );
    }

}
