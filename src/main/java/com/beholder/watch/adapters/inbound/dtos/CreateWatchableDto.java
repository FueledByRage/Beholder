package com.beholder.watch.adapters.inbound.dtos;

import com.beholder.watch.dtos.inboud.CreateWatchable;

import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateWatchableDto implements CreateWatchable {
    @NotEmpty(message = "Name may not be empty")
    @NotNull(message = "Name may not be null")
    private String name;

    @NotEmpty(message = "url may not be empty")
    @NotNull(message = "url may not be null")
    private String url;

    @NotNull(message = "checkInterval may not be null")
    @Min(value = 1, message = "checkInterval may be greater than 0")
    private Integer checkInterval;

    private String body;

    private String credentials;

    private String credentialsName;

    private String httpMethod;
}