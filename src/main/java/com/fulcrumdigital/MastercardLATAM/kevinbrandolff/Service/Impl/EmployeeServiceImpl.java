package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Service.Impl;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.EmployeeDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.ProjectDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.EmployeeEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.ProjectEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.EmployeeException.EmailAlreadyRegistred;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.EmployeeException.EmployeePersistException;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.EmployeeException.ResourceNotFoundException;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.EmployeeException.RegisterAlreadyExists;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.EmployeeRepository;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.ProjectRepository;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Service.EmployeeService;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Service.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Util.Converters.*;

@Service("kevinEmployeeService")
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final ProjectService projectService;

    public EmployeeServiceImpl( EmployeeRepository repository, ProjectService projectService ) {
        this.repository = repository;
        this.projectService = projectService;
    }

    @Override
    @Transactional( rollbackFor = Exception.class )
    public EmployeeDTO createEmployee( EmployeeDTO employeeDTO ) throws EmployeePersistException {
        verifyIsRegisterAllowed( employeeDTO.getRegister() );
        verifyIsEmailAllowed( employeeDTO.getEmail() );
        EmployeeEntity employeeEntity = EmployeeEntity.builder().build();
        BeanUtils.copyProperties( employeeDTO, employeeEntity );
        if ( employeeDTO.getProjects() != null ) {
            employeeEntity.setProjects( projectService.verifyProjectsDTOsToConvertToProjectEntityToPersist( employeeDTO.getProjects() ) );
        }
        employeeEntity.setCreatedAt(LocalDate.now());
        return new EmployeeDTO( repository.save( employeeEntity ) );
    }

    @Override
    @Transactional( rollbackFor = Exception.class )
    public EmployeeDTO update( EmployeeDTO employeeDTO, Integer id ) throws EmployeePersistException {

        EmployeeDTO employeeDTOchecked = this.findById( employeeDTO.getId() );

        if ( !employeeDTOchecked.getRegister().equals( employeeDTO.getRegister() ) ) {
            verifyIsRegisterAllowed( employeeDTO.getRegister() );
        }
        if ( !employeeDTOchecked.getEmail().equals( employeeDTO.getEmail() ) ) {
            verifyIsEmailAllowed( employeeDTO.getEmail() );
        }

        EmployeeEntity employeeEntity = EmployeeEntity.builder().build();
        BeanUtils.copyProperties( employeeDTO, employeeEntity );
        if ( employeeDTO.getProjects() != null ) {
            employeeEntity.setProjects( projectService.verifyProjectsDTOsToConvertToProjectEntityToPersist( employeeDTO.getProjects() ) );
        }

        employeeEntity.setUpdatedAt( LocalDate.now() );

        return new EmployeeDTO( repository.save( employeeEntity ) );
    }

    @Override
    public void deleteById( Integer id ) {
        repository.deleteById( id );
    }

    @Override
    public EmployeeDTO findById( Integer id ) {
        EmployeeEntity employee = repository.findById( id ).orElseThrow( () -> new ResourceNotFoundException( id.toString()) );
        return new EmployeeDTO( employee );
    }

    @Override
    public List<EmployeeDTO> findAll(){
        return listConverterEmployeeEntityToDto( repository.findAll() );
    }

    @Override
    public List<EmployeeDTO> findAllByName( String name ){
        return listConverterEmployeeEntityToDto( repository.findAllByName( name ) );
    }

    @Override
    public EmployeeDTO findByRegister( String register ) {
        EmployeeEntity employee = repository.findByRegister( register ).orElseThrow(
                () -> new ResourceNotFoundException()
        );
        return new EmployeeDTO( employee );
    }

    @Override
    public List<EmployeeDTO> findAllByAge( Integer age ){
        return listConverterEmployeeEntityToDto( repository.findAllByAge( age ) );
    }

    @Override
    public List<EmployeeDTO> findAllByAddress( String address ){
        return listConverterEmployeeEntityToDto( repository.findAllByAddress( address ) );
    }

    @Override
    public List<EmployeeDTO> findAllBySalary( Double salary ){
        return listConverterEmployeeEntityToDto(repository.findAllBySalary( salary ));
    }

    @Override
    public EmployeeDTO findByEmail( String email ) {
        EmployeeEntity employee = repository.findByEmail( email ).orElseThrow(
                () -> new ResourceNotFoundException()
        );
        return new EmployeeDTO( employee );
    }

    @Override
    public List<EmployeeDTO> findAllByDepartment( String department ){
        return listConverterEmployeeEntityToDto( repository.findAllByDepartment( department ) );
    }

    @Override
    public List<EmployeeDTO> findAllByCreatedAtBetween( String start, String end ) {
        LocalDate startDate = LocalDate.parse( start, DateTimeFormatter.ofPattern("dd/MM/yy") );
        LocalDate endDate = LocalDate.parse( end, DateTimeFormatter.ofPattern("dd/MM/yy") );
        return listConverterEmployeeEntityToDto( repository.findAllByCreatedAtBetween( startDate, endDate ) );
    }

    private void verifyIsRegisterAllowed( String register ) throws EmployeePersistException {
        if ( repository.findByRegister( register ).isPresent() ) {
            throw new RegisterAlreadyExists();
        }
    }

    private void verifyIsEmailAllowed( String email ) throws EmployeePersistException {
        if ( repository.findByEmail( email ).isPresent() ){
            throw new EmailAlreadyRegistred();
        }
    }

}
