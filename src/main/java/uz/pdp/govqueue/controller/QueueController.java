package uz.pdp.govqueue.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.govqueue.payload.*;
import uz.pdp.govqueue.utils.AppConstants;

import java.util.List;

@RequestMapping(QueueController.BASE_PATH)
public interface QueueController {

    String BASE_PATH = AppConstants.BASE_PATH + "/queue";
    String ADD_PATH = "/add";
    String OPERATOR_CALL_PATH = "/call";
    String MOVE_PATH = "/move";
    String FOR_BOARD_PATH = "/board";


    @GetMapping(FOR_BOARD_PATH)
    HttpEntity<ApiResult<List<StatusDTO>>> forBoard();


    /**
     * {@link QueueForPrintDTO}
     *
     * @param addQueueDTO the {@link  AddQueueDTO}'s element type
     * @return a {@link HttpEntity}<{@link QueueForPrintDTO}> containing the elements of the given {@code Collection}
     * @throws RuntimeException if coll is bala battar
     * @implNote agar bal battar ....
     */
    @PostMapping(ADD_PATH)
    HttpEntity<ApiResult<QueueForPrintDTO>> add(@RequestBody @Valid AddQueueDTO addQueueDTO);

    @PatchMapping(value = OPERATOR_CALL_PATH)
    HttpEntity<?> callQueue();


    @PatchMapping(MOVE_PATH)
    HttpEntity<QueueDTO> move(@Valid @RequestBody QueueMoveDTO queueMoveDTO);
}
