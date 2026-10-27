package net.computingproject.ringrota.repository;


import net.computingproject.ringrota.entities.HolidayEntity;
import net.computingproject.ringrota.entities.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HolidayRepository extends JpaRepository<HolidayEntity, Integer> {

    //Custom methods that do not come with Jpa

    //find every holiday request made by one staff member
    public List<HolidayEntity> findByAssignedStaff(StaffEntity staff);
}
