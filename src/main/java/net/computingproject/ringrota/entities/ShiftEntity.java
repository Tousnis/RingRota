package net.computingproject.ringrota.entities;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity //Makes the class into db table
public class ShiftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shiftId;

    @Column(nullable = false)
    private LocalDate shiftDate;

    @Column(nullable = false)
    private LocalTime shiftStarts;

    @Column(nullable = false)
    private LocalTime shiftEnds;

    @Column(nullable = false)
    private String groupId; // shared ID for shifts created in the same assignment action single day or range

    @ManyToOne  //Joining the foreign key, which is this case it is passing the class
    @JoinColumn(name = "staff_id", nullable = false)
    private StaffEntity assignedStaff;

    public ShiftEntity() {
    }

    // getters and setters

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int id) {
        this.shiftId = id;
    }

    public LocalDate getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(LocalDate shiftDate) {
        this.shiftDate = shiftDate;
    }

    public LocalTime getShiftStarts() {
        return shiftStarts;
    }

    public void setShiftStarts(LocalTime startTime) {
        this.shiftStarts = startTime;
    }

    public LocalTime getShiftEnds() {
        return shiftEnds;
    }

    public void setShiftEnds(LocalTime endTime) {
        this.shiftEnds = endTime;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public StaffEntity getAssignedStaff() {
        return assignedStaff;
    }

    public void setAssignedStaff(StaffEntity assignedStaff) {
        this.assignedStaff = assignedStaff;
    }
}