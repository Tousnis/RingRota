package net.computingproject.ringrota.services;


import net.computingproject.ringrota.dto.AvailableStaffDto;
import net.computingproject.ringrota.entities.ShiftEntity;
import net.computingproject.ringrota.entities.StaffEntity;
import net.computingproject.ringrota.repository.ShiftRepository;
import net.computingproject.ringrota.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvailableStaffService {


    private final StaffRepository staffRepository;
    private final ShiftRepository shiftRepository;

    public AvailableStaffService(StaffRepository staffRepository, ShiftRepository shiftRepository) {
        this.staffRepository = staffRepository;
        this.shiftRepository = shiftRepository;
    }

    public List<AvailableStaffDto> displayAvailableStaff(LocalDate date) {

        // Step 1: get every staff member
        List<StaffEntity> allStaff = staffRepository.findAll();

        // Step 2: get every shift already assigned on this date
        List<ShiftEntity> shiftsThatDay = shiftRepository.findByShiftDate(date);

        // Step 3: pull out just the staff ids of people already assigned that day
        List<Integer> assignedStaffIds = new ArrayList<>();
        for (ShiftEntity shift : shiftsThatDay) {
            assignedStaffIds.add(shift.getAssignedStaff().getStaffId());
        }

        // Step 4: build the final list — only staff NOT in the assigned list, converted to AvailableStaffDto
        List<AvailableStaffDto> availableStaff = new ArrayList<>();

        for (StaffEntity staff : allStaff) {
            if (!assignedStaffIds.contains(staff.getStaffId())) {

                AvailableStaffDto dto = new AvailableStaffDto();
                dto.setStaffId(staff.getStaffId());
                dto.setName(staff.getName());
                dto.setRole(staff.getRole().toString());

                availableStaff.add(dto);
            }
        }

        return availableStaff;
    }

}
