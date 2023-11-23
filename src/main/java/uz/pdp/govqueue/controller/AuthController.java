package uz.pdp.govqueue.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.govqueue.payload.ApiResult;
import uz.pdp.govqueue.payload.LoginDTO;
import uz.pdp.govqueue.utils.AppConstants;

@RequestMapping(AuthController.BASE_PATH)
public interface AuthController {

    String BASE_PATH = AppConstants.BASE_PATH + "/auth";
    String LOGIN_PATH = "/login";

    @PostMapping(LOGIN_PATH)
    HttpEntity<ApiResult<String>> login(@Valid @RequestBody LoginDTO loginDTO);
}
