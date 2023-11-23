package uz.pdp.govqueue.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.govqueue.payload.ApiResult;
import uz.pdp.govqueue.payload.LoginDTO;
import uz.pdp.govqueue.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public HttpEntity<ApiResult<String>> login(LoginDTO loginDTO) {
        ApiResult<String> result = authService.signIn(loginDTO);
        return ResponseEntity.ok(result);
    }
}
