package net.computingproject.ringrota.config;

import net.computingproject.ringrota.entities.StaffEntity;
import net.computingproject.ringrota.repository.StaffRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class Global {

    private final StaffRepository staffRepository;

    public Global(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @ModelAttribute("loggedName")
    public String loggedName(Authentication authentication){


        if (authentication == null){
            return null;
        }

        StaffEntity staff = staffRepository.findByEmail(authentication.getName());
        return staff.getName();
    }
}
