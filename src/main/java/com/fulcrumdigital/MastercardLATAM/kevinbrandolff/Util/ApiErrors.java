package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Util;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class ApiErrors {

    private List<String> errors;

    public ApiErrors( String errorMenssage ) {
        this.errors = Arrays.asList(errorMenssage);
    }

    public ApiErrors( List<String> errors ) {
        this.errors = errors;
    }
}
