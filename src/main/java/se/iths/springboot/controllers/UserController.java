package se.iths.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.iths.springboot.db.User;
import se.iths.springboot.db.UserDto;
import se.iths.springboot.services.UserService;

import java.util.List;

@RestController()
public class UserController {

    private UserService userService;
    //Dependency injection here to skip all the @override methods
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1.Use Optional here to return either optional of User or return one User.
    // 2.Changed from Optional to ResponseEntity to be able to send a 404 error.
    // 3. To skip ResponseEntity, throw new ResponseStatusException instead.
    // 4. use result.orElseThrow to clean code, instead of ->
    // if(result.isPresent())
    //            return result.get();
    //        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User with id "+id+" not found.");
    @GetMapping("/users/{id}")
    public UserDto one(@PathVariable int id) {
        return userService.getOne(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "User with id "+id+" not found."));
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable int id){
        userService.delete(id);
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers () {
        return userService.getAllUsers();
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto user){
       return userService.createUser(user);
    }

    @PutMapping("/users/{id}")
    public UserDto replace(@RequestBody UserDto userDto, @PathVariable int id){
        return userService.replace(id, userDto);
    }

    @PatchMapping("/users/{id}")
    public UserDto update(@RequestBody UserDto userDto, @PathVariable int id){
        return userService.update(id, userDto);
    }
}
