package learn.kd.spring.security.service;

import learn.kd.spring.security.dto.AuthenticationRequest;
import learn.kd.spring.security.dto.AuthenticationResponse;

public interface AuthenticationService {
    
    public AuthenticationResponse authenticate(AuthenticationRequest request);

}
