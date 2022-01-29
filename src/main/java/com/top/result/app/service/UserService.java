package com.top.result.app.service;

import com.top.result.app.dto.CreateUserDto;
import com.top.result.app.dto.IdLocationDto;
import com.top.result.app.dto.UserDto;

import java.net.URISyntaxException;
import java.util.List;

public interface UserService {

    IdLocationDto save(CreateUserDto createUserDto) throws URISyntaxException;

    List<UserDto> getUserInfo(int userId);

    List<UserDto> getLevelInfo(int levelId);
}
