package net.computingproject.ringrota.services;


import net.computingproject.ringrota.dto.ClockRecordDto;
import net.computingproject.ringrota.entities.ClockEntity;
import net.computingproject.ringrota.entities.StaffEntity;
import net.computingproject.ringrota.repository.ClockRepository;
import net.computingproject.ringrota.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class ClockService {



    private final ClockRepository clockRepository;
    private final StaffRepository staffRepository;


    public ClockService(ClockRepository clockRepository, StaffRepository staffRepository) {

        this.clockRepository = clockRepository;
        this.staffRepository = staffRepository;
    }

    //admin side display all the clock records
    public List<ClockRecordDto> displayAllStaffRecords() {

        List<ClockEntity> records = clockRepository.findAll();
        List<ClockRecordDto> clockRecords = new ArrayList<>();

        for (ClockEntity record : records) {
            ClockRecordDto dto = new ClockRecordDto();
            dto.setClockId(record.getClockId());
            dto.setStaffName(record.getAssignedStaff().getName());
            dto.setClockDate(record.getClockDate());
            dto.setClockIn(record.getClockIn());
            dto.setClockOut(record.getClockOut());
            dto.setStatus(record.getStatus());
            clockRecords.add(dto);
        }

        return clockRecords;
    }

    //Staff side clock in
    public void clockIn(String email){

        StaffEntity staff = staffRepository.findByEmail(email);

        //Adapted from Albourai (2026): the record with no clock out = still clocked in
        ClockEntity openRecord = clockRepository.findByAssignedStaffAndClockOutIsNull(staff);

        if (openRecord != null){
            throw new IllegalStateException("You are already clocked in.");
        }

        ClockEntity record = new ClockEntity(); //Creating the clock in record
        record.setAssignedStaff(staff);
        record.setClockDate(LocalDate.now());
        record.setClockIn(LocalTime.now());
        record.setStatus("PENDING");

        clockRepository.save(record);
    }

    //Staff side clock out
    public void clockOut(String email){

        StaffEntity staff = staffRepository.findByEmail(email);
        ClockEntity openRecord = clockRepository.findByAssignedStaffAndClockOutIsNull(staff);

        if (openRecord == null){
            throw new IllegalStateException("You need to clock in first.");
        }
        //updating the previous clock record row without creating a new one
        openRecord.setClockOut(LocalTime.now());
        clockRepository.save(openRecord);
    }

    //staff side method to display all  the records from staff
    public List<ClockRecordDto> displayAllStaffRecords(String email){
        StaffEntity staff = staffRepository.findByEmail(email);

    List<ClockEntity> records = clockRepository.findByAssignedStaff(staff);
    List<ClockRecordDto> clockRecords = new ArrayList<>(); //Empty array list

        for (ClockEntity record : records){ //iterate throw the clockEntity records
             ClockRecordDto dto = new ClockRecordDto(); //Crete the empty obj for the dto
            dto.setClockId(record.getClockId());
            dto.setClockDate(record.getClockDate());
            dto.setClockIn(record.getClockIn());
            dto.setClockOut(record.getClockOut());
            dto.setStatus(record.getStatus());

            clockRecords.add(dto); //add to the array List
        }
        return clockRecords;
    }

    //admin side approve records

    public void approveRecord(Integer clockId){
        ClockEntity record = clockRepository.findById(clockId).orElseThrow();
        record.setStatus("APPROVED");
        clockRepository.save(record);
    }

    //admin side/delete reject record
    public void rejectRecord(Integer clockId){
        ClockEntity record = clockRepository.findById(clockId).orElseThrow();
        record.setStatus("REJECTED");
        clockRepository.save(record);
    }



}
