package uz.pdp.govqueue.payload;


import jakarta.validation.constraints.NotBlank;

public record LoginDTO(@NotBlank String username,
                       @NotBlank String password) {
}
