package com.arrafi.itineraryplanner.auth;

import com.arrafi.itineraryplanner.config.JwtService;
import com.arrafi.itineraryplanner.model.Customer;
import com.arrafi.itineraryplanner.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                ));
        Customer customer = customerRepo.findByUsername(request.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(customer);
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken, customer.getUsername(), customer.getId()));
    }
}
