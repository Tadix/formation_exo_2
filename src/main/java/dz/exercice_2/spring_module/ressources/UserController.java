package dz.exercice_2.spring_module.ressources;

import com.fasterxml.jackson.annotation.JsonFormat;
import dz.exercice_2.spring_module.models.UserEntity;
import dz.exercice_2.spring_module.services.UserService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Autowired
  UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public UserEntity getUserById(@RequestParam Integer param) {
    try {
      return (UserEntity) userService
        .getUserById(param)
        .orElseThrow(() ->
          new NoSuchElementException("User not found with id: " + param)
        );
    } catch (Exception e) {
      throw new RuntimeException("Error processing the request", e);
    }
  }

  @DeleteMapping("/delete/{id}")
  public boolean deleteUser(@PathVariable Integer id) {
    try {
      return userService.deleteUser(id);
    } catch (UnsupportedOperationException e) {
      throw new UnsupportedOperationException(
        "Error processing the request",
        e
      );
    } catch (Exception e) {
      throw new RuntimeException("Error processing the request", e);
    }
  }

  @GetMapping
  @JsonFormat(shape = JsonFormat.Shape.OBJECT)
  public List<UserEntity> getMethodName() {
    return userService.getAllUser();
  }

  @PutMapping("/edit/{id}")
  public UserEntity putMethodName(
    @PathVariable Integer id,
    @RequestBody UserEntity entity
  ) {
    try {
      return (UserEntity) userService.updateUser(id, entity).orElseThrow();
    } catch (Exception e) {
      throw new ResponseStatusException(
        HttpStatus.INTERNAL_SERVER_ERROR,
        e.getMessage(),
        null
      );
    }
  }

  @PostMapping
  public UserEntity postMethodName(@RequestBody UserEntity entity) {
    try {
      return userService.createUser(entity);
    } catch (UnsupportedOperationException e) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "Invalid user creation request",
        e
      );
    } catch (Exception e) {
      throw new ResponseStatusException(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Internal server error",
        e
      );
    }
  }
}
