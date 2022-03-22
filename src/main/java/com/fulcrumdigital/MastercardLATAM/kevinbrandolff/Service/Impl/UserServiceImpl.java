package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Service.Impl;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.UserDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.UserEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.UserException.RoleInvalidException;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.UserException.UserPersistException;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.UserRepository;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("kevinUserServiceImpl")
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl( UserRepository repository ){
        this.repository = repository;
    }

    @Override
    @Transactional( rollbackFor = Exception.class )
    public UserDTO createUser( UserDTO userDTO ) {
        verifyUsername(userDTO.getUsername() );
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties( userDTO, userEntity );
        userEntity.setRole( verifyRole( userDTO.getRole() ) );
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        userEntity.setPassword( bcrypt.encode( userEntity.getPassword() ) );
        UserDTO userDTOsaved = new UserDTO( repository.save( userEntity ) );
        userDTOsaved.setPassword(null);
        return userDTOsaved;
    }

    private UserEntity.RoleEnum verifyRole( String roleEnum ) {
        String role = roleEnum.toUpperCase();
        switch ( role ) {
            case "USER":
                return UserEntity.RoleEnum.USER;
            case "ADMIN":
                return UserEntity.RoleEnum.ADMIN;
        }
        throw new RoleInvalidException();
    }

    private void verifyUsername( String username ){
        if ( repository.findByUsername( username ).isPresent() ) {
            throw new UserPersistException("Username already existent");
        }
    }

}
