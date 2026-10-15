package net.computingproject.ringrota.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class ClockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clockId;

    @Column(nullable = false)
    private LocalTime clockIn;

    @Column(nullable = true) //clock can be empty until staff clocks out
    private LocalTime clockOut;

    @Column(nullable = false)
    private LocalDate clockDate;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "staff_id" , nullable = false)
    private StaffEntity assignedStaff;


    public ClockEntity(){

    }


}
