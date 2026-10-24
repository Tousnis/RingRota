package net.computingproject.ringrota.repository;

import net.computingproject.ringrota.entities.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//interface that manages the operations made to the db
public interface StaffRepository extends JpaRepository<StaffEntity, Integer> {

   public StaffEntity findByEmail(String email);


}
