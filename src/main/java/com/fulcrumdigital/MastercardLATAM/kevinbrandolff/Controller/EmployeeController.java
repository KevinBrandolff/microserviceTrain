package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Controller;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.EmployeeDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.EmployeeException.EmployeePersistException;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Service.EmployeeService;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Service.Impl.EmployeeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController("kevinEmployeeController")
@RequestMapping( value = "/kevin/employee")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController( EmployeeService service ) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee( @RequestBody @Valid EmployeeDTO employeeDTO ){
        return new ResponseEntity<>( service.createEmployee( employeeDTO ), HttpStatus.CREATED );
    }

    @PutMapping( value = "/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO, @PathVariable Integer id ){
        return ResponseEntity.ok( service.update( employeeDTO, id ) );
    }

    @DeleteMapping( value = "/{id}" )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void deleteById( @PathVariable Integer id ) {
        service.deleteById( id );
    }

    @GetMapping( value = "/{id}" )
    public ResponseEntity<EmployeeDTO> findById(@PathVariable Integer id ) {
        return ResponseEntity.ok( service.findById( id ) );
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> findAll(){
        return ResponseEntity.ok( service.findAll() );
    }

    @GetMapping( value = "/name/{name}")
    public ResponseEntity<List<EmployeeDTO>> findAllByName( @PathVariable String name ){
        return ResponseEntity.ok( service.findAllByName( name ) );
    }

    @GetMapping( value = "/register/{register}")
    public ResponseEntity<EmployeeDTO> findByRegister( @PathVariable String register ) {
        return ResponseEntity.ok( service.findByRegister( register ) );
    }

    @GetMapping( value = "/age/{age}" )
    public ResponseEntity<List<EmployeeDTO>> findAllByAge( @PathVariable Integer age ){
        return ResponseEntity.ok( service.findAllByAge( age ) );
    }

    @GetMapping( value = "/address/{address}" )
    public ResponseEntity<List<EmployeeDTO>> findAllByAddress( @PathVariable String address ){
        return ResponseEntity.ok( service.findAllByAddress( address ) );
    }

    @GetMapping( value = "/salary/{salary}" )
    public ResponseEntity<List<EmployeeDTO>> findAllBySalary( @PathVariable Double salary ){
        return ResponseEntity.ok( service.findAllBySalary( salary ) );
    }

    @GetMapping( value = "/email/{email}" )
    public ResponseEntity<EmployeeDTO> findByEmail( @PathVariable String email ) {
        return ResponseEntity.ok( service.findByEmail( email ) );
    }

    @GetMapping( value = "/department/{department}" )
    public ResponseEntity<List<EmployeeDTO>> findAllByDepartment( @PathVariable String department ){
        return ResponseEntity.ok( service.findAllByDepartment( department ) );
    }

    @GetMapping( value = "/createdAt/{start}/{end}" )
    public ResponseEntity<List<EmployeeDTO>> findAllByDepartment( @PathVariable String start, @PathVariable String end ){
        return ResponseEntity.ok( service.findAllByCreatedAtBetween( start, end ) );
    }

}
