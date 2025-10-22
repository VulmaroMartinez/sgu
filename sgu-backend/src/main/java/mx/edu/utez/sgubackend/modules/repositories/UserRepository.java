package mx.edu.utez.sgubackend.modules.repositories;

import mx.edu.utez.sgubackend.modules.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByName(String name);

    Optional<UserModel> findByEmail(String email);
}
