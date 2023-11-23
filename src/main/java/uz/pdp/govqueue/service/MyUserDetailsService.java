package uz.pdp.govqueue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.govqueue.model.Operator;
import uz.pdp.govqueue.repository.OperatorRepository;
import uz.pdp.govqueue.security.MyUserDetails;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final OperatorRepository operatorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Operator operator = operatorRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
        return new MyUserDetails(null);
    }
}
