package net.computingproject.ringrota.controller;


import net.computingproject.ringrota.dto.AddShiftDto;
import net.computingproject.ringrota.dto.AvailableStaffDto;
import net.computingproject.ringrota.services.ShiftService;
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


   private final ShiftService shiftService;


    public ShiftController(ShiftService shiftService) {

        this.shiftService = shiftService;
    }


    //home/hub display the data from the db
    // home/hub: reads every shift group from the DB and displays it in the table
    @GetMapping("/admin/rota-management")
    public String displayRota(Model model) {
        return "admin-rota-management";
    }


    // 1- admin clicks on day, get show, display list of available staff
    @GetMapping("/admin/rota-day/{date}")
    public String displayDay(@PathVariable LocalDate date, Model model) {

        List<AvailableStaffDto> availableStaff = shiftService.displayAvailableStaff(date);
        model.addAttribute("availableStaff", availableStaff);
        model.addAttribute("date", date);
        return "day-detail";
    }





    //Receives the empty dto and send it to the service
    @PostMapping("admin/shift-save")
    public String saveShift(@ModelAttribute AddShiftDto dto){

        shiftService.createShift(dto);
        return "redirect:/admin/rota-management";
    }








    //Receives the page with the empty dto and will send it to the service
    //Method does not send anything to the browser just receives

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //STAFF SIDE CONTROLLER


     //display the staff shift (Read)
    @GetMapping("/staff-shift")
    public String displayStaffShift(){
        return "staff-shift";
    }

}
