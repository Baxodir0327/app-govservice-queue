package uz.pdp.govqueue.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.govqueue.enums.PeriodEnum;
import uz.pdp.govqueue.payload.ApiResult;
import uz.pdp.govqueue.payload.DashboardServiceDTO;
import uz.pdp.govqueue.payload.QueueDTO;
import uz.pdp.govqueue.service.DashboardService;
import uz.pdp.govqueue.utils.AppConstants;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(DashboardController.BASE_PATH)
public class DashboardController {

    public static final String BASE_PATH = AppConstants.BASE_PATH + "/dashboard";

    private final DashboardService dashboardService;

    @GetMapping("/my-by-status")
    public HttpEntity<?> myQueueByStatus(
            @RequestParam(defaultValue = "DAILY") PeriodEnum period,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ApiResult<?> result = dashboardService.myQueueByStatus(period, startDate, endDate);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/my-queue")
    public HttpEntity<List<QueueDTO>> myQueues(int page, int size, LocalDate startDate, LocalDate endDate) {

        return null;
    }

    @GetMapping("/my-by-service")
    public HttpEntity<ApiResult<DashboardServiceDTO>> myQueuesByService(
            @RequestParam(defaultValue = "DAILY") PeriodEnum period,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ApiResult<DashboardServiceDTO> result = dashboardService.myQueuesByService(period, startDate, endDate);
        return ResponseEntity.ok(result);
    }
}
