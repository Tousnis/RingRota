package net.computingproject.ringrota.controller;



import net.computingproject.ringrota.dto.ChangePasswordDto;
import net.computingproject.ringrota.services.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountController {



    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //Staff/admin side - (Read)
    //URL /profile
    //Template name = profile
    //dto passed in thymeleaf = myProfile
    @GetMapping("/profile")
    public String displayProfile(Model model, Authentication authentication){

        model.addAttribute("myProfile", accountService.displayMyProfile(authentication.getName()));
        return "profile";
    }

    //Staff/admin side - settings
    //URL /settings
    //Template name = settings
    @GetMapping("/settings")
    public String displaySettings(){
        return "settings";
    }

    //Staff/admin side - (Update save) change password
    //takes the dto from the settings form and saves through the service
    //URL /save-new-password
    //Redirects to URL redirect:/settings
    @PostMapping("/save-new-password")
    public String saveNewPassword(@ModelAttribute ChangePasswordDto dto, Authentication authentication, RedirectAttributes redirectAttributes){

        try {
            accountService.changePassword(authentication.getName(), dto);
            redirectAttributes.addFlashAttribute("passwordChanged", "Password changed.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("passwordError", e.getMessage());
        }
        return "redirect:/settings";
    }
}
