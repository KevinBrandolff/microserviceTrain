package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Service;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.EmployeeDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.EmployeeException.EmployeePersistException;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO ) throws EmployeePersistException;
    EmployeeDTO update( EmployeeDTO employeeDTO, Integer id ) throws EmployeePersistException;
    void deleteById( Integer id );
    EmployeeDTO findById( Integer id );
    List<EmployeeDTO> findAll();
    List<EmployeeDTO> findAllByName( String name );
    EmployeeDTO findByRegister( String register );
    List<EmployeeDTO> findAllByAge( Integer age );
    List<EmployeeDTO> findAllByAddress( String address );
    List<EmployeeDTO> findAllBySalary( Double salary );
    EmployeeDTO findByEmail( String email );
    List<EmployeeDTO> findAllByDepartment( String department );
    List<EmployeeDTO> findAllByCreatedAtBetween( String start, String end );

}
