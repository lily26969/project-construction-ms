package tn.esprit.tpfoyer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
