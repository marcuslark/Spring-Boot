package se.iths.springboot.controllers;

import se.iths.springboot.dtos.FirstnameDto;
import se.iths.springboot.dtos.LastnameDto;
import se.iths.springboot.dtos.UserDto;
import se.iths.springboot.services.Service;

import java.util.List;
import java.util.Optional;

public class TestService implements Service {

    @Override
    public List<UserDto> searchByFirstname(String term) {
        return null;
    }

    @Override
    public List<UserDto> searchByLastname(String term) {
        return null;
    }
    @Override
    public List<UserDto> findAllByFirstName(String firstName) {
        return null;
    }
    @Override
    public List<UserDto> getAllUsers() {
        return null;
    }
    @Override
    public Optional<UserDto> getOne(int id) {
        if(id==1)
            return Optional.of(new UserDto(1,"Test","Test"));
        return Optional.empty();
    }
    @Override
    public UserDto createUser(UserDto user) {
        return new UserDto(5,"Anna","Ståhlberg");
    }
    @Override
    public void delete(int id) {
    }
    @Override
    public UserDto replace(int id, UserDto userDto) {
        return null;
    }
    @Override
    public UserDto updateFirstname(int id, FirstnameDto firstnameDto) {
        return null;
    }
    @Override
    public UserDto updateLastname(int id, LastnameDto lastnameDto) {
        return null;
    }
}
