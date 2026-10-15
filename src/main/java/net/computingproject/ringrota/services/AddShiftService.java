package net.computingproject.ringrota.services;



import net.computingproject.ringrota.dto.AddShiftDto;
import net.computingproject.ringrota.entities.ShiftEntity;
import net.computingproject.ringrota.entities.StaffEntity;
import net.computingproject.ringrota.repository.ShiftRepository;
import net.computingproject.ringrota.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

//todo make it more simpler
@Service
public class AddShiftService {

    private final StaffRepository staffRepository;
    private final ShiftRepository shiftRepository;

    public AddShiftService(StaffRepository staffRepository, ShiftRepository shiftRepository) {
        this.staffRepository = staffRepository;
        this.shiftRepository = shiftRepository;
    }


    public void createShift(AddShiftDto dto) {

        // Step 1: fetch the real StaffEntity using the id from the dto
        StaffEntity staff = staffRepository.findById(dto.getStaffId()).orElseThrow();

        // there could be different rows in between them but still a UUID will give them
        // the same identifier so to belong to the same person
        String groupId = UUID.randomUUID().toString();

        // Step 3: loop through every date from shiftDateStarts to shiftDateEnds (inclusive)
        LocalDate currentDate = dto.getShiftDateStarts();

        while (!currentDate.isAfter(dto.getShiftDateEnds())) {

            ShiftEntity shift = new ShiftEntity();
            shift.setShiftDate(currentDate);
            shift.setShiftStarts(dto.getShiftStarts());
            shift.setShiftEnds(dto.getShiftEnds());
            shift.setAssignedStaff(staff);
            shift.setGroupId(groupId);

            shiftRepository.save(shift);

            currentDate = currentDate.plusDays(1);
        }
    }

}

