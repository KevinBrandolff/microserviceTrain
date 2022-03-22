package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Service.Impl;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.EmployeeDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.ProjectDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.EmployeeEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.ProjectEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.EmployeeException.ResourceNotFoundException;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.ProjectException.ProjectNameAlreadyRegistred;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.EmployeeRepository;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Repository.ProjectRepository;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Service.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Util.Converters.*;

@Service("kevinProjectService")
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;
    private final EmployeeRepository employeeRepository;

    public ProjectServiceImpl( ProjectRepository repository, EmployeeRepository employeeRepository ) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ProjectDTO createProject( ProjectDTO projectDTO ) throws ProjectNameAlreadyRegistred {
        verifyNameIsAllowed(projectDTO.getName() );
        ProjectEntity projectEntity = ProjectEntity.builder().build();
        BeanUtils.copyProperties( projectDTO, projectEntity );
        if ( projectDTO.getEmployees() != null ) {
            projectEntity.setEmployees( verifyEmployeeDTOsToConvertToEmployeeEntityToPersist( projectDTO.getEmployees() ) );
        }
        projectEntity.setCreatedAt( LocalDate.now() );
        return new ProjectDTO( repository.save( projectEntity ) );
    }

    @Override
    public ProjectDTO update( ProjectDTO projectDTO ) throws ProjectNameAlreadyRegistred {

        ProjectDTO projectDTOchecked = this.findById( projectDTO.getId() );

        if ( !projectDTOchecked.getName().equals( projectDTO.getName() ) ){
            verifyNameIsAllowed( projectDTO.getName() );
        }

        ProjectEntity projectEntity = ProjectEntity.builder().build();
        BeanUtils.copyProperties( projectDTO, projectEntity );
        projectEntity.setUpdatedAt( LocalDate.now() );

        if ( projectDTO.getEmployees() == null ) {
            projectEntity.setEmployees( listConverterEmployeeDTOtoEntity( projectDTOchecked.getEmployees() ) );
        }

        return new ProjectDTO( repository.save( projectEntity ) );
    }

    @Override
    public ProjectDTO addEmployeeToProject( EmployeeDTO employeeDTO, Integer id ) {

        Optional<ProjectEntity> projectEntity = repository.findById( id );

        if ( projectEntity.isPresent() ) {

            EmployeeEntity employeeEntity = new EmployeeEntity();
            BeanUtils.copyProperties( employeeDTO, employeeEntity );

            projectEntity.get().getEmployees().add( employeeEntity );
        }else {
            throw new ResourceNotFoundException( id.toString() );
        }

        return new ProjectDTO( repository.save( projectEntity.get() ) );
    }

    @Override
    public ProjectDTO deleteEmployeeFromProject( Integer projectId, Integer employeeId ) {

        Optional<ProjectEntity> projectEntity = repository.findById( projectId );

        if ( projectEntity.isPresent() ) {
            for ( int i = 0; i < projectEntity.get().getEmployees().size(); i++ ) {
                if ( projectEntity.get().getEmployees().get(i).getId() == employeeId ) {
                    projectEntity.get().getEmployees().remove( projectEntity.get().getEmployees().get(i) );
                }
            }
        }else {
            throw new ResourceNotFoundException( projectId.toString() );
        }

        return new ProjectDTO( repository.save( projectEntity.get() ) );
    }

    @Override
    public void deleteById( Integer id ) {
        repository.deleteById( id );
    }

    @Override
    public List<ProjectDTO> findAll() {
        return listConverterProjectEntityToDto( repository.findAll() );
    }

    @Override
    public ProjectDTO findById( Integer id ) {
        ProjectEntity projectEntity = repository.findById( id ).orElseThrow( () -> new ResourceNotFoundException( id.toString() ) );
        return new ProjectDTO( projectEntity );
    }

    @Override
    public ProjectDTO findByName( String name ) {
        ProjectEntity projectEntity = repository.findByName( name ).orElseThrow( () -> new ResourceNotFoundException() );
        return new ProjectDTO( projectEntity );
    }

    @Override
    public List<ProjectDTO> findAllByEmployeesId( Integer id ) {
        return listConverterProjectEntityToDto( repository.findAllByEmployeesId( id ) );
    }

    private void verifyNameIsAllowed( String name ) throws ProjectNameAlreadyRegistred {
        if ( repository.findByName( name ).isPresent() ) {
            throw new ProjectNameAlreadyRegistred();
        }
    }

    @Override
    public List<ProjectEntity> verifyProjectsDTOsToConvertToProjectEntityToPersist( List<ProjectDTO> projectsDTOs ) {

        if ( projectsDTOs == null ) return null;

        List<ProjectEntity> listProjectEntity = new ArrayList<>();

        for ( ProjectDTO projectsDTO : projectsDTOs ) {
            Optional<ProjectEntity> projectEntity = projectsDTO.getId() != null ? repository.findById( projectsDTO.getId() ) : Optional.ofNullable(null);
            if ( projectEntity.isPresent() ) {
                projectEntity.get().setName( projectsDTO.getName() );
                listProjectEntity.add( projectEntity.get() );
            }else {
                ProjectEntity projectEntity1 = ProjectEntity.builder().build();
                BeanUtils.copyProperties( projectsDTO, projectEntity1 );
                projectEntity1.setCreatedAt( LocalDate.now() );
                listProjectEntity.add( projectEntity1 );
            }
        }

        return listProjectEntity;

    }

    private List<EmployeeEntity> verifyEmployeeDTOsToConvertToEmployeeEntityToPersist( List<EmployeeDTO> employeesDTOs ) {

        if ( employeesDTOs == null ) return null;

        List<EmployeeEntity> listEmployeeEntity = new ArrayList<>();

        for ( EmployeeDTO employeesDTO : employeesDTOs ) {
            Optional<EmployeeEntity> projectEntity = employeesDTO.getId() != null ? employeeRepository.findById( employeesDTO.getId() ) : Optional.ofNullable(null);
            if ( projectEntity.isPresent() ) {
                projectEntity.get().setName( employeesDTO.getName() );
                listEmployeeEntity.add( projectEntity.get() );
            }else {
                EmployeeEntity employeeEntity1 = EmployeeEntity.builder().build();
                BeanUtils.copyProperties( employeesDTO, employeeEntity1 );
                employeeEntity1.setCreatedAt( LocalDate.now() );
                listEmployeeEntity.add( employeeEntity1 );
            }
        }

        return listEmployeeEntity;

    }
}
