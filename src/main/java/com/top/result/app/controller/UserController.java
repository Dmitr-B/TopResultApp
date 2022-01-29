package com.top.result.app.controller;

import com.top.result.app.dto.CreateUserDto;
import com.top.result.app.dto.IdLocationDto;
import com.top.result.app.dto.UserDto;
import com.top.result.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    @PutMapping("/setinfo")
    public ResponseEntity<IdLocationDto> create(@RequestBody CreateUserDto createUserDto) throws URISyntaxException {

        IdLocationDto idLocationDto = userService.save(createUserDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(idLocationDto);
    }

    @GetMapping("/userinfo/{user_id}")
    public ResponseEntity<List<UserDto>> getUserInfo(@PathVariable("user_id") int userId) {
        List<UserDto> users = userService.getUserInfo(userId);

        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/levelinfo/{level_id}")
    public ResponseEntity<List<UserDto>> getLevelInfo(@PathVariable("level_id") int levelId) {
        List<UserDto> users = userService.getLevelInfo(levelId);

        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(users);
    }
}
