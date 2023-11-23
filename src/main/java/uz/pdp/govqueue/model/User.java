package uz.pdp.govqueue.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import uz.pdp.govqueue.enums.RoleEnum;

@Table("users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    private Integer id;

    private String username;

    private String password;

    private RoleEnum role;

    public User(String username, String password, RoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
