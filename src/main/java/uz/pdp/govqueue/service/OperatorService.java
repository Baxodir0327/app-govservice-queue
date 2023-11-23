package uz.pdp.govqueue.service;

import uz.pdp.govqueue.payload.ApiResult;
import uz.pdp.govqueue.payload.OperatorAddDTO;
import uz.pdp.govqueue.payload.OperatorDTO;

public interface OperatorService {

    ApiResult<OperatorDTO> create(OperatorAddDTO operatorAddDTO);
}
