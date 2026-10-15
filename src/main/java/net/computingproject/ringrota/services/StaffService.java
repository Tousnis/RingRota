package net.computingproject.ringrota.services;


import net.computingproject.ringrota.dto.AddStaffDto;
import net.computingproject.ringrota.dto.StaffListDto;
import net.computingproject.ringrota.entities.StaffEntity;
import net.computingproject.ringrota.repository.StaffRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StaffService {


    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;

    public StaffService(StaffRepository staffRepository, PasswordEncoder passwordEncoder) {
        this.staffRepository = staffRepository;
        this.passwordEncoder = passwordEncoder;
    }


    //method to create staff member
    public void createStaff(AddStaffDto dto){ //Passing the raw data into the method

        StaffEntity staff = new StaffEntity(); //Creating the actual obj


        staff.setName(dto.getName());
        staff.setLastName(dto.getLastName());
        staff.setPassword(passwordEncoder.encode(dto.getPassword()));
        staff.setEmail(dto.getEmail());
        staff.setPhoneNumber(dto.getPhoneNumber());
        staff.setRole(dto.getRole());


        staffRepository.save(staff);

    }




    //Display all the staff =
    public List<StaffListDto> displayStaff(){   // Return the staff List
        List<StaffListDto> staffList = new ArrayList<>();   //Create the list that will contain the list of staff obj (dto)
        List<StaffEntity> allStaff = staffRepository.findAll(); //Ask Jpa first to find all the entities

        for(StaffEntity oneStaff : allStaff){  //Loop throw every row
            StaffListDto dto = new StaffListDto();




            dto.setStaffId(oneStaff.getStaffId());
            dto.setName(oneStaff.getName());
            dto.setLastName(oneStaff.getLastName());
            dto.setEmail(oneStaff.getEmail());
            dto.setPhoneNumber(oneStaff.getPhoneNumber());
            dto.setRole(oneStaff.getRole());
            dto.setTotalHours(oneStaff.getTotalHours());

            staffList.add(dto);
        }
        return staffList;
    }


    //Method to find staff by ID,
    //get the entity and convert it into a dto to pass it to the controller
    //method is being reused twice:
    // once for displaying the staff profile hardcoded data from the db  = admin/view-staff-profile/
    //two for displaying the staff profile for edits staff/find-edit/{id}


    public StaffListDto getStaffForEdit(Integer id) {
        StaffEntity staff = staffRepository.findById(id).orElseThrow();

        StaffListDto dto = new StaffListDto();
        dto.setStaffId(staff.getStaffId());
        dto.setTotalHours(staff.getTotalHours());
        dto.setName(staff.getName());
        dto.setLastName(staff.getLastName());
        dto.setEmail(staff.getEmail());
        dto.setPhoneNumber(staff.getPhoneNumber());
        dto.setRole(staff.getRole());

        return dto;
    }

    //Find staff member and convert the obj into dto obj save it into the db

    public void updateStaffEdit(Integer id, StaffListDto dto){
        StaffEntity staff = staffRepository.findById(id).orElseThrow();

        staff.setName(dto.getName());
        staff.setLastName(dto.getLastName());
        staff.setPhoneNumber(dto.getPhoneNumber());
        staff.setRole(dto.getRole());
        staff.setTotalHours(dto.getTotalHours());

        staffRepository.save(staff);
    }


    public void deleteStaff(Integer id){

        staffRepository.deleteById(id);

    }


}
