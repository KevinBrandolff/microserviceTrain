package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity( name = "kevinUserEntity" )
@Table( name = "user_kevin" )
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column( nullable = false )
    @Enumerated( EnumType.STRING )
    private RoleEnum role;

    public static enum RoleEnum {
        ADMIN, USER;
    }

}

