package uz.pdp.govqueue.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OperatorAddDTO {

    @NotBlank
    private String name;

    private boolean status;

    @NotNull
    @NotEmpty
    private List<Integer> services;
}
