package net.computingproject.ringrota.controller;

import net.computingproject.ringrota.dto.AddShiftDto;
import net.computingproject.ringrota.dto.AvailableStaffDto;
import net.computingproject.ringrota.dto.ShiftListDto;
import net.computingproject.ringrota.services.ShiftService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ShiftController {


   private final ShiftService shiftService;


    public ShiftController(ShiftService shiftService) {

        this.shiftService = shiftService;
    }


    //home/hub display the data from the db
    // home/hub: reads every shift group from the DB and displays it in the table
    @GetMapping("/admin/rota-management")
    public String displayAllShifts(Model model) {

        List<ShiftListDto> allShifts = shiftService.displayAllShifts();
        model.addAttribute("allShifts", allShifts);
        return "admin-rota-management";
    }


    // 1- admin clicks on day, get show, display list of available staff
    @GetMapping("/admin/rota-add-shift-day/{date}")
    public String displayDay(@PathVariable LocalDate date, Model model) {

        List<AvailableStaffDto> availableStaff = shiftService.displayAvailableStaff(date);
        model.addAttribute("availableStaff", availableStaff);
        model.addAttribute("onShift", shiftService.displayStaffOnShift(date));
        model.addAttribute("date", date);
        return "day-detail";
    }


    //method to create staff shifts in a range
    @GetMapping("/admin/rota-add-shifts-range")
    public String displayAddShiftsRange(Model model) {
        List<AvailableStaffDto> allStaff = shiftService.displayAllStaff();
        model.addAttribute("allStaff", allStaff);
        return "admin-add-shift";
    }






    //Receives the empty dto and send it to the service for saving
    @PostMapping("/admin/shift-save")
    public String saveShift(@ModelAttribute AddShiftDto dto, RedirectAttributes redirectAttributes){

        //end time has to be after start time
        if (!dto.getShiftEnds().isAfter(dto.getShiftStarts())) {
            redirectAttributes.addFlashAttribute("shiftError", "End time must be after start time.");
            return "redirect:/admin/rota-management";
        }

        //end date can't be before start date
        if (dto.getShiftDateEnds().isBefore(dto.getShiftDateStarts())) {
            redirectAttributes.addFlashAttribute("shiftError", "End date can't be before start date.");
            return "redirect:/admin/rota-management";
        }

        shiftService.createShift(dto);
        redirectAttributes.addFlashAttribute("shiftAdded", "staff has been added to shift.");
        return "redirect:/admin/rota-management";
    }


    @PostMapping("/admin/delete-shift/{id}")
    public String deleteShifts(@PathVariable int id, RedirectAttributes redirectAttributes){

        try {
            shiftService.deleteShift(id);
            redirectAttributes.addFlashAttribute("shiftDeleted", "Shift has been removed.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("shiftError", "This shift has a cover request on the forum, so it can't be deleted.");
        }

        return "redirect:/admin/rota-management";
    }


    //Admin side - (Read) edit shift
    //URL /admin/find-shift-for-edit/{id}
    //Template name = admin-shift-edit
    //dto passed in thymeleaf = editShift
    @GetMapping("/admin/find-shift-for-edit/{id}")
    public String findShiftForEdit(@PathVariable Integer id, Model model){

        model.addAttribute("editShift", shiftService.findShiftForEdit(id));
        return "admin-shift-edit";
    }


    //Admin side - (Update save)
    //takes the editShift dto and saves the new date and times
    //URL /admin/save-shift-edit/{id}
    //Redirects to URL redirect:/admin/rota-management
    @PostMapping("/admin/save-shift-edit/{id}")
    public String saveShiftEdit(@PathVariable Integer id, @ModelAttribute ShiftListDto editShift, RedirectAttributes redirectAttributes){

        //end time has to be after start time
        if (!editShift.getShiftEnds().isAfter(editShift.getShiftStarts())) {
            redirectAttributes.addFlashAttribute("shiftError", "End time must be after start time.");
            return "redirect:/admin/find-shift-for-edit/" + id;
        }

        shiftService.saveShiftEdit(id, editShift);
        redirectAttributes.addFlashAttribute("shiftEdited", "Shift updated.");
        return "redirect:/admin/rota-management";
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //STAFF SIDE CONTROLLER


     //display the staff shift (Read)
    @GetMapping("/staff-shift")
    public String displayStaffShift(Model model, Authentication authentication){
        List<ShiftListDto> staffShifts = shiftService.displayMyShifts(authentication.getName());
        model.addAttribute("staffShifts", staffShifts);


        return "staff-shift";
    }

    //Staff side - (Read)
    //staff clicks a day on the calendar and sees who is on shift
    //URL /staff-shift-day/{date}
    //Template name = staff-day-detail
    //dto passed in thymeleaf = onShift
    @GetMapping("/staff-shift-day/{date}")
    public String displayStaffDay(@PathVariable LocalDate date, Model model){
        model.addAttribute("onShift", shiftService.displayStaffOnShift(date));
        model.addAttribute("date", date);
        return "staff-day-detail";
    }

}
