package net.computingproject.ringrota.controller;


import net.computingproject.ringrota.dto.AddHolidayDto;
import net.computingproject.ringrota.services.HolidayService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HolidayController {

    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }


    //Admin side - (Read)
    //URL /admin/staff-holiday-request
    //Template name = admin-staff-holiday-request
    //dto passed in thymeleaf = allHolidays
    @GetMapping("/admin/staff-holiday-request")
    public String displayAdminHoliday(Model model){
        model.addAttribute("allHolidays", holidayService.displayAllHolidays());
        return "admin-staff-holiday-request";
    }

    //Staff side - (Read)
    //URL /staff-holiday-request
    //Template name = staff-holiday-request
    //dto passed in thymeleaf = myHolidays and myShifts for the dropdown
    @GetMapping("/staff-holiday-request")
    public String displayStaffHoliday(Model model, Authentication authentication){
        model.addAttribute("myHolidays", holidayService.displayMyHolidays(authentication.getName()));
        model.addAttribute("myShifts", holidayService.displayMyShifts(authentication.getName()));
        return "staff-holiday-request";
    }

    //Staff side - (Create save)
    //takes the dto from the holiday form and saves through the service
    //URL /staff-holiday-request-save
    //Redirects to URL redirect:/staff-holiday-request
    @PostMapping("/staff-holiday-request-save")
    public String requestHoliday(@ModelAttribute AddHolidayDto dto, Authentication authentication, RedirectAttributes redirectAttributes){

        //To date can't be before From date
        if (dto.getHolidayTo().isBefore(dto.getHolidayFrom())) {
            redirectAttributes.addFlashAttribute("holidayError", "Past dates can't be selected.");
            return "redirect:/staff-holiday-request";
        }

        holidayService.requestHoliday(authentication.getName(), dto);
        redirectAttributes.addFlashAttribute("holidayRequested", "Holiday requested.");
        return "redirect:/staff-holiday-request";
    }

    //Admin side - (Update) approve
    //URL /admin/staff-holiday-approve/{holidayId}
    //shifts in those dates go to the forum for cover
    //Redirects to URL redirect:/admin/staff-holiday-request
    @PostMapping("/admin/staff-holiday-approve/{holidayId}")
    public String approveHoliday(@PathVariable Integer holidayId, RedirectAttributes redirectAttributes){
        holidayService.approveHoliday(holidayId);
        redirectAttributes.addFlashAttribute("holidayApproved", "Holiday approved. Their shifts are on the forum for cover.");
        return "redirect:/admin/staff-holiday-request";
    }

    //Admin side - (Update) reject
    //URL /admin/staff-holiday-reject/{holidayId}
    //Redirects to URL redirect:/admin/staff-holiday-request
    @PostMapping("/admin/staff-holiday-reject/{holidayId}")
    public String rejectHoliday(@PathVariable Integer holidayId, RedirectAttributes redirectAttributes){
        holidayService.rejectHoliday(holidayId);
        redirectAttributes.addFlashAttribute("holidayRejected", "Holiday rejected.");
        return "redirect:/admin/staff-holiday-request";
    }
}
