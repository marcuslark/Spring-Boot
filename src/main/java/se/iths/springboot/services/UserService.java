package se.iths.springboot.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.iths.springboot.entities.User;
import se.iths.springboot.dtos.UserDto;
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

    @Override
    public List<UserDto> findAllByFirstName(String firstName) {
        if(userMapper.mapp(userRepository.findAllByFirstName(firstName)).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return userMapper.mapp(userRepository.findAllByFirstName(firstName));
    }

    @Override
    public List<UserDto> getAllUsers(){
        return userMapper.mapp(userRepository.findAll());
    }

    @Override
    public Optional<UserDto> getOne(int id) {
        return userMapper.mapp(userRepository.findById(id));
    }

    @Override
    public UserDto createUser(UserDto user){
        if(user.getFirstName().isEmpty()) {
            throw new RuntimeException();
        }
        return userMapper.mapp(userRepository.save(userMapper.mapp(user)));
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto replace(int id, UserDto userDto) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            User updatedUser = user.get();
            updatedUser.setFirstName(userDto.getFirstName());
            updatedUser.setLastName(userDto.getLastName());
            return userMapper.mapp(userRepository.save(updatedUser));
        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Id "+id+" not found.");
    }

    @Override
    public UserDto update(int id, UserDto userDto) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            User updatedUser = user.get();
            if(userDto.getFirstName() != null)
                updatedUser.setFirstName(userDto.getFirstName());
            if(userDto.getLastName() != null)
                updatedUser.setLastName(userDto.getLastName());
            return userMapper.mapp(userRepository.save(updatedUser));
        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Id "+id+" not found.");
    }
}
