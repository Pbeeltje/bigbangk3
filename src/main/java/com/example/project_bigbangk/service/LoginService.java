// Created by Deek
// Creation date 12/3/2021

package com.example.project_bigbangk.service;

import com.example.project_bigbangk.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final Logger logger = LoggerFactory.getLogger(LoginService.class);
    ClientService clientService;
    AuthenticateService authenticateService;
    ITokenService jwtService;

    @Autowired
    public LoginService(AuthenticateService authenticateService, ITokenService jwtService, ClientService clientService) {
        super();
        logger.info("New LoginService");
        this.clientService = clientService;
        this.authenticateService = authenticateService;
        this.jwtService = jwtService;
    }

    public String login(String email, String password) {
        if ((authenticateService.authenticate(email, password))) {
            Client client = clientService.getClientByEmail(email);
            String token = jwtService.getToken(email, client.getFirstName());
            logger.info("login user %s succes", client.getEmail());
            return token;
        }
        logger.info("No combination of %s %s", email, password);
        return null;

    }
}