package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
        response.setContentType( APPLICATION_JSON_VALUE );
        response.getWriter().write( "Token Expired! " + accessDeniedException.getMessage());
    }

}
