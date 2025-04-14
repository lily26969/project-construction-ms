package com.example.Pidev.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id_User;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private int num_tel;
    private String keycloakId;
    private byte[] image;
}
