package com.top.result.app.service.impl;

import com.top.result.app.dto.CreateUserDto;
import com.top.result.app.dto.IdLocationDto;
import com.top.result.app.dto.UserDto;
import com.top.result.app.repository.domain.User;
import com.top.result.app.service.UserService;
import com.top.result.app.service.converter.UserConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserConverter userConverter;
    private final List<User> users = Collections.synchronizedList(new LinkedList<>());
    Comparator<? super User> resultLevelIdComparator = Comparator.comparingInt(User::getResult)
            .thenComparingInt(User::getLevelId).reversed();
    Comparator<? super User> resultUserIdComparator = Comparator.comparingInt(User::getResult)
            .thenComparingInt(User::getId).reversed();

    @PostConstruct
    public void doUsersInit() {
        Random userIdGen = new Random();
        Random levelIdGen = new Random();
        Random resultGen = new Random();

        for (int i = 0; i < 400; i++) {
            users.add(new User(userIdGen.nextInt(10) + 1, levelIdGen.nextInt(10) + 1, resultGen.nextInt(100) + 1));
        }
    }

    @Override
    public IdLocationDto save(CreateUserDto createUserDto){
        User user = userConverter.convert(createUserDto);

        users.add(user);

        try {
            return new IdLocationDto(user.getId(), new URI("/user/" + user.getId()));
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<UserDto> getUserInfo(int userId) {
        System.out.println(users);

        return users.stream()
                .filter(user -> user.getId() == userId)
                .sorted(resultLevelIdComparator)
                .limit(20)
                .map(userConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getLevelInfo(int levelId) {

        return users.stream()
                .filter(user -> user.getLevelId() == levelId)
                .sorted(resultUserIdComparator)
                .limit(20)
                .map(userConverter::convert)
                .collect(Collectors.toList());
    }


}
