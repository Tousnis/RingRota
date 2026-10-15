package net.computingproject.ringrota.controller;


import net.computingproject.ringrota.dto.AddShiftDto;
import net.computingproject.ringrota.dto.AvailableStaffDto;
import net.computingproject.ringrota.dto.ShiftDetailsDto;
import net.computingproject.ringrota.services.AddShiftService;
import net.computingproject.ringrota.services.AvailableStaffService;
import net.computingproject.ringrota.services.ShiftDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ShiftController {


    private final AddShiftService addShiftService;
    private final AvailableStaffService availableStaffService;
    private final ShiftDetailsService shiftDetailsService;

    public ShiftController(AddShiftService addShiftService, AvailableStaffService availableStaffService, ShiftDetailsService shiftDetailsService) {
        this.addShiftService = addShiftService;
        this.availableStaffService = availableStaffService;
        this.shiftDetailsService = shiftDetailsService;
    }


    //home/hub display the data from the db
    // home/hub: reads every shift group from the DB and displays it in the table
    @GetMapping("/admin/rota-management") // CHANGED: was /admin-rota, now matches sidebar
    public String displayRota(Model model) {
        List<ShiftDetailsDto> shiftGroups = shiftDetailsService.displayShiftDetails();
        model.addAttribute("shiftGroups", shiftGroups);
        return "admin-rota-management";
    }


    // 1- admin clicks on day, get show, display list of available staff
    @GetMapping("/admin-rota/day/{date}")
    public String displayDay(@PathVariable LocalDate date, Model model) {

        List<AvailableStaffDto> availableStaff = availableStaffService.displayAvailableStaff(date);
        model.addAttribute("availableStaff", availableStaff);
        model.addAttribute("date", date);
        return "day-detail";
    }





    //Receives the empty dto and send it to the service
    @PostMapping("admin/shift-save")
    public String saveShift(@ModelAttribute AddShiftDto dto){

        addShiftService.createShift(dto);
        return "redirect:/admin/rota-management"; // CHANGED: was redirect:/admin-rota (that URL no longer exists)
    }








    //Receives the page with the empty dto and will send it to the service
    //Method does not send anything to the browser just receives

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //STAFF FACE CONTROLLER


     //display the staff shift (Read)
    @GetMapping("/staff-shift")
    public String displayStaffShift(){
        return "staff-shift";
    }

}
