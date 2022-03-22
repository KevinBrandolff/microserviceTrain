package com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.ProjectBuilder;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.EmployeeDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.ProjectDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.EmployeeEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.ProjectEntity;

import static com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.EmployeeBuilder.EmployeeDTOBuilder.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public class ProjectDTOBuilder {

    private ProjectDTO projectDTO;

    private ProjectDTOBuilder(){
    }

    public static ProjectDTOBuilder createAProjectDTOWithEmployeesCompleted(){
        ProjectDTOBuilder projectDTOBuilder = new ProjectDTOBuilder();
        projectDTOBuilder.projectDTO = new ProjectDTO();
        projectDTOBuilder.projectDTO.setName("Project 1");
        projectDTOBuilder.projectDTO.setId(1);
        projectDTOBuilder.projectDTO.setCreatedAt(LocalDate.now());

        EmployeeDTO employeeDTO1 = oneEmployeeDTOCompleted().now();

        List<EmployeeDTO> listOfEmployees = Arrays.asList( employeeDTO1 );

        projectDTOBuilder.projectDTO.setEmployees(listOfEmployees);

        return projectDTOBuilder;
    }

    public static ProjectDTOBuilder createAProjectDTOWithoutEmployeesCompleted(){
        ProjectDTOBuilder projectDTOBuilder = new ProjectDTOBuilder();
        projectDTOBuilder.projectDTO = new ProjectDTO();
        projectDTOBuilder.projectDTO.setName("Project 1");
        projectDTOBuilder.projectDTO.setId(1);
        projectDTOBuilder.projectDTO.setCreatedAt(LocalDate.now());

        return projectDTOBuilder;
    }

    public ProjectDTO now(){
        return projectDTO;
    }

    public ProjectDTOBuilder withId( Integer id ){
        this.projectDTO.setId(id);
        return this;
    }

    public ProjectDTOBuilder withCreateAt( LocalDate localDate ){
        this.projectDTO.setCreatedAt(localDate);
        return this;
    }

    public ProjectDTOBuilder withName( String name ){
        this.projectDTO.setName(name);
        return this;
    }

}
