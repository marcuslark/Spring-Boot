package se.iths.springboot.mappers;

import org.springframework.stereotype.Component;
import se.iths.springboot.db.User;
import se.iths.springboot.db.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserMapper() {
    }

    public List<UserDto> mapp(List<User> all) {
        return all
                .stream()
                .map(this::mapp)
                .collect(Collectors.toList());
//  In modern Java we use this stream method instead^

//        List<UserDto> userDtoList = new ArrayList<>();
//        for (var user: all) {
//            userDtoList.add(mapp(user));
//        }
//        return userDtoList;
    }

    public UserDto mapp(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName());
    }

    public User mapp(UserDto userDto) {
        return new User(userDto.getId(), userDto.getFirstName(), userDto.getLastName());
    }

    public Optional<UserDto> mapp(Optional<User> optionalUser) {
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(mapp(optionalUser.get()));
    }
}