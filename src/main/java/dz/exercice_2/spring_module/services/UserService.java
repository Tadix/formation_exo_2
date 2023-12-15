package dz.exercice_2.spring_module.services;

import dz.exercice_2.spring_module.models.UserEntity;
import dz.exercice_2.spring_module.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  // List<UserEntity> users;

  public UserService(UserRepository userRepository) {
    // users = new ArrayList<UserEntity>();
    this.userRepository = userRepository;
  }

  public UserEntity createUser(UserEntity user) {
    try {
      userRepository.save(user);

      return user;
    } catch (Exception e) {
      throw new UnsupportedOperationException("Error creating user", e);
    }
  }

  public Optional<UserEntity> getUserById(Integer param) {
    Optional<UserEntity> optional = Optional.empty();
    try {
      optional = userRepository.findById(param);
      return optional;
    } catch (Exception e) {
      throw new NoSuchElementException("GET USER BY ID ERROR " + e);
    }
  }

  public List<UserEntity> getAllUser() {
    try {
      return userRepository.findAll();
    } catch (Exception e) {
      throw new NoSuchElementException("GET USER ERROR " + e);
    }
  }

  public Optional<UserEntity> updateUser(Integer id, UserEntity user) {
    Optional<UserEntity> optional = Optional.empty();
    try {
      UserEntity existingUser = userRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with ID: " + id)
        );

      if (user.getUsername() != null) {
        existingUser.setUsername(user.getUsername());
      }
      if (user.getEmail() != null) {
        existingUser.setEmail(user.getEmail());
      }

      return optional = Optional.of(userRepository.save(existingUser));
    } catch (Exception e) {
      throw new UnsupportedOperationException("Error updating user", e);
    }
  }

  public boolean deleteUser(Integer id) {
    try {
      userRepository.deleteById(id);
      return true;
    } catch (Exception e) {
      throw new UnsupportedOperationException("Error delete user", e);
    }
  }
}
