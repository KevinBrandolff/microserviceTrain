package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.EmployeeEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

import static com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Util.Converters.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDTO {

    private Integer id;
    @NotNull( message = "Name required" )
    private String name;
    @NotNull( message = "Register required" )
    private String register;
    private Integer age;
    @NotNull( message = "Address required" )
    private String address;
    @NotNull( message = "Salary required" )
    private Double salary;
    @NotNull( message = "Email required" )
    private String email;
    @NotNull( message = "Department required" )
    private String department;
    private List<ProjectDTO> projects;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public EmployeeDTO( EmployeeEntity employeeEntity ) {
        this.id = employeeEntity.getId();
        this.name = employeeEntity.getName();
        this.register = employeeEntity.getRegister();
        this.age = employeeEntity.getAge();
        this.address = employeeEntity.getAddress();
        this.salary = employeeEntity.getSalary();
        this.email = employeeEntity.getEmail();
        this.department = employeeEntity.getDepartment();
        this.projects = listConverterProjectEntityToDto( employeeEntity.getProjects() );
        this.createdAt = employeeEntity.getCreatedAt();
        this.updatedAt = employeeEntity.getUpdatedAt();
    }

}
