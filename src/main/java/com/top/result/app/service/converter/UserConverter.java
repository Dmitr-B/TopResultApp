package com.top.result.app.service.converter;

import com.top.result.app.dto.CreateUserDto;
import com.top.result.app.dto.UserDto;
import com.top.result.app.repository.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDto convert(User user) {
        return new UserDto(user.getId(), user.getLevelId(), user.getResult());
    }

    public User convert(CreateUserDto createUserDto) {
        return new User(createUserDto.getUserId(), createUserDto.getLevelId(), createUserDto.getResult());
    }
}
