package se.iths.springboot.services;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.iths.springboot.dtos.FirstnameDto;
import se.iths.springboot.dtos.LastnameDto;
import se.iths.springboot.entities.SearchCriteria;
import se.iths.springboot.entities.User;
import se.iths.springboot.dtos.UserDto;
import se.iths.springboot.entities.UserSpecification;
import se.iths.springboot.repositories.UserRepository;
import se.iths.springboot.mappers.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements se.iths.springboot.services.Service {

    private final UserMapper userMapper;
    private UserRepository userRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    //SEARCH methods
    @Override
    public List<UserDto> searchByFirstname(String term) {
        UserSpecification us = new UserSpecification(new SearchCriteria("firstName",":",term));

        return userRepository.findAll(Specification.where(us));
    }
    @Override
    public List<UserDto> searchByLastname(String term) {
        UserSpecification us = new UserSpecification(new SearchCriteria("lastName",":",term));

        return userRepository.findAll(Specification.where(us));
    }
    //Read GET methods
    @Override
    public List<UserDto> findAllByFirstName(String firstName) {
        if(userMapper.map(userRepository.findAllByFirstNameContaining(firstName)).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return userMapper.map(userRepository.findAllByFirstNameContaining(firstName));
    }
    @Override
    public List<UserDto> getAllUsers(){
        return userMapper.map(userRepository.findAll());
    }
    @Override
    public Optional<UserDto> getOne(int id) {
        if(userMapper.map(userRepository.findById(id)).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            return userMapper.map(userRepository.findById(id));
    }
    //Create POST methods
    @Override
    public UserDto createUser(UserDto user){
        if(user.getFirstName() == null || user.getLastName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return userMapper.map(userRepository.save(userMapper.map(user)));
    }
    //DELETE method
    @Override
    public void delete(int id) {
        if(!userRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        userRepository.deleteById(id);
    }
    //Update PUT method
    @Override
    public UserDto replace(int id, UserDto userDto) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            User updatedUser = user.get();
            updatedUser.setFirstName(userDto.getFirstName());
            updatedUser.setLastName(userDto.getLastName());
            return userMapper.map(userRepository.save(updatedUser));
        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Id "+id+" not found.");
    }
    //Update PATCH methods
    @Override
    public UserDto updateLastname(int id, LastnameDto userDto) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            User updatedUser = user.get();
            if(userDto.lastName != null)
                updatedUser.setLastName(userDto.lastName);
            return userMapper.map(userRepository.save(updatedUser));
        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Id "+id+" not found.");
    }
    @Override
    public UserDto updateFirstname(int id, FirstnameDto firstnameDto) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            User updatedUser = user.get();
            if(firstnameDto.firstName != null)
                updatedUser.setFirstName(firstnameDto.firstName);
            return userMapper.map(userRepository.save(updatedUser));
        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Id "+id+" not found.");
    }

}
