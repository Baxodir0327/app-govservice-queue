package uz.pdp.govqueue.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.pdp.govqueue.enums.RoleEnum;
import uz.pdp.govqueue.model.User;
import uz.pdp.govqueue.payload.ApiResult;
import uz.pdp.govqueue.payload.LoginDTO;
import uz.pdp.govqueue.repository.UserRepository;
import uz.pdp.govqueue.security.JWTProvider;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    private AuthService authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JWTProvider jwtProvider;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(userRepository, passwordEncoder, jwtProvider);
    }

    @Test
    public void testSignInSuccess() {

        LoginDTO loginDTO = new LoginDTO("ketone", "123");

        User user = new User(1, loginDTO.username(), loginDTO.password(), RoleEnum.ADMIN);
        String token = UUID.randomUUID().toString();

        when(userRepository.findByUsername("ketone")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtProvider.generateToken(user)).thenReturn(token);

        ApiResult<String> apiResult = authService.signIn(loginDTO);
        System.out.println(apiResult);
        Assertions.assertThat(apiResult.getData()).isEqualTo(token);


    }


    @Test
    public void testSignFailUsername() {

        LoginDTO loginDTO = new LoginDTO("ketmon", "123");

        when(userRepository.findByUsername("ketmon")).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> authService.signIn(loginDTO))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
