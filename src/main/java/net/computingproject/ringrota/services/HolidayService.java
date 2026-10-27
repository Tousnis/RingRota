package net.computingproject.ringrota.services;

import net.computingproject.ringrota.dto.AddHolidayDto;
import net.computingproject.ringrota.dto.HolidayDto;
import net.computingproject.ringrota.dto.ShiftListDto;
import net.computingproject.ringrota.entities.HolidayEntity;
import net.computingproject.ringrota.entities.PostEntity;
import net.computingproject.ringrota.entities.ShiftEntity;
import net.computingproject.ringrota.entities.StaffEntity;
import net.computingproject.ringrota.repository.HolidayRepository;
import net.computingproject.ringrota.repository.PostRepository;
import net.computingproject.ringrota.repository.ShiftRepository;
import net.computingproject.ringrota.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class HolidayService {

    private final HolidayRepository holidayRepository;
    private final StaffRepository staffRepository;
    private final ShiftRepository shiftRepository;
    private final PostRepository postRepository;

    public HolidayService(HolidayRepository holidayRepository, StaffRepository staffRepository,
                          ShiftRepository shiftRepository, PostRepository postRepository) {
        this.holidayRepository = holidayRepository;
        this.staffRepository = staffRepository;
        this.shiftRepository = shiftRepository;
        this.postRepository = postRepository;
    }


    //STAFF SIDE==========================================================================

    //method to display my holidays
    public List<HolidayDto> displayMyHolidays(String email){

        StaffEntity staff = staffRepository.findByEmail(email);
        List<HolidayEntity> listOfHolidays = holidayRepository.findByAssignedStaff(staff);
        List<HolidayDto> holidayList = new ArrayList<>();

        for (HolidayEntity holiday : listOfHolidays){
            HolidayDto dto = new HolidayDto();
            dto.setHolidayId(holiday.getHolidayId());
            dto.setHolidayFrom(holiday.getHolidayFrom());
            dto.setHolidayTo(holiday.getHolidayTo());
            dto.setStatus(holiday.getStatus());

            holidayList.add(dto);
        }
        return holidayList;
    }

    //method to display my shifts for the dropdowns
    public List<ShiftListDto> displayMyShifts(String email){

        StaffEntity staff = staffRepository.findByEmail(email);
        List<ShiftEntity> listOfShifts = shiftRepository.findByAssignedStaff(staff);
        List<ShiftListDto> shiftList = new ArrayList<>();

        for (ShiftEntity shifts : listOfShifts){
            ShiftListDto dto = new ShiftListDto();
            dto.setShiftId(shifts.getShiftId());
            dto.setShiftDate(shifts.getShiftDate());
            dto.setShiftStarts(shifts.getShiftStarts());
            dto.setShiftEnds(shifts.getShiftEnds());

            shiftList.add(dto);
        }
        return shiftList;
    }

    //method to request holiday
    public void requestHoliday(String email, AddHolidayDto dto){

        StaffEntity staff = staffRepository.findByEmail(email);

        HolidayEntity holiday = new HolidayEntity();
        holiday.setHolidayFrom(dto.getHolidayFrom());
        holiday.setHolidayTo(dto.getHolidayTo());
        holiday.setStatus("PENDING");
        holiday.setAssignedStaff(staff);

        holidayRepository.save(holiday);
    }


    //ADMIN SIDE==========================================================================

    //method to display all the holidays
    public List<HolidayDto> displayAllHolidays(){

        List<HolidayEntity> listOfHolidays = holidayRepository.findAll();
        List<HolidayDto> holidayList = new ArrayList<>();

        for (HolidayEntity holiday : listOfHolidays){
            HolidayDto dto = new HolidayDto();
            dto.setHolidayId(holiday.getHolidayId());
            dto.setStaffName(holiday.getAssignedStaff().getName());
            dto.setHolidayFrom(holiday.getHolidayFrom());
            dto.setHolidayTo(holiday.getHolidayTo());
            dto.setStatus(holiday.getStatus());

            holidayList.add(dto);
        }
        return holidayList;
    }

    //method to approve the holiday and put the shifts on the forum for cover
    public void approveHoliday(Integer holidayId){

        HolidayEntity holiday = holidayRepository.findById(holidayId).orElseThrow();
        holiday.setStatus("APPROVED");
        holidayRepository.save(holiday);

        List<ShiftEntity> shiftsToCover = shiftRepository.findByAssignedStaffAndShiftDateBetween(
                holiday.getAssignedStaff(), holiday.getHolidayFrom(), holiday.getHolidayTo());

        for (ShiftEntity shift : shiftsToCover){
            PostEntity post = new PostEntity();
            post.setStaff(holiday.getAssignedStaff());
            post.setMessage(holiday.getAssignedStaff().getName() + " is on holiday, can anyone cover this shift?");
            post.setPostedAt(LocalDateTime.now());
            post.setShift(shift);
            post.setStatus("OPEN");

            postRepository.save(post);
        }
    }

    //method to reject the holiday
    public void rejectHoliday(Integer holidayId){
        HolidayEntity holiday = holidayRepository.findById(holidayId).orElseThrow();
        holiday.setStatus("REJECTED");
        holidayRepository.save(holiday);
    }
}
