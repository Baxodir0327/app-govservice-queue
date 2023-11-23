package uz.pdp.govqueue.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.govqueue.exceptions.MyException;
import uz.pdp.govqueue.model.User;
import uz.pdp.govqueue.payload.ApiResult;
import uz.pdp.govqueue.payload.LoginDTO;
import uz.pdp.govqueue.repository.UserRepository;
import uz.pdp.govqueue.security.JWTProvider;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;

    @Override
    public ApiResult<String> signIn(LoginDTO loginDTO) {

        User user = userRepository.findByUsername(loginDTO.username()).orElseThrow(() -> new UsernameNotFoundException("User not found: " + loginDTO.username()));

        if (!passwordEncoder.matches(loginDTO.password(), user.getPassword()))
            throw new BadCredentialsException("Bad credential");

        String token = jwtProvider.generateToken(user);

        return new ApiResult<>(token);
    }


}
