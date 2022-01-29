package com.top.result.app.service;

import com.top.result.app.dto.CreateUserDto;
import com.top.result.app.dto.IdLocationDto;
import com.top.result.app.dto.UserDto;
import com.top.result.app.repository.domain.User;
import com.top.result.app.service.converter.UserConverter;
import com.top.result.app.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    private static final int TEST_USER_ID = 1;
    private static final int TEST_LEVEL_ID = 1;
    private static final int TEST_RESULT = 1;

    private static final List<User> usersInMemory = new LinkedList<>();
    private static final List<UserDto> expectedUsersInfo = new LinkedList<>();
    private static final List<UserDto> expectedLevelsInfo = new LinkedList<>();

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserConverter userConverter;

    @BeforeAll
    public static void createData() {
        usersInMemory.add(new User(TEST_USER_ID, TEST_LEVEL_ID, TEST_RESULT));
        usersInMemory.add(new User(TEST_USER_ID, 3, 10));
        usersInMemory.add(new User(4, 3, 12));
        usersInMemory.add(new User(TEST_USER_ID, 5, 17));
        usersInMemory.add(new User(4, 2, 8));
        usersInMemory.add(new User(TEST_USER_ID, 2, 4));

        expectedUsersInfo.add(new UserDto(TEST_USER_ID, 5, 17));
        expectedUsersInfo.add(new UserDto(TEST_USER_ID, 3, 10));
        expectedUsersInfo.add(new UserDto(TEST_USER_ID, 2, 4));
        expectedUsersInfo.add(new UserDto(TEST_USER_ID, TEST_LEVEL_ID, TEST_RESULT));

        expectedLevelsInfo.add(new UserDto(4, 3, 12));
        expectedLevelsInfo.add(new UserDto(4, 3, 10));
    }

    @Test
    void saveUser_whenSave_theOk() throws URISyntaxException {
        IdLocationDto expected = new IdLocationDto(TEST_USER_ID, new URI("/user/" + TEST_USER_ID));
        User user = new User(TEST_USER_ID, TEST_LEVEL_ID, TEST_RESULT);

        when(userConverter.convert((CreateUserDto) any())).thenReturn(user);

        IdLocationDto actual = userService.save(new CreateUserDto(TEST_USER_ID, TEST_LEVEL_ID, TEST_RESULT));

        assertEquals(expected, actual);
    }

    @Test
    void getUserInfo_whenExists_theOk() {

        ReflectionTestUtils.setField(userService, "users", usersInMemory);

        when(userConverter.convert(usersInMemory.get(3))).thenReturn(expectedUsersInfo.get(0));
        when(userConverter.convert(usersInMemory.get(1))).thenReturn(expectedUsersInfo.get(1));
        when(userConverter.convert(usersInMemory.get(5))).thenReturn(expectedUsersInfo.get(2));
        when(userConverter.convert(usersInMemory.get(0))).thenReturn(expectedUsersInfo.get(3));

        List<UserDto> actual = userService.getUserInfo(TEST_USER_ID);

        assertEquals(expectedUsersInfo, actual);
    }

    @Test
    void getLevelInfo_whenExists_theOk() {

        ReflectionTestUtils.setField(userService, "users", usersInMemory);

        when(userConverter.convert(usersInMemory.get(2))).thenReturn(expectedLevelsInfo.get(0));
        when(userConverter.convert(usersInMemory.get(1))).thenReturn(expectedLevelsInfo.get(1));

        List<UserDto> actual = userService.getLevelInfo(3);

        assertEquals(expectedLevelsInfo, actual);
    }

}
