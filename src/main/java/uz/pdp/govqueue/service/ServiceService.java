package uz.pdp.govqueue.service;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import uz.pdp.govqueue.payload.ApiResult;
import uz.pdp.govqueue.payload.LevelDTO;
import uz.pdp.govqueue.payload.GovServiceDTO;

import java.util.List;

public interface ServiceService {

    ApiResult<List<LevelDTO>> getServicesForQueue();

    GovServiceDTO create(GovServiceDTO govServiceDTO);

    ApiResult<?> list(int page, int size, String sort, Sort.Direction sortType, String search);

    HttpEntity<ApiResult<GovServiceDTO>> update(Integer id, GovServiceDTO govServiceDTO);
}
