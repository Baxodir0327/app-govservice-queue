package uz.pdp.govqueue.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.govqueue.payload.ApiResult;
import uz.pdp.govqueue.payload.LevelDTO;
import uz.pdp.govqueue.payload.GovServiceDTO;
import uz.pdp.govqueue.service.ServiceService;

import java.util.List;

@RestController
//@RequiredArgsConstructor
public class GovServiceControllerImpl implements GovServiceController {

    private final ServiceService serviceService;


    @Value("${app.page}")
    private int defaultPage;

    @Value("${app.size}")
    private int defaultSize;

    public GovServiceControllerImpl(ServiceService serviceService) {
        this.serviceService = serviceService;
    }


    @Override
    public HttpEntity<ApiResult<List<LevelDTO>>> forQueue() {
        ApiResult<List<LevelDTO>> servicesForQueue = serviceService.getServicesForQueue();
        return ResponseEntity.ok(servicesForQueue);
    }

    @Override
    public HttpEntity<GovServiceDTO> add(GovServiceDTO govServiceDTO) {
        govServiceDTO = serviceService.create(govServiceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(govServiceDTO);
    }

    @Override
    public HttpEntity<ApiResult<GovServiceDTO>> edit(Integer id, GovServiceDTO govServiceDTO) {
        return serviceService.update(id, govServiceDTO);
    }

    @Override
    public HttpEntity<ApiResult<?>> list(int page, int size, String sort, Sort.Direction sortType, String search) {
        page = page < 0 ? defaultPage : page;
        size = size < 0 ? defaultSize : size;
        ApiResult<?> list = serviceService.list(page, size, sort, sortType, search);
        return ResponseEntity.ok(list);
    }
}
