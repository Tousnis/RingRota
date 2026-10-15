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

    private Integer postId;          //needed for the Pick up button url
    private String staffName;        //who wrote the post
    private LocalDateTime postedAt;
    private String message;

    //only filled in for a cover post (empty for a chat post)
    private LocalDate shiftDate;
    private LocalTime shiftStarts;
    private LocalTime shiftEnds;
    private String status;           //OPEN or CLAIMED
    private String coveredBy;        //name of who picked the shift up, once CLAIMED

    public PostDto(){

    }

}
