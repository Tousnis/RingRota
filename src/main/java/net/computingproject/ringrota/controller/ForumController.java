package net.computingproject.ringrota.controller;


import jakarta.servlet.http.HttpServletRequest;
import net.computingproject.ringrota.dto.MyShiftDto;
import net.computingproject.ringrota.dto.PostDto;
import net.computingproject.ringrota.services.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ForumController {


    private final PostService postService;

    public ForumController(PostService postService) {
        this.postService = postService;
    }

    //One forum for admin and staff: same posts, only the page (sidebar) is different
    @GetMapping("/forum")
    public String displayForum(Model model, HttpServletRequest request, Authentication authentication){

        List<PostDto> posts = postService.displayAllPost(); //everyone sees the same posts
        model.addAttribute("posts", posts);

        //my shifts for the "Need cover for a shift?" dropdown, so it needs who is logged in
        List<MyShiftDto> myShifts = postService.displayMyShifts(authentication.getName());
        model.addAttribute("myShifts", myShifts);

        //return the page (not redirect:), a redirect would lose the model
        if(request.isUserInRole("ADMIN")) {
            return "admin-staff-forum";
        }else {
            return "staff-forum";
        }
    }

    //New post from the form: a chat message, or a cover request if a shift was picked
    @PostMapping("/create-post")
    public String createPost(Authentication authentication,
                             @RequestParam String message,
                             @RequestParam(required = false) Integer shiftId, //"Chat" sends nothing, so it arrives as null
                             RedirectAttributes redirectAttributes){

        postService.createPost(authentication.getName(), message, shiftId);
        redirectAttributes.addFlashAttribute("forumMessage", "Posted!");
        return "redirect:/forum";
    }

    //Pick up a cover post: the shift is given to the logged-in person
    @PostMapping("/pick-up-shift/{postId}")
    public String pickUpShift(@PathVariable Integer postId, Authentication authentication, RedirectAttributes redirectAttributes){

        try {
            postService.pickUpShift(postId, authentication.getName());
            redirectAttributes.addFlashAttribute("forumMessage", "Shift picked up! It is now on your shifts.");
        } catch (IllegalStateException e){
            redirectAttributes.addFlashAttribute("forumError", e.getMessage()); //already covered / your own shift
        }
        return "redirect:/forum";
    }
}
