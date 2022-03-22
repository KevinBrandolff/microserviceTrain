package com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.ProjectBuilder;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.EmployeeEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.ProjectEntity;

import static com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.EmployeeBuilder.EmployeeEntityBuilder.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ProjectEntityBuilder {

    private ProjectEntity projectEntity;

    private ProjectEntityBuilder(){
    }

    public static ProjectEntityBuilder createAProjectEntityWithEmployeesCompleted(){
        ProjectEntityBuilder projectEntityBuilder = new ProjectEntityBuilder();
        projectEntityBuilder.projectEntity = new ProjectEntity();
        projectEntityBuilder.projectEntity.setName("Project 1");
        projectEntityBuilder.projectEntity.setId(1);
        projectEntityBuilder.projectEntity.setCreatedAt(LocalDate.now());

        EmployeeEntity employeeEntity1 = oneEmployeeEntityCompleted().now();

        List<EmployeeEntity> listOfEmployees = Arrays.asList( employeeEntity1 );

        projectEntityBuilder.projectEntity.setEmployees(listOfEmployees);

        return projectEntityBuilder;
    }

    public static ProjectEntityBuilder createAProjectEntityWithEmployeesSavedCompleted(){
        ProjectEntityBuilder projectEntityBuilder = new ProjectEntityBuilder();
        projectEntityBuilder.projectEntity = new ProjectEntity();
        projectEntityBuilder.projectEntity.setName("Project 1");
        projectEntityBuilder.projectEntity.setId(1);
        projectEntityBuilder.projectEntity.setCreatedAt(LocalDate.now());

        EmployeeEntity employeeEntity1 = oneEmployeeEntityCompleted().withId(null).now();

        List<EmployeeEntity> listOfEmployees = Arrays.asList( employeeEntity1 );

        projectEntityBuilder.projectEntity.setEmployees(listOfEmployees);

        return projectEntityBuilder;
    }

    public ProjectEntity now(){
        return projectEntity;
    }

    public ProjectEntityBuilder withId( Integer id ){
        this.projectEntity.setId(id);
        return this;
    }

    public ProjectEntityBuilder withCreateAt( LocalDate localDate ){
        this.projectEntity.setCreatedAt(localDate);
        return this;
    }

    public ProjectEntityBuilder withName( String name ){
        this.projectEntity.setName(name);
        return this;
    }
}
