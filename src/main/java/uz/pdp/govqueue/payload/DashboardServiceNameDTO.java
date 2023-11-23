package uz.pdp.govqueue.payload;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import java.util.Map;

@Data
@Builder
public class DashboardServiceNameDTO {

    private String id;

    private String name;
}
