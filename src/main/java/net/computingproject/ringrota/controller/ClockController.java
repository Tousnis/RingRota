package net.computingproject.ringrota.controller;


import jakarta.servlet.http.HttpServletRequest;
import net.computingproject.ringrota.dto.ClockRecordDto;
import net.computingproject.ringrota.services.ClockService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ClockController {

    private final ClockService clockService;

    public ClockController(ClockService clockService) {
        this.clockService = clockService;
    }


    //Admin side
    //Display the home hub and all the clock records
    @GetMapping("/admin/staff-clock-management")
    public String displayClockRecords(Model model) {

        List<ClockRecordDto> clockRecords = clockService.displayAllStaffRecords();
        model.addAttribute("clockRecords", clockRecords);
        return "admin-staff-clock-management";

    }

    //Staff face controller

    @GetMapping("/staff-clock-in-out")
    public String displayStaffClock(Model model, Authentication authentication) {

        List<ClockRecordDto> staffClockRecords = clockService.displayAllStaffRecords(authentication.getName());
        model.addAttribute("staffClockRecords", staffClockRecords);
        return "staff-clock-in-out";
    }



    //Staff/admin side clock in
    @PostMapping("/staff-clock-in")
    public String clockIn(Authentication authentication, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            clockService.clockIn(authentication.getName());
            redirectAttributes.addFlashAttribute("clockMessage", "You have clocked in!");

        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("clockError",e.getMessage());
        }
        //if admin go back to the admin page
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/admin/staff-clock-management";
        }
        return "redirect:/staff-clock-in-out";
    }

   //Staff/admin clock out
    @PostMapping("/staff-clock-out")
    public String clockOut(Authentication authentication, RedirectAttributes redirectAttributes, HttpServletRequest request){

        try {
            clockService.clockOut(authentication.getName());
            redirectAttributes.addFlashAttribute("clockMessage","You have clocked out!");

        }catch (IllegalStateException e){
            redirectAttributes.addFlashAttribute("clockError", e.getMessage());
        }
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/admin/staff-clock-management";
        }
        return "redirect:/staff-clock-in-out";
    }


    @PostMapping("/admin/staff-clock-record-approve/{clockId}")
    public String approveRecord(@PathVariable Integer clockId, RedirectAttributes redirectAttributes){
        clockService.approveRecord(clockId);
        redirectAttributes.addFlashAttribute("approveMessage", "Record has been approved");
        return "redirect:/admin/staff-clock-management";
    }

    @PostMapping("/admin/staff-clock-record-reject/{clockId}")
    public String rejectApproved(@PathVariable Integer clockId,RedirectAttributes redirectAttributes){
        clockService.rejectRecord(clockId);
        redirectAttributes.addFlashAttribute("rejectMessage","Record has been rejected. Please investigate");
        return "redirect:/admin/staff-clock-management";
    }

}


