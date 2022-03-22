package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Controller;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO.UserDTO;
import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("kevinUserController")
@RequestMapping( value = "/kevin/user" )
public class UserController {

    private final UserService service;

    public UserController( UserService service ){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser( @RequestBody @Valid UserDTO userDTO ){
        return new ResponseEntity<>( service.createUser( userDTO ), HttpStatus.CREATED );
    }

}
