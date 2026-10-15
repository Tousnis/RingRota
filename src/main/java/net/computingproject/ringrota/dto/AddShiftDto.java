package net.computingproject.ringrota.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter

public class AddShiftDto {

    private int staffId;
    private LocalDate shiftDateEnds;
    private LocalDate shiftDateStarts;
    private LocalTime shiftStarts;
    private LocalTime shiftEnds;


    public AddShiftDto(){

    }


    //Imaginary setters and getters

}
