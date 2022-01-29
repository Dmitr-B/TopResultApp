package com.top.result.app.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

@Data
public class CreateUserDto {

    private int userId;
    private int levelId;
    private int result;

    @JsonCreator
    public CreateUserDto(int userId, int levelId, int result) {
        this.userId = userId;
        this.levelId = levelId;
        this.result = result;
    }
}
