package uz.pdp.govqueue.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.govqueue.payload.*;
import uz.pdp.govqueue.service.QueueService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QueueControllerImpl implements QueueController {

    private final QueueService queueService;


    @Override
    public HttpEntity<ApiResult<List<StatusDTO>>> forBoard() {
        ApiResult<List<StatusDTO>> statusDTOList = queueService.forBoard();
        return ResponseEntity.ok(statusDTOList);
    }

    @Override
    public HttpEntity<ApiResult<QueueForPrintDTO>> add(AddQueueDTO addQueueDTO) {
        ApiResult<QueueForPrintDTO> queueForPrintDTO = queueService.create(addQueueDTO);
        return ResponseEntity.ok(queueForPrintDTO);
    }

    @Override
    public HttpEntity<?> callQueue() {
        ApiResult<QueueDTO> result = queueService.callQueue();
        return ResponseEntity.ok(result);
    }

    @Override
    public HttpEntity<QueueDTO> move(QueueMoveDTO queueMoveDTO) {
        return null;
    }
}
