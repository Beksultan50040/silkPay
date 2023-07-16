package com.example.silkpaytask.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_credentials")
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotNull
    @Size(max = 100)
    private String firstName;
    @NotBlank
    @NotNull
    @Size(max = 100)
    private String lastName;
    @NotBlank
    @NotNull
    @Email
    @Column(nullable = false, unique = true)
    @Size(max = 100)
    private String email;
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private Roles roles = Roles.ROLE_USER;
    @NotEmpty
    @NotNull
    private String password;

}