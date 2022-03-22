package com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.EmployeeDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.UserDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.EmployeeEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.ProjectEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.UserEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.EmployeeRepository;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.ProjectRepository;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.UserRepository;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.EmployeeBuilder.EmployeeDTOBuilder;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.EmployeeBuilder.EmployeeEntityBuilder;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.Builders.ProjectBuilder.ProjectEntityBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("kevin")
public class SecurityTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private ProjectRepository projectRepository;

    @Test
    public void shouldDoLoginAndTryFindEmployeeByIdWithToken() throws Exception {

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        UserEntity userEntity = UserEntity.builder().username("kevin").password( bcrypt.encode( "123456" )).role(UserEntity.RoleEnum.ADMIN).id(1).build();

        UserDTO userDTO = UserDTO.builder().username("kevin").password("123456").build();

        when( userRepository.findByUsername( "kevin" ) ).thenReturn( Optional.of( userEntity ) );

        MvcResult mvcResultLogin = this.mockMvc.perform(post("/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect( status().isOk() )
                .andReturn();

        String token = mvcResultLogin.getResponse().getHeader("Authorization");

        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().now();

        when( employeeRepository.findById( 1 ) ).thenReturn( Optional.of( employeeEntity ) );

        MvcResult mvcResultFindEmployeeById = this.mockMvc.perform( get( "/kevin/employee/1" ).header("Authorization", token))
                .andExpect( status().isOk() ).
                andReturn();

        String response = mvcResultFindEmployeeById.getResponse().getContentAsString();

        Assertions.assertEquals( objectMapper.writeValueAsString( employeeEntity ), response );


    }

    @Test
    public void shouldDoLoginAndTryFindEmployeeByIdWithStatusForbiddenCauseTheRoleNeededIsAdmin() throws Exception {

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        UserEntity userEntity = UserEntity.builder().username("kevin").password( bcrypt.encode( "123456" )).role(UserEntity.RoleEnum.USER).id(1).build();

        UserDTO userDTO = UserDTO.builder().username("kevin").password("123456").build();

        when( userRepository.findByUsername( any( String.class ) ) ).thenReturn( Optional.of( userEntity ) );

        MvcResult mvcResultLogin = this.mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect( status().isOk() )
                .andReturn();

        String token = mvcResultLogin.getResponse().getHeader("Authorization");

        EmployeeEntity employeeEntity = EmployeeEntityBuilder.oneEmployeeEntityCompleted().now();

        when( employeeRepository.findById( 1 ) ).thenReturn( Optional.of( employeeEntity ) );

        MvcResult mvcResultFindEmployeeById = this.mockMvc.perform( get( "/kevin/employee/1" ).header("Authorization", token))
                .andExpect( status().isForbidden() ).
                andReturn();


    }

    @Test
    public void shouldDoLoginAndFailCausePasswordIsWrong() throws Exception {

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        UserEntity userEntity = UserEntity.builder().username("kevin").password( bcrypt.encode( "123456" )).role(UserEntity.RoleEnum.USER).id(1).build();

        UserDTO userDTO = UserDTO.builder().username("kevin").password("12345678").build();

        when( userRepository.findByUsername( any( String.class ) ) ).thenReturn( Optional.of( userEntity ) );

        MvcResult mvcResultLogin = this.mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect( status().isUnauthorized() )
                .andReturn();


    }

    @Test
    public void shouldDoLoginAndFailCauseUsernameIsWrong() throws Exception {

        UserDTO userDTO = UserDTO.builder().username("kevinerror").password("123456").build();

        when( userRepository.findByUsername( "kevinerror" ) ).thenReturn( Optional.ofNullable( null ) );

        MvcResult mvcResultLogin = this.mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect( status().isUnauthorized() )
                .andReturn();


    }

    @Test
    public void shouldDoLoginAndTryFindProjectByIdWithToken() throws Exception {

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        UserEntity userEntity = UserEntity.builder().username("kevin").password( bcrypt.encode( "123456" )).role(UserEntity.RoleEnum.USER).id(1).build();

        UserDTO userDTO = UserDTO.builder().username("kevin").password("123456").build();

        when( userRepository.findByUsername( "kevin" ) ).thenReturn( Optional.of( userEntity ) );

        MvcResult mvcResultLogin = this.mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect( status().isOk() )
                .andReturn();

        String token = mvcResultLogin.getResponse().getHeader("Authorization");

        ProjectEntity projectEntity = ProjectEntityBuilder.createAProjectEntityWithEmployeesCompleted().now();

        when( projectRepository.findById( 1 ) ).thenReturn( Optional.of( projectEntity ) );

        MvcResult mvcResultFindEmployeeById = this.mockMvc.perform( get( "/kevin/project/1" ).header("Authorization", token))
                .andExpect( status().isOk() ).
                andReturn();

        String response = mvcResultFindEmployeeById.getResponse().getContentAsString();

        Assertions.assertEquals( objectMapper.writeValueAsString( projectEntity ), response );


    }

    @Test
    public void shouldDoLoginAndTryFindProjectByIdWithStatusForbiddenCauseTheRoleNeededIsUser() throws Exception {

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        UserEntity userEntity = UserEntity.builder().username("kevin").password( bcrypt.encode( "123456" )).role(UserEntity.RoleEnum.ADMIN).id(1).build();

        UserDTO userDTO = UserDTO.builder().username("kevin").password("123456").build();

        when( userRepository.findByUsername( "kevin" ) ).thenReturn( Optional.of( userEntity ) );

        MvcResult mvcResultLogin = this.mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect( status().isOk() )
                .andReturn();

        String token = mvcResultLogin.getResponse().getHeader("Authorization");

        ProjectEntity projectEntity = ProjectEntityBuilder.createAProjectEntityWithEmployeesCompleted().now();

        when( projectRepository.findById( 1 ) ).thenReturn( Optional.of( projectEntity ) );

        MvcResult mvcResultFindEmployeeById = this.mockMvc.perform( get( "/kevin/project/1" ).header("Authorization", token))
                .andExpect( status().isForbidden() ).
                andReturn();


    }

}
