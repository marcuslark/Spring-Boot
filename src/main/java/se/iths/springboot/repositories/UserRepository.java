package se.iths.springboot.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import se.iths.springboot.dtos.UserDto;
import se.iths.springboot.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<UserDto> {
    List<User> findAllByFirstNameContaining(String firstName);
}
