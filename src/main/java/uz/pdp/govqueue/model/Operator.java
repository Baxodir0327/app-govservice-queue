package uz.pdp.govqueue.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Operator {

    @Id
    private Integer id;

    private String name;

    private boolean status;

    private Integer userId;
}
