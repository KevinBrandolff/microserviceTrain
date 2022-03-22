package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("kevinUserRepository")
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username );

}
