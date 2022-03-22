package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.EmployeeException;

public class EmailAlreadyRegistred extends EmployeePersistException{

    public EmailAlreadyRegistred() {
        super( "Email already registred!" );
    }

}
