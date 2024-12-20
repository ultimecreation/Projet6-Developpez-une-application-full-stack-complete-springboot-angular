package com.openclassrooms.MddApi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.MddApi.entity.User;
import com.openclassrooms.MddApi.error.UserNotFoundException;
import com.openclassrooms.MddApi.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        return (user == null) ? null : user;
    }

    /**
     * @param id the user id
     * @return returns the user
     */
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    /**
     * @param email takes an email string
     * @return
     */
    public User getByUsernameOrEmail(String username, String email) throws UserNotFoundException {
        return userRepository.findByUsernameOrEmail(username, email);
    }

    /**
     * @param user takes a User object
     * @return the repository response
     */
    public User saveUser(User user) {
        return userRepository.save(user);

    }

}