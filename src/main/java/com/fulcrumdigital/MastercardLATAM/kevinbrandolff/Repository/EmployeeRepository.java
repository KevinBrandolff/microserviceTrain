package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository("kevinEmployeeRepository")
public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Integer> {

    List<EmployeeEntity> findAll();

    List<EmployeeEntity> findAllByName( String name );

    Optional<EmployeeEntity> findByRegister(String register );

    List<EmployeeEntity> findAllByAge( Integer age );

    List<EmployeeEntity> findAllByAddress( String address );

    List<EmployeeEntity> findAllBySalary( Double salary );

    Optional<EmployeeEntity> findByEmail( String email );

    List<EmployeeEntity> findAllByDepartment( String department );

    List<EmployeeEntity> findAllByCreatedAtBetween(LocalDate start, LocalDate end);

}
