package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Controller;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.EmployeeDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.ProjectDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.ProjectException.ProjectNameAlreadyRegistred;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController("kevinProjectController")
@RequestMapping( value = "/kevin/project")
public class ProjectController {

    private final ProjectService service;

    public ProjectController( ProjectService service ) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createEmployee(@RequestBody @Valid ProjectDTO projectDTO ){
        return new ResponseEntity<>( service.createProject( projectDTO ), HttpStatus.CREATED );
    }

    @PutMapping
    public ResponseEntity<ProjectDTO> updateEmployee(@RequestBody ProjectDTO projectDTO ){
        return ResponseEntity.ok( service.update( projectDTO ) );
    }

    @DeleteMapping( value = "/{id}" )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void deleteById( @PathVariable Integer id ) {
        service.deleteById( id );
    }

    @GetMapping( value = "/{id}" )
    public ResponseEntity<ProjectDTO> findById(@PathVariable Integer id ) {
        return ResponseEntity.ok( service.findById( id ) );
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> findAll(){
        return ResponseEntity.ok( service.findAll() );
    }

    @GetMapping( value = "/name/{name}")
    public ResponseEntity<ProjectDTO> findByName( @PathVariable String name ){
        return ResponseEntity.ok( service.findByName( name ) );
    }

    @GetMapping( value = "/employee/{id}")
    public ResponseEntity<List<ProjectDTO>> findAllByEmployeesId( @PathVariable Integer id ){
        return ResponseEntity.ok( service.findAllByEmployeesId( id ) );
    }

    @PostMapping( value = "/project/{id}/employee" )
    public ResponseEntity<ProjectDTO> addEmployeeToProject( @RequestBody @Valid EmployeeDTO employeeDTO, @PathVariable Integer id ){
        return ResponseEntity.ok( service.addEmployeeToProject( employeeDTO, id ) );
    }

    @DeleteMapping( value = "/project/{projectId}/employee/{employeeId}" )
    public ResponseEntity<ProjectDTO> deleteEmployeeFromProject( @PathVariable Integer projectId, @PathVariable Integer employeeId ){
        return ResponseEntity.ok( service.deleteEmployeeFromProject( projectId, employeeId ) );
    }

}
