package uz.pdp.govqueue.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import uz.pdp.govqueue.payload.ApiResult;
import uz.pdp.govqueue.payload.LoginDTO;
import uz.pdp.govqueue.service.AuthService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private AuthControllerImpl authController;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authController = new AuthControllerImpl(authService);
    }

    @Test
    void login() {
        LoginDTO loginDTO = new LoginDTO("ketmon", "123");
        when(authService.signIn(any())).thenReturn(new ApiResult<>("tokenjon"));

        HttpEntity<ApiResult<String>> login = authController.login(loginDTO);
        assertThat(login.getBody()).isNotNull();
        assertThat(login.getBody().getData()).isEqualTo("tokenjon");
    }
}