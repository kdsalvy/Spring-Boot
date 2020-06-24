package learn.kd.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import learn.kd.spring.security.config.JpaWebSecurityConfig;
import learn.kd.spring.security.dto.AuthenticationRequest;
import learn.kd.spring.security.dto.AuthenticationResponse;
import learn.kd.spring.security.service.AuthenticationService;

@RestController
@ConditionalOnBean(value = JpaWebSecurityConfig.class)
public class JWTController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping(path = "authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> getJwtToken(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(this.authService.authenticate(request));
    }
}
