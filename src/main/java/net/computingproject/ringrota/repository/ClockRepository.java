package net.computingproject.ringrota.repository;


import net.computingproject.ringrota.entities.ClockEntity;
import net.computingproject.ringrota.entities.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClockRepository extends JpaRepository<ClockEntity, Integer> {

    //Custom methods that do not come with Jpa

    public ClockEntity findByAssignedStaffAndClockOutIsNull(StaffEntity staff);

    //find every clock record assigned to staff
    public List<ClockEntity> findByAssignedStaff(StaffEntity staff);
}
