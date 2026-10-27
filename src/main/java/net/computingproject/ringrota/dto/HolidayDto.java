package net.computingproject.ringrota.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter

public class HolidayDto {


    private int holidayId;
    private String staffName;
    private LocalDate holidayFrom;
    private LocalDate holidayTo;
    private String status;


    public HolidayDto(){

    }

}
