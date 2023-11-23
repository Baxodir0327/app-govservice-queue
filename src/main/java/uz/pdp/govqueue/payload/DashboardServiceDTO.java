package uz.pdp.govqueue.payload;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.govqueue.model.GovService;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardServiceDTO {

    private List<DashboardServiceDateDTO> dates;

    private List<DashboardServiceNameDTO> services;
}
