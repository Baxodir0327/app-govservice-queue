package uz.pdp.govqueue.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperatorDTO {

    private Integer id;

    private String name;

    private boolean status;

    private List<GovServiceDTO> services;
}
