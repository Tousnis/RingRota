package net.computingproject.ringrota.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import net.computingproject.ringrota.entities.EnumRole;

@Setter
@Getter
public class AddStaffDto {
    
    //validation
    @NotBlank(message = "First name is required")  //can't be null, empty or just white-space
    private String name;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    @Pattern(regexp = ".+@ringrota\\.com$", message = "Email must use the @ringrota.com domain")
    private String email;

    @Pattern(regexp = "\\d{11}", message = "Phone number must be 11 digits")
    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Must be at least 6 characters")
    private String password;


    @NotNull(message = "Role must be chosen")
    private EnumRole role;
    
    //default constructor 
    public AddStaffDto() {
    }
    


}
