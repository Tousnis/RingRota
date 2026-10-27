package net.computingproject.ringrota.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class ChangePasswordDto {


    private String currentPassword;
    private String newPassword;
    private String confirmPassword;


    public ChangePasswordDto(){

    }

}
