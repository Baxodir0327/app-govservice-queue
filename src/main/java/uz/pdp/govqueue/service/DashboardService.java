package uz.pdp.govqueue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.govqueue.enums.PeriodEnum;
import uz.pdp.govqueue.exceptions.MyException;
import uz.pdp.govqueue.model.GovService;
import uz.pdp.govqueue.payload.*;
import uz.pdp.govqueue.repository.GovServiceRepository;
import uz.pdp.govqueue.repository.QueueRepository;
import uz.pdp.govqueue.security.MyUserDetails;
import uz.pdp.govqueue.utils.CommonUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final QueueRepository queueRepository;
    private final GovServiceRepository govServiceRepository;

    public ApiResult<?> myQueueByStatus(PeriodEnum period,
                                        LocalDate startDate,
                                        LocalDate endDate) {

        if (startDate == null && period.equals(PeriodEnum.DAILY)) {
            startDate = LocalDate.now();
        } else if (startDate == null) {
            throw new MyException("Started date null bo'lmasin", HttpStatus.BAD_REQUEST);
        }
        if (period.equals(PeriodEnum.CUSTOM) && endDate == null)
            throw new MyException("Oka end date bering", HttpStatus.BAD_REQUEST);

        if (!period.equals(PeriodEnum.CUSTOM))
            endDate = startDate.plus(period.getValue(), period.getUnit());

        MyUserDetails currentUser = CommonUtils.getCurrentUserFromContext();
        Integer operatorId = currentUser.getOperatorId();

        DashboardByStatusDTO dashboardByStatusDTO = queueRepository.getMyQueueForDashboardByStatus(operatorId, startDate, endDate).orElseGet(DashboardByStatusDTO::new);

        dashboardByStatusDTO.setDate(startDate + " / " + endDate);

        return new ApiResult<>(dashboardByStatusDTO);
    }


    public ApiResult<DashboardServiceDTO> myQueuesByService(PeriodEnum period, LocalDate startDate, LocalDate endDate) {
        if (startDate == null && period.equals(PeriodEnum.DAILY)) {
            startDate = LocalDate.now();
        } else if (startDate == null) {
            throw new MyException("Started date null bo'lmasin", HttpStatus.BAD_REQUEST);
        }
        if (period.equals(PeriodEnum.CUSTOM) && endDate == null)
            throw new MyException("Oka end date bering", HttpStatus.BAD_REQUEST);

        if (!period.equals(PeriodEnum.CUSTOM))
            endDate = startDate.plus(period.getValue(), period.getUnit());


        MyUserDetails user = CommonUtils.getCurrentUserFromContext();
        Integer operatorId = user.getOperatorId();

        List<GovService> services = govServiceRepository.getGovServicesByOperatorId(startDate, endDate, operatorId);
        List<DashboardTempDTO> tempDTOList = queueRepository.getMyQueueForDashboardByService(operatorId, startDate, endDate);

        LinkedList<DashboardServiceNameDTO> serviceNameDTOList = mapDashboardServiceNameDTO(services);
        serviceNameDTOList.addFirst(DashboardServiceNameDTO.builder()
                .id("ALL")
                .name("ALL").build());
        DashboardServiceDTO dashboardServiceDTO = DashboardServiceDTO.builder()
                .services(serviceNameDTOList)
                .dates(mapDashboardServiceDateDTOList(tempDTOList))
                .build();

        return new ApiResult<>(dashboardServiceDTO);
    }


    private List<DashboardServiceDateDTO> mapDashboardServiceDateDTOList(List<DashboardTempDTO> tempDTOList) {
        LinkedList<DashboardServiceDateDTO> dashboardServiceDateDTOList = new LinkedList<>();

        DashboardServiceDateDTO firstAll = null;
        Map<String, Integer> firstAllMap = new HashMap<>();
        for (DashboardTempDTO dashboardTempDTO : tempDTOList) {
            if (dashboardTempDTO.getIdx() == 0) {
                firstAllMap.put("ALL", dashboardTempDTO.getCount());
                firstAll = DashboardServiceDateDTO.builder()
                        .date(dashboardTempDTO.getDate())
                        .values(firstAllMap)
                        .build();
            } else if (dashboardTempDTO.getIdx() == 1) {
                firstAllMap.put(dashboardTempDTO.getService(), dashboardTempDTO.getCount());
            } else if (dashboardTempDTO.getIdx() == 2) {

                Map<String, Integer> map = new HashMap<>();
                List<DashboardTempDTO> collect = tempDTOList.stream()
                        .filter(temp -> temp.getIdx() == 3 && temp.getDate().equals(dashboardTempDTO.getService())).collect(Collectors.toList());

                collect.forEach(tempDTO -> map.put(tempDTO.getService(), tempDTO.getCount()));
                dashboardServiceDateDTOList.add(
                        DashboardServiceDateDTO.builder()
                                .date(dashboardTempDTO.getService())
                                .values(map)
                                .build());
            }
        }
        dashboardServiceDateDTOList.addFirst(firstAll);

        tempDTOList.stream().filter(tempDTO -> tempDTO.getIdx() == 2).forEach(tempDTO -> {
            dashboardServiceDateDTOList.stream().filter(dashboardServiceDateDTO ->
                            dashboardServiceDateDTO.getDate().equals(tempDTO.getService()))
                    .findFirst().get().getValues().put("ALL", tempDTO.getCount());
        });


        return dashboardServiceDateDTOList;
    }

    private LinkedList<DashboardServiceNameDTO> mapDashboardServiceNameDTO(List<GovService> govServices) {
        return govServices.stream().map(govService -> DashboardServiceNameDTO.builder()
                        .name(govService.getName())
                        .id(String.valueOf(govService.getId()))
                        .build())
                .collect(Collectors.toCollection(LinkedList::new));
    }

}
