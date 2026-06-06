package com.example.ecommerce.user;

import com.example.ecommerce.common.ApiResponse;
import com.example.ecommerce.user.UserRequest;
import com.example.ecommerce.user.UserResponse;
import com.example.ecommerce.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(
            @Valid @RequestBody UserRequest request) {

        UserResponse response = service.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<UserResponse>builder()
                                .success(true)
                                .message("User Created Successfully")
                                .data(response)
                                .build()
                );
    }

    // 2. GET USER BY ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> get(@PathVariable Long id) {

        UserResponse response = service.get(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<UserResponse>builder()
                                .success(true)
                                .message("User Retrieved Successfully")
                                .data(response)
                                .build()
                );
    }

    // 3. UPDATE USER (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {

        UserResponse response = service.update(id, request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<UserResponse>builder()
                                .success(true)
                                .message("User Updated Successfully")
                                .data(response)
                                .build()
                );
    }

    // 4. DELETE USER (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<Void>builder() // डिलीट में कोई डेटा वापस नहीं जाता, इसलिए Void
                                .success(true)
                                .message("User Deleted Successfully")
                                .data(null)
                                .build()
                );
    }
}