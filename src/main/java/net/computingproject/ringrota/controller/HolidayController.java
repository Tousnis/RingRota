package net.computingproject.ringrota.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HolidayController {


    //Admin side: display every holiday request (url matches the admin sidebar link)
    @GetMapping("/admin/staff-holiday-request")
    public String displayAdminHoliday(){
        return "admin-staff-holiday-request";
    }

    //Staff side: request holiday and see my requests (url matches the staff sidebar link)
    @GetMapping("/staff-holiday-request")
    public String displayStaffHoliday(){
        return "staff-holiday-request";
    }
}
