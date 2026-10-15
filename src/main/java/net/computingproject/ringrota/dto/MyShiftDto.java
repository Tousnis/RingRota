package net.computingproject.ringrota.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class MyShiftDto {

    //Note: dto for the "Need cover for a shift?" dropdown, one option per shift of the logged-in staff

    private Integer shiftId;        //the option value sent with the form
    private LocalDate shiftDate;
    private LocalTime shiftStarts;
    private LocalTime shiftEnds;

    public MyShiftDto(){

    }

}
