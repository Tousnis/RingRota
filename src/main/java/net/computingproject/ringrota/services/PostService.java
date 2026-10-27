package net.computingproject.ringrota.services;


import net.computingproject.ringrota.dto.AddPostDto;
import net.computingproject.ringrota.dto.ShiftListDto;
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

    //method to display all the posts
    public List<PostDto> displayAllPost(){

      List<PostEntity> posts = postRepository.findAllByOrderByPostedAtDesc();
      List<PostDto> postList = new ArrayList<>();

      for (PostEntity post : posts){
          PostDto dto = new PostDto();
          dto.setPostId(post.getPostId());
          dto.setStaffName(post.getStaff().getName());
          dto.setPostedAt(post.getPostedAt());
          dto.setMessage(post.getMessage());

          dto.setStatus(post.getStatus());

          if (post.getShift() != null){
              dto.setShiftDate(post.getShift().getShiftDate());
              dto.setShiftStarts(post.getShift().getShiftStarts());
              dto.setShiftEnds(post.getShift().getShiftEnds());
              dto.setCoveredBy(post.getShift().getAssignedStaff().getName());
          }

          postList.add(dto);
      }
      return postList;
    }


    //method to create a post
    public void createPost(String email, AddPostDto dto){
        StaffEntity staff = staffRepository.findByEmail(email);

        PostEntity post = new PostEntity();
        post.setStaff(staff);
        post.setMessage(dto.getMessage());
        post.setPostedAt(LocalDateTime.now());

        if (dto.getShiftId() != null){
            ShiftEntity shift = shiftRepository.findById(dto.getShiftId()).orElseThrow();
            post.setShift(shift);
            post.setStatus("OPEN");
        }

        postRepository.save(post);
    }

    //method to pick up a shift
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
        shift.setAssignedStaff(picker);
        shiftRepository.save(shift);

        post.setStatus("CLAIMED");
        postRepository.save(post);
    }

    //method to display my shifts for the dropdown
    public List<ShiftListDto> displayMyShifts(String email){
        StaffEntity staff = staffRepository.findByEmail(email);
        List<ShiftEntity> shifts = shiftRepository.findByAssignedStaff(staff);
        List<ShiftListDto> myShifts = new ArrayList<>();

        for (ShiftEntity shift : shifts){
            ShiftListDto dto = new ShiftListDto();
            dto.setShiftId(shift.getShiftId());
            dto.setShiftDate(shift.getShiftDate());
            dto.setShiftStarts(shift.getShiftStarts());
            dto.setShiftEnds(shift.getShiftEnds());
            myShifts.add(dto);
        }
        return myShifts;
    }

}
