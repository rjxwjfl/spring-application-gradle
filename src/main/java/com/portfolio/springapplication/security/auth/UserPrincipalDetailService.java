package com.portfolio.springapplication.security.auth;

import com.portfolio.springapplication.entity.User;
import com.portfolio.springapplication.repository.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailService implements UserDetailsService {

    @Autowired
    private AuthRepo authRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepo.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException(username + "has not found.");
        }

        return new UserPrincipalDetail(user);
    }
}
