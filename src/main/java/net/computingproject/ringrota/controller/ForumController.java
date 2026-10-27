package net.computingproject.ringrota.controller;


import jakarta.servlet.http.HttpServletRequest;
import net.computingproject.ringrota.dto.AddPostDto;
import net.computingproject.ringrota.dto.ShiftListDto;
import net.computingproject.ringrota.dto.PostDto;
import net.computingproject.ringrota.services.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ForumController {


    private final PostService postService;

    public ForumController(PostService postService) {
        this.postService = postService;
    }

    //Staff/admin side - (Read)
    //URL /forum
    //Template name = admin-staff-forum or staff-forum depends on the role
    //dto passed in thymeleaf = posts and myShifts
    @GetMapping("/forum")
    public String displayForum(Model model, HttpServletRequest request, Authentication authentication){

        List<PostDto> posts = postService.displayAllPost();
        model.addAttribute("posts", posts);

        List<ShiftListDto> myShifts = postService.displayMyShifts(authentication.getName());
        model.addAttribute("myShifts", myShifts);

        if(request.isUserInRole("ADMIN")) {
            return "admin-staff-forum";
        }else {
            return "staff-forum";
        }
    }

    //Staff/admin side - (Create save)
    //takes the dto from the forum form, chat or cover request
    //URL /create-post
    //Redirects to URL redirect:/forum
    @PostMapping("/create-post")
    public String createPost(Authentication authentication, @ModelAttribute AddPostDto dto, RedirectAttributes redirectAttributes){

        postService.createPost(authentication.getName(), dto);
        redirectAttributes.addFlashAttribute("forumMessage", "Posted!");
        return "redirect:/forum";
    }

    //Staff/admin side - (Update) pick up shift
    //URL /pick-up-shift/{postId}
    //Redirects to URL redirect:/forum
    @PostMapping("/pick-up-shift/{postId}")
    public String pickUpShift(@PathVariable Integer postId, Authentication authentication, RedirectAttributes redirectAttributes){

        try {
            postService.pickUpShift(postId, authentication.getName());
            redirectAttributes.addFlashAttribute("forumMessage", "Shift picked up! It is now on your shifts.");
        } catch (IllegalStateException e){
            redirectAttributes.addFlashAttribute("forumError", e.getMessage());
        }
        return "redirect:/forum";
    }
}
