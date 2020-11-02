package project.scope.am.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.scope.am.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String username);
//    List<User> findAllByTypeIsLike(Type type);

}
