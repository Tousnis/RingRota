package net.computingproject.ringrota.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ShiftListDto {



    private Integer shiftId;
    private LocalDate shiftDate;
    private LocalTime shiftStarts;
    private LocalTime shiftEnds;
    private String staffName;

    public ShiftListDto(){

    }

}
