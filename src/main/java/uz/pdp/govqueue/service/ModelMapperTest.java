package uz.pdp.govqueue.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.govqueue.enums.LevelEnum;
import uz.pdp.govqueue.model.GovService;
import uz.pdp.govqueue.payload.GovServiceDTO;

@Service
public class ModelMapperTest {

    public GovService mapWithModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        GovServiceDTO govServiceDTO = new GovServiceDTO(1, "as", LevelEnum.EASY, 's', true);
        GovService govService = modelMapper.map(govServiceDTO, GovService.class);
        System.out.println(govService);
        return govService;
    }
}
