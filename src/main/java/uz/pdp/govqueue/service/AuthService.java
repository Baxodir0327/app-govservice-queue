package uz.pdp.govqueue.service;

import uz.pdp.govqueue.payload.ApiResult;
import uz.pdp.govqueue.payload.LoginDTO;

public interface AuthService {
    ApiResult<String> signIn(LoginDTO loginDTO);
}
