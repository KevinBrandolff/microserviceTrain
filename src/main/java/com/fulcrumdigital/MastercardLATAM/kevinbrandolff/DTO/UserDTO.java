package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.DTO;

import com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity.UserEntity;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Integer id;
    @NotNull( message = "Username required" )
    private String username;
    @NotNull( message = "Password required" )
    private String password;
    @NotNull( message = "Role required" )
    private String role;

    public UserDTO( UserEntity user ) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole().toString();
    }

}
