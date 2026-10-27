package net.computingproject.ringrota.services;

import net.computingproject.ringrota.dto.ChangePasswordDto;
import net.computingproject.ringrota.dto.StaffListDto;
import net.computingproject.ringrota.entities.StaffEntity;
import net.computingproject.ringrota.repository.StaffRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AccountService {

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(StaffRepository staffRepository, PasswordEncoder passwordEncoder) {
        this.staffRepository = staffRepository;
        this.passwordEncoder = passwordEncoder;
    }


    //method to display my profile
    public StaffListDto displayMyProfile(String email){

        StaffEntity staff = staffRepository.findByEmail(email);

        StaffListDto dto = new StaffListDto();
        dto.setStaffId(staff.getStaffId());
        dto.setName(staff.getName());
        dto.setLastName(staff.getLastName());
        dto.setEmail(staff.getEmail());
        dto.setPhoneNumber(staff.getPhoneNumber());
        dto.setRole(staff.getRole());
        dto.setTotalHours(staff.getTotalHours());

        return dto;
    }


    //method to change password
    public void changePassword(String email, ChangePasswordDto dto){

        StaffEntity staff = staffRepository.findByEmail(email);

        if (!passwordEncoder.matches(dto.getCurrentPassword(), staff.getPassword())) {
            throw new IllegalStateException("Current password is wrong.");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalStateException("New passwords don't match.");
        }

        staff.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        staffRepository.save(staff);
    }

}
