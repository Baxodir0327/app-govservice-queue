package uz.pdp.govqueue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.pdp.govqueue.enums.LevelEnum;
import uz.pdp.govqueue.mapper.GovServiceMapper;
import uz.pdp.govqueue.model.GovService;
import uz.pdp.govqueue.payload.ApiResult;
import uz.pdp.govqueue.payload.DashboardTempDTO;
import uz.pdp.govqueue.payload.GovServiceDTO;
import uz.pdp.govqueue.payload.LevelDTO;
import uz.pdp.govqueue.repository.GovServiceRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final GovServiceRepository govServiceRepository;
    private final GovServiceMapper govServiceMapper;

    @Override
    public ApiResult<List<LevelDTO>> getServicesForQueue() {

        List<GovService> govServices = govServiceRepository.findAllByStatusTrue();

        Map<LevelEnum, LevelDTO> levelDTOMap = new TreeMap<>(Comparator.comparing(LevelEnum::getPriority));

        for (GovService govService : govServices) {
            LevelDTO levelDTO = levelDTOMap.getOrDefault(
                    govService.getLevel(),
                    new LevelDTO(govService.getLevel()));
            levelDTO.getServices().add(mapServiceDTO(govService));
            levelDTOMap.putIfAbsent(levelDTO.getLevel(), levelDTO);
        }

        return new ApiResult<>(new ArrayList<>(levelDTOMap.values()));
    }

    @Override
    public GovServiceDTO create(GovServiceDTO govServiceDTO) {

//        if (govServiceRepository.existsByName(govServiceDTO.getName()))
//            throw new RuntimeException("Service already exists");

//        GovService govService = mapToGovService(govServiceDTO);
        GovService govService = govServiceMapper.toGovService(govServiceDTO);


        govService = govServiceRepository.save(govService);

        return govServiceMapper.toGovServiceDTO(govService);
    }

    @Override
    public HttpEntity<ApiResult<GovServiceDTO>> update(Integer id,
                                                       GovServiceDTO govServiceDTO) {
        GovService govService = govServiceRepository.findById(id).orElseThrow();

        govServiceMapper.updateGovService(govService, govServiceDTO);


        return null;
    }

    @Override
    public ApiResult<?> list(int page, int size, String sort, Sort.Direction sortType, String search) {

//        Sort sortByNimadir = Sort.by(sortType, sort);
//        Pageable pageable = PageRequest.of(page, size, sortByNimadir);
        Pageable pageable = PageRequest.of(page, size, sortType, sort);

//        Page<GovService> govServicePage = govServiceRepository.findAll(pageable);
        Character fl = search.isEmpty() ? ' ' : Character.toUpperCase(search.charAt(0));

        Page<GovService> govServicePage =
                govServiceRepository.findAllByNameContainingIgnoreCaseOrFirstLetter(
                        search,
                        fl,
                        pageable);

        return new ApiResult<>(govServicePage);
    }

    private GovServiceDTO mapServiceDTO(GovService govService) {
        return null;
//        return GovServiceDTO.builder()
//                .id(govService.getId())
//                .firstLetter(govService.getFirstLetter())
//                .level(govService.getLevel())
//                .name(govService.getName())
//                .status(govService.isStatus())
//                .build();
    }
}
