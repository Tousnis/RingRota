package net.computingproject.ringrota.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ShiftDetailsDto {

    private String groupId;      // not shown in the table, used by the View/Edit link
    private String staffName;    // Staff column
    private LocalDate fromDate;  // From column: earliest date in the group
    private LocalDate toDate;    // To column: latest date in the group
    private double totalHours;   // Total Hours column: added up across the group
}
