package com.example.silkpaytask.controllers;


import com.example.silkpaytask.config.UserAuthProvider;
import com.example.silkpaytask.dto.CredentialsDto;
import com.example.silkpaytask.dto.SignUpDto;
import com.example.silkpaytask.dto.UserDto;
import com.example.silkpaytask.entities.UserData;
import com.example.silkpaytask.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthProvider userAuthProvider;


    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {


        UserDto userDto = userService.register(signUpDto);

        userDto.setToken(userAuthProvider.generateToken(userDto.getEmail()));

        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {

        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthProvider.generateToken(userDto.getEmail()));
        return ResponseEntity.ok(userDto);
    }
    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        userService.validateToken(token);
        return "Token is valid";
    }

    @GetMapping("/{id}")
    @Operation(summary = "Secured Endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserData> findById(@PathVariable String id) {

        return ResponseEntity.ok(userService.findById(id));

    }
}
