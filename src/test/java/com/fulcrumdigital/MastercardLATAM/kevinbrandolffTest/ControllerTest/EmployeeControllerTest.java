package com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.EmployeeException.EmployeePersistException;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.EmployeeException.ResourceNotFoundException;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.EmployeeBuilder.EmployeeEntityBuilder;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.EmployeeBuilder.EmployeeDTOBuilder;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.EmployeeDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.EmployeeEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc( addFilters = false )
@ActiveProfiles("kevin")
public class EmployeeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void shouldReturnAEmployeeById() throws Exception {

        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().now();
        EmployeeDTO employeeDTO = EmployeeDTOBuilder.oneEmployeeDTOCompleted().now();

        when( employeeRepository.findById( any( Integer.class ) ) ).thenReturn( Optional.of( employeeEntity ) );

        MvcResult mvcResult = this.mockMvc.perform(get("/kevin/employee/1")).andExpect( status().isOk() ).andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( employeeDTO ), response );

    }

    @Test
    public void shouldReturnStatus200WhenCallFindByIdWithIdNotExistent() throws Exception {

        when( employeeRepository.findById( 2 ) ).thenReturn( Optional.ofNullable(null) );

        this.mockMvc.perform(get("/kevin/employee/2"))
                .andExpect( status().isNotFound() )
                .andExpect( result -> assertTrue( result.getResolvedException() instanceof ResourceNotFoundException) );

    }

    @Test
    public void shouldReturnAllEmployess() throws Exception {

        EmployeeEntity employee1 = EmployeeEntityBuilder.oneEmployeeEntityCompleted().now();
       
        List<EmployeeEntity> employeesEntityLHS = new LinkedList<>();
        employeesEntityLHS.add(employee1);

        List<EmployeeDTO> employeesDTOLHS = new LinkedList<>();
        employeesDTOLHS.add(new EmployeeDTO(employee1));

        when( employeeRepository.findAll() ).thenReturn( employeesEntityLHS );

        MvcResult mvcResult = this.mockMvc.perform(get("/kevin/employee/")).andExpect( status().isOk() ).andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( employeesDTOLHS ), response );

    }

    @Test
    public void shouldReturn200WhenCallFindAllAndDontExistEmployees() throws Exception {

        when( employeeRepository.findAll() ).thenReturn( null );

        this.mockMvc.perform(get("/kevin/employee/")).andExpect( status().isOk() );

    }

    @Test
    public void shouldReturnAEmployeeSavedWithStatusCreated() throws Exception {

        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(1).now();

        EmployeeDTO employeeDTO = EmployeeDTOBuilder.oneEmployeeDTOCompleted().withId(null).now();
        EmployeeDTO employeeDTOSaved = EmployeeDTOBuilder.oneEmployeeDTOCompleted().withId(1).now();

        when( employeeRepository.save( any( EmployeeEntity.class ) ) ).thenReturn( employeeEntity );

        MvcResult mvcResult = mockMvc.perform(post("/kevin/employee/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                        .andExpect(status().isCreated())
                        .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( employeeDTOSaved ), response );


    }

    @Test
    public void shouldThrowsMethodArgumentNotValidExceptionWhenSaveAEmployeeWithoutRegister() throws Exception {

        EmployeeDTO employeeDTO = EmployeeDTOBuilder.oneEmployeeDTOCompleted().withOutRegister().now();

        mockMvc.perform(post("/kevin/employee/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isBadRequest())
                .andExpect( result -> assertTrue( result.getResolvedException() instanceof MethodArgumentNotValidException ) );

    }

    @Test
    public void shouldDeleteAEmployeeWithStatusNoContent() throws Exception {

        mockMvc.perform(delete("/kevin/employee/1"))
                .andExpect(status().isNoContent());

    }

    @Test
    public void shouldReturnStatusOkWhenUpdateAEmployee() throws Exception {

        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().now();
        EmployeeEntity employeeEntityWithOtherName = EmployeeEntityBuilder.oneEmployeeEntityCompleted().withName("guilherme").now();

        EmployeeDTO employeeDTO = EmployeeDTOBuilder.oneEmployeeDTOCompleted().now();
        employeeDTO.setName("guilherme");

        when( employeeRepository.findById( same( employeeDTO.getId() ) ) ).thenReturn( Optional.of( employeeEntity ) );
        when( employeeRepository.save( employeeEntityWithOtherName ) ).thenReturn( employeeEntityWithOtherName );

        mockMvc.perform(put("/kevin/employee/" + employeeDTO.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldThrowsEmployeePersistExceptionWhenUpdateAEmployeeWithARegisterAlreadyExisting() throws Exception {

        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().now();

        EmployeeDTO employeeDTO = EmployeeDTOBuilder.oneEmployeeDTOCompleted().now();
        employeeDTO.setRegister("90909090");

        when( employeeRepository.findById( same( employeeDTO.getId() ) ) ).thenReturn( Optional.of( employeeEntity ) );
        when( employeeRepository.findByRegister( matches( "90909090" ) ) ).thenReturn( Optional.of( EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(2).withRegister("90909090").now() ) );

        mockMvc.perform(put("/kevin/employee/" + employeeDTO.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isBadRequest())
                .andExpect( result -> assertTrue( result.getResolvedException() instanceof EmployeePersistException) );


    }

    @Test
    public void shouldThrowsEmployeePersistExceptionWhenUpdateAEmployeeWithAEmailAlreadyExisting() throws Exception {

        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().now();

        EmployeeDTO employeeDTO = EmployeeDTOBuilder.oneEmployeeDTOCompleted().now();
        employeeDTO.setEmail("aaa@aaaa.com");

        when( employeeRepository.findById( same( employeeDTO.getId() ) ) ).thenReturn( Optional.of( employeeEntity ) );
        when( employeeRepository.findByEmail( matches( "aaa@aaaa.com" ) ) ).thenReturn( Optional.of( EmployeeEntityBuilder.oneEmployeeEntityCompleted().withId(2).withEmail("aaa@aaaa.com").now() ) );

        mockMvc.perform(put("/kevin/employee/" + employeeDTO.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isBadRequest())
                .andExpect( result -> assertTrue( result.getResolvedException() instanceof EmployeePersistException) );


    }

}
