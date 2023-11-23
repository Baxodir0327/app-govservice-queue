package uz.pdp.govqueue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.govqueue.model.Operator;
import uz.pdp.govqueue.payload.OperatorDTO;

@Mapper(componentModel = "spring")
public interface OperatorMapper {


    OperatorDTO toOperatorDTO(Operator operator);
}
