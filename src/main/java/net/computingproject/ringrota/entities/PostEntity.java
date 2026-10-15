package net.computingproject.ringrota.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity
public class PostEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int postId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime postedAt;

    @Column(nullable = true)
    private String status;


    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private StaffEntity staff;

    @ManyToOne
    @JoinColumn(name = "shift_id", nullable = true)
    private ShiftEntity shift;


    public PostEntity(){

    }
}
