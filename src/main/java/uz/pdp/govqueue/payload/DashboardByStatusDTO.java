package uz.pdp.govqueue.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardByStatusDTO {

    private Integer cancelledCount;

    private Integer completedCount;

    private Integer allCount;

    private String date;
}
