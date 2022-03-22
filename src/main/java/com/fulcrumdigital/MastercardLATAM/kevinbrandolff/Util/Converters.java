package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Util;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.EmployeeDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.ProjectDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.EmployeeEntity;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.ProjectEntity;
import org.springframework.beans.BeanUtils;

import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Converters {

    public static List<EmployeeDTO> listConverterEmployeeEntityToDto(List<EmployeeEntity> listEmployeeEntity ){
        List<EmployeeDTO> listDTO = new LinkedList<>();
        if ( listEmployeeEntity != null ) {
            listEmployeeEntity.parallelStream()
                    .map( (employee) -> {
                        EmployeeDTO employeeDTO = EmployeeDTO.builder().build();
                        BeanUtils.copyProperties( employee, employeeDTO );
                        return listDTO.add( employeeDTO );
                    } )
                    .collect( toList() );
        }else {
            return null;
        }
        return  listDTO;
    }

    public static List<EmployeeEntity> listConverterEmployeeDTOtoEntity(List<EmployeeDTO> listEmployeeDTO ){
        List<EmployeeEntity> listEmployeeEntity = new LinkedList<>();
        if ( listEmployeeDTO != null ) {
            listEmployeeDTO.parallelStream()
                    .map( (employeeDTO) -> {
                        EmployeeEntity employeeEntity = EmployeeEntity.builder().build();
                        BeanUtils.copyProperties( employeeDTO, employeeEntity );
                        return listEmployeeEntity.add( employeeEntity );
                    } )
                    .collect( toList() );
        }else {
            return null;
        }
        return  listEmployeeEntity;
    }

    public static List<ProjectDTO> listConverterProjectEntityToDto(List<ProjectEntity> listProjectEntity ){
        List<ProjectDTO> listDTO = new LinkedList<>();
        if ( listProjectEntity != null ) {
            listProjectEntity.parallelStream()
                    .map( (project) -> {
                        ProjectDTO projectDTO = ProjectDTO.builder().build();
                        BeanUtils.copyProperties( project, projectDTO );
                        projectDTO.setEmployees( listConverterEmployeeEntityToDto( project.getEmployees() ) );
                        return listDTO.add( projectDTO );
                    } )
                    .collect( toList() );
        }else {
            return null;
        }
        return  listDTO;
    }

    public static List<ProjectEntity> listConverterProjectDTOtoEntity(List<ProjectDTO> listProjectDTO ){
        List<ProjectEntity> listProjectEntity = new LinkedList<>();
        if ( listProjectDTO != null ) {
            listProjectDTO.parallelStream()
                    .map( (projectDTO) -> {
                        ProjectEntity projectEntity = ProjectEntity.builder().build();
                        BeanUtils.copyProperties( projectDTO, projectEntity );
                        return listProjectEntity.add( projectEntity );
                    } )
                    .collect( toList() );
        }else {
            return null;
        }
        return  listProjectEntity;
    }

}
