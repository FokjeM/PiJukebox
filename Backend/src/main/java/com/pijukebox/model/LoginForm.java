package com.pijukebox.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginForm implements Serializable {
    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;
}
