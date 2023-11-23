package uz.pdp.govqueue.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.govqueue.enums.RoleEnum;
import uz.pdp.govqueue.model.User;
import uz.pdp.govqueue.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.run.times}")
    private String mode;

    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("first")) {
            userRepository.save(new User(
                    "admin@gmail.com",
                    passwordEncoder.encode("root123"),
                    RoleEnum.ADMIN
            ));
        }
    }
}
