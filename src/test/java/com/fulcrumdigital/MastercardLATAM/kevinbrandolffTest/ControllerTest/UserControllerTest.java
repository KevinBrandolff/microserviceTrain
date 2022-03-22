package com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.UserDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.UserEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc( addFilters = false )
@ActiveProfiles("kevin")
public class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;

    @Test
    public void createUser() throws Exception {

        UserEntity userEntitySaved = UserEntity.builder()
                .id(1)
                .username("test")
                .password("123456")
                .role(UserEntity.RoleEnum.ADMIN)
                .build();


        UserDTO userDTO = UserDTO.builder()
                .username("test")
                .password("123456")
                .role("ADMIN")
                .build();

        when( repository.save( any( UserEntity.class ) ) ).thenReturn( userEntitySaved );

        MvcResult mvcResult = this.mockMvc.perform(post("/kevin/user/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                        .andExpect( status().isCreated() )
                        .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        userDTO.setId(userEntitySaved.getId() );
        userDTO.setPassword(null);

        assertEquals( objectMapper.writeValueAsString( userDTO ), response );

    }

    @Test
    public void shouldThrowsMethodArgumentNotValidExceptionWhenTrySaveAUserWithoutUsername() throws Exception {

        UserDTO userDTO = UserDTO.builder()
                .username(null)
                .password("123456")
                .role("ADMIN")
                .build();

        mockMvc.perform(post("/kevin/user/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect( result -> assertTrue( result.getResolvedException() instanceof MethodArgumentNotValidException) );

    }

    @Test
    public void shouldThrowsMethodArgumentNotValidExceptionWhenTrySaveAUserWithoutPassword() throws Exception {

        UserDTO userDTO = UserDTO.builder()
                .username("test")
                .password(null)
                .role("ADMIN")
                .build();

        mockMvc.perform(post("/kevin/user/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect( result -> assertTrue( result.getResolvedException() instanceof MethodArgumentNotValidException) );

    }

    @Test
    public void shouldThrowsMethodArgumentNotValidExceptionWhenTrySaveAUserWithoutRole() throws Exception {

        UserDTO userDTO = UserDTO.builder()
                .username("test")
                .password("123456")
                .role(null)
                .build();

        mockMvc.perform(post("/kevin/user/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect( result -> assertTrue( result.getResolvedException() instanceof MethodArgumentNotValidException) );

    }

}
