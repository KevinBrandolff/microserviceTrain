package com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.ServiceTest;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.EmployeeBuilder.EmployeeDTOBuilder;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.EmployeeBuilder.EmployeeEntityBuilder;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.EmployeeDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.EmployeeEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.EmployeeException.EmployeePersistException;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.EmployeeException.ResourceNotFoundException;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.EmployeeRepository;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("kevin")
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void whenSaveEmployeeShouldReturnEmployee() throws EmployeePersistException {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("kevin");
        employeeDTO.setRegister("12345678");
        employeeDTO.setAge(21);
        employeeDTO.setAddress("wallstreet");
        employeeDTO.setEmail("kevin@gmail.com");
        employeeDTO.setDepartment("fire");

        EmployeeEntity employeeEntityToBeReturn = EmployeeEntityBuilder.oneEmployeeWithDtosProperties( employeeDTO ).withId(1).now();

        when( employeeRepository.save( any( EmployeeEntity.class ) ) ).thenReturn( employeeEntityToBeReturn );

        EmployeeDTO employeeDTOSaved = employeeService.createEmployee( employeeDTO );

        assertEquals( employeeDTOSaved.getName(), employeeDTO.getName() );

    }

    @Test
    public void whenSaveEmployeeWithARegisterExistentThrowsEmployeePersistException(){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("kevin");
        employeeDTO.setRegister("12345678");
        employeeDTO.setAge(21);
        employeeDTO.setAddress("wallstreet");
        employeeDTO.setEmail("kevin@gmail.com");
        employeeDTO.setDepartment("fire");

        when( employeeRepository.save( any(EmployeeEntity.class) ) ).thenReturn( EmployeeEntityBuilder.oneEmployeeEntityCompleted().now() );
        when( employeeRepository.findByRegister( matches("12345678") ) ).thenReturn( Optional.of(EmployeeEntityBuilder.oneEmployeeEntityCompleted().now()) );

        assertThrows( EmployeePersistException.class,
                () -> {
                    employeeService.createEmployee( employeeDTO );
                });
    }

    @Test
    public void whenSaveEmployeeWithAEmailExistentThrowsEmployeePersistException(){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("kevin");
        employeeDTO.setRegister("12345678");
        employeeDTO.setAge(21);
        employeeDTO.setAddress("wallstreet");
        employeeDTO.setEmail("kevin@gmail.com");
        employeeDTO.setDepartment("fire");

        when( employeeRepository.save( any(EmployeeEntity.class) ) ).thenReturn( EmployeeEntityBuilder.oneEmployeeEntityCompleted().now() );
        when( employeeRepository.findByEmail( matches( "kevin@gmail.com" ) ) ).thenReturn( Optional.of(EmployeeEntityBuilder.oneEmployeeEntityCompleted().now()) );

        assertThrows( EmployeePersistException.class,
                () -> {
                    employeeService.createEmployee( employeeDTO );
                });
    }

    @Test
    public void editEmployeeChangingJustTheName() throws Exception {

        EmployeeDTO employeeDTO = EmployeeDTOBuilder.oneEmployeeDTOCompleted().now();
        employeeDTO.setName("joao");

        EmployeeEntity employeeToBeReturnInFindById = EmployeeEntityBuilder.oneEmployeeEntityCompleted().now();

        when( employeeRepository.findById( any( Integer.class ) ) ).thenReturn(Optional.of(employeeToBeReturnInFindById) );

        EmployeeEntity employeeEditedToBeReturnWhenSaved = employeeToBeReturnInFindById;
        employeeEditedToBeReturnWhenSaved.setName("joao");

        when( employeeRepository.save( any( EmployeeEntity.class ) ) ).thenReturn( employeeEditedToBeReturnWhenSaved );

        EmployeeDTO employeeDTOUpdated = employeeService.update( employeeDTO, 1 );

        assertEquals( employeeDTOUpdated.getName(), employeeDTO.getName() );

    }

    @Test
    public void editEmployeeSendARegisterAlreadyExistentThrowsEmployeePersistException() throws ResourceNotFoundException, EmployeePersistException {

        EmployeeDTO employeeDTO = EmployeeDTOBuilder.oneEmployeeDTOCompleted().now();
        employeeDTO.setRegister("123456789");

        EmployeeEntity employeeAlreadyExistingInDBWithTheIdSent = EmployeeEntityBuilder.oneEmployeeEntityCompleted().now();
        EmployeeEntity anotherEmployeeAlreadyExistingInDB = EmployeeEntityBuilder.anotherEmployeeEntityCompleted().now();

        when( employeeRepository.findById( any( Integer.class ) ) ).thenReturn(Optional.of(employeeAlreadyExistingInDBWithTheIdSent) );

        when( employeeRepository.findByRegister( matches( "123456789" ) ) ).thenReturn( Optional.of(anotherEmployeeAlreadyExistingInDB) );

        assertThrows( EmployeePersistException.class,
                () -> {
                    employeeService.update( employeeDTO, 1 );
                });
    }

    @Test
    public void editEmployeeSendAEmailAlreadyExistentThrowsEmployeePersistException() throws ResourceNotFoundException, EmployeePersistException {

        EmployeeDTO employeeDTO = EmployeeDTOBuilder.oneEmployeeDTOCompleted().now();
        employeeDTO.setEmail("gab@gmail.com");

        EmployeeEntity employeeAlreadyExistingInDBWithTheIdSent = EmployeeEntityBuilder.oneEmployeeEntityCompleted().now();
        EmployeeEntity anotherEmployeeAlreadyExistingInDB = EmployeeEntityBuilder.anotherEmployeeEntityCompleted().now();

        when( employeeRepository.findById( any( Integer.class ) ) ).thenReturn(Optional.of(employeeAlreadyExistingInDBWithTheIdSent) );

        when( employeeRepository.findByEmail( matches( "gab@gmail.com" ) ) ).thenReturn( Optional.of(anotherEmployeeAlreadyExistingInDB) );

        assertThrows( EmployeePersistException.class,
                () -> {
                    employeeService.update( employeeDTO, 1 );
                });
    }

    @Test
    public void findByIdShouldReturnAEmployeeDTO() throws ResourceNotFoundException {

        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().now();

        EmployeeDTO employeeDTO = EmployeeDTOBuilder.oneEmployeeDTOCompleted().now();

        when( employeeRepository.findById( same( 1 ) ) ).thenReturn( Optional.of(employeeEntity) );

        EmployeeDTO employeeDTOResponse = employeeService.findById(1);

        assertEquals( employeeDTO, employeeDTOResponse );

    }

    @Test
    public void findByIdShouldThrowsResourceNotFoundException() throws ResourceNotFoundException {

        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().now();

        EmployeeDTO employeeDTO = EmployeeDTOBuilder.oneEmployeeDTOCompleted().now();

        when( employeeRepository.findById( same( 1 ) ) ).thenReturn( Optional.ofNullable(null) );

        assertThrows( ResourceNotFoundException.class,
                () -> {
                    employeeService.findById(1);
                });

    }

}
