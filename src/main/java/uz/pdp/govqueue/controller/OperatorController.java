package uz.pdp.govqueue.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.govqueue.payload.ApiResult;
import uz.pdp.govqueue.payload.OperatorAddDTO;
import uz.pdp.govqueue.payload.OperatorDTO;
import uz.pdp.govqueue.utils.AppConstants;

@RequestMapping(OperatorController.BASE_PATH)
public interface OperatorController {

    String BASE_PATH = AppConstants.BASE_PATH + "/operator";

    @PostMapping
    HttpEntity<ApiResult<OperatorDTO>> add(@Valid @RequestBody OperatorAddDTO operatorAddDTO);
}
