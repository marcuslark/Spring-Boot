package se.iths.springboot.db;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

//    List<User> findAllbyFirstName(String firstName);
//    List<User> findAllbyLastName(String lastName);
}
