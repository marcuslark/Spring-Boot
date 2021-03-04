package se.iths.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.springboot.entities.TestUser;

public interface TestUserRepository extends JpaRepository<TestUser, Integer> {

}
