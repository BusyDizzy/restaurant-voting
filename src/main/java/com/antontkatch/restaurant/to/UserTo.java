package com.antontkatch.restaurant.to;

import com.antontkatch.restaurant.HasIdAndEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.beans.ConstructorProperties;
import java.io.Serial;
import java.io.Serializable;

@Value
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class UserTo extends NamedTo implements Serializable, HasIdAndEmail {

    @Serial
    private static final long serialVersionUID = 1L;

    @Email
    @NotBlank
    @Size(max = 128)
    String email;

    @NotBlank
    @Size(min = 5, max = 32, message = "length must be between 5 and 32 characters")
    String password;


    @ConstructorProperties({"id", "name", "email", "password"})
    public UserTo(Integer id, String name, String email, String password) {
        super(id, name);
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserTo:" + id + '[' + email + ']';
    }
}
