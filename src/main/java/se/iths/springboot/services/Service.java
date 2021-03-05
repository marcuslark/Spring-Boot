package se.iths.springboot.services;

import se.iths.springboot.dtos.FirstnameDto;
import se.iths.springboot.dtos.LastnameDto;
import se.iths.springboot.dtos.UserDto;

import java.util.List;
import java.util.Optional;

public interface Service {
    UserDto createUser(UserDto user);

    List<UserDto> findAllByFirstName(String firstName);
    List<UserDto> getAllUsers();
    List<UserDto> searchByLastname(String term);
    List<UserDto> searchByFirstname(String term);

    Optional<UserDto> getOne(int id);

    UserDto replace(int id, UserDto userDto);
    UserDto updateFirstname(int id, FirstnameDto firstnameDto);
    UserDto updateLastname(int id, LastnameDto lastnameDto);

    void delete(int id);
}
