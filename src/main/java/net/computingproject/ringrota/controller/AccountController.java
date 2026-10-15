package net.computingproject.ringrota.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {


    //My own profile, for admin and staff (no /admin/ in the url, so any logged-in user)
    @GetMapping("/profile")
    public String displayProfile(){
        return "profile";
    }

    //Change my own password, for admin and staff
    @GetMapping("/settings")
    public String displaySettings(){
        return "settings";
    }
}
