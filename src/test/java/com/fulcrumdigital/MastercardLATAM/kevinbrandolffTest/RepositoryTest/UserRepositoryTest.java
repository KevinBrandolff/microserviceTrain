package com.fulcrumdigital.MastercardLATAM.kevinbrandolffTest.RepositoryTest;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.UserEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("kevin")
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void saveUser(){

        UserEntity userEntity = UserEntity.builder()
                .username("test")
                .password("123456")
                .role(UserEntity.RoleEnum.ADMIN)
                .build();

        UserEntity userEntitySaved = repository.save( userEntity );

        assertTrue( userEntitySaved.getId() != null );

    }

    @Test
    public void throwExceptionWhenTrysaveUserWithANameAlreadyExistent(){

        UserEntity userEntity = UserEntity.builder()
                .username("test")
                .password("123456")
                .role(UserEntity.RoleEnum.ADMIN)
                .build();

        UserEntity userEntity2 = UserEntity.builder()
                .username("test")
                .password("123456")
                .role(UserEntity.RoleEnum.ADMIN)
                .build();

        UserEntity userEntitySaved = repository.save( userEntity );

        assertThrows( Exception.class,
                () ->  repository.save( userEntity2 ) );

    }

}
