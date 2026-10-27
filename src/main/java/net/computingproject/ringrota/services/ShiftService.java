package net.computingproject.ringrota.services;

import net.computingproject.ringrota.dto.AddShiftDto;
import net.computingproject.ringrota.dto.AvailableStaffDto;
import net.computingproject.ringrota.dto.ShiftListDto;
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


    //ADMIN SIDE=====================================================================================================

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



     //Method to show staff not on shift 
    public List<AvailableStaffDto> displayAvailableStaff(LocalDate date) {


        List<StaffEntity> allStaff = staffRepository.findAll();

        List<ShiftEntity> shiftsThatDay = shiftRepository.findByShiftDate(date);

        List<Integer> assignedStaffIds = new ArrayList<>();
        for (ShiftEntity shift : shiftsThatDay) {
            assignedStaffIds.add(shift.getAssignedStaff().getStaffId());
        }

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


    //Display all shift
    public List<ShiftListDto> displayAllShifts(){

        List<ShiftEntity> listOfShifts = shiftRepository.findAll();
        List<ShiftListDto> shiftList = new ArrayList<>();

        for (ShiftEntity shifts: listOfShifts){
            ShiftListDto dto = new ShiftListDto();
            dto.setShiftId(shifts.getShiftId());
            dto.setShiftDate(shifts.getShiftDate());
            dto.setShiftStarts(shifts.getShiftStarts());
            dto.setShiftEnds(shifts.getShiftEnds());
            dto.setStaffName(shifts.getAssignedStaff().getName());

            shiftList.add(dto);

        }
        return shiftList;
    }


    //method to display all staff for the dropdown
    public List<AvailableStaffDto> displayAllStaff(){

        List<StaffEntity> allStaff = staffRepository.findAll();
        List<AvailableStaffDto> staffList = new ArrayList<>();

        for (StaffEntity staff : allStaff){
            AvailableStaffDto dto = new AvailableStaffDto();
            dto.setStaffId(staff.getStaffId());
            dto.setName(staff.getName());
            dto.setRole(staff.getRole().toString());

            staffList.add(dto);
        }
        return staffList;
    }

    //Method to delete a shift
    public void deleteShift(Integer id){
        shiftRepository.deleteById(id);
    }

    //Method to show staff already on shift
    public List<AvailableStaffDto> displayStaffOnShift(LocalDate date) {

        // Step 1: get every staff member
        List<StaffEntity> everyStaffMember = staffRepository.findAll();

        // Step 2: get every shift already assigned on this date
        List<ShiftEntity> shiftsOnThisDate = shiftRepository.findByShiftDate(date);

        // Step 3: pull out just the staff ids of people already assigned that day
        List<Integer> onShiftStaffIds = new ArrayList<>();
        for (ShiftEntity shift : shiftsOnThisDate) {
            onShiftStaffIds.add(shift.getAssignedStaff().getStaffId());
        }

        // Step 4: build the final list — only staff IN the assigned list, converted to AvailableStaffDto
        List<AvailableStaffDto> staffOnShift = new ArrayList<>();

        for (StaffEntity staff : everyStaffMember) {
            if (onShiftStaffIds.contains(staff.getStaffId())) {

                AvailableStaffDto dto = new AvailableStaffDto();
                dto.setStaffId(staff.getStaffId());
                dto.setName(staff.getName());
                dto.setRole(staff.getRole().toString());

                staffOnShift.add(dto);
            }
        }

        return staffOnShift;
    }

    //method to find the shift for edit
    public ShiftListDto findShiftForEdit(Integer id){

        ShiftEntity shift = shiftRepository.findById(id).orElseThrow();

        ShiftListDto dto = new ShiftListDto();
        dto.setShiftId(shift.getShiftId());
        dto.setShiftDate(shift.getShiftDate());
        dto.setShiftStarts(shift.getShiftStarts());
        dto.setShiftEnds(shift.getShiftEnds());
        dto.setStaffName(shift.getAssignedStaff().getName());

        return dto;
    }

    //method to save the shift edit
    public void saveShiftEdit(Integer id, ShiftListDto dto){

        ShiftEntity shift = shiftRepository.findById(id).orElseThrow();
        shift.setShiftDate(dto.getShiftDate());
        shift.setShiftStarts(dto.getShiftStarts());
        shift.setShiftEnds(dto.getShiftEnds());

        shiftRepository.save(shift);
    }

    //STAFF SIDE==========================================================================================================================================

    //display individual shifts for staff
    public List<ShiftListDto> displayMyShifts(String email){

        StaffEntity staff = staffRepository.findByEmail(email);
        List<ShiftEntity> listOfShifts = shiftRepository.findByAssignedStaff(staff);
        List<ShiftListDto> shiftList = new ArrayList<>();

        for (ShiftEntity shifts: listOfShifts){
            ShiftListDto dto = new ShiftListDto();
            dto.setShiftId(shifts.getShiftId());
            dto.setShiftDate(shifts.getShiftDate());
            dto.setShiftStarts(shifts.getShiftStarts());
            dto.setShiftEnds(shifts.getShiftEnds());

            shiftList.add(dto);

        }
        return shiftList;
    }


}
