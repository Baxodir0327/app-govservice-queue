package uz.pdp.govqueue.payload;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.govqueue.enums.LevelEnum;

import java.sql.Timestamp;

@Data
//@Builder
public class GovServiceDTO {

    private Integer id;

    @NotBlank
    private String name;//KADASTR

    @NotNull
    private LevelEnum level;

    @NotNull
    private Character firstLetter;

    @NotNull
    private Boolean status;

//    private String test;

//    private DashboardTempDTO dashboard;


    public GovServiceDTO(Integer id, String name, LevelEnum level, Character firstLetter, Boolean status) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.firstLetter = firstLetter;
        this.status = status;
    }
}
