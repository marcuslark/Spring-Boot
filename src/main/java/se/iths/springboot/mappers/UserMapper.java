package se.iths.springboot.mappers;

import org.springframework.stereotype.Component;
import se.iths.springboot.entities.User;
import se.iths.springboot.dtos.UserDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Mappers to be able to be able to validate however you want.
@Component
public class UserMapper {
    public UserMapper() {
    }

    public List<UserDto> map(List<User> all) {
        return all
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public UserDto map(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName());
    }

    public User map(UserDto userDto) {
        return new User(userDto.getId(), userDto.getFirstName(), userDto.getLastName());
    }

    public Optional<UserDto> map(Optional<User> optionalUser) {
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(map(optionalUser.get()));
    }
}