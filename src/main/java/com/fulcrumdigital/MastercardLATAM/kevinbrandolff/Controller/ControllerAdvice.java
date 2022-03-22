package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Controller;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.EmployeeException.EmployeePersistException;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.EmployeeException.ResourceNotFoundException;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.ProjectException.ProjectPersistException;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.UserException.RoleInvalidException;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.UserException.UserPersistException;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Util.ApiErrors;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import static java.util.stream.Collectors.toList;

@RestControllerAdvice
@Profile("kevin")
public class ControllerAdvice {

    @ExceptionHandler( MethodArgumentNotValidException.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException ex ){
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(toList());
        return new ApiErrors(errors);
    }

    @ExceptionHandler( EmployeePersistException.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ApiErrors employeePersistException( EmployeePersistException ex ){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler( ResponseStatusException.class )
    @ResponseStatus( HttpStatus.NOT_FOUND )
    public ApiErrors responseStatusException( ResponseStatusException ex ){
        return new ApiErrors(ex.getReason());
    }

    @ExceptionHandler( Exception.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ApiErrors exception( Exception ex ){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler( ResourceNotFoundException.class )
    @ResponseStatus( HttpStatus.NOT_FOUND )
    public ApiErrors ResourceNotFoundException( ResourceNotFoundException ex ){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler( ProjectPersistException.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ApiErrors projectPersistException( ProjectPersistException ex ){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler( UserPersistException.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ApiErrors UserPersistException( UserPersistException ex ) {
        return new ApiErrors( ex.getMessage() );
    }


}
