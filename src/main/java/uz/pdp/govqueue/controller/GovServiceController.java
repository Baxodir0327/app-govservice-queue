package uz.pdp.govqueue.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.govqueue.payload.ApiResult;
import uz.pdp.govqueue.payload.LevelDTO;
import uz.pdp.govqueue.payload.GovServiceDTO;
import uz.pdp.govqueue.utils.AppConstants;

import java.util.List;

@RequestMapping(GovServiceController.BASE_PATH)
public interface GovServiceController {

    String BASE_PATH = AppConstants.BASE_PATH + "/service";

    @GetMapping("/for-queue")
    HttpEntity<ApiResult<List<LevelDTO>>> forQueue();

    @PostMapping
    HttpEntity<GovServiceDTO> add(@Valid @RequestBody GovServiceDTO govServiceDTO);

    @PutMapping("/{id}")
    HttpEntity<ApiResult<GovServiceDTO>> edit(@PathVariable Integer id, @RequestBody GovServiceDTO govServiceDTO);

    //TODO pagination, sort, search
    @GetMapping
    HttpEntity<ApiResult<?>> list(@RequestParam(defaultValue = "-1") int page,
                                  @RequestParam(defaultValue = "-1") int size,
                                  @RequestParam(defaultValue = "name") String sort,
                                  @RequestParam(defaultValue = "ASC") Sort.Direction sortType,
                                  @RequestParam(defaultValue = "") String search);
}
