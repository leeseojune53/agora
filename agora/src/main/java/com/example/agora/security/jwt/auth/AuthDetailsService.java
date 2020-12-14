package com.example.agora.security.jwt.auth;

import com.example.agora.entity.user.UserRepository;
import com.example.agora.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public AuthDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findById(Integer.parseInt(id))
                .map(AuthDetails::new)
                .orElseThrow(UserNotFoundException::new);
    }
}
