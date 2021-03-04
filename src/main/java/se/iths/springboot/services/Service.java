package se.iths.springboot.services;

import se.iths.springboot.dtos.UserDto;

import java.util.List;
import java.util.Optional;

public interface Service {
    List<UserDto> findAllByFirstName(String firstName);
    List<UserDto> getAllUsers();

    Optional<UserDto> getOne(int id);

    UserDto createUser(UserDto user);
    void delete(int id);

    UserDto replace(int id, UserDto userDto);
    UserDto update(int id, UserDto userDto);

    List<UserDto> searchByFirst(String term);
}
