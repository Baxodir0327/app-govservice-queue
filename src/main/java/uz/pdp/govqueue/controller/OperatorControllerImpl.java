package uz.pdp.govqueue.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.govqueue.payload.ApiResult;
import uz.pdp.govqueue.payload.OperatorAddDTO;
import uz.pdp.govqueue.payload.OperatorDTO;
import uz.pdp.govqueue.service.OperatorService;

@RestController
@RequiredArgsConstructor
public class OperatorControllerImpl implements OperatorController {

    private final OperatorService operatorService;

    @Override
    public HttpEntity<ApiResult<OperatorDTO>> add(OperatorAddDTO operatorAddDTO) {
        ApiResult<OperatorDTO> result = operatorService.create(operatorAddDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
