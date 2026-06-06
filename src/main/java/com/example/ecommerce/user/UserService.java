package com.example.ecommerce.user;

import com.example.ecommerce.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse create(UserRequest request) {
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ROLE_USER)
                .build();

        return toResponse(repository.save(user));
    }

    public UserResponse get(Long id) {
        return toResponse(
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found")));
    }

    public UserResponse update(Long id, UserRequest request) {
        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        return toResponse(repository.save(user));
    }

    public void delete(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        repository.delete(user);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail());
    }

    // OLD CODE - kept for reference (plain-text password, no role)
    // public UserResponse create(UserRequest request) {
    //     User user = User.builder()
    //             .name(request.name())
    //             .email(request.email())
    //             .password(request.password())
    //             .build();
    //     return toResponse(repository.save(user));
    // }
    //
    // public UserResponse update(Long id, UserRequest request) {
    //     user.setPassword(request.password());
    //     ...
    // }
}
