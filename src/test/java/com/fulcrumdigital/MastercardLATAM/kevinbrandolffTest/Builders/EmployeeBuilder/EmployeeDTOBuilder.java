package com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.EmployeeBuilder;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.EmployeeDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.EmployeeEntity;

import java.time.LocalDate;

public class EmployeeDTOBuilder {

    private EmployeeDTO employeeDTO;

    public EmployeeDTOBuilder(){}

    public static EmployeeDTOBuilder oneEmployeeDTOCompleted() {
        EmployeeDTOBuilder employeeDTOBuilder = new EmployeeDTOBuilder();
        employeeDTOBuilder.employeeDTO = new EmployeeDTO();
        employeeDTOBuilder.employeeDTO.setId(1);
        employeeDTOBuilder.employeeDTO.setName("kevin");
        employeeDTOBuilder.employeeDTO.setRegister("12345678");
        employeeDTOBuilder.employeeDTO.setAge(21);
        employeeDTOBuilder.employeeDTO.setSalary(123.23);
        employeeDTOBuilder.employeeDTO.setAddress("wallstreet");
        employeeDTOBuilder.employeeDTO.setEmail("kevin@gmail.com");
        employeeDTOBuilder.employeeDTO.setDepartment("fire");
        employeeDTOBuilder.employeeDTO.setCreatedAt(LocalDate.now());
        return employeeDTOBuilder;
    }

    public static EmployeeDTOBuilder anotheroneEmployeeDTOCompleted() {
        EmployeeDTOBuilder employeeDTOBuilder = new EmployeeDTOBuilder();
        employeeDTOBuilder.employeeDTO = new EmployeeDTO();
        employeeDTOBuilder.employeeDTO.setId(2);
        employeeDTOBuilder.employeeDTO.setName("gab");
        employeeDTOBuilder.employeeDTO.setRegister("123456789");
        employeeDTOBuilder.employeeDTO.setAge(21);
        employeeDTOBuilder.employeeDTO.setSalary(123.23);
        employeeDTOBuilder.employeeDTO.setAddress("wallstreet");
        employeeDTOBuilder.employeeDTO.setEmail("gab@gmail.com");
        employeeDTOBuilder.employeeDTO.setDepartment("fire");
        employeeDTOBuilder.employeeDTO.setCreatedAt(LocalDate.now());
        return employeeDTOBuilder;
    }

    public EmployeeDTO now() {
        return employeeDTO;
    }

    public EmployeeDTOBuilder withOutRegister() {
        this.employeeDTO.setRegister(null);
        return this;
    }

    public EmployeeDTOBuilder withId( Integer id ) {
        this.employeeDTO.setId( id );
        return this;
    }

    public EmployeeDTOBuilder withRegister( String register ) {
        this.employeeDTO.setRegister( register );
        return this;
    }

    public EmployeeDTOBuilder withEmail( String email ) {
        this.employeeDTO.setEmail( email );
        return this;
    }

}
