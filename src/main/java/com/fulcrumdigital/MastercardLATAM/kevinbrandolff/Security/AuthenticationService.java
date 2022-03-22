package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Security;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.UserEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("kevinAuthenticationService")
@Profile("kevin")
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = this.repository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found!") );

        String[] roles = new String[1];

        if ( user.getRole().equals( UserEntity.RoleEnum.ADMIN ) ) {
            roles[0] = "ADMIN";
        }else if ( user.getRole().equals( UserEntity.RoleEnum.USER ) ) {
            roles[0] = "USER";
        }

        return User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .build();

    }
}
