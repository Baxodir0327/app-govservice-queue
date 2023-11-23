package uz.pdp.govqueue.mapper;

import org.mapstruct.*;
import uz.pdp.govqueue.aop.ToKetmon;
import uz.pdp.govqueue.model.GovService;
import uz.pdp.govqueue.payload.DashboardTempDTO;
import uz.pdp.govqueue.payload.GovServiceDTO;

import java.lang.annotation.Retention;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GovServiceMapper {

    //    @Mapping(target = "name", expression = "java(govServiceDTO.getName().toUpperCase())")
//    @Mapping(target = "test", qualifiedByName = "strToList")
//    @Mapping(target = "service", source = "dashboard.service")
//    @Mapping(target = "service", expression = "java(govServiceDTO.getDashboard().getService())")

    //    @Mapping(target = "test", expression = "java(testOK ? toListIntegerFromString(govServiceDTO.getTest()) : null)")
//    @Mapping(target = ".", source = "dashboard")
    GovService toGovService(GovServiceDTO govServiceDTO);


    //    @Mapping(target = "mixData", expression = "java(govService.getName() + ' ' + govService.getFirstLetter())")
//    @ToKetmon

    //    @Mapping(target = "test", qualifiedByName = "listToStr")
    GovServiceDTO toGovServiceDTO(GovService govService);


    @Named("listToStr")
    default String integerListToString(List<Integer> integers) {
        return integers.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

    @Named("strToList")
    default List<Integer> toListIntegerFromString(String string) {
        return Arrays.stream(string.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    void updateGovService(@MappingTarget GovService govService, GovServiceDTO govServiceDTO);

    // [5,6,8,1,0,9] -> "5,6,8,1,0,9"
    // "5,6,8,1,0,9" -> [5,6,8,1,0,9]
}
