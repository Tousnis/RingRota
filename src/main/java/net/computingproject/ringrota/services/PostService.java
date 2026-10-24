package net.computingproject.ringrota.services;


import net.computingproject.ringrota.dto.MyShiftDto;
import net.computingproject.ringrota.dto.PostDto;
import net.computingproject.ringrota.entities.PostEntity;
import net.computingproject.ringrota.entities.ShiftEntity;
import net.computingproject.ringrota.entities.StaffEntity;
import net.computingproject.ringrota.repository.PostRepository;
import net.computingproject.ringrota.repository.ShiftRepository;
import net.computingproject.ringrota.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ShiftRepository shiftRepository;
    private  final StaffRepository staffRepository;


    public PostService(PostRepository postRepository, ShiftRepository shiftRepository, StaffRepository staffRepository) {
        this.postRepository = postRepository;
        this.shiftRepository = shiftRepository;
        this.staffRepository = staffRepository;
    }

    //Display every post for the forum page, newest first
    public List<PostDto> displayAllPost(){

      List<PostEntity> posts = postRepository.findAllByOrderByPostedAtDesc();
      List<PostDto> postList = new ArrayList<>(); //empty list to fill with dtos

      for (PostEntity post : posts){
          PostDto dto = new PostDto();
          dto.setPostId(post.getPostId());
          dto.setStaffName(post.getStaff().getName());
          dto.setPostedAt(post.getPostedAt());
          dto.setMessage(post.getMessage());

          dto.setStatus(post.getStatus()); //empty for a chat post

          //a chat post has no shift, so only read the shift if there is one (otherwise NullPointerException)
          if (post.getShift() != null){
              dto.setShiftDate(post.getShift().getShiftDate());
              dto.setShiftStarts(post.getShift().getShiftStarts());
              dto.setShiftEnds(post.getShift().getShiftEnds());
              dto.setCoveredBy(post.getShift().getAssignedStaff().getName()); //whoever has the shift now (shown only when CLAIMED)
          }

          postList.add(dto);
      }
      return postList;
    }


    public void createPost(String email, String message, Integer shiftId){
        StaffEntity staff = staffRepository.findByEmail(email);

        PostEntity post = new PostEntity();
        post.setStaff(staff);
        post.setMessage(message);
        post.setPostedAt(LocalDateTime.now()); //time comes from the server, like clock in

        //"Chat" in the dropdown sends nothing, so shiftId is null for a chat post
        if (shiftId != null){
            ShiftEntity shift = shiftRepository.findById(shiftId).orElseThrow();
            post.setShift(shift);
            post.setStatus("OPEN"); //waiting for someone to pick it up
        }

        postRepository.save(post);
    }

    //Pick up a cover post: the same shift row is given to the person who picked it up (one update)
    public void pickUpShift(Integer postId, String email){
        StaffEntity picker = staffRepository.findByEmail(email);
        PostEntity post = postRepository.findById(postId).orElseThrow();

        if (!"OPEN".equals(post.getStatus())){
            throw new IllegalStateException("This shift has already been covered.");
        }
        if (post.getStaff().getEmail().equals(email)){
            throw new IllegalStateException("You can't pick up your own shift.");
        }

        ShiftEntity shift = post.getShift();
        shift.setAssignedStaff(picker);   //same shift, new staff member
        shiftRepository.save(shift);      //has an id, so it updates

        post.setStatus("CLAIMED");        //hides the Pick up button
        postRepository.save(post);
    }

    //The logged-in staff member's shifts, for the "Need cover for a shift?" dropdown
    public List<MyShiftDto> displayMyShifts(String email){
        StaffEntity staff = staffRepository.findByEmail(email);
        List<ShiftEntity> shifts = shiftRepository.findByAssignedStaff(staff);
        List<MyShiftDto> myShifts = new ArrayList<>();

        for (ShiftEntity shift : shifts){
            MyShiftDto dto = new MyShiftDto();
            dto.setShiftId(shift.getShiftId());
            dto.setShiftDate(shift.getShiftDate());
            dto.setShiftStarts(shift.getShiftStarts());
            dto.setShiftEnds(shift.getShiftEnds());
            myShifts.add(dto);
        }
        return myShifts;
    }

}
