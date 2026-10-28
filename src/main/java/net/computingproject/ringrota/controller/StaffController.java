package net.computingproject.ringrota.controller;


import jakarta.validation.Valid;
import net.computingproject.ringrota.dto.AddStaffDto;
import net.computingproject.ringrota.dto.StaffListDto;

import net.computingproject.ringrota.services.StaffService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class StaffController {

    //Before using Model ask three question am I sending a template with data yes or not, redirect does not create a template it just redirects somewhere else
    // Do I need to transform data if yes, create a service, if it can be called directly use the repository
    // Class Model and @ModelAttribute are not the same thing


    private final StaffService staffService; //created once, shared, injected wherever needed, because they don't hold per-request data — they just do things.


    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }



    // Admin side -
    // Home hub, and display list of staff (Read)
    // URL /admin/staff-manament
    //Template name admin-staff-management
    // dto passed in thymeleaf  = staffList
    @GetMapping("/admin/staff-management")
    public String displayListOfStaff(Model model){

        List<StaffListDto> staffList = staffService.displayStaff();
        model.addAttribute("staffList", staffList);
        return "admin-staff-management";

    }

    //Admin side - (CREATE)
    //URl /admin/create-staff-sign-up
    //Display sign up template to create a staff member, takes one dto object
    //Template name = admin-create-staff-sign-up
    //dto signUpForm linked to thymeleaf
    @GetMapping("/admin/create-staff-sign-up")
    public String staffSignUp(Model model) {

        AddStaffDto staff = new AddStaffDto();
        model.addAttribute("signUpForm", staff); //the empty dto with new

        return "admin-create-staff-sign-up";
    }

    //Admin side - (Create save)
    //Takes the signUpForm dto from the sign-up form and saves through the service
    //has validation annotation that takes from addStaffDto
    // Url /admin/save-staff-sign-up
    //Redirects to URL redirect:/admin/create-staff-sign-up
    @PostMapping("/admin/save-staff-sign-up")
    public String saveStaff(@Valid @ModelAttribute("signUpForm") AddStaffDto dto, BindingResult result, RedirectAttributes redirectAttributes) {

        //Before creating the empty obj implementing the validation
        if (result.hasErrors()){
            return "admin-create-staff-sign-up";
        }

        try {
            staffService.createStaff(dto);
            redirectAttributes.addFlashAttribute("memberCreated", "Staff member created successfully.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "A staff member with this email or phone number already exists.");
        }
        return "redirect:/admin/create-staff-sign-up";
    }



    //Admin side - Find (Read) finds staff by id, staffId, before accessing through the repo and now using the service layer
    //URL /admin/view-staff-profile/{id} will look for the id
    //Template admin-view-staff-profile
    //Hardcoded data from the db
    @GetMapping("/admin/view-staff-profile/{id}")
    public String findStaff(@PathVariable Integer id, Model model) {
        StaffListDto staff = staffService.getStaffForEdit(id);
        model.addAttribute("staff", staff);
        return "admin-view-staff-profile";

    }


    //Admin side find the staff with editable values from the current data state of the db
    @GetMapping("admin/find-staff-for-edit/{id}")
    public String findEditStaff(@PathVariable Integer id, Model model){
        StaffListDto dto = staffService.getStaffForEdit(id); //todo change spring generic message
        model.addAttribute("editStaff", dto);
        return "admin-find-staff-for-edit";
    }



    //Admin side, takes the editStaff dto and saves into the db
    //URl admin/save-staff-edit/{1}
    //Validation from staffListDto
    //redirects to admin/find-staff-for-edit/
    @PostMapping("admin/save-staff-edit/{id}")
    public String updateStaff( @PathVariable  Integer id, @Valid @ModelAttribute("editStaff") StaffListDto dto, BindingResult validation, RedirectAttributes redirectAttributes){
        if (validation.hasErrors()){
            dto.setStaffId(id);
            return "admin-find-staff-for-edit";
        }
        staffService.updateStaffEdit(id,dto);

        redirectAttributes.addFlashAttribute("memberChanged", "Staff member updated successfully.");
        return "redirect:/admin/find-staff-for-edit/" + id;
    }




    //Admin side - (DELETE) deletes a staff member through the service
    //URL /admin/delete-staff/{id}
    //Redirects to URL redirect:/admin/staff-management
    @PostMapping("admin/delete-staff/{id}")
    public String deleteStaff(@PathVariable Integer id, RedirectAttributes redirectAttributes){

        try {
            staffService.deleteStaff(id);
            redirectAttributes.addFlashAttribute("memberDeleted", "Staff member removed.");
        } catch (DataIntegrityViolationException e){
            redirectAttributes.addFlashAttribute( "errorMessage","This staff member can't be deleted because they still have shifts or clock records");
        }


        return "redirect:/admin/staff-management";
    }













    }



