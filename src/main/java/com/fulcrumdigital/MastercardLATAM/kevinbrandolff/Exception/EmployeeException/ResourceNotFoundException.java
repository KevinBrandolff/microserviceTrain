package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Exception.EmployeeException;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException() {
        super( "Not Found!" );
    }

    public ResourceNotFoundException( String id ) {
        super( "id " + id +" Not Found!" );
    }

}
