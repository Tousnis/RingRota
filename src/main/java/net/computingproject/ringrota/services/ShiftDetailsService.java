package net.computingproject.ringrota.services;

import net.computingproject.ringrota.dto.ShiftDetailsDto;
import net.computingproject.ringrota.entities.ShiftEntity;
import net.computingproject.ringrota.repository.ShiftRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShiftDetailsService {

    private final ShiftRepository shiftRepository;

    public ShiftDetailsService(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    public List<ShiftDetailsDto> displayShiftDetails() {

        List<ShiftDetailsDto> shiftDetails = new ArrayList<>();

        // 1. get every groupId, once each
        List<String> groupIds = shiftRepository.findDistinctGroupIds();

        // 2. one call per group
        for (String groupId : groupIds) {

            List<ShiftEntity> shifts = shiftRepository.findByGroupId(groupId);

            // start from the first shift in the group
            ShiftEntity first = shifts.get(0);
            LocalDate fromDate = first.getShiftDate();
            LocalDate toDate = first.getShiftDate();
            double totalHours = 0;

            // 3. go through each day in the group
            for (ShiftEntity shift : shifts) {

                if (shift.getShiftDate().isBefore(fromDate)) {
                    fromDate = shift.getShiftDate();
                }
                if (shift.getShiftDate().isAfter(toDate)) {
                    toDate = shift.getShiftDate();
                }

                Duration length = Duration.between(shift.getShiftStarts(), shift.getShiftEnds());
                totalHours = totalHours + (length.toMinutes() / 60.0);
            }

            // 4. build one row for this group
            ShiftDetailsDto dto = new ShiftDetailsDto();
            dto.setGroupId(groupId);
            dto.setStaffName(first.getAssignedStaff().getName());
            dto.setFromDate(fromDate);
            dto.setToDate(toDate);
            dto.setTotalHours(totalHours);

            shiftDetails.add(dto);
        }

        return shiftDetails;
    }
}
