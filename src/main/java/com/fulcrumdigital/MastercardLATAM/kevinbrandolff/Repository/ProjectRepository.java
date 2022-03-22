package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.ProjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("kevinProjectRepository")
public interface ProjectRepository extends CrudRepository<ProjectEntity, Integer> {

    List<ProjectEntity> findAll();
    Optional<ProjectEntity> findByName(String name );
    List<ProjectEntity> findAllByEmployeesId( Integer id );

}
