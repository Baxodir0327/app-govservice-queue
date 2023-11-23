package uz.pdp.govqueue.repository;

import org.springframework.data.repository.CrudRepository;
import uz.pdp.govqueue.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findFirstByUsernameStartingWithOrderByUsernameDesc(String username);

    Optional<User> findByUsername(String username);
}
