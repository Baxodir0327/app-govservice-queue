package uz.pdp.govqueue.payload;

import lombok.Data;

@Data
public class DashboardTempDTO {

    private String date;

    private String service;

    private Integer count;

    private int idx;
}
