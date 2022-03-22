package com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.ServiceTest;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.UserDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.UserEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.UserException.UserPersistException;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.UserRepository;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Service.Impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("kevin")
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    public void createAUser(){

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

        UserDTO userDTOSaved = service.createUser( userDTO );

        assertEquals( userEntitySaved.getId(), userDTOSaved.getId() );
    };

    @Test
    public void throwEmployeePersistExceptionWhenTrySaveAEmployeeWithAWrongRole(){

        UserEntity userEntitySaved = UserEntity.builder()
                .id(1)
                .username("test")
                .password("123456")
                .role(UserEntity.RoleEnum.ADMIN)
                .build();


        UserDTO userDTO = UserDTO.builder()
                .username("test")
                .password("123456")
                .role("ADMINTEST")
                .build();

        assertThrows( UserPersistException.class,
                () -> service.createUser( userDTO ) );

    };

}
