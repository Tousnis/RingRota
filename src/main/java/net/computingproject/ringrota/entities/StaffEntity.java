package net.computingproject.ringrota.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


//layout table for staff sign up form

@Setter
@Getter
@Entity
public class StaffEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //Generates the id number automatically
    @Id
    private int staffId;

    @Column(nullable = false)  // unique emails, nullable = false cannot be empty
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumRole role;

    @Column
    private int totalHours;

    public StaffEntity() {

    }

//setter and getters=====================================================================================================

}
