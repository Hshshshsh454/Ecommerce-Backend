package com.example.ecommerce.security;

import com.example.ecommerce.dto.AuthResponse;
import com.example.ecommerce.dto.LoginRequest;
import com.example.ecommerce.dto.RegisterRequest;
import com.example.ecommerce.exception.BusinessException;
import com.example.ecommerce.user.Role;
import com.example.ecommerce.user.User;
import com.example.ecommerce.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        if (repository.existsByEmail(request.email())) {
            throw new BusinessException("Email Already Exists");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(encoder.encode(request.password()))
                .role(Role.ROLE_USER)
                .build();

        repository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponse(token, "Bearer"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()));

        String token = jwtService.generateToken(request.email());

        return ResponseEntity.ok(new AuthResponse(token, "Bearer"));
    }
}

// OLD CODE - kept for reference
// import javax.management.relation.Role;
// import org.springframework.security.core.userdetails.User;
//
// User user = User.builder()
//         .name(request.name())
//         .email(request.email())
//         .password(encoder.encode(request.password()))
//         .role(Role.ROLE_USER)
//         .build();
