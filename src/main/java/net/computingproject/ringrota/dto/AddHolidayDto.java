package net.computingproject.ringrota.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter

public class AddHolidayDto {


    private LocalDate holidayFrom;
    private LocalDate holidayTo;


    public AddHolidayDto(){

    }

}
