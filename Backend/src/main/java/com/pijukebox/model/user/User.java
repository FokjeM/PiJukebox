package com.pijukebox.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pijukebox.model.BaseModel;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "user")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String firstname;

    @Column(name = "lastName", nullable = false)
    private String lastname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "token")
    private String token;

    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "role_id", nullable = false)
    private String roleId;
}
