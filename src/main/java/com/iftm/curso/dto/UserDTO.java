package com.iftm.curso.dto;

import com.iftm.curso.entities.User;
import com.iftm.curso.services.validation.UserUpdateValid;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@UserUpdateValid
public class UserDTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    @NotEmpty(message = "Can't be empty")
    @Length(min = 5, max = 80, message = "length must be between 5 and 80")
    private String name;

    @NotEmpty(message = "Can't be empty")
    @Email(message = "invalid email")
    private String email;

    @NotEmpty(message = "Can't be empty")
    @Length(min = 8, max = 20, message = "length must be between 5 and 80")
    private String phone;

    public UserDTO(){

    }

    public UserDTO(Long id, String name, String email, String phone) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public UserDTO(User entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User toEntity(){
        return new User(id, name, email, phone, null);
    }
}
