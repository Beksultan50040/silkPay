package com.example.silkpaytask.entities;

import com.example.silkpaytask.entities.Account.Account;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import  jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_data")
public class UserData {
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
    @Size(max = 100)
    private String email;
    private LocalDate birthDate;
    @OneToOne(mappedBy = "userId", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Account account;

}
