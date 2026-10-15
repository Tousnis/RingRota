package net.computingproject.ringrota.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    //Public side
    //URL /
    //Home page, before login
    //Template name = index
    @GetMapping("/")
    public String home() {

        return "index";
    }

    @GetMapping("/about-us")
    public String aboutUs() {
        return "about-us";
    }

    @GetMapping("/contact-us")
    public String contactUs() {
        return "contact-us";
    }


    @GetMapping("/home")
    public String homeRedirect(HttpServletRequest request) {
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/admin/staff-management";
        } else {
            return "redirect:/staff-shift";
        }
    }
    //display logging form spring gets the post so no need
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
