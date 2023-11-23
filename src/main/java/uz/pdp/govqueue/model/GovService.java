package uz.pdp.govqueue.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import uz.pdp.govqueue.enums.LevelEnum;
import uz.pdp.govqueue.payload.DashboardTempDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GovService {

    @Id
    private Integer id;

    private String name;

    private LevelEnum level;

    private Character firstLetter;

    private boolean status;

//    private List<Integer> test;
//
//    private String service;
//
//    private String date;
//
//    private Integer count;
//
//    private int idx;
}
