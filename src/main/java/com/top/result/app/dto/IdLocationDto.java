package com.top.result.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URI;

@Data
@AllArgsConstructor
public class IdLocationDto {

    private int id;
    private URI location;
}
