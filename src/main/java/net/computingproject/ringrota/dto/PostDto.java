package net.computingproject.ringrota.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class PostDto {

    //Note: dto for the Get method to display the forum posts

    private Integer postId;
    private String staffName;
    private LocalDateTime postedAt;
    private String message;

    private LocalDate shiftDate;
    private LocalTime shiftStarts;
    private LocalTime shiftEnds;
    private String status;
    private String coveredBy;

    public PostDto(){

    }

}
