package se.iths.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.iths.springboot.dtos.UserDto;
import se.iths.springboot.services.Service;

import java.util.List;

@RestController()
public class UserController {

    private Service service;
    //Dependency injection here to skip all the @override methods
    @Autowired
    public UserController(Service service) {
        this.service = service;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto user){
        return service.createUser(user);
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
        return service.getOne(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "User with id "+id+" not found."));
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers () {
        return service.getAllUsers();
    }

    @GetMapping("/users/search/{firstName}")
    public List<UserDto> searchFirstName(@PathVariable String firstName){
        return service.findAllByFirstName(firstName);
    }

    @PutMapping("/users/{id}")
    public UserDto replace(@RequestBody UserDto userDto, @PathVariable int id){
        return service.replace(id, userDto);
    }

    @PatchMapping("/users/{id}")
    public UserDto update(@RequestBody UserDto userDto, @PathVariable int id){
        return service.update(id, userDto);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
