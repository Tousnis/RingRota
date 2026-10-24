package net.computingproject.ringrota.repository;

import net.computingproject.ringrota.entities.ShiftEntity;
import net.computingproject.ringrota.entities.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


//interface that manages the operations made to the db
public interface ShiftRepository extends JpaRepository<ShiftEntity, Integer> {



    //Custom methods that Jpa does not contain, they will parse by sprin automatically
    public List<ShiftEntity> findByShiftDate(LocalDate shiftDate);


    public List<ShiftEntity> findByAssignedStaff(StaffEntity staff);
}
