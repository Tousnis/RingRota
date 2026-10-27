package net.computingproject.ringrota.dto;



import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import net.computingproject.ringrota.entities.EnumRole;


@Getter
@Setter

public class StaffListDto {

    private int staffId;


    @NotBlank(message = "First name is required")
    private String name;


    @NotBlank(message = "Last name is required")
    private String lastName;

    private String email;


    @Pattern(regexp = "\\d{11}", message = "Phone number must be 11 digits")
    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;


    @NotNull(message = "Role must be chosen")
    private EnumRole role;

    private int totalHours;

    public StaffListDto() {
    }


}
