package net.computingproject.ringrota.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ClockRecordDto {

    //Note: dto for the Get method to display the clock records

    private Integer clockId;
    private String staffName;
    private LocalDate clockDate;
    private LocalTime clockIn;
    private LocalTime clockOut;
    private String status;

    public ClockRecordDto(){

    }




}
