package se.iths.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/getAll")
    public List<Users> showAll() {

    }

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World";
    }
}
