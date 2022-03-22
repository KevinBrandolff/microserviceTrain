package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Service;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.EmployeeDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.ProjectDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.ProjectEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.ProjectException.ProjectNameAlreadyRegistred;

import java.util.List;

public interface ProjectService {

    ProjectDTO createProject( ProjectDTO projectDTO ) throws ProjectNameAlreadyRegistred;
    ProjectDTO update( ProjectDTO projectDTO ) throws ProjectNameAlreadyRegistred;
    void deleteById( Integer id );
    List<ProjectDTO> findAll();
    ProjectDTO findById( Integer id );
    ProjectDTO findByName( String name );
    List<ProjectDTO> findAllByEmployeesId( Integer id );
    ProjectDTO addEmployeeToProject( EmployeeDTO employeeDTO, Integer id );
    ProjectDTO deleteEmployeeFromProject( Integer projectId, Integer employeeId );
    List<ProjectEntity> verifyProjectsDTOsToConvertToProjectEntityToPersist( List<ProjectDTO> projectDTOs );

}
