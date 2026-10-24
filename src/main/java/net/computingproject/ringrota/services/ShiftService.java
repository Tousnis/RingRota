package net.computingproject.ringrota.services;

import net.computingproject.ringrota.dto.AddShiftDto;
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
public class ShiftService {

    private final StaffRepository staffRepository;
    private final ShiftRepository shiftRepository;

    public ShiftService(StaffRepository staffRepository, ShiftRepository shiftRepository) {
        this.staffRepository = staffRepository;
        this.shiftRepository = shiftRepository;
    }


    public void createShift(AddShiftDto dto) {

        // Step 1: fetch the real StaffEntity using the id from the dto
        StaffEntity staff = staffRepository.findById(dto.getStaffId()).orElseThrow();

        // Step 3: loop through every date from shiftDateStarts to shiftDateEnds (inclusive)
        LocalDate currentDate = dto.getShiftDateStarts();

        while (!currentDate.isAfter(dto.getShiftDateEnds())) {

            ShiftEntity shift = new ShiftEntity();
            shift.setShiftDate(currentDate);
            shift.setShiftStarts(dto.getShiftStarts());
            shift.setShiftEnds(dto.getShiftEnds());
            shift.setAssignedStaff(staff);

            shiftRepository.save(shift);

            currentDate = currentDate.plusDays(1);
        }
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
