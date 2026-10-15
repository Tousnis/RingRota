package net.computingproject.ringrota.services;

import net.computingproject.ringrota.entities.StaffEntity;
import net.computingproject.ringrota.repository.StaffRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class LoginService implements UserDetailsService {


    private final StaffRepository staffRepository;

    public LoginService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        StaffEntity staff = staffRepository.findByEmail(username);

        if (staff == null){
            throw new UsernameNotFoundException("No staff member with email:" + username);
        }

        //user builder package that checks who they are, email, password and role
         return User.withUsername(staff.getEmail())
                .password(staff.getPassword())
                .roles(staff.getRole().name())
                .build();
    }
}
