package net.computingproject.ringrota.repository;


import net.computingproject.ringrota.entities.PostEntity;
import net.computingproject.ringrota.entities.ShiftEntity;
import net.computingproject.ringrota.entities.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {

    //Custom method
    public List<PostEntity> findAllByOrderByPostedAtDesc();

}

