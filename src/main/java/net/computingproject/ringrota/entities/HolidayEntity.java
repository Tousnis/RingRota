package net.computingproject.ringrota.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class HolidayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int holidayId;

    @Column(nullable = false)
    private LocalDate holidayFrom;

    @Column(nullable = false)
    private LocalDate holidayTo;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private StaffEntity assignedStaff;


    public HolidayEntity(){

    }


}
