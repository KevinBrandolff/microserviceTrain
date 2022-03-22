package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.EmployeeException;

public class RegisterAlreadyExists extends EmployeePersistException{

    public RegisterAlreadyExists() {
        super( "Register already exists!" );
    }

}
