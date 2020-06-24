package learn.kd.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import learn.kd.spring.security.config.JpaWebSecurityConfig;
import learn.kd.spring.security.dto.AuthenticationRequest;
import learn.kd.spring.security.dto.AuthenticationResponse;
import learn.kd.spring.security.util.JwtUtil;

@ConditionalOnBean(value = JpaWebSecurityConfig.class)
@Order(2)
@Service
public class JwtAuthenticationService implements AuthenticationService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String jwtToken = jwtUtil.generateToken(userDetails);
        return new AuthenticationResponse(jwtToken);
    }

}
