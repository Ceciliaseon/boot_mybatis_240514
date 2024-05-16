package com.ezen.www.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Getter
@Setter
@Component
@Slf4j
public class LoginFailHandler implements AuthenticationFailureHandler {

    private String authEmail;
    private String errorMessage;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        setAuthEmail(request.getParameter("email"));
        if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException){
            setErrorMessage(exception.getMessage().toString());
        }
        log.info(">> errMsg >> {}", this.errorMessage);
        request.setAttribute("email", authEmail);
        request.setAttribute("errMsg", errorMessage);

        request.getRequestDispatcher("/member/login").include(request, response);
        //response.sendRedirect("/member/login?error="+ errorMessage);
    }
}
