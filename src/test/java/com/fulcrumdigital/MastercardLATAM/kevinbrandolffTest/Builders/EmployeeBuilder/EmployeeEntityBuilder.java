package com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.EmployeeBuilder;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.EmployeeDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.EmployeeEntity;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.Optional;

public class EmployeeEntityBuilder {

    private EmployeeEntity employeeEntity;

    private EmployeeEntityBuilder(){}

    public static EmployeeEntityBuilder oneEmployeeEntityCompleted() {
        EmployeeEntityBuilder employeeBuilder = new EmployeeEntityBuilder();
        employeeBuilder.employeeEntity = new EmployeeEntity();
        employeeBuilder.employeeEntity.setId(1);
        employeeBuilder.employeeEntity.setName("kevin");
        employeeBuilder.employeeEntity.setRegister("12345678");
        employeeBuilder.employeeEntity.setAge(21);
        employeeBuilder.employeeEntity.setSalary(123.23);
        employeeBuilder.employeeEntity.setAddress("wallstreet");
        employeeBuilder.employeeEntity.setEmail("kevin@gmail.com");
        employeeBuilder.employeeEntity.setDepartment("fire");
        employeeBuilder.employeeEntity.setCreatedAt(LocalDate.now());
        return employeeBuilder;
    }

    public static EmployeeEntityBuilder anotherEmployeeEntityCompleted() {
        EmployeeEntityBuilder employeeBuilder = new EmployeeEntityBuilder();
        employeeBuilder.employeeEntity = new EmployeeEntity();
        employeeBuilder.employeeEntity.setId(2);
        employeeBuilder.employeeEntity.setName("gab");
        employeeBuilder.employeeEntity.setRegister("123456789");
        employeeBuilder.employeeEntity.setAge(21);
        employeeBuilder.employeeEntity.setSalary(123.23);
        employeeBuilder.employeeEntity.setAddress("wallstreet");
        employeeBuilder.employeeEntity.setEmail("gab@gmail.com");
        employeeBuilder.employeeEntity.setDepartment("fire");
        employeeBuilder.employeeEntity.setCreatedAt(LocalDate.now());
        return employeeBuilder;
    }

    public static EmployeeEntityBuilder oneEmployeeWithDtosProperties( EmployeeDTO employeeDTO ) {
        EmployeeEntityBuilder employeeBuilder = new EmployeeEntityBuilder();
        employeeBuilder.employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties( employeeDTO, employeeBuilder.employeeEntity );
        return employeeBuilder;
    }

    public EmployeeEntity now() {
        return employeeEntity;
    }

    public Optional<EmployeeEntity> nowOptional() {
        return Optional.of(employeeEntity);
    }

    public EmployeeEntityBuilder withId( Integer id ) {
        this.employeeEntity.setId(id);
        return this;
    }

    public EmployeeEntityBuilder withName( String name ) {
        this.employeeEntity.setName( name );
        return this;
    }

    public EmployeeEntityBuilder withRegister( String register ) {
        this.employeeEntity.setRegister(register);
        return this;
    }

    public EmployeeEntityBuilder withEmail( String email ) {
        this.employeeEntity.setEmail(email);
        return this;
    }

}
