package se.iths.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.springboot.db.User;
import se.iths.springboot.db.UserRepository;

import java.util.List;

@RestController
public class Controller {

    //Dependency injection here to skip all the @override methods
    private UserRepository userRepository;

    @Autowired
    public Controller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/getuser")
    public User showUser() {
        return new User(1,"Marcus","Lärk");
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World";
    }

    @GetMapping("/getall")
    public List<User> getAllUsers () {
        return userRepository.findAll();
    }
}
