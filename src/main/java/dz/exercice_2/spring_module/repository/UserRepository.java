package dz.exercice_2.spring_module.repository;

import dz.exercice_2.spring_module.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
  UserEntity findByUsername(String username);
}
