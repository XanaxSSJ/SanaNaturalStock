package com.sananatural.backend.controller;

import com.sananatural.backend.seguridad.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        UserDetails user = userDetailsService.loadUserByUsername(username);
        String role = user.getAuthorities().stream().findFirst().get().getAuthority().replace("ROLE_", "");

        String token = jwtUtil.generateToken(username, role);

        return Map.of("token", token, "role", role);
    }
}
