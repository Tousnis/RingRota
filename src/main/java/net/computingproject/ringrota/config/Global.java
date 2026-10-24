package net.computingproject.ringrota.config;

import net.computingproject.ringrota.entities.StaffEntity;
import net.computingproject.ringrota.repository.StaffRepository;
import net.computingproject.ringrota.services.StaffService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class Global {


    private final StaffService staffService;

    public Global(StaffRepository staffRepository, StaffService staffService) {

        this.staffService = staffService;
    }

    @ModelAttribute("loggedName")
    public String loggedName(Authentication authentication){


        if (authentication == null){
            return null;
        }

        return staffService.displayName(authentication.getName());

    }
}
