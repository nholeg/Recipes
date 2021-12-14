package recipes.security;


import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Validated
@Entity
public class User {
    @Id
    @NotBlank
    @Email(regexp = "^\\S+@\\S+\\.\\S+$")
    private String email;
    @NotBlank
    @Size(min = 8)
    private String password;
    private String role;

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
